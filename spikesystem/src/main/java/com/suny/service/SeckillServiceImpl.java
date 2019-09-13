package com.suny.service;

import com.suny.dao.SeckillMapper;
import com.suny.dao.SuccesskilledMapper;
import com.suny.dao.cache.RedisDao;
import com.suny.dto.*;
import com.suny.entity.Seckill;
import com.suny.entity.SuccessKilled;
import com.suny.enumerate.SeckillStatEnum;
import com.suny.service.interfaces.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final  String salt = "thisIsAsAltValue";

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private SuccesskilledMapper successkilledMapper;

    /**
     * 查询单条秒杀记录
     */
    @Override
    public Seckill getById(long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    /**
     * 查询全部秒杀记录
     * @return 返回秒杀记录列表
     */
    @Override
    public List<Seckill> getSeckillList() {
        return seckillMapper.queryAll(0,4);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {

        //先从redis中获取秒杀商品信息，如果没有缓存再连接数据库，减少并发压力
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(null == seckill){
            seckill = seckillMapper.queryById(seckillId);
            redisDao.putSeckill(seckill);
            if(null == seckill){
                logger.warn("查询的id不存在");
                return new Exposer(false,seckillId);
            }
        }

        LocalDateTime startTime = seckill.getStartTime();
        LocalDateTime endTime = seckill.getEndTime();
        LocalDateTime nowTime = LocalDateTime.now();

        if (nowTime.isAfter(startTime) && nowTime.isBefore(endTime)){
          //秒杀开启，返回秒杀商品信息
          String md5 = getMd5(seckillId);
          return new Exposer(true,md5,seckillId);
        };
        return new Exposer(false,seckillId,nowTime,startTime,endTime);
    }

    //md5加密方法
    private String getMd5(long seckillId){
        String base = seckillId +  "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    };


    /**
     * 执行秒杀操作，失败的返回异常
     * @param seckillId 秒杀商品id
     * @param userPhone 用户手机号
     * @param md5 md5加密值
     * @return 根据秒杀结果返回实体状态信息
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    @Transactional
    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMd5(seckillId))){
            logger.error("秒杀数据被篡改");
            throw new SeckillException("seckill data rewrite");
        }

        LocalDateTime nowTime = LocalDateTime.now();

        try{
            //执行减少库存操作
            int reduceNumber = seckillMapper.reduceNumber(seckillId,nowTime);
            if (reduceNumber <= 0 ){
                logger.warn("没有更新数据库记录，说明秒杀结束");
                throw new SeckillCloseException("seckill is closed");
            }else{
                //此时已经秒杀成功
                int insertCount = successkilledMapper.insertSuccessKilled(seckillId,userPhone);
                if (insertCount <= 0){
                    throw new RepeatKillException("seckill repeat");
                }else{
                    //秒杀成功，返回插入成功的信息
                    SuccessKilled successKilled = successkilledMapper.queryByIdWithSeckill(seckillId,userPhone);

                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }

        }catch (SeckillCloseException | RepeatKillException e1){
            throw e1;
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    /**
     * 执行秒杀存储过程操作，失败的返回异常
     * @param seckillId 秒杀商品id
     * @param userPhone 用户手机号
     * @param md5 md5加密值
     * @return 根据秒杀结果返回实体状态信息
     */
    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5){
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
        }
        LocalDateTime killTime = LocalDateTime.now();
        Map<String, Object> map = new HashMap<>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        // 执行储存过程，result被复制
        try {
            seckillMapper.executeSeckillProcedure(map);
            // 获取result
            int result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SuccessKilled successKilled = successkilledMapper.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
            } else {
                return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
        }
    }
}

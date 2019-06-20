package com.suny.interfaces;

import com.suny.dto.*;
import com.suny.entity.Seckill;
import sun.security.provider.MD5;

import java.util.List;

public interface SeckillService {
    /**
     * 根据id查询单条秒杀记录
     * @param seckillId 秒杀商品id
     * @rerurn 返回秒杀记录信息
     */
    Seckill getById(long seckillId);

    /**
     * 查询全部秒杀记录
     * @return 秒杀记录列表
     */
    List<Seckill>  getSeckillList();

    /**
     * 秒杀开启时返回秒杀接口地址，否则返回商品信息和秒杀时间
     * @param seckillId 秒杀商品id
     * @return 返回秒杀状态实体
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作失败时返回异常
     * @param seckillId 秒杀商品id
     * @param userPhone 用户手机号
     * @param md5 md5加密值
     * @return 根据不同结果返回不同的状态信息
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException;
}

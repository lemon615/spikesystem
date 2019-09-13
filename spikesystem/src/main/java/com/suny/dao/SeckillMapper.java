package com.suny.dao;

import com.suny.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SeckillMapper {
    /**
     * 根据传过来的id，减少商品的库存
     * @param seckillId 秒杀商品Id
     * @param killTime 秒杀时间
     * @return 秒杀成功返回1 失败则返回0
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime")LocalDateTime killTime);

    /**
     *根据商品id，查询商品详情
     * @param seckillId 秒杀商品Id
     * @return 对应商品id的数据
     */
    Seckill queryById(@Param("seckillId") long seckillId);

    /**
     * 根据一个偏移量来查询商品列表
     * @param offset 偏移量
     * @param limit 限制查询出来的个数
     * @return 符合偏移量查询出来的商品个数
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 执行秒杀的存储过程
     * @param paramMap 存储过程参数
     */
    void executeSeckillProcedure(Map<String,Object> paramMap);

}

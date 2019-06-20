package com.suny.dao;

import com.suny.entity.Seckill;
import com.suny.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuccesskilledMapper {
    /**
     * 插入一条详细的购买信息
     * @param seckillId 商品id
     * @param userPhone 用户手机
     * @return 成功为1，失败为0
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据商品Id查询秒杀成功的商品信息
     * @param seckillId 商品id
     * @param userPhone 购买用户的手机号码
     * @return seckill 商品详情
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}

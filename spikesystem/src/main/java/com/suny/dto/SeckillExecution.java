package com.suny.dto;

import com.suny.entity.Seckill;
import com.suny.entity.SuccessKilled;
import com.suny.enumerate.SeckillStatEnum;

public class SeckillExecution {
    //秒杀商品id
    private long seckillId;
    //执行秒杀结果的状态
    private int state;
    //秒杀状态详细信息
    private String stateInfo;
    //秒杀成功商品信息
    private SuccessKilled successkilled;

    //秒杀成功返回地实体
    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled){
        this.seckillId = seckillId;
        this.state= statEnum.getState();
        this.stateInfo = statEnum.getInfo();
        this.successkilled = successKilled;
    };

    //秒杀失败返回实体
    public SeckillExecution(long seckillId, SeckillStatEnum statEnum){
        this.seckillId = seckillId;
        this.state= statEnum.getState();
        this.stateInfo = statEnum.getInfo();
    };

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateinfo() {
        return stateInfo;
    }

    public void setStateinfo(String stateinfo) {
        this.stateInfo = stateinfo;
    }

    public SuccessKilled getSuccesskilled() {
        return successkilled;
    }

    public void setSuccesskilled(SuccessKilled successkilled) {
        this.successkilled = successkilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "秒杀的商品ID=" + seckillId +
                ", 秒杀状态=" + state +
                ", 秒杀状态信息='" + stateInfo + '\'' +
                ", 秒杀的商品=" + successkilled +
                '}';
    }
}

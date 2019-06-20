package com.suny.entity;

import javax.swing.plaf.PanelUI;
import java.io.Serializable;
import java.security.PublicKey;
import java.time.LocalDateTime;

public class SuccessKilled implements Serializable {
    private static final long serialVersionUID = 1834437127882846202L;

    //秒杀状态信息
    private long seckillId;
    private long userPhone;
    private short state;
    private LocalDateTime createTime;

    private Seckill seckill;

    public SuccessKilled(){

    }

    public SuccessKilled(long seckillId,long userPhone,short state,LocalDateTime createTime){
        this.seckillId = seckillId;
        this.createTime = createTime;
        this.state = state;
        this.userPhone = userPhone;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "主键ID=" + seckillId +
                ", 手机号码=" + userPhone +
                ", 秒杀状态=" + state +
                ", 创建时间=" + createTime +
                ", 秒杀的商品=" + seckill +
                '}';
    }
}

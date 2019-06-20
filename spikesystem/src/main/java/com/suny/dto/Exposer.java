package com.suny.dto;

import java.time.LocalDateTime;

public class Exposer {
    //是否开启秒杀
    private boolean exposed;
    //对秒杀进行加密措施
    private String md5;
    //秒杀商品id
    private long seckillId;
    //系统当前时间
    private LocalDateTime now;
    //秒杀开始时间
    private LocalDateTime start;
    //秒杀结束时间
    private LocalDateTime end;

    public Exposer(){};

    public Exposer(boolean exposed,String md5,long seckillId){
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    };

    public Exposer(boolean exposed,long seckillId,LocalDateTime now,LocalDateTime start,LocalDateTime end){
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    };

    public Exposer(boolean exposed,long seckillId){
        this.exposed = exposed;
        this.seckillId = seckillId;
    };

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "秒杀状态=" + exposed +
                ", md5加密值='" + md5 + '\'' +
                ", 秒杀ID=" + seckillId +
                ", 当前时间=" + now +
                ", 开始时间=" + start +
                ", 结束=" + end +
                '}';
    }
}

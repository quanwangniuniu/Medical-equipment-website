package com.example.llt.utils;


public class SnowflakeIdUtils {
    //因为二进制里第一个 bit 为如果是 1，那么都是负数，但是我们生成的 id 都是正数，所以第一个 bit 统一都是 0。
    //代表一毫秒内生成的多个id的最新序号 2进制12位 2^12 - 1 = 4095个
    private long sequence;
    //机器ID 2进制5位 2^5 - 1 = 31个
    private long workerId;
    //机房ID 2进制5位 2^5 - 1 = 31个
    private long datacenterId;
    //设置一个时间初始值 2进制41位 2^41 - 1差不多可以用69年
    //2021-01-01 => 1609430400000
    private long epoch = 1609430400000L;
    //12位的最新序号
    private long sequenceBits = 12L;
    //5位的机器id
    private long workerIdBits = 5L;
    //5位的机房id
    private long datacenterIdBits = 5L;

    //这个是二进制运算，就是5 bit机器id最大值31
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    //这个是一个意思，就是5 bit机房id最大值31
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampShift = sequenceBits + workerIdBits + datacenterIdBits;

    //记录产生时间毫秒数，判断是否是同1毫秒
    private long lastTimestamp = -1L;

    public SnowflakeIdUtils(long datacenterId, long workerId, long sequence) {
        //检查机房id和机器id 不能超过31 不能小于0
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
        this.sequence = sequence;
    }

    //生成一个全局唯一的id
    public synchronized long nextId() {
        //获取当前时间戳，单位毫秒
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            System.err.printf(
                    "clock is moving backwards. Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }
        //同一个毫秒内生成多个id，sequence序号递增1，最大是4095
        if (lastTimestamp == timestamp) {
            //这个位运算避免sequence超过4095
            sequence = (sequence + 1) & sequenceMask;
            //当某一毫秒产生的id数超过4095，系统会进入等待，直到下一毫秒继续产生id
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        //记录一下最近一次生成id的时间戳，单位毫秒
        lastTimestamp = timestamp;
        //二进制位运算操作，生成一个64bit的id
        return ((timestamp - epoch) << timestampShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) | sequence;
    }

    //当某一毫秒产生的id数超过4095，系统会进入等待，直到下一毫秒继续产生id
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    //获取当前时间戳
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowflakeIdUtils worker = new SnowflakeIdUtils(1, 1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(worker.nextId());
        }
    }
}


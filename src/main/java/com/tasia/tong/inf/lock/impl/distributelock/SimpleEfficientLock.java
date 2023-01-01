package com.tasia.tong.inf.lock.impl.distributelock;

import com.tasia.tong.inf.lock.ILock;
import com.tasia.tong.inf.lock.exception.LockAcquireFailedException;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleEfficientLock implements ILock {

    // TODO: Object 引入分布式锁
    @Autowired(required = false)
    private Object redisClient;

    @Override
    public boolean hasLock(String key) {
        return false;
    }

    @Override
    public void acquire(String key, Integer expireSeconds) {
        // TODO:
    }

    @Override
    public void acquire(String key, Integer timeout, Integer expireSeconds)
            throws LockAcquireFailedException {

    }

    @Override
    public void release(String key) throws LockAcquireFailedException {

    }
}

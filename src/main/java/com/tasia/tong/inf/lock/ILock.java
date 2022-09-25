package com.tasia.tong.inf.lock;

import com.tasia.tong.inf.lock.exception.LockAcquireFailedException;

public interface ILock {

    /**
     * 判断是否有锁
     * @param key
     * @return
     */
    boolean hasLock(String key);

    /**
     * 尝试获取锁, 默认超时
     * @param key
     * @param expireSeconds
     * @throws LockAcquireFailedException
     */
    void acquire(String key, Integer expireSeconds) throws LockAcquireFailedException;


    /**
     * 尝试获取锁, 设置超时时间
     * @param key
     * @param timeout
     * @param expireSeconds 所的有效期
     * @throws LockAcquireFailedException
     */
    void acquire(String key, Integer timeout, Integer expireSeconds) throws LockAcquireFailedException;


    /**
     * 释放锁
     * @param key
     * @throws LockAcquireFailedException
     */
    void release(String key) throws LockAcquireFailedException;
}

package com.tasia.tong.inf.lock;

public interface ILockAndRetryableExcutable {

    void doWithLockAndRetry(ILockable lockable, Runnable runnable);
}

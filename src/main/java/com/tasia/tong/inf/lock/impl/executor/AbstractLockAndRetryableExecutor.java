package com.tasia.tong.inf.lock.impl.executor;

import com.tasia.tong.inf.lock.ILockAndRetryableExcutable;
import com.tasia.tong.inf.lock.ILockable;
import com.tasia.tong.inf.lock.impl.distributelock.SimpleEfficientLock;
import com.tasia.tong.inf.lock.exception.LockAcquireFailedException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract
public class AbstractLockAndRetryableExecutor implements ILockAndRetryableExcutable {

    /**
     * 分布式锁重试次数
     */
    private final static int MAX_RETRY_COUNT = 3;

    /**
     * 获取分布式锁默认超时时间, ms
     */
    private final static int DEFAULT_TIMEOUT = 200;

    /**
     * 分布式锁失效时间, s
     */
    private final static int DEFAULT_EXPIRE_SECOND = 2;

    @Resource
    private SimpleEfficientLock simpleEfficientLock;

    @Override
    public void doWithLockAndRetry(ILockable lockable, Runnable runnable) {

        String lockKey = lockable.getLockKey();
        for (int i = 0; i < getMaxRetryCount(); ++ i) {
            boolean acquired = false;
            try {
                simpleEfficientLock.acquire(lockKey, getTimeOut());
                acquired = true;
                runnable.run();
                return;
            } catch (LockAcquireFailedException e) {
                log.info("[模块 {}] {} 在获取分布式锁时发生锁冲突, 即将进行重试, 锁对象:{}, e:{}",
                        this.getClass().getSimpleName(), lockable, e);
            } catch (Exception e) {
                log.info("[模块 {}] 在获取分布式锁时发生为预期异常, 锁对象:{}, e:{}",
                        this.getClass().getSimpleName(), lockable, e);
                throw e;
            } finally {
                simpleEfficientLock.release(lockKey);
            }
        }
        throw new LockAcquireFailedException("锁对象 " + lockable + " 获取锁次数超过最大次数, 依赖外部重试恢复");
    }

    protected int getTimeOut() {
        return DEFAULT_TIMEOUT;
    }

    protected int getExpireTime() {
        return DEFAULT_EXPIRE_SECOND;
    }

    protected int getMaxRetryCount() {
        return MAX_RETRY_COUNT;
    }
}


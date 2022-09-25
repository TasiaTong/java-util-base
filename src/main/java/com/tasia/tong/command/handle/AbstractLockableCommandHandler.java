package com.tasia.tong.command.handle;

import com.tasia.tong.command.command.BaseCommand;
import com.tasia.tong.inf.lock.SimpleEfficientLock;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
abstract
public class AbstractLockableCommandHandler implements ICommandHandle{

    private final static Integer DEFAULT_LOCK_EXPIRE = 10 * 1000;

    @Resource
    private SimpleEfficientLock lock;

    @Override
    public void handle(BaseCommand command) {
        preHandle(command);
        String lockKey = getLockKey(command);
        try {
            if (StringUtils.isNotBlank(lockKey) && Objects.nonNull(getLockExpire())) {
                lock.acquire(lockKey, getLockExpire());
            }
            log.info("[模块 指令执行器] 指令: {} 进入实际的业务逻辑执行阶段", command);
            innerHandle(command);
        } finally {
            if (StringUtils.isNotBlank(lockKey) && Objects.nonNull(getLockExpire())) {
                lock.release(lockKey);
            }
        }
        postHandle(command);
    }

    protected abstract void postHandle(BaseCommand command);

    protected abstract void innerHandle(BaseCommand command);

    protected Integer getLockExpire() {
        return DEFAULT_LOCK_EXPIRE;
    }

    protected abstract String getLockKey(BaseCommand command);

    protected abstract void preHandle(BaseCommand command);
}

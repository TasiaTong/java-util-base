package com.tasia.tong.command.command;


import com.tasia.tong.command.context.BaseCommandHandleContext;
import com.tasia.tong.command.exception.CommandNotSupportHandleException;
import com.tasia.tong.command.handle.ICommandHandle;
import com.tasia.tong.domain.event.BaseDomainEvent;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;

@Data
@Slf4j
abstract
public class BaseCommand {

    private BaseCommandHandleContext context;

    /**
     * 指令校验
     */
    public abstract void validate();

    /**
     * 指令处理
     */
    public void handle() {
        log.info("[模块 指令执行器] 指令: {} 即将执行, 环境信息: {}", this, context);
        ICommandHandle handler = getHandler();
        if (handler == null) {
            throw new CommandNotSupportHandleException("指令: " + this + " 不支持执行");
        }

        getHandler().handle(this);
    }

    public void publish(List<BaseDomainEvent> willPublishEvents) {
        for (BaseDomainEvent event : ListUtils.emptyIfNull(willPublishEvents)) {
            event.publish();
        }
    }

    /**
     * 构造事件
     *
     * @return
     */
    public abstract List<BaseDomainEvent> constructEvents();

    /**
     * 获取指令处理器
     *
     * @return
     */
    protected abstract ICommandHandle getHandler();

}

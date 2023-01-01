package com.tasia.tong.inf.mq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tasia.tong.basetype.enums.IBaseEnum;
import com.tasia.tong.utils.JsonUtils;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;

@Slf4j
abstract
public class AbstractMessageListener<T> {

    private static final ThreadLocal<String> messageContext = new ThreadLocal<>();

    @Getter
    private final Class<T> messageClass;

    public AbstractMessageListener(Class<T> messageType) {
        this.messageClass = (Class<T>) GenericTypeResolver
                .resolveTypeArgument(getClass(), AbstractMessageListener.class);
    }


    /**
     * TODO: 确定消费状态(成功0, 失败-1, 重试1)
     *
     * @return
     */
    protected ConsumeStatus onRecvMessage(String message) {
        // TODO：确定context内容
        messageContext.set(null);

        log.info("[模块 MQ] 接收到消息: {}", message);
        try {
            T msg;
            if (Objects.nonNull(getMessageClass())) {
                msg = JsonUtils.parseObject(message, getMessageClass());
            } else {
                msg = JsonUtils.parseSimpleObject(message, this.messageClass);
            }

            return handle(msg) ? ConsumeStatus.CONSUME_SUCCESS : ConsumeStatus.CONSUME_LATER;
        } catch (Exception e) {
            log.error("[模块 MQ] 消费消息 {}, 发生非预期异常", message, e);
        }
        return ConsumeStatus.CONSUME_SUCCESS;
    }

    /**
     *
     * @return
     */
    protected String getMessageContext() {
        return messageContext.get();
    }

    /**
     * 消息处理逻辑
     *
     * @param msg
     * @return
     */
    protected abstract boolean handle(T msg);

    protected TypeReference<T> getMessageClass() {
        return null;
    }

    @AllArgsConstructor
    static enum ConsumeStatus implements IBaseEnum<Integer, String> {
        /**
         *
         */
        CONSUME_SUCCESS(0, "success"),
        CONSUME_FAILED(1, "failed"),
        CONSUME_LATER(2, "later")
        ;

        @Getter
        private final Integer code;
        @Getter
        private final String desc;
    }
}

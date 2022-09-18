package com.tasia.tong.exceptions.domain;

import com.tasia.tong.exceptions.api.UnexpectedApiException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DomainExceptionFactory {

    public <T extends BaseDomainException> BaseDomainException wrapException(
            @NotNull Class<T> myExceptionClass, @NotNull Exception rawException,
            @NotNull String detailMessage) {
        try {
            Constructor<T> constructor = myExceptionClass
                    .getConstructor(String.class, Throwable.class);
            return constructor.newInstance(detailMessage, rawException);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new UnexpectedApiException(detailMessage);
        }
    }

    public <T extends BaseDomainException> BaseDomainException warpException(
            @NotNull Class<T> myExceptionClass, @NotNull String detailMessage,
            @NotNull String bizMessage) {
        try {
            Constructor<T> constructor = myExceptionClass
                    .getConstructor(String.class, String.class);
            return constructor.newInstance(detailMessage, bizMessage);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new UnexpectedApiException(detailMessage);
        }
    }

    public <T extends BaseDomainException> BaseDomainException wrapException(
            @NotNull Class<T> myExceptionClass, @NotNull Exception rawException,
            @NotNull String detailMessage, @NotNull String bizMessage) {
        try {
            Constructor<T> constructor = myExceptionClass
                    .getConstructor(String.class, String.class, Throwable.class);
            return constructor.newInstance(detailMessage, bizMessage, rawException);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new UnexpectedApiException(detailMessage);
        }
    }
}

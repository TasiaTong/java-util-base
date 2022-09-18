package com.tasia.tong.exceptions.factory;

import com.tasia.tong.exceptions.api.BaseApiException;
import com.tasia.tong.exceptions.api.UnexpectedApiException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import javax.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class ApiExceptionFactory {

    public <T extends BaseApiException> BaseApiException wrapException(Class<?> myExceptionClass,
            @NotNull Exception rawException, String detailMessage, Object... args) {
        String message = StringUtils.isBlank(detailMessage) ? StringUtils.EMPTY
                : MessageFormat.format(detailMessage, args);

        try {
            Constructor<T> constructor = (Constructor<T>) myExceptionClass
                    .getConstructor(String.class, Throwable.class);
            return constructor.newInstance(message, rawException);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return new UnexpectedApiException(rawException);
        }
    }

    public <T extends BaseApiException> BaseApiException createException(
            @NotNull Class<T> myExceptionClass,
            String detailMessage, Object... args) {
        String message = StringUtils.isBlank(detailMessage) ? StringUtils.EMPTY
                : MessageFormat.format(detailMessage, args);

        try {
            Constructor<T> constructor = myExceptionClass.getConstructor(String.class);
            return constructor.newInstance(message);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new UnexpectedApiException(e);
        }
    }
}

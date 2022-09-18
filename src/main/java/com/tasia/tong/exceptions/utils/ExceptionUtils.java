package com.tasia.tong.exceptions.utils;

import com.tasia.tong.exceptions.api.ParamInvalidApiException;
import com.tasia.tong.exceptions.factory.ApiExceptionFactory;
import java.text.MessageFormat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {

    public void checkAndThrowException(boolean test, String message, Object... args) {
        String errorMsg = MessageFormat.format(message, args);
        if (test) {
            ApiExceptionFactory.createException(ParamInvalidApiException.class, errorMsg);
        }
    }
}

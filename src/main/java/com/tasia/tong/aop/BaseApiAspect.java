package com.tasia.tong.aop;

import com.tasia.tong.exceptions.api.BaseApiException;
import com.tasia.tong.exceptions.domain.BaseDomainException;
import com.tasia.tong.exceptions.api.UnexpectedApiException;
import com.tasia.tong.exceptions.factory.ApiExceptionFactory;
import com.tasia.tong.utils.JsonUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.beanutils.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;

@Slf4j
abstract
public class BaseApiAspect {

    private static final String API_CODE_FILED_NAME = "code";

    private static final String API_MESSAGE_FILED_NAME = "message";

    /**
     * 切点
     */
    abstract public void thriftImplPointcut();

    /**
     * 获取异常code
     * @param e
     * @return
     */
    abstract protected Integer getExceptionCode(Exception e);


    @Around("thriftImplPointcut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getName();

        Object[] args = pjp.getArgs();

        String message = "[thrift] 接口: {}, 请求: {}";

        boolean ignore = hasIgnoreAccessLogAnnotation(method);

        String argString = JsonUtils.toJsonString(args);

        try {
            if (!ignore) {
                log.info(message, methodName, argString);
            }

            long startTime = System.currentTimeMillis();
            Object ret = pjp.proceed();
            if (!ignore) {
                log.info(message + "响应: {}, 耗时: {}(ms)",
                        methodName, argString, JsonUtils.toJsonString(ret),
                        System.currentTimeMillis() - startTime);
            }
            return ret;
        } catch (Exception e) {
            if (!ignoreReportError(e)) {
                log.error(message, methodName, argString, e);
            } else {
                log.error(message, methodName, argString, ExceptionUtils.getStackTrace(e));
            }

            reportError(pjp, methodName, argString, e);

            return buildErrorResponse(methodName, args, pjp, getExceptionCode(e), getSafeExposedMessage(e), e);
        }
    }

    private Object buildErrorResponse(String methodName, Object[] args, ProceedingJoinPoint pjp,
            Integer exceptionCode, String safeExposedMessage, Exception ex) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Class<?> returnType = methodSignature.getReturnType();

        try {
            Object returnObject = returnType.getConstructor().newInstance();
            setCodeAndMessage(returnObject, exceptionCode, safeExposedMessage);
            return returnObject;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            log.error(methodName, methodName, Arrays.asList(args), ex);
            throw ApiExceptionFactory.wrapException(UnexpectedApiException.class, e,
                    "创建接口: " + methodName + " 的返回时出现错误");
        }
    }

    private void setCodeAndMessage(Object obj, Integer code, String message)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        PropertyUtils.setProperty(obj, API_CODE_FILED_NAME, code);
        PropertyUtils.setProperty(obj, API_MESSAGE_FILED_NAME, message);
    }

    private String getSafeExposedMessage(Exception e) {
        String exposedMessage = getExposedMessage(e);
        return Optional.ofNullable(exposedMessage).orElse(StringUtils.EMPTY);
    }

    private String getExposedMessage(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return e.getMessage();
        } else if (e instanceof BaseApiException) {
            return e.getMessage();
        } else if (e instanceof BaseDomainException) {
            String bizMessage = ((BaseDomainException) e).getBizMessage();
            return StringUtils.isBlank(bizMessage) ? e.getMessage() : bizMessage;
        } else {
            return e.getMessage();
        }
    }

    private void reportError(ProceedingJoinPoint pjp,
            String methodName, String argString, Exception ex) {
        // TODO:
    }

    private boolean ignoreReportError(Exception e) {
        return false;
    }

    private boolean hasIgnoreAccessLogAnnotation(Method method) {

        IgnoreAccessLog annotation = method.getAnnotation(IgnoreAccessLog.class);
        return annotation != null;
    }
}

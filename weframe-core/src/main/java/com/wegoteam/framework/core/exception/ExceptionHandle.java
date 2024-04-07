package com.wegoteam.framework.core.exception;

import com.wegoteam.framework.core.base.CommonResult;
import com.wegoteam.framework.core.code.SystemCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.DecodeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
@Component
public class ExceptionHandle implements HandlerExceptionResolver, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    @Value("${system.errorCode}")
    private String systemErrorCode;

    @Value("${system.errorMsg:系统处理异常，请联系管理员！}")
    private String defaultErrMsg;

    @Autowired
    private SystemCodeService systemCodeService;

    @Override
    public int getOrder() {
        return -2147483648;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return resolveException0(request, response, handler, ex);
    }

    private ModelAndView resolveException0(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        String errorCode = systemErrorCode, errorMsg = defaultErrMsg;
        if(ex instanceof DecodeException) {
            DecodeException decodeException = (DecodeException) ex;
            Throwable throwable = decodeException.getCause();
            if(throwable instanceof AppException) {
                AppException appException = (AppException) throwable;
                errorCode = appException.getErrorCode();
                errorMsg = appException.getErrorMsg();
                if(StringUtils.isEmpty(errorMsg)){
                    errorMsg = systemCodeService.getMessage(errorCode, appException.getAll());
                }
                LOGGER.error(errorMsg + " : "+ errorCode, appException.getCause());
            }
        }
        else if (ex instanceof AppException) {
            AppException appException = (AppException) ex;
            errorCode = appException.getErrorCode();
            errorMsg = appException.getErrorMsg();
            if(StringUtils.isEmpty(errorMsg)){
                errorMsg = systemCodeService.getMessage(errorCode, appException.getAll());
            }
            LOGGER.error(errorMsg + " : "+ errorCode, appException.getCause());
        }
        else if(ex instanceof ParamException) {
            ParamException paramException = (ParamException) ex;
            errorCode = paramException.getErrorCode();
            errorMsg = paramException.getErrorMsg();
            LOGGER.error(errorMsg);
        }
        else if(ex instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                errorCode = item.getMessage();
                break;
            }
            errorMsg = systemCodeService.getMessageOptional(errorCode).orElse("Invalid Parameter");
            LOGGER.error(errorMsg);
        } else {
//            errorMsg = ex.getMessage();
            LOGGER.error("Uncaught exception", ex);
        }

        if(defaultErrMsg.equals(errorMsg)){
            errorMsg = systemCodeService.getMessageOptional(errorCode).orElse(defaultErrMsg);
        }
        CommonResult commonOuterResponse = new CommonResult();
        commonOuterResponse.setReturnCode(errorCode);
        commonOuterResponse.setReturnMsg(errorMsg);

        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getOutputStream().write(commonOuterResponse.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception ee) {
            LOGGER.error("resolveException", ee);
        }

        return new ModelAndView();
    }
}
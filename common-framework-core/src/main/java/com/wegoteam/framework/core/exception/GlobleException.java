package com.wegoteam.framework.core.exception;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
//@Configuration
//@ControllerAdvice
public class GlobleException {

    /**
     * 跳转自定义异常页面
     *
     * @param response
     * @param e
     * @param model
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Throwable.class)
    public String gbExceptionHandler(HttpServletResponse response, Throwable e, Model model) throws Exception {
        // response错误码
        response.setStatus(400);
        JSONObject jsonObject = new JSONObject();
        // 错误编码
        jsonObject.put("code", e.getMessage());
        // 错误原因
        Throwable cause = e.getCause();
        if (cause != null) {
            // 错误信息
            jsonObject.put("cause", cause.getMessage());
        }
        // 异常信息
        model.addAttribute("errorMsg", jsonObject.toJSONString());
        // 自定义错误页面
        return "400";
    }
}

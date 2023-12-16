package com.wego.framework.core.em;


import com.wego.framework.core.code.SystemCode;

/**
 * @project: itools-backend
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
@SystemCode
public class SystemCodeBean {

    public enum SystemCode {
        /**
         * 需要在配置文件指定
         * system:
         *   errorCode: Code001
         */
        SYSTEM_EXCEPTION("Code001" , "系统内部错误"),
        RETE_EXCEPTION("Code002" , "接口请求频繁，请稍后重试！"),
        IDEMPOTENT_EXCEPTION("Code003" , "数据重复提交，请稍后重试！"),
        CONTENT_NULL_EXCEPTION("Code004" , "暂无提交数据内容"),


        ;
        private final String code;
        private final String message;

        SystemCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }
}

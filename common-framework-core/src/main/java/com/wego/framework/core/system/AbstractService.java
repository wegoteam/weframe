package com.wego.framework.core.system;

import com.wego.framework.core.code.SystemCodeService;
import com.wego.framework.core.snowflake.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2020-09-28 22:56
 */
public class AbstractService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SystemCodeService systemCodeService;
    @Autowired
    protected SequenceService sequenceService;


    public String getMessage(String code) {
        return this.systemCodeService.getMessage(code);
    }

    public String createGeneralCode() {
        return String.valueOf(this.sequenceService.nextValue(null));
    }
}
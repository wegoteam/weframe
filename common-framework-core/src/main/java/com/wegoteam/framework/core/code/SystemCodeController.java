package com.wegoteam.framework.core.code;

import com.wegoteam.framework.core.base.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @project: itools-backend
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
@RestController
@RequestMapping("system")
public class SystemCodeController {

    private final SystemCodeService systemCodeService;

    public SystemCodeController(@Autowired SystemCodeService systemCodeService) {
        this.systemCodeService = systemCodeService;
    }

    @RequestMapping(value = "translate/{code}", method = RequestMethod.GET)
    public CommonResult<Map<String, String>> translate(@PathVariable(value = "code") String code){
        Map<String, String> result = new HashMap<>(2);
        result.put("code", code);
        result.put("msg", systemCodeService.getMessageOptional(code).orElse(""));
        return CommonResult.success(result);
    }

    @RequestMapping(value = "translate/codes", method = RequestMethod.GET)
    public CommonResult<Map<String, String>> codes(){
        return CommonResult.success(systemCodeService.getSystemCodeMap());
    }
}

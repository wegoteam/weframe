package com.wego.framework.core.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-06-01 10:14
 */
public class CharacterValidateConstraint implements ConstraintValidator<CharacterValidate,String> {

    private String numberRegularization;
    private boolean isValiNull;
    private int min;
    private int max;
    /**
     * 方法是用来初始化数据, 也就是存放注解里面的配置信息
     * @param constraintAnnotation
     */
    @Override
    public void initialize(CharacterValidate constraintAnnotation) {
        isValiNull = constraintAnnotation.isAllowNull();
        numberRegularization = constraintAnnotation.regularization();
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    /**
     * 校验逻辑的方法
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //不允许为空且检验的对象为空
        if (!isValiNull && StringUtils.isBlank(value)){
            return false;
        }
        //允许为空且检验的对象为空
        if (isValiNull && StringUtils.isBlank(value)){
            return true;
        }

        //正则
        if (StringUtils.isNotBlank(numberRegularization)){
            boolean matches = value.matches(numberRegularization);
            if (!matches){
                return false;
            }
        }
        //验证长度
        int length = value.length();
        if (length>max || length<min){
            return false;
        }
        return true;
    }
}

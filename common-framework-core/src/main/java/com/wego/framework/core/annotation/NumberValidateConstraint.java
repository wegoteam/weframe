package com.wego.framework.core.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-06-01 10:14
 */
public class NumberValidateConstraint implements ConstraintValidator<NumberValidate,Object> {

    private String numberRegularization;
    private boolean isValiNull;
    /**
     * 方法是用来初始化数据, 也就是存放注解里面的配置信息
     * @param constraintAnnotation
     */
    @Override
    public void initialize(NumberValidate constraintAnnotation) {
        isValiNull = constraintAnnotation.isValiNull();
        numberRegularization = constraintAnnotation.numberRegularization();
    }

    /**
     * 校验逻辑的方法
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //不允许为空且检验的对象为空
        if (!isValiNull && value == null){
            return false;
        }
        //允许为空且检验的对象为空
        if (isValiNull && value == null){
            return true;
        }
        String str = String.valueOf(value);
        boolean matches = str.matches(numberRegularization);
        if (matches){
            return true;
        }
        return false;
    }
}

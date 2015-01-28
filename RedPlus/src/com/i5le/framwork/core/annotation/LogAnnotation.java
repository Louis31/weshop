package com.i5le.framwork.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LogAnnotation {
	boolean value() default true;//是否打印日志
    boolean writeRespBody() default true;//是否把整个响应结果都打印
    boolean writeParams() default true;//是否打印参数
    boolean writeAdminUser() default true;//是否打印后台管理员信息，会打印user.id和user.loginName
    String describe() default "";//操作描述
}

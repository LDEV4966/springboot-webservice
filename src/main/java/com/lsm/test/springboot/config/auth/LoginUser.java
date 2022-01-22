package com.lsm.test.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //해당 어노테이션을 파라미터로 선언된 객체에서만 사용될 수 있다.
@Retention(RetentionPolicy.RUNTIME) //어노테이션을 런타임시까지도 유지하게 한다.
public @interface LoginUser {
}

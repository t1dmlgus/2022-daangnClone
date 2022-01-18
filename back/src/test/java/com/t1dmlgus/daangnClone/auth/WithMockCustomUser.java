package com.t1dmlgus.daangnClone.auth;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory =  WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "testID";

    String name() default "이의현";

}
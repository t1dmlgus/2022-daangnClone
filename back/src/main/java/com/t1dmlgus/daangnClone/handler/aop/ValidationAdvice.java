package com.t1dmlgus.daangnClone.handler.aop;


import com.t1dmlgus.daangnClone.handler.exception.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationAdvice {

    Logger logger = LoggerFactory.getLogger(ValidationAdvice.class);

    @Around("execution(* com.t1dmlgus.daangnClone.user.ui.UserApiController.join(..))")
    public Object joinAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {

            logger.info("arg,{}", arg);

            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError fieldError : bindingResult.getFieldErrors()) {
                        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());

                    }
                    throw new CustomValidationException("유효성 검사 실패", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();


    }

}

package com.backbase.assignment.logging;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Loggable {

    /**
     * set the logging message literally
     *
     * @return the logged message
     */
    String message() default "";

}

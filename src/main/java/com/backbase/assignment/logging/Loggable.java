package com.backbase.assignment.logging;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * Created by Ahmad on 6/4/2017.
 */
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

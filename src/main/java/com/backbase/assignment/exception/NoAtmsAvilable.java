/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backbase.assignment.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Ahmad on 6/4/2017.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No ATMs availble for this city.")
public class NoAtmsAvilable extends RuntimeException {

    private static final Logger LOG = Logger.getLogger(NoAtmsAvilable.class);

    @Override
    public String getMessage() {
        LOG.error("No ATMs availble for this city.");
        return "No ATMs availble for this city.";
    }

}

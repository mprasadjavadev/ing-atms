package com.backbase.assignment.controller;

import com.backbase.assignment.model.Atm;
import com.backbase.assignment.service.AtmService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ahmad on 6/4/2017.
 */
@Controller
public class AtmController {

    @Autowired
    AtmService atmService;

    @ApiOperation(value = "Get city atms", notes = "Get city atms.")
    @RequestMapping(value = "/atms/all/{city}", method = {RequestMethod.GET})
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Atm> getCityAtms(@PathVariable String city) throws IOException {
        return atmService.getCityAtms(city);
    }

    @ApiOperation(value = "Get city atms with pagination", notes = "Get city atms with pagination.")
    @RequestMapping(value = "/atms/{city}", method = {RequestMethod.GET})
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Atm> getCityAtmsPagination(@PathVariable String city, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) throws IOException {
        return atmService.getCityAtmsWithPagination(city, pageNumber, pageSize);
    }

    @ApiOperation(value = "Get city atms size", notes = "Get city atms size.")
    @RequestMapping(value = "/atms/size/{city}", method = {RequestMethod.GET})
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public Integer getCityAtmsSize(@PathVariable String city) throws IOException {
        return atmService.getCityAtmsSize(city);
    }
}

package com.backbase.assignment.controller;

import com.backbase.assignment.AbstractWebTest;
import com.backbase.assignment.Application;
import com.backbase.assignment.model.Address;
import com.backbase.assignment.model.Atm;
import com.backbase.assignment.model.GeoLocation;
import com.backbase.assignment.service.AtmService;
import com.backbase.assignment.util.ATMsWrapper;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Ahmad on 4/16/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
@WithMockUser(authorities = "ADMIN")
public class AtmControllerTest extends AbstractWebTest {
    private static final Logger LOG = Logger.getLogger(AtmControllerTest.class);

    @Autowired
    @InjectMocks
    AtmService atmService;
    @Mock
    ATMsWrapper atMsWrapper;

    private String city = "Assendelft";
    private String wrongCity = "AssenwrongCitydelft";
    private Atm[] atms = new Atm[2];

    @Before
    public void setup() throws IOException {
        Address address = new Address();
        address.setCity(city);
        address.setHousenumber("10A");
        address.setPostalcode("1567 JP");
        address.setStreet("Kaaikhof");
        address.setGeoLocation(new GeoLocation());
        Atm atm = new Atm();
        atm.setAddress(address);
        atm.setDistance(0);
        atm.setType("ING");
        Atm anotherAtm = new Atm();
        Address anotherAddress = new Address();
        anotherAddress.setCity(city);
        anotherAddress.setHousenumber("20A");
        anotherAddress.setPostalcode("1567 JP");
        anotherAddress.setStreet("Kaaikhof");
        anotherAddress.setGeoLocation(new GeoLocation());
        anotherAtm.setAddress(anotherAddress);
        atms[0] = atm;
        atms[1] = anotherAtm;
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetValidCityATMs() {
        try {
            LOG.info("Test shorten url");
            LOG.debug("City is: " + city);
            when(atMsWrapper.getATMs(any())).thenReturn(atms);
            mockMvc.perform(get("/atms/all/" + city)).
                    andExpect(status().isOk()).
                    andExpect(jsonPath("$[0].address.city", Is.is(city))).
                    andExpect((jsonPath("$.*", Matchers.hasSize(2)))).
                    andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetNotValidCityATMsReturnNoContent() {
        try {
            LOG.info("Test shorten url");
            LOG.debug("City is: " + wrongCity);
            when(atMsWrapper.getATMs(any())).thenReturn(atms);
            mockMvc.perform(get("/atms/all/" + wrongCity)).
                    andExpect(status().isNotFound());
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetValidCityATMsSize() {
        try {
            LOG.info("Test shorten url");
            LOG.debug("City is: " + city);
            when(atMsWrapper.getATMs(any())).thenReturn(atms);
            mockMvc.perform(get("/atms/size/" + city)).
                    andExpect(status().isOk()).
                    andExpect(content().string("2")).
                    andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetValidCityATMsWithPagination() {
        try {
            LOG.info("Test shorten url");
            LOG.debug("City is: " + city);
            when(atMsWrapper.getATMs(any())).thenReturn(atms);
            mockMvc.perform(get("/atms/" + city + "?pageNumber=0&pageSize=1")).
                    andExpect(status().isOk()).
                    andExpect((jsonPath("$.*", Matchers.hasSize(1)))).
                    andExpect(jsonPath("$[0].address.city", Is.is(city))).
                    andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }
}

package com.backbase.assignment.util;

import com.backbase.assignment.model.Atm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class ATMsWrapper {

    private static final Logger LOG = Logger.getLogger(ATMsWrapper.class);
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    public Atm[] getATMs(String Url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(Url, String.class);
        String data = result.getBody().substring(result.getBody().indexOf('['));
        Atm[] atms = objectMapper.readValue(data, Atm[].class);

        if (atms.length == 0) {
            LOG.warn("No result found");
        }
        return atms;
    }
}

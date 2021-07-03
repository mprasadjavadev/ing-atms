package com.backbase.assignment.service;

import com.backbase.assignment.exception.NoAtmsAvilable;
import com.backbase.assignment.model.Atm;
import com.backbase.assignment.util.ATMsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AtmService {

    @Autowired
    ATMsWrapper atMsWrapper;

    public List<Atm> getCityAtms(String city) throws IOException {
        List<Atm> cityATMs = getAtms(city);
        return cityATMs;
    }

    public Integer getCityAtmsSize(String city) throws IOException {
        return getAtms(city).size();
    }

    public List<Atm> getCityAtmsWithPagination(String city, int pageNumber, int pageSize) throws IOException {
        return getAtms(city).stream().sorted()
                .skip((long) (pageNumber * pageSize))
                .limit((long) (pageSize)).collect(Collectors.toList());
    }

    private List<Atm> getAtms(String city) throws IOException {
        List<Atm> cityATMs = Arrays.stream(atMsWrapper.getATMs("https://www.ing.nl/api/locator/atms/")).filter(atm -> atm.getAddress().getCity().equals(city)).sorted().collect(Collectors.toList());
        if (cityATMs.size() == 0) {
            throw new NoAtmsAvilable();
        }
        return cityATMs;
    }
}

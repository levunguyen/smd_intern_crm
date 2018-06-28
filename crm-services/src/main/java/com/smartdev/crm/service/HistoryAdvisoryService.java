package com.smartdev.crm.service;

import com.smartdev.user.entity.Customer;
import com.smartdev.user.entity.HistoryAdvisory;
import com.smartdev.user.model.StatusCount;

import java.util.List;

public interface HistoryAdvisoryService {
    public  List<HistoryAdvisory> getHistoryAdvisoriesByCustomer(Customer customer);
  
    List<HistoryAdvisory> findAll();

    List<Integer> getYearOfHistoryAdvisory();

    StatusCount getNumberOfEachStatusByYearAndMonth(int year, int month);
}

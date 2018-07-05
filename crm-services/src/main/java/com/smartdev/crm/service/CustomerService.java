package com.smartdev.crm.service;

import com.smartdev.user.entity.Customer;
import com.smartdev.user.entity.Status;
import com.smartdev.user.entity.User;

import java.util.List;

public interface CustomerService {
    void saveCustomer(Customer customer);
  
    void addCustomer(Customer customer);
  
    Customer findOneid(Integer id);

    List<Customer> findCustomersByStatusId(Status status);

    List<Customer> findCustomersByProductType(Integer productType);

    List<Customer> findByProductTypeAndStatusByStatusId(Integer productType, Status statusId);

    List<Customer> findByUserBySellerAndIsDelete(User user,Integer isDelete);
  
    List<Customer> findByNameContaining(String name,Integer isDelete);
  
    List<Customer> findByCompanyContaining (String company,Integer isDelete);
  
    List<Customer>  findByMailContaining(String mail,Integer isDelete);
  
    List<Customer> findByUserBySeller( User user,Integer isDelete);

    List<Customer> checkOption(String option , String search);

    List<Customer> listAllCustomer();

    List<Customer> listCustomerWithFilter(Integer statusId, Integer productType, Integer pageNum);

}

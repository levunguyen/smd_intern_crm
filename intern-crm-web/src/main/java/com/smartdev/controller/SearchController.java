package com.smartdev.controller;

import com.smartdev.crm.service.CustomerService;
import com.smartdev.crm.service.HistoryAdvisoryService;
import com.smartdev.crm.service.StatusService;
import com.smartdev.user.entity.Customer;
import com.smartdev.user.entity.HistoryAdvisory;
import com.smartdev.user.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private HistoryAdvisoryService historyAdvisoryService;

    @RequestMapping(value="/search",method = RequestMethod.POST )
    public ModelAndView search( ModelAndView modelAndView ,@RequestParam(value = "search" ) String search, @RequestParam(value ="thongtin") String thongtin , Model model,Authentication authentication){
        List<Customer> customers = customerService.checkOption(thongtin,search);
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_SELLER")) {
               modelAndView.setViewName("list-custom-search");
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
                 modelAndView.setViewName("list-custom-manage-search");
                break;
            }

        }
        modelAndView.addObject("list",customers);
        modelAndView.addObject("key" , search);


        return modelAndView;
    }
    @RequestMapping(value="/seller/searchSeller",method = RequestMethod.POST )
    public ModelAndView searchSeller(@RequestParam(value = "search" ) String search, @RequestParam(value ="thongtin") String thongtin , Model model){
        System.out.println("ten:" + search + "tim kiem" + thongtin);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        List<Customer> customers = customerService.checkOption(thongtin,search);
        List<Customer> customersl = new ArrayList<Customer>();
        HashMap<Customer,HistoryAdvisory> map = new HashMap<>();
        for (Customer c: customers) {
            if((username).equals(c.getUserBySeller().getUserName())){
                customersl.add(c);
                List<HistoryAdvisory> historyAdvisoryList = historyAdvisoryService.getHistoryAdvisoriesByCustomer(c);
                HistoryAdvisory historyAdvisory = historyAdvisoryList.get(0);
                map.put(c,historyAdvisory);
            }
        }
        ModelAndView modelAndView = new ModelAndView("list-custom-seller");
        modelAndView.addObject("customers",customersl);
        modelAndView.addObject("key" , search);
        modelAndView.addObject("map",map);
        return modelAndView;
    }
    @RequestMapping(value="/search-block",method = RequestMethod.POST )
    public ModelAndView searchBlock( ModelAndView modelAndView ,@RequestParam(value = "search" ) String search, @RequestParam(value ="thongtin") String thongtin , Model model,Authentication authentication){
        Status status = statusService.findById(5);
        List<Customer> customers = customerService.checkOptionBlock(thongtin,search, status);
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_SELLER")) {
                modelAndView.setViewName("block-search-for-seller");
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
                modelAndView.setViewName("block-search-for-manager");
                break;
            }

        }
        modelAndView.addObject("itemsBlock",customers);
        modelAndView.addObject("key" , search);
        return modelAndView;
    }
}

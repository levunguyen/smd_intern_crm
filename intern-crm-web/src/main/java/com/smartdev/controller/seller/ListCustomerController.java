package com.smartdev.controller.seller;

import com.smartdev.crm.service.HistoryAdvisoryService;
import com.smartdev.crm.service.ListCustomManageService;
import com.smartdev.user.entity.Customer;
import com.smartdev.crm.service.CustomerService;
import com.smartdev.user.entity.HistoryAdvisory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/seller")
public class ListCustomerController {

    @Autowired
    ListCustomManageService listCustomManageService;
    @Autowired
    CustomerService customerService;
    @Autowired
    HistoryAdvisoryService historyAdvisoryService;

    @RequestMapping(value = "/list-custom", method = RequestMethod.GET)
    public String listCustom(Model model){
        List<Customer> customerList = listCustomManageService.listAllCustomer();
        model.addAttribute("list",customerList);
        return "list-custom";
    }

    @RequestMapping(value = "/advisory/{id}", method = RequestMethod.GET)
    public ModelAndView advisory(@PathVariable(value = "id") Integer id){
        Customer customer = customerService.afindOneid(id);
        List<HistoryAdvisory> historyAdvisories= historyAdvisoryService.getHistoryAdvisoriesByCustomer(customer);
        ModelAndView modelAndView = new ModelAndView("advisory");
        modelAndView.addObject("getItemCustomer",customer);
        modelAndView.addObject("historyAdvisories",historyAdvisories);
        return modelAndView;
    }

    @RequestMapping(value = "/addcustomer",method = RequestMethod.GET)
    public ModelAndView addCustomer(){
        ModelAndView modelAndView = new ModelAndView("addcustomer");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @RequestMapping(value = "/addcustomer", method = RequestMethod.POST)
    public String handleAddCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult result) {
        if(result.hasErrors()) {
            return "addcustomer";
        }
        customerService.addCustomer(customer);
        return "redirect:/seller/list-custom";
    }
}

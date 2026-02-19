package com.oceanview.resort.controller;
import com.oceanview.resort.model.Customer;
import com.oceanview.resort.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/register")
    public Customer register(@RequestBody Customer c){
        return service.register(c);
    }

    @PostMapping("/login")
    public Customer login(@RequestBody Customer c){
        //  FIXED METHOD NAME
        return service.login(c.getEmail(), c.getPassword());
    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {

        Customer existing = service.getCustomerById(id);

        if (existing != null) {
            existing.setName(updatedCustomer.getName());
            existing.setEmail(updatedCustomer.getEmail());

            // update password only if provided
            if (updatedCustomer.getPassword() != null && !updatedCustomer.getPassword().isEmpty()) {
                existing.setPassword(updatedCustomer.getPassword());
            }

            return service.save(existing);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        service.deleteCustomer(id);
    }



    @GetMapping
    public List<Customer> getAllCustomers(){
        return service.getAllCustomers();
    }

}

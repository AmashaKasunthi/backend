package com.oceanview.resort.service;

import com.oceanview.resort.model.Customer;
import com.oceanview.resort.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    public Customer register(Customer c){
        return repo.save(c);
    }

    public Customer login(String email, String password){
        Customer c = repo.findByEmail(email);
        if(c != null && c.getPassword().equals(password)){
            return c;
        }
        return null;
    }

    public List<Customer> getAllCustomers(){
        return repo.findAll();
    }

    public Customer getCustomerById(Long id){
        return repo.findById(id).orElse(null);
    }

    public Customer save(Customer c){
        return repo.save(c);
    }

    public void deleteCustomer(Long id){
        repo.deleteById(id);
    }
}


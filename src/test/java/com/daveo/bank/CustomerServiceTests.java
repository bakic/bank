package com.daveo.bank;

import com.daveo.bank.entity.Customer;
import com.daveo.bank.repository.CustomerRepository;
import com.daveo.bank.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void list_customers_should_succeed() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("john");
        customer1.setLastName("doe");
        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("peter");
        customer2.setLastName("pan");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.listCustomers();

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());

        Assertions.assertThat(result.get(0))
                .extracting(Customer::getId, Customer::getFirstName, Customer::getLastName)
                .containsExactly(1, "john", "doe");
        Assertions.assertThat(result.get(1))
                .extracting(Customer::getId, Customer::getFirstName, Customer::getLastName)
                .containsExactly(2, "peter", "pan");
    }

}

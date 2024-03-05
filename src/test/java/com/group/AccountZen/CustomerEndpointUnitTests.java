package com.group.AccountZen;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.group.AccountZen.dto.CreateCustomerRequest;
import com.group.AccountZen.dto.CustomerOperationRequest;
import com.group.AccountZen.entity.Customer;
import com.group.AccountZen.service.CustomerService;
@SpringBootTest
@AutoConfigureMockMvc
class CustomerEndpointUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void testCreateCustomer() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setCustName("John Doe");
        Customer customer = new Customer();
        customer.setCustId(1L);
        when(customerService.createCustomer("John Doe")).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerOperationRequest request = new CustomerOperationRequest();
        request.setCustId(1L);
        Customer customer = new Customer();
        customer.setCustId(1L);
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testCreateCustomerFailedCreation() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setCustName("John Doe");
        when(customerService.createCustomer("John Doe")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        CustomerOperationRequest request = new CustomerOperationRequest();
        request.setCustId(1L);
        when(customerService.getCustomerById(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}


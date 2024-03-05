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

import com.group.AccountZen.dto.AccountOperationRequest;
import com.group.AccountZen.dto.AccountTransactionRequest;
import com.group.AccountZen.dto.CreateAccountRequest;
import com.group.AccountZen.entity.Account;
import com.group.AccountZen.service.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
class AccountEndpointUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void testCreateAccount() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountType("Savings");
        Account account = new Account();
        account.setAccountId(1L);
        when(accountService.createAccount("Savings")).thenReturn(account);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testGetAccountById() throws Exception {
        AccountOperationRequest request = new AccountOperationRequest();
        request.setAccountId(1L);
        Account account = new Account();
        account.setAccountId(1L);
        when(accountService.getAccountById(1L)).thenReturn(account);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDepositCash() throws Exception {
        AccountTransactionRequest request = new AccountTransactionRequest();
        request.setAccountId(1L);
        request.setAmount(100.00);
        Account account = new Account();
        account.setAccountId(1L);
        when(accountService.depositCash(1L, 100.00)).thenReturn(account);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testWithdrawCash() throws Exception {
        AccountTransactionRequest request = new AccountTransactionRequest();
        request.setAccountId(1L);
        request.setAmount(50.00);
        Account account = new Account();
        account.setAccountId(1L);
        when(accountService.withdrawCash(1L, 50.00)).thenReturn(account);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCloseAccount() throws Exception {
        AccountOperationRequest request = new AccountOperationRequest();
        request.setAccountId(1L);
        when(accountService.closeAccount(1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/close")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    void testDepositCashNotFound() throws Exception {
        AccountTransactionRequest request = new AccountTransactionRequest();
        request.setAccountId(1L);
        request.setAmount(100.00);
        when(accountService.depositCash(1L, 100.00)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testWithdrawCashInvalidAmount() throws Exception {
        AccountTransactionRequest request = new AccountTransactionRequest();
        request.setAccountId(1L);
        request.setAmount(-50.00); // Negative amount
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCloseAccountNotFound() throws Exception {
        AccountOperationRequest request = new AccountOperationRequest();
        request.setAccountId(1L);
        when(accountService.closeAccount(1L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/close")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testCreateAccountFailedCreation() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountType("Savings");
        when(accountService.createAccount("Savings")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testGetAccountByIdNotFound() throws Exception {
        AccountOperationRequest request = new AccountOperationRequest();
        request.setAccountId(1L);
        when(accountService.getAccountById(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
package bank.interfaces.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import bank.data.OperationError;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankRestServiceTests {

    @Autowired
    private MockMvc mockMvc;
    
    private static final String CREATE_ACCOUNT_URI = "/bank/rest/createAccount";
    private static final String TRANSFER_URI = "/bank/rest/transfer";

    @Test
    public void createAccountAlreadyCreated() throws Exception {
        String accountName1 = "randomNameAccount1"; 
        this.mockMvc.perform(get(CREATE_ACCOUNT_URI).param("name", accountName1).param("balance", "0"))
        			.andDo(print())
        			.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        // Call it again with the same arguments to check the already exists
        this.mockMvc.perform(get(CREATE_ACCOUNT_URI).param("name", accountName1).param("balance", "0"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.errorCode").value(OperationError.ACCOUNT_ALREADY_EXISTS.getCode()));
    }

    @Test
    public void transferSuccess() throws Exception {
        String accountName2 = "randomNameAccount2"; 
        String accountName3 = "randomNameAccount3"; 
        this.mockMvc.perform(get(CREATE_ACCOUNT_URI).param("name", accountName2).param("balance", "20"))
        			.andDo(print())
        			.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        this.mockMvc.perform(get(CREATE_ACCOUNT_URI).param("name", accountName3).param("balance", "100"))
        			.andDo(print())
        			.andExpect(status().isOk())
        			.andExpect(jsonPath("$.success").value(true));

        this.mockMvc.perform(get(TRANSFER_URI).param("from", accountName2).param("to", accountName3).param("value", "10"))
        			.andDo(print())
        			.andExpect(status().isOk())
        			.andExpect(jsonPath("$.success").value(true));
 
    }


    @Test
    public void transferNotEnoughFunds() throws Exception {
        String accountName1 = "randomNameAccount6"; 
        String accountName2 = "randomNameAccount18"; 
        this.mockMvc.perform(get(CREATE_ACCOUNT_URI).param("name", accountName1).param("balance", "20"))
        			.andDo(print())
        			.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        this.mockMvc.perform(get(CREATE_ACCOUNT_URI).param("name", accountName2).param("balance", "100"))
        			.andDo(print())
        			.andExpect(status().isOk())
        			.andExpect(jsonPath("$.success").value(true));

        this.mockMvc.perform(get(TRANSFER_URI).param("from", accountName1).param("to", accountName2).param("value", "50"))
        			.andDo(print())
        			.andExpect(status().isOk())
    				.andExpect(jsonPath("$.success").value(false))
    				.andExpect(jsonPath("$.errorCode").value(OperationError.ACCOUNT_NOT_ENOUGH_FUNDS.getCode()));
 
    }
}

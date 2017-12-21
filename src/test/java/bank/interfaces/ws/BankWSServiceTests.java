package bank.interfaces.ws;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.maxintech.bank.ws.CreateAccountRequest;
import com.maxintech.bank.ws.CreateAccountResponse;

import bank.data.OperationError;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BankWSServiceTests {
    private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    @Before
    public void init() throws Exception {
        marshaller.setPackagesToScan(ClassUtils.getPackageName(CreateAccountRequest.class));
        marshaller.afterPropertiesSet();
    }

    @Test
    public void createAccountAlreadyCreated() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("randomAccountName09");
        request.setBalance(100);

        Object response = ws.marshalSendAndReceive("http://localhost:8080/bank/ws", request);
        assertThat(response).isNotNull().isInstanceOf(CreateAccountResponse.class);
        assertThat(((CreateAccountResponse)response).getOperationStatus().isStatus()).isEqualTo(true);

        // Call it again with the same arguments to check the already exists
        response = ws.marshalSendAndReceive("http://localhost:8080/bank/ws", request);
        assertThat(response).isNotNull().isInstanceOf(CreateAccountResponse.class);
        assertThat(((CreateAccountResponse)response).getOperationStatus().isStatus()).isEqualTo(false);        
        assertThat(((CreateAccountResponse)response).getOperationStatus().getErrorCode()).isEqualTo(OperationError.ACCOUNT_ALREADY_EXISTS.getCode());		
    }
}

package bank.interfaces.ws;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.maxintech.bank.ws.CreateAccountRequest;
import com.maxintech.bank.ws.CreateAccountResponse;
import com.maxintech.bank.ws.TransferRequest;
import com.maxintech.bank.ws.TransferResponse;

import bank.data.OperationStatus;
import bank.services.BankBaseServices;

/**
 * This class is a Web Service interface for the service {@link BankBaseServices}.
 * 
 * Using the annotation {@code @Endpoint}, registers this class with Spring WS as a potential candidate 
 * for processing incoming SOAP messages.
 * 
 * @author Maximiliano Sanchez de Bustamante
 *
 */
@Endpoint
public class BankServiceEndpoint {
	private static final String NAMESPACE_URI = "http://maxintech.com/bank/ws";

	/**
	 * This method is the Web Service wrapper of the {@link BankBaseServices}.createAccount method.
	 * 
	 * The annotation {@code @PayloadRoot} is used by Spring WS to pick the handler method based on 
	 * the message’s namespace and localPart. {@code @RequestPayload} indicates that the incoming message 
	 * will be mapped to the method’s request parameter.
	 * The {@code @ResponsePayload} annotation makes Spring WS map the returned value to the response 
	 * payload.
	 * 
	 * @param request The incoming message. An instance of {@link CreateAccountRequest}
	 * @return Returns the response with an instance of @{CreateAccountResponse}
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccountRequest")
	@ResponsePayload
	public CreateAccountResponse createAccount(@RequestPayload CreateAccountRequest request) {
		CreateAccountResponse response = new CreateAccountResponse();
		BankBaseServices service = new BankBaseServices();
		OperationStatus status = service.createAccount(request.getName(), request.getBalance());
		com.maxintech.bank.ws.OperationStatus wsStatus = new com.maxintech.bank.ws.OperationStatus();
		wsStatus.setStatus(status.isSuccess());
		wsStatus.setErrorCode(status.getErrorCode().getCode());
 		response.setOperationStatus(wsStatus);

		return response;
	}

	/**
	 * This method is the Web Service wrapper of the {@link BankBaseServices}.transfer method.
	 * 
	 * The annotation {@code @PayloadRoot} is used by Spring WS to pick the handler method based on 
	 * the message’s namespace and localPart. {@code @RequestPayload} indicates that the incoming message 
	 * will be mapped to the method’s request parameter.
	 * The {@code @ResponsePayload} annotation makes Spring WS map the returned value to the response 
	 * payload.
	 * 
	 * @param request The incoming message. An instance of {@link TransferRequest}
	 * @return Returns the response with an instance of @{TransferResponse}
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "transferRequest")
	@ResponsePayload
	public TransferResponse transfer(@RequestPayload TransferRequest request) {
		TransferResponse response = new TransferResponse();
		BankBaseServices service = new BankBaseServices();

		OperationStatus status = service.transfer(request.getFrom(), request.getTo(), request.getValue());

		com.maxintech.bank.ws.OperationStatus wsStatus = new com.maxintech.bank.ws.OperationStatus();
		wsStatus.setStatus(status.isSuccess());
		wsStatus.setErrorCode(status.getErrorCode().getCode());
 		response.setOperationStatus(wsStatus);

		return response;
	}
}

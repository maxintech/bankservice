package bank.interfaces.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bank.data.OperationStatus;
import bank.services.BankBaseServices;

/**
 * This class is a RESTful interface for the service {@link BankBaseServices}.
 * Uses the Spring’s approach to building RESTful web services where HTTP requests are handled by a controller. 
 * These components are easily identified by the {@code @RequestMapping} annotation. This annotation 
 * ensures the HTTP requests are based to {@code /bank/rest}
 *
 * @author Maximiliano Sanchez de Bustamante
 *
 */

@RestController
@RequestMapping("/bank/rest")
public class BankRestService {
	/**
	 * This method is the RESTful wrapper of the {@link BankBaseServices}.createAccount method.
	 * The {@code @RequestMapping} annotation ensures the HTTP request to {@code /createAccount}
	 * are mapped to this method.
	 *  
	 * @param name Name of the new account. The parameter is mandatory.
	 * @param balance Balance of the new account. The parameter is mandatory.
	 * @return Passes the @{link {@link OperationStatus} given by the {@code BankBaseServices} object
	 */
    @RequestMapping(value = "/createAccount", method = RequestMethod.GET)
    public OperationStatus createAccount(
    						@RequestParam(value="name", required=true) String name,
    						@RequestParam(value="balance", required=true) long balance) {
    		BankBaseServices service = new BankBaseServices();
    		OperationStatus status = service.createAccount(name, balance);
    		
    		return status;
    }
    

	/**
	 * This method is the RESTful wrapper of the {@link BankBaseServices}.transfer method.
	 * The {@code @RequestMapping} annotation ensures the HTTP request to {@code /createAccount}
	 * are mapped to this method.
	 *  
	 * @param fromAccountName Name of the source account. The parameter is mandatory.
	 * @param toAccountName Name of the target account. The parameter is mandatory.
	 * @return Passes the {@link OperationStatus} given by the {@code BankBaseServices} object
	 * @return
	 */
    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public OperationStatus transfer(
			@RequestParam(value="from", required=true) String fromAccountName,
			@RequestParam(value="to", required=true) String toAccountName,
    			@RequestParam(value="value", required=true) long transferValue) {
    		BankBaseServices service = new BankBaseServices();
    		OperationStatus status = service.transfer(fromAccountName, toAccountName, transferValue);
    		
    		return status;
    }
}

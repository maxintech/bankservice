package bank.data;

/**
 * This class allows to drive the success or failure of an operation of the bank services.
 * It is used by the services to indicate if the operation was successfull or in case of and error indicate which 
 * kind of error. 
 * 
 * @author Maximiliano Sanchez de Bustamante
 *
 */
public class OperationStatus {
	private boolean success;
	private OperationError error;

	public OperationStatus(boolean success) {
		this(success, OperationError.NO_ERROR);
	}

	public OperationStatus(boolean success, OperationError error) {
		this.success = success;
		this.error = error;
	}
	
	public boolean isSuccess() {
		return success;
	}

	protected void setSuccess(boolean success) {
		this.success = success;
	}

	public OperationError getErrorCode() {
		return error;
	}

	protected void setErrorCode(OperationError error) {
		this.error = error;
	}
}

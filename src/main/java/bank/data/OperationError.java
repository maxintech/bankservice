package bank.data;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration to indicate the kind of errors a bank operstion could have.  
 * 
 * @author Maximiliano Sanchez de Bustamante
 *
 */
public enum OperationError {
	NO_ERROR(0),
	ACCOUNT_ALREADY_EXISTS(500),
	ACCOUNT_DOESNT_EXIST(501),
	ACCOUNT_NOT_ENOUGH_FUNDS(502),
	INVALID_VALUE(503),
	ACCOUNT_OUT_OF_SEQUENCE(504),
	ACCOUNTS_ARE_EQUAL(505)
	;
	
	private final int code;
	OperationError(int code) {
		this.code = code;
	}
	/**
	 * Obtains the numeric value of the operation.
	 * The annotation JsonValue is for the JSON deserializer when is used to translate the value
	 * to number instead the face value of the enumeration.
	 * @return Return the numeric value of the operation
	 */
	@JsonValue
	public int getCode() {
		return this.code;
	}
}

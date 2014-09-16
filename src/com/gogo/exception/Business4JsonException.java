package com.gogo.exception;

/**
 * json的错误信息，会在baseControl里链接该异常的信息，并直接输出。
 * @author allen
 *
 */
public class Business4JsonException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 2332608236621015980L;

	private String code;

	public Business4JsonException() {
		super();
	}

	public Business4JsonException(String message) {
		super(message);
	}

	public Business4JsonException(String code, String message) {
		super(message);
		this.code = code;
	}

	public Business4JsonException(Throwable cause) {
		super(cause);
	}

	public Business4JsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public Business4JsonException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

package kr.kkiro.projects.bukkit.planecart.exceptions;

public class InvaildBlockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1937578570769008463L;

	public InvaildBlockException() {
	}

	public InvaildBlockException(String message) {
		super(message);
	}

	public InvaildBlockException(Throwable cause) {
		super(cause);
	}

	public InvaildBlockException(String message, Throwable cause) {
		super(message, cause);
	}

}

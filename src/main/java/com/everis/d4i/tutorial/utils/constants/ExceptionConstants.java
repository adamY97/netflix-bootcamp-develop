package com.everis.d4i.tutorial.utils.constants;

public class ExceptionConstants {

	public static final String ERROR = "ERROR";

	public static final String MESSAGE_INEXISTENT_SEASON = "SEASON INEXISTENT - Season does not exist";
	public static final String MESSAGE_INEXISTENT_CHAPTER = "CHAPTER INEXISTENT - Chapter does not exist";
	public static final String MESSAGE_INEXISTENT_ACTOR = "ACTOR INEXISTENT - Actor does not exist";
	public static final String MESSAGE_INEXISTENT_CATEGORY = "CATEGORY INEXISTENT - Category does not exist";

	public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR - An internal server error has ocurred";
	public static final String BAD_REQUEST = "BAD_REQUEST - Incorrect request";
	public static final String UNAUTHORIZED = "UNAUTHORIZED - Doesn't have permissions";
	
	public static final String ID_ERROR = "NOT FOUND ERROR, ID no encontrada.";

	private ExceptionConstants() {
		throw new IllegalStateException("Utility Class");
	}

}

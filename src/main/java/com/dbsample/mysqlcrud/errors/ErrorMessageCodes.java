package com.dbsample.mysqlcrud.errors;

/**
 * 
 * ErrorMessageCodes
 *
 */

public interface ErrorMessageCodes {

	public static final String NAME_IS_BLANK = "Name cannot be blank. ";
	public static final String GENDER_IS_BLANK = "Gender cannot be blank. ";
	public static final String GRADE_IS_BLANK = "Grade cannot be blank. ";
	public static final String EMAIL_IS_BLANK = "Email cannot be blank. ";
	public static final String PHONE_IS_BLANK = "Phone number cannot be blank. ";
	public static final String ADDRESS_IS_BLANK = "Address cannot be blank. ";
	public static final String MESSAGE_IS_BLANK = "Message cannot be blank. ";
	public static final String SENDER_IS_BLANK = "Sender cannot be blank. ";
	public static final String RECEIVER_IS_BLANK = "Receiver cannot be blank. ";
	public static final String USERNAME_IS_BLANK = "User Name cannot be blank. ";
	public static final String PASSWORD_IS_BLANK = "Password cannot be blank. ";

	public static final String ID_INVALID = "ID has to be an integer. ";
	public static final String NAME_INVALID = "Name must start with a letter. It can contain only letters, Spaces, Commas, Hyphens (-), Periods (.), and Single quotes ('). ";
	public static final String GENDER_INVALID = "Gender must be male or female. ";
	public static final String GRADE_INVALID = "Grade must be letters from A+ to D- or F. ";
	public static final String EMAIL_INVALID = "Email must follow format a@b.c ";
	public static final String PHONE_IS_INVALID = "Phone number must have 10 digits. ";
	
	public static final String ID_DNE = "ID is not in Database. ";
	public static final String MID_DNE = "Message ID is not in Database. ";
	public static final String NAME_DNE = "Name is not in Database. ";
	
	public static final String STUDENT_DNE = "Student is not in Database. ";
	public static final String STUDENT_MESSAGE_MISMATCH = "Incorrect id or message id";
}
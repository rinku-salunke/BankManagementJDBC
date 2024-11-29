package Exceptions.com;

public class NotEligibleForAccountCreationException extends Exception{
public NotEligibleForAccountCreationException() {
}
public NotEligibleForAccountCreationException(String msg) {
	super(msg);
}
}
package com.ideas2it.exceptions;

/**
 * this class is used to throw custom exception based on the operations
 * 
 * @author sibi
 * @created 2021-04-21
 */
public class EmployeeManagementException extends Exception {
    
    /**
     * receives exception message and
     * uses super class constructor
     * to show exception message
     * 
     * @param exception message that occurs during execution
     */
    public EmployeeManagementException(String message) {
        super(message);
    }
    
    /**
     * calls super class constructor to
     * show both exception message and cause
     * 
     * @param message
     * @param cause
     */
    public EmployeeManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}

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
     * calls super clause constructor to
     * show the cause of exception
     * 
     * @param cause
     */
    public EmployeeManagementException(Throwable cause) {
        super(cause);
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
    
    /**
     * calls super class constructor to
     * show exception message, cause according
     * with exception suppression and exception trace
     * 
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public EmployeeManagementException(String message, Throwable cause
            , boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

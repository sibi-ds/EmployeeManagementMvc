package com.ideas2it.loggers;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * logs the informations during execution
 * 
 * @author sibi
 * @created 2021-04-23
 */
public class EmployeeManagementLogger {
    
    private Logger logger;
    
    /**
     * loads class for which logs to be recorded
     * 
     * @param logClass
     */
    public EmployeeManagementLogger(Class<?> logClass) {
        logger = LogManager.getLogger(logClass);
    }
    
    /**
     * captures info messages
     * 
     * @param logInfoMessage
     */
    public void logInfo(Object logInfoMessage) {
        logger.info(logInfoMessage);
    }
    
    /**
     * captures debug messages
     * 
     * @param logDebugMessage
     */
//    public void logDebug(Object logDebugMessage) {
//        logger.debug(logDebugMessage);
//    }
    
    /**
     * captures error messages
     * 
     * @param logErrorMessage
     */
    public void logError(Object logErrorMessage) {
        logger.error(logErrorMessage);
    }
    
    /**
     * captures warnings messages
     * 
     * @param logDatalMessage
     */
//    public void logWarning(Object logDatalMessage) {
//        logger.warn(logDatalMessage);
//    }
    
    /**
     * captures fatal messages
     * 
     * @param logDatalMessage
     */
//    public void logFatal(Object logDatalMessage) {
//        logger.fatal(logDatalMessage);
//    }
}

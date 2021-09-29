package ua.dp.exhibitions.exceptions;


/**
 * DaoException is a custom exception thrown by DAO classes
 */
public class DaoException extends Exception{
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }

}
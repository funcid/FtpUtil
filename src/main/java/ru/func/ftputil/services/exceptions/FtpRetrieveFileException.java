package ru.func.ftputil.services.exceptions;

/**
 * @author func 01.09.2019
 */
public class FtpRetrieveFileException extends FtpServiceException {

    public FtpRetrieveFileException(final String message) {
        super(message);
    }

    public FtpRetrieveFileException(final Throwable cause) {
        super(cause);
    }
}

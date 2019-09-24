package ru.func.ftputil.services.exceptions;

/**
 * @author func 01.09.2019
 */
public class FtpDisconnectException extends FtpServiceException {

    public FtpDisconnectException(final String message) {
        super(message);
    }

    public FtpDisconnectException(final Throwable cause) {
        super(cause);
    }
}

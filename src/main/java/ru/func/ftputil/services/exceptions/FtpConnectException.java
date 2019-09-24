package ru.func.ftputil.services.exceptions;

/**
 * @author func 01.09.2019
 */
public class FtpConnectException extends FtpServiceException {

    public FtpConnectException(final String message) {
        super(message);
    }

    public FtpConnectException(final Throwable cause) {
        super(cause);
    }
}

package ru.func.ftputil.services.exceptions;

public class FtpDisconnectException extends FtpServiceException {

    public FtpDisconnectException(final String message) {
        super(message);
    }

    public FtpDisconnectException(final Throwable cause) {
        super(cause);
    }
}

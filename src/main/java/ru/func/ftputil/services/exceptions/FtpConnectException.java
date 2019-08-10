package ru.func.ftputil.services.exceptions;

public class FtpConnectException extends FtpServiceException {

    public FtpConnectException(String message) {
        super(message);
    }

    public FtpConnectException(final Throwable cause) {
        super(cause);
    }
}

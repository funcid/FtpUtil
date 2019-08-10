package ru.func.ftputil.services.exceptions;

public class FtpSetupConnectionException extends FtpServiceException {

    public FtpSetupConnectionException(final String message) {
        super(message);
    }

    public FtpSetupConnectionException(final Throwable cause) {
        super(cause);
    }
}

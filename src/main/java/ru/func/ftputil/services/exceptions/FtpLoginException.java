package ru.func.ftputil.services.exceptions;

public class FtpLoginException extends FtpServiceException {

    public FtpLoginException(final String message) {
        super(message);
    }

    public FtpLoginException(final Throwable cause) {
        super(cause);
    }
}

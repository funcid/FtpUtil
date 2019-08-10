package ru.func.ftputil.services.exceptions;

public class FtpLoginException extends FtpServiceException {

    public FtpLoginException(String message) {
        super(message);
    }

    public FtpLoginException(final Throwable cause) {
        super(cause);
    }
}

package ru.func.ftputil.services.exceptions;

public class FtpServiceException extends Exception {

    FtpServiceException(final Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getCause().getMessage();
    }
}

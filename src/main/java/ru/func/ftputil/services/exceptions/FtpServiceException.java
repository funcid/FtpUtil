package ru.func.ftputil.services.exceptions;

public class FtpServiceException extends Exception {

    FtpServiceException(String message) {
        super(message);
    }

    FtpServiceException(final Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        if (getCause() != null) {
            return getCause().getMessage();
        } else {
            return super.getMessage();
        }
    }
}

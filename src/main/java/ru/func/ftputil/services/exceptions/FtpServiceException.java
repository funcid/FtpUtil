package ru.func.ftputil.services.exceptions;

public class FtpServiceException extends Exception {

    FtpServiceException(final String message) {
        super(message);
    }

    FtpServiceException(final Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getCause() != null ? getCause().getMessage() : super.getMessage();
    }
}

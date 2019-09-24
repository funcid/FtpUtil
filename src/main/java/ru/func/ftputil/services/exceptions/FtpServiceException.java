package ru.func.ftputil.services.exceptions;

/**
 * @author func 01.09.2019
 */
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

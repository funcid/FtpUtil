package ru.func.ftputil.services.exceptions;

public class FtpServiceException extends Exception {

    public FtpServiceException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getCause().getMessage();
    }
}

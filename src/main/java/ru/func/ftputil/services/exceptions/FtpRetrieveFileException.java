package ru.func.ftputil.services.exceptions;

public class FtpRetrieveFileException extends FtpServiceException {

    public FtpRetrieveFileException(final String message) {
        super(message);
    }

    public FtpRetrieveFileException(final Throwable cause) {
        super(cause);
    }
}

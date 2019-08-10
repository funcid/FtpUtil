package ru.func.ftputil.services.exceptions;

public class FtpSendFileException extends FtpServiceException {

    public FtpSendFileException(String message) {
        super(message);
    }

    public FtpSendFileException(final Throwable cause) {
        super(cause);
    }
}

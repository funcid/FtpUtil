package ru.func.ftputil.services.exceptions;

/**
 * @author func 01.09.2019
 */
public class FtpSendFileException extends FtpServiceException {

    public FtpSendFileException(final String message) {
        super(message);
    }

    public FtpSendFileException(final Throwable cause) {
        super(cause);
    }
}

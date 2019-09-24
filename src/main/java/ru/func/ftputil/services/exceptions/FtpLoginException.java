package ru.func.ftputil.services.exceptions;

/**
 * @author func 01.09.2019
 */
public class FtpLoginException extends FtpServiceException {

    public FtpLoginException(final String message) {
        super(message);
    }

    public FtpLoginException(final Throwable cause) {
        super(cause);
    }
}

package ru.func.ftputil.services.exceptions;

/**
 * @author func 01.09.2019
 */
public class FtpSetupConnectionException extends FtpServiceException {

    public FtpSetupConnectionException(final String message) {
        super(message);
    }

    public FtpSetupConnectionException(final Throwable cause) {
        super(cause);
    }
}

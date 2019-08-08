package ru.func.ftputil.exceptions;

public class FtpConnectException extends Exception {

    public FtpConnectException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getCause().getMessage();
    }
}

package ru.func.ftputil.services;

import ru.func.ftputil.services.exceptions.*;

import java.io.File;

public class FtpServiceFake implements FtpService {

    private String host;
    private String login;

    @Override
    public void connect(final String host) throws FtpConnectException {
        connect(host, 21);
    }

    @Override
    public void connect(final String host, final int port) throws FtpConnectException {
        if (host.toLowerCase().contains("exception"))
            throw new FtpConnectException("Connection error");

        this.host = host;
    }

    @Override
    public boolean login(final String login, final String password) throws FtpLoginException {
        if (password.toLowerCase().contains("exception"))
            throw new FtpLoginException("Login error");
        else {
            this.login = login;
            return !password.toLowerCase().contains("false");
        }
    }

    @Override
    public void setupConnection() throws FtpSetupConnectionException {
        if (login.equalsIgnoreCase("exception"))
            throw new FtpSetupConnectionException("Setup connection error");
    }

    @Override
    public boolean sendFile(final String uploadPath, final File sendingFile) throws FtpSendFileException {
        if (uploadPath.toLowerCase().contains("exception"))
            throw new FtpSendFileException("Send file error");
        else
            return !uploadPath.toLowerCase().contains("false");
    }

    @Override
    public boolean retrieveFile(final File localFile, final String remoteFilePath) throws FtpRetrieveFileException {
        if (remoteFilePath.toLowerCase().contains("exception"))
            throw new FtpRetrieveFileException("Retrieve file error");
        else
            return !remoteFilePath.toLowerCase().contains("false");
    }

    @Override
    public void disconnect() throws FtpDisconnectException {
        if (host.toLowerCase().contains("diserror") || login.toLowerCase().contains("diserror"))
            throw new FtpDisconnectException("Disconnect error");
    }

    @Override
    public String[] listDirNames(final String dir) {
        return dir.toLowerCase().contains("empty") ? new String[0] : new String[]{"dir_1", "dir_2", "dir_3"};
    }

    @Override
    public String[] listFileNames(final String dir) {
        return dir.toLowerCase().contains("empty") ? new String[0] : new String[]{"file_1", "file_2", "file_3"};
    }

    @Override
    public boolean removeServerFile(final String path) {
        return !path.toLowerCase().contains("false");
    }
}

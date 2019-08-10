package ru.func.ftputil.services;

import ru.func.ftputil.services.exceptions.*;

import java.io.File;

public class FtpServiceFake implements FtpService {

    private String host;
    private String login;

    @Override
    public void connect(String host) throws FtpConnectException {
        connect(host, 21);
    }

    @Override
    public void connect(String host, int port) throws FtpConnectException {
        if (host.toLowerCase().contains("exception")) {
            throw new FtpConnectException("Connection error");
        }

        this.host = host;
    }

    @Override
    public boolean login(String login, String password) throws FtpLoginException {
        if (password.toLowerCase().contains("exception")) {
            throw new FtpLoginException("Login error");
        } else {
            this.login = login;
            return !password.toLowerCase().contains("false");
        }
    }

    @Override
    public void setupConnection() throws FtpSetupConnectionException {
        if (login.equalsIgnoreCase("exception")) {
            throw new FtpSetupConnectionException("Setup connection error");
        }
    }

    @Override
    public boolean sendFile(String uploadPath, File sendingFile) throws FtpSendFileException {
        if (uploadPath.toLowerCase().contains("exception")) {
            throw new FtpSendFileException("Send file error");
        } else {
            return !uploadPath.toLowerCase().contains("false");
        }
    }

    @Override
    public boolean retrieveFile(File localFile, String remoteFilePath) throws FtpRetrieveFileException {
        if (remoteFilePath.toLowerCase().contains("exception")) {
            throw new FtpRetrieveFileException("Retrieve file error");
        } else {
            return !remoteFilePath.toLowerCase().contains("false");
        }
    }

    @Override
    public void disconnect() throws FtpDisconnectException {
        if (host.toLowerCase().contains("diserror")
            || login.toLowerCase().contains("diserror")) {
            throw new FtpDisconnectException("Disconnect error");
        }
    }

    @Override
    public String[] listDirNames(String dir) {
        if (dir.toLowerCase().contains("empty")) {
            return new String[0];
        } else {
            return new String[]{"dir_1", "dir_2", "dir_3"};
        }
    }

    @Override
    public String[] listFileNames(String dir) {
        if (dir.toLowerCase().contains("empty")) {
            return new String[0];
        } else {
            return new String[]{"file_1", "file_2", "file_3"};
        }
    }

    @Override
    public boolean removeServerFile(String path) {
        return !path.toLowerCase().contains("false");
    }
}

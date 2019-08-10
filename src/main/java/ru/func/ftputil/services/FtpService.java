package ru.func.ftputil.services;

import ru.func.ftputil.services.exceptions.*;

import java.io.File;

public interface FtpService {

    void connect(String host) throws FtpConnectException;

    void connect(String host, int port) throws FtpConnectException;

    boolean login(String login, String password) throws FtpLoginException;

    void setupConnection() throws FtpSetupConnectionException;

    boolean sendFile(String uploadPath, File sendingFile) throws FtpSendFileException;

    boolean retrieveFile(File localFile, String remoteFilePath) throws FtpRetrieveFileException;

    void disconnect() throws FtpDisconnectException;

    String[] listDirNames(String dir);

    String[] listFileNames(String dir);

    boolean removeServerFile(String path);
}

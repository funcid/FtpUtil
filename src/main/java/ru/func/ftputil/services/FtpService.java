package ru.func.ftputil.services;

import ru.func.ftputil.services.exceptions.*;

import java.io.File;

public interface FtpService {

    void connect(final String host) throws FtpConnectException;

    void connect(final String host, final int port) throws FtpConnectException;

    boolean login(final String login, final String password) throws FtpLoginException;

    void setupConnection() throws FtpSetupConnectionException;

    boolean sendFile(final String uploadPath, final File sendingFile) throws FtpSendFileException;

    boolean retrieveFile(final File localFile, final String remoteFilePath) throws FtpRetrieveFileException;

    void disconnect() throws FtpDisconnectException;

    String[] listDirNames(final String dir);

    String[] listFileNames(final String dir);

    boolean removeServerFile(final String path);
}

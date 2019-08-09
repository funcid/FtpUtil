package ru.func.ftputil.services;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import ru.func.ftputil.services.exceptions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

public class FtpService {

    private static final int DEFAULT_FTP_PORT = 21;
    private static final int DEFAULT_FILE_TYPE = 2;

    private FTPClient client = new FTPClient();

    public void connect(String host) throws FtpConnectException {
        connect(host, DEFAULT_FTP_PORT);
    }

    public void connect(String host, int port) throws FtpConnectException {
        try {
            client.connect(host, port);
        } catch (IOException e) {
            throw new FtpConnectException(e);
        }
    }

    public boolean login(String login, String password) throws FtpLoginException {
        try {
            return client.login(login, password);
        } catch (IOException e) {
            throw new FtpLoginException(e);
        }
    }

    public void setupConnection() throws FtpSetupConnectionException {
        try {
            client.enterLocalPassiveMode();
            client.setFileType(DEFAULT_FILE_TYPE);
            client.enterLocalPassiveMode();
            client.setAutodetectUTF8(true);
        } catch (IOException e) {
            throw new FtpSetupConnectionException(e);
        }
    }

    public boolean sendFile(String uploadPath, File sendingFile) throws FtpSendFileException {
        try (FileInputStream inputStream = new FileInputStream(sendingFile)) {
            return client.storeFile(uploadPath, inputStream);
        } catch (IOException e) {
            throw new FtpSendFileException(e);
        }
    }

    public boolean retrieveFile(File localFile, String remoteFilePath) throws FtpRetrieveFileException {
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            return client.retrieveFile(remoteFilePath, fos);
        } catch (IOException e) {
            throw new FtpRetrieveFileException(e);
        }
    }

    public void disconnect() throws FtpDisconnectException {
        try {
            if (client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        } catch (IOException ex) {
            throw new FtpDisconnectException(ex);
        }
    }

    public String[] listDirNames(final String dir) {
        try {
            String[] names = Stream.of(client.listDirectories(dir))
                    .map(FTPFile::getName)
                    .toArray(String[]::new);
            return names.length == 0 ? new String[] {"Директория не содержит поддеректорий."} : names;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[] {"Директория не содержит поддеректорий."};
    }

    public String[] listFileNames(final String dir) {
        try {
            String[] names = Stream.of(client.listFiles(dir))
                    .filter(FTPFile::isFile)
                    .map(FTPFile::getName)
                    .toArray(String[]::new);
            return names.length == 0 ? new String[] {"Директория не содержит файлов."} : names;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[] {"Директория не содержит файлов."};
    }

    public boolean removeServerFile(final String path) {
        try {
            return client.deleteFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

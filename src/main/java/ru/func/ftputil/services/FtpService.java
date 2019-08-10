package ru.func.ftputil.services;

import ru.func.ftputil.services.exceptions.*;

import java.io.File;

public interface FtpService {

    /**
     * Подключает клиента к серверу по FTP протоколу.
     * @param host адресс хоста или же IP
     * @throws FtpConnectException может выбросить исключение о неудавшимся подключении
     */
    void connect(final String host) throws FtpConnectException;

    /**
     * Подключает клиента к серверу по FTP протоколу. По не стандартному для FTP порта 21, а по порту - 2221
     * @param host адресс хоста или же IP
     * @param port порт для подключения к серверу
     * @throws FtpConnectException может выбросить исключение о неудавшимся подключении
     */
    void connect(final String host, final int port) throws FtpConnectException;

    /**
     * Для аудентификации FTP клиента
     * @param login имя пользователя
     * @param password пароль пользователя
     * @return подключился ли пользователь или же нет
     * @throws FtpLoginException может выбросить исключение о неудавшейся аудетификации
     */
    boolean login(final String login, final String password) throws FtpLoginException;

    /**
     * Устанавливает необходимые параметры FTP подключения и обмена файлами
     * @throws FtpSetupConnectionException может выбросить исключение о неудавшемся установлении параметров
     */
    void setupConnection() throws FtpSetupConnectionException;

    /**
     * Метод осуществляющий отправку файла на сервер по FTP протоколу
     * @param uploadPath директория сервера, в котурую будет загружен файл
     * @param sendingFile отправляемый файл, выбираемый на устройстве FTP клиента
     * @return был ли оправлен файл успешно или же нет
     * @throws FtpSendFileException может выбросить исключение о неудавшейся оправке файла
     */
    boolean sendFile(final String uploadPath, final File sendingFile) throws FtpSendFileException;

    /**
     * Метод осуществляющий выкачку файла с сервера по FTP протоколу
     * @param localFile файл на сервере который будет выгружен
     * @param remoteFilePath директория, в котурую будет скачиваться файл
     * @return был ли скачан файл с сервера или нет
     * @throws FtpRetrieveFileException может выбросить исключение о неудавшейся скачивании файла
     */
    boolean retrieveFile(final File localFile, final String remoteFilePath) throws FtpRetrieveFileException;

    /**
     * Метод отключающий пользователя от сервера
     * @throws FtpDisconnectException может выбросить исключение о неудавшейся закрытии подключения
     */
    void disconnect() throws FtpDisconnectException;

    /**
     * @param dir конечная директория
     * @return список директорий в конечном директории
     */
    String[] listDirNames(final String dir);

    /**
     * @param dir конечная директория
     * @return список файлов в конечном директоии
     */
    String[] listFileNames(final String dir);

    /**
     * Метод осуществляющий удаление файла на сервере
     * @param path путь к удаляемому файлу
     * @return был ли удален файл успешно или же нет
     */
    boolean removeServerFile(final String path);
}

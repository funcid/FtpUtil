<h1 align="center">FTP UTIL</h1>
<h2 align="center">
<a href="https://lgtm.com/projects/g/funcid/FtpUtil/context:java"><img alt="Language grade: Java" src="https://img.shields.io/lgtm/grade/java/g/funcid/FtpUtil.svg?logo=lgtm&logoWidth=18"/></a><a href="https://lgtm.com/projects/g/funcid/FtpUtil/alerts/"><img alt="Total alerts" src="https://img.shields.io/lgtm/alerts/g/funcid/FtpUtil.svg?logo=lgtm&logoWidth=18"/></a>
</h2>
<hr>

## Скачать клиент

 - https://drive.google.com/open?id=1j2LOzurlM9oH3UXswOWnwgMZp_rzA7d9
 - https://clck.ru/HYgGX
 
## Требования

- Java 12
- Maven 3

## Сборка и запуск

```shell
mvn clean package
java -jar ftputil-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Запуск с фейковым FTP клиентом

Такой клиент треубется для ручного тестирования пользовательского интерфейса.

```shell
java -Dftpfake=true -jar ftputil-1.0-SNAPSHOT-jar-with-dependencies.jar
```

#### Ключевые слова для управления фейковым клиентом

Хост/Host:

- наличие слова _"exception"_ будет провоцировать ошибки на этапе **подключения**;
- наличие слова _"diserror"_ будет провоцировать ошибку на этапе **отключения**.

Авторизация/Login:

- наличие слова _"exception"_ в пароле будет провоцировать ошибку на этапе **автроризации**;
- наличие слова _"false"_ в пароле будет провоцировать отказ в **авторизации**;
- наличие слова _"exception"_ в логине будет провоцировать ошибку на этапе **настройки соединения**;
- наличие слова _"diserror"_ в логине будет провоцировать ошибку на этапе **отключения**.

Отправка файлов/Send files:

- наличие слова _"exception"_ в пути удалённого файла (remote path), будет провоцировать ошибку **отправки файла**;
- наличие слова _"false"_ в пути удалённого файла (remote path), будет провоцировать отказ в **отправке файла**.

Получение файлов/Retrieve files:

- наличие слова _"exception"_ в пути удалённого файла (remote path), будет провоцировать ошибку **получения файла**;
- наличие слова _"false"_ в пути удалённого файла (remote path), будет провоцировать отказ в **получении файла**.

Получение списка файлов/List files:

- наличие слова _"empty"_ в пути удалённого файла (remote path), будет провоцировать **вывод пустого списка**.

Получение списка директорий/List directories:

- наличие слова _"empty"_ в пути удалённого файла (remote path), будет провоцировать **вывод пустого списка**.

Удаление удалённого файла/Remove remote file:

- наличие слова _"false"_ в пути удалённого файла (remote path), будет провоцировать **отказ удаления файла**.

package com.geekbrains.cloud.june.cloudapplication;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    private Network network;
    //    @FXML
//    public TextField textField;
    @FXML
    public ListView<String> clientList;
    @FXML
    public ListView<String> serverList;

    @FXML
    public Button buttonOut;

    private final String MYFILE = "C:\\Users\\adyak\\IdeaProjects\\geek-cloud\\cloud-application\\file\\";
    private byte[] bytes;


    public void sendMess(ActionEvent actionEvent) throws IOException {
        // берем текст из TextField и отправляем
        //  String msg = textField.getText();

        // чистим поле
        /// textField.clear();


    }

    //читаем сообщения от срвера
    private void readLoop() {
        try {
            while (true) {

                String command = network.readMessage();
                if (command.equals("#list")) {
                    Platform.runLater(() -> serverList.getItems().clear());
                    int len = network.getInt();
                    for (int i = 0; i < len; i++) {
                        String file = network.readMessage();
                        Platform.runLater(() -> serverList.getItems().add(file));
                        System.out.println(file + "read");
                    }
                }
                else if (command.equals("#list_client")){
                    createFile();
                    Platform.runLater(() -> clientList.getItems().clear());
                    List<String> files = getFiles(MYFILE);
                    for (String file : files) {
                        Platform.runLater(() -> clientList.getItems().add(file));
                    }

                   // Platform.runLater(() -> clientList.getItems().clear());
                  //  Platform.runLater(()-> clientList.getItems().addAll(getFiles(MYFILE));

                  //  clientList.getItems().addAll(getFiles(MYFILE));


                }
//                String msg = network.readMessage();
//                System.out.println(msg);
                // отправляем в лист
                // Platform.runLater(()->listViewServer.getItems().add(msg));
                // network.utFile();
            }

            } catch(Exception e){
                System.out.println("Connection lost");
            }
        }





    @Override
    // этот метод вызывается после инициализации полей fxml
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // создаем массив батов для чтения из файла сначала в буфер
            String dir = MYFILE;
            bytes = new byte[256];
            // передаем пкть к директории и выводим список файлов а лис клиента
            clientList.getItems().addAll(getFiles(MYFILE));

            ///  System.out.println("Users: " + System.getProperty("Users"));
            //ChatController controller = new ChatController();
            network = new Network(8289);
            Thread readThread = new Thread(this::readLoop);

//            String command = network.getIs().readUTF();
//            if (command.equals("#list_client")){
//                createFile();
//            }


            // если приложение остановиться то выпадет ошибка. а с setDaemon то приложение завершается если основной поток завершен
            // если main закрылся и все остальные треды тоже закроются без ошибки
            readThread.setDaemon(true);
            readThread.start();


        } catch (Exception e) {
            System.out.println(e);
        }
    }



    // при нажатии на кнопку  и передаем файлы в список серевера
    public void fileFromServer(ActionEvent actionEvent) throws IOException {
        network.getOut().writeUTF("#file");
        // береь имя файла на который кликнули
        String file = clientList.getSelectionModel().getSelectedItem();


        // отправляем имя файла
        network.getOut().writeUTF(file);
        System.out.println(file + "FileFromServer");
        // находим выбранный файл в папке
        // берем его размер
        File toSend = Path.of(MYFILE).resolve(file).toFile();
        // отправляем размер файла
        network.getOut().writeLong(toSend.length());
        // чтение из файла через try сам потом закроет поток чтения
        try (FileInputStream fis = new FileInputStream(toSend)) {
            // читаем все
            while (fis.available() > 0) {
                int read = fis.read(bytes);
                // пишем все что прочитали
                network.getOut().write(bytes, 0, read);
            }
        }

        network.getOut().flush();
    }

    // отправляем имя файла на сервер
    public void fileFromClient(ActionEvent actionEvent) throws IOException {
        // отравляем команду на срвер
        network.writeMessage("#server_file");
        // берем имя файла на который кликнул
        String fileServer = serverList.getSelectionModel().getSelectedItem();
        //отправляем на срвер
        network.writeMessage(fileServer);
        System.out.println(fileServer);


    }
    private void createFile() throws IOException {

        String fileName = network.readMessage();
        System.out.println("createFile" + fileName);

                    // размер
                    long len = network.getIs().readLong();
                    // берем имя дирректории создаем файл который получили
                    File file = Path.of(MYFILE).resolve(fileName).toFile();
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        for (int i = 0; i < (len + 255) / 256; i++) {
                            int read = network.getIs().read(bytes);
                            fos.write(bytes, 0, read);


                        }



                       // clientList.getItems().addAll(getFiles(MYFILE));
                    }
    }

    public List<String> getFiles(String n){
        String[] list = new File(n).list();
        assert list != null;
        return Arrays.asList(list);
    }
}




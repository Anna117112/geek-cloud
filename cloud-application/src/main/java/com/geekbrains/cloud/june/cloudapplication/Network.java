package com.geekbrains.cloud.june.cloudapplication;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class Network {
    private final int port;
    private DataInputStream is;
    private DataOutputStream os;
    private InputStream isFile;
    private ChatController controller;
    public  FileClient fileClient;

    OutputStream out;
    private final String MYFILE = "C:\\Users\\adyak\\IdeaProjects\\geek-cloud\\cloud-application\\file\\";


    public Network(int port) throws IOException {
        this.port = port;
        //this.controller = chatController;



        Socket socket = new Socket("localhost", port);
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());

    }

    public String readMessage() throws IOException {
        return is.readUTF();
    }

    public int getInt() throws IOException {
        return is.readInt();
    }
// передаем сообщения серверу
    public void writeMessage(String message) throws IOException {

        os.writeUTF(message);
       os.flush();
    }

    public DataOutputStream getOut() {
        return os;
    }
    public DataInputStream getIs(){
        return is;
    }

//    public void outFileServer(String nameFile){
//        // прописываем путь к файлу
//        File file = new File(MYFILE + nameFile );
//        String msg = file.getName();
//        try {
//            // читаем имя файла и отправляем еа сервер
//            os.writeUTF(msg);
//            System.out.println(nameFile + " читаем переданную инфо о файлах");
//            // читаем инфо с файла
//            read(file.getAbsolutePath());
//            // пишем прочитанный текст на сервер
//            writeMessage(read(file.getAbsolutePath()));

// передаем в метод путь к файлу
       //// String message = read(file.getAbsolutePath());
            //System.out.println(message);

       // передаем на записть инфо которую прочитали из файла


//        } catch (IOException e) {
//            throw new RuntimeException("Проблема при работе с историей");
//        }
//
//
//
//    }
//// читаем сообщение из файла
//    public String read(String fileName) {
//        StringBuilder builder = new StringBuilder();
//        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
//            String str;
//            while ((str = in.readLine()) != null) {
//                builder.append(str);
//            }
//            return builder.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("Проблема при работе с историей");
//        }
//    }
//

// перебираем файлы в папке и записываем тх в лист
//    public List<String> listFile() {
//
//        File dir = new File("C:\\Users\\adyak\\IdeaProjects\\geek-cloud\\cloud-application\\file");
//        List<String> fileArLisr = new ArrayList<>();
//
//        File[] files = dir.listFiles();
//
//        if (files != null && files.length > 0) {
//            for (File file : files) {
//                fileArLisr.add(file.getName());
//
//            }
//
//
//        }
//        return fileArLisr;
//    }
//
//
//    //отправляем файл на сервер
//
//    public void utFile()  {
//try {
//
//    int count = 0;
//
//    File file = new File("C:\\Users\\adyak\\IdeaProjects\\geek-cloud\\cloud-application\\file\\text.2");
//    String filename = file.getName();
//    os.writeUTF(filename);
//    InputStream inputStream = new FileInputStream(file);
//    byte[] mybitearr = new byte[60];
//
//    while ((count = inputStream.read(mybitearr)) > 0) {
//        out.write(mybitearr, 0, count);
//    }
//    inputStream.close();
//    out.close();
//}
//catch (Exception e){
//    System.out.println(e);}
//    }


}





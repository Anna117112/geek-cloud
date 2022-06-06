package com.geekbrains.cloud;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChatHandler implements Runnable {
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    OutputStream out;
    InputStream in;
    private final String serverFiles = "C:\\Users\\adyak\\IdeaProjects\\geek-cloud\\cloud-server\\files_server\\";

    public ChatHandler(Socket socket) throws IOException {
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        //in = socket.getInputStream();

        System.out.println("Client accepted");
        //при подключнии клиета вытаскиваем все файлы
        //List<String> files = getFiles(serverFiles);
        // перебераем файлы пишем клиенту
        sendListOfFiles(serverFiles);

    }

    private void sendListOfFiles(String dir) throws IOException {
        os.writeUTF("#list");
        List<String> files = getFiles(serverFiles);
        os.writeInt(files.size());
        for (String file : files) {
            os.writeUTF(file);

            System.out.println(file + "sendListOfFiles");
        }
        os.flush();
    }

    // получаем список файлов из папки серевра
    private List<String> getFiles(String dir) {
        String[] list = new File(dir).list();
        assert list !=null;
        return Arrays.asList(list);
    }


    @Override
    public void run() {
        byte[] buffer = new byte[256];
        try {
            String command = is.readUTF();
            if (command.equals("#file")) {
                // читаем имя файла
                String fileName = is.readUTF();
                System.out.println(fileName+"run");
                // размер
                long len = is.readLong();
                // берем имя дирректории создаем файл который получили
                File file = Path.of(serverFiles).resolve(fileName).toFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    for (int i = 0; i < (len + 255) / 256; i++) {
                        int read = is.read(buffer);
                        fos.write(buffer, 0, read);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sendListOfFiles(serverFiles);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}








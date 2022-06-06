package com.geekbrains.cloud.june.cloudapplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileClient {
    private InputStream in;
    private OutputStream out;
    private final String MYFILE1 = "C:\\Users\\adyak\\IdeaProjects\\geek-cloud\\cloud-application\\file";


    public List<String> listFile() {

        File dir = new File(MYFILE1);
        List<String> fileArLisr = new ArrayList<>();

        File[] files = dir.listFiles();

        if (files != null && files.length > 0) {
            for (File file : files) {
                fileArLisr.add(file.getName());

            }


        }
        return fileArLisr;
    }


    public void init(String fileName) {
        try {
            this.out = new FileOutputStream(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Проблема при работе с историей");
        }
    }

    public void write(String message) {
        try {
            out.write(message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Проблема при работе с историей");
        }
    }

    public String load(String fileName) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String str;
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Проблема при работе с историей");
        }
    }

    public void close() {

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

//    private String getFilename() {
//        return "history/history_" + login + ".txt";
//    }
//}

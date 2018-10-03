package com.example.mich.myfirstapp;

import android.util.Log;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Пока простой самый компонент  по записи в файл, потом разберёмся с потоками, буферами и т.п.
 */
public class DataFileComponent {

    private static final String TAG = "DataFileComponent";
    //    private FileWriter dataFile;
    private String fileName;

    /**
     * Дают имя файла - создать текстовый файл с заголовком
     * /sdcard/Temp
     *
     * @param fileName - имя файла
     */
    public void startRecording(String fileName) {
        this.fileName = fileName;
        try {
            FileWriter dataFile = new FileWriter(fileName, false);
            dataFile.write("Volume");
            dataFile.append('\n');
            dataFile.flush();
        } catch (IOException e) {
            Log.e(TAG, "startRecording failed");
        }
    }

    /**
     * Записать следующее int значение в файл, добавить перевод строки
     *
     * @param value - это будут данные о Громкости
     */
    public void write(int value) {
        try {
            FileWriter dataFile = new FileWriter(fileName, true);
            dataFile.write(String.valueOf(value));  // + CRLF ?
            dataFile.append('\n');
            dataFile.flush();
        } catch (IOException e) {
            Log.e(TAG, "write failed");
        }
    }

    public void stopRecording() {
        try {
            FileWriter dataFile = new FileWriter(fileName, true);
            dataFile.close();
        } catch (IOException e) {
            Log.e(TAG, "stopRecording failed");
        }
    }
}

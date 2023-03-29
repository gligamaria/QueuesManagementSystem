package Controller;

import java.io.FileWriter;
import java.io.IOException;

public class FileManagement {

    private static String file;
    private static FileWriter fileWriter;

    public static void setFile(String file) {
        FileManagement.file = file;
        try {
            fileWriter = new FileWriter(FileManagement.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String string){
        try {
            fileWriter.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFile(){
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

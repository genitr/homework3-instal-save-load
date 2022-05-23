package src;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        StringBuilder gameLog = new StringBuilder();

    }

    public static void createDirectory(File dir, StringBuilder gameLog) {
        if(dir.mkdir()) {
            System.out.println("Директория была создана");
            gameLog.append("Создана директория ~ ").append(dir.getPath()).append("\n");
        }
    }

    public static void createFile(File gameFile, StringBuilder gameLog) {
        try {
            if (gameFile.createNewFile()) {
                System.out.println("Файл был создан");
                gameLog.append("Создан файл в каталоге ~ ").append(gameFile.getPath()).append("\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}

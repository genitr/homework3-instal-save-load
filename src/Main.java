package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();

        List<File> gameDirectories = new ArrayList<>();
        gameDirectories.add(new File("C:\\Games\\src"));
        gameDirectories.add(new File("C:\\Games\\res"));
        gameDirectories.add(new File("C:\\Games\\savegames"));
        gameDirectories.add(new File("C:\\Games\\temp"));
        gameDirectories.add(new File("C:\\Games\\src\\main"));
        gameDirectories.add(new File("C:\\Games\\src\\test"));
        gameDirectories.add(new File("C:\\Games\\res\\drawables"));
        gameDirectories.add(new File("C:\\Games\\res\\vectors"));
        gameDirectories.add(new File("C:\\Games\\res\\icons"));

        List<File> gameFiles = new ArrayList<>();
        gameFiles.add(new File("C:\\Games\\src\\main\\Main.java"));
        gameFiles.add(new File("C:\\Games\\src\\main\\Utils.java"));
        gameFiles.add(new File("C:\\Games\\temp\\temp.txt"));

        for (int i = 0; i < gameDirectories.size(); i++) {
            createDirectory(gameDirectories.get(i), sb);
        }

        for (int i = 0; i < gameFiles.size(); i++) {
            createFile(gameFiles.get(i), sb);
        }

        String gameLog = sb.toString();

        try(FileWriter log = new FileWriter("C:\\Games\\temp\\temp.txt");) {
            log.write(gameLog);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createDirectory(File dir, StringBuilder gameLog) {
        if(dir.mkdir()) {
            System.out.println("Директория была создана");
            gameLog.append("Создана папка ~ ")
                    .append(dir.getName())
                    .append(" [")
                    .append(dir.getPath())
                    .append("] ")
                    .append("\n");
        }
    }

    public static void createFile(File gameFile, StringBuilder gameLog) {
        try {
            if (gameFile.createNewFile()) {
                System.out.println("Файл был создан");
                gameLog.append("Создан файл ~ ")
                        .append(gameFile.getName())
                        .append(" [")
                        .append(gameFile.getPath())
                        .append("] ")
                        .append("\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}

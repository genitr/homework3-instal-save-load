package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    private static String LOG_PATH;
    private static final StringBuilder GAME_LOG = new StringBuilder();

    public static void main(String[] args) {

        // список директорий
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

        String saveDir = String.valueOf(gameDirectories.get(2));

        // список файлов
        List<File> gameFiles = new ArrayList<>();
        gameFiles.add(new File("C:\\Games\\src\\main\\Main.java"));
        gameFiles.add(new File("C:\\Games\\src\\main\\Utils.java"));
        gameFiles.add(new File("C:\\Games\\temp\\temp.txt"));
        LOG_PATH = String.valueOf(gameFiles.get(2));

        gameInstall(gameDirectories, gameFiles);

        List<GameProgress> gameProgress = new ArrayList<>();
        gameProgress.add(new GameProgress(110, 2, 1, 125.6));
        gameProgress.add(new GameProgress(58, 12, 11, 190.0));
        gameProgress.add(new GameProgress(200, 6, 4, 111.11));

        List<String> saveList = new ArrayList<>();

        // Сохранение
        saveGame(saveDir, gameProgress, saveList);
        zipFiles(saveDir, saveList);

        // Загрузка
        openZip(saveDir, saveDir);
        GameProgress loadProgress = openProgress(saveDir);
        System.out.println(loadProgress);
    }

    public static void createDirectory(File dir) {
        if(dir.mkdir()) {
            System.out.println("Директория была создана");
            GAME_LOG.append("Создана папка ~ ")
                    .append(dir.getName())
                    .append(" [")
                    .append(dir.getPath())
                    .append("] ")
                    .append("\n");
        }
    }

    public static void createFile(File gameFile) {
        try {
            if (gameFile.createNewFile()) {
                System.out.println("Файл был создан");
                GAME_LOG.append("Создан файл ~ ")
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

    public static void gameInstall (List<File> directories, List<File> files) {
        for (File gameDirectory : directories) {
            createDirectory(gameDirectory);
        }

        for (File gameFile : files) {
            createFile(gameFile);
        }
        createLog();
    }

    public static void createLog() {
        try(FileWriter logFile = new FileWriter(LOG_PATH)) {
            logFile.write(GAME_LOG.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveGame(String path, List<GameProgress> gameProgress, List<String> saveList) {
        //String name = "\\save";
        for (int i = 0; i < gameProgress.size(); i++) {
            try(FileOutputStream fos = new FileOutputStream(path + "\\save" + (i + 1) + ".dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(gameProgress.get(i));
                } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            saveList.add(path + "\\save" + (i + 1) + ".dat");
        }
    }

    public static void zipFiles(String path, List<String> saveList) {
        String name = "\\save.zip";
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + name))) {
            for (int i = 0; i < saveList.size(); i++) {
                try(FileInputStream fis = new FileInputStream(saveList.get(i))) {
                    ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                File fileToDelete = new File(saveList.get(i));
                if (fileToDelete.exists()) fileToDelete.delete();
                else System.out.println("Файд не найден");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openZip(String zipPath, String path) {
        String zipName = "\\save.zip";
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath + zipName))) {
            ZipEntry entry;
            String fileName;
            while ((entry = zis.getNextEntry()) != null) {
                fileName = entry.getName();
                FileOutputStream fos = new FileOutputStream(path + "\\" + fileName);
                for (int c = zis.read(); c!= -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String path) {
        GameProgress progress = null;
        String fileName = "\\save3.dat";
        try(FileInputStream fis = new FileInputStream(path + fileName)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            progress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return progress;
    }
}

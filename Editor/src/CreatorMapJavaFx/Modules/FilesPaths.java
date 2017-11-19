package CreatorMapJavaFx.Modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;


public class FilesPaths {


    private static String FILE_NAME = "C:\\Users\\battl\\OneDrive\\Documents\\IdeaProjects\\CreatingMap\\src\\pathToFiles";
    private static ObservableList<String> pathsObs;
    private static List<String> paths;
    private static ObservableList<CustomImage> images = FXCollections.observableArrayList();


    public static List<String> getPaths() {
        return paths;
    }

    public static ObservableList<String> getPathsObs() {
        return pathsObs;
    }

    public static ObservableList<CustomImage> getImages() {
        return images;
    }

    public static void getFilesFromPackage(){

        images.clear();

        for(String s: paths) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(s))) {
                for (Path file: stream) {
                    if(!file.toFile().isDirectory() ) {
                        String path = "file:"+ file.getParent()+"\\"+file.getFileName();
                        images.add(new CustomImage(path));
                    }
                }
            } catch (IOException | DirectoryIteratorException x) {
                JOptionPane.showMessageDialog(null,(x.getMessage()+"Directory of path is incorrect"));
            }
        }
    }

    public static void deleteFromListAndFile(String s){

        for(int i = 0;i<paths.size();i++)
            if (paths.get(i).equals(s))
                paths.remove(i);

        //удаление и создание нового файла
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try {
                file.delete();
                file.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, (e.getMessage() + " Error creating/deleting file"));
            }
        }

        //добавление первой строки в файл
        if(paths.size() != 0) {
            try {
                Files.write(Paths.get(FILE_NAME), paths.get(0).getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, (e.getMessage() + " Error creating new file"));
            }
        }

        //добавление в файл остальных строк
        for(int i = 1;i<paths.size();i++) {
            try {
                Files.write(Paths.get(FILE_NAME),("\n"+paths.get(i)).getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, (e.getMessage() + " Error adding string to file"));
            }
        }
        readPathFiles();
    }

    public static void addPathToListAndFile(String s){

        String nl="";
        if(paths.size() != 0)
            nl="\n";

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(s))) {
            try {
                Files.write(Paths.get(FILE_NAME),((nl+s).getBytes()), StandardOpenOption.APPEND);
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(null,(e.getMessage()+" Error adding to file"));
            }
        } catch (IOException | DirectoryIteratorException x) {
            JOptionPane.showMessageDialog(null,(x.getMessage()+" Directory of path is incorrect"));
        }


        readPathFiles();
    }

    public static void readPathFiles() {
        try {
            paths = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);
            pathsObs = FXCollections.observableArrayList(paths);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,(e.getMessage()+"Error reading file"));
        }
        getFilesFromPackage();
    }
}


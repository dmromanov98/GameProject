package CreatorMapJavaFx.Modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.*;

public class GettingImagesObj {

    public static ObservableList<CustomImage> getPaths(String directoryPath,String packageName){

        ObservableList<CustomImage> images = FXCollections.observableArrayList();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath))) {
            for (Path file: stream) {
                if(!file.toFile().isDirectory() ) {
                    String path = directoryPath+"\\"+file.getFileName();
                    images.add(new CustomImage(path,packageName+"\\"+file.getFileName()));
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            JOptionPane.showMessageDialog(null,(x.getMessage()+"Directory of path is incorrect"));
        }
        return images;
    }


}

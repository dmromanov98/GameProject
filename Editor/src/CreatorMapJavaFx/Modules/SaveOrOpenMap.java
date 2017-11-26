package CreatorMapJavaFx.Modules;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SaveOrOpenMap {

    private static String defaultPath = "Editor/maps";

    public static void saveMap(String json, String path) {
        if (!path.equals("")) {
            path = defaultPath + "/" + path;
            if (new File(path).exists()) {
                int b = JOptionPane.showConfirmDialog(null,
                        "WARNING", "This file is already exist in current directory.Do you want to continue?", JOptionPane.YES_NO_OPTION);
                //1-no
                //0-yes
                if (b == 0) {
                    File file = new File(path);
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Can't create map file");
                    }
                    rewriteFile(json, path);
                }
            } else {
                rewriteFile(json, path);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Enter the name of the card!");
        }
    }

    public static void rewriteFile(String json, String path) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(path, true));
            bw.append(json);
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error rewrite file");
        }
    }

    public static String openFile(String path) {
        String res = null;
        if (!path.equals("")) {
            path = defaultPath + "/" + path;

            try {
                List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
                res = lines.get(0);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error read/find file");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Enter the name of the card!");
        }
        return res;
    }

}

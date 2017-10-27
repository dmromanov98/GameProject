package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class File
{
    private File(){}

    public static String loadAsString(String file)
    {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer + '\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }



   /* Эти листы я буду использовать для мгновенной загрузки кучи всяких шейдеров.
    * Пишу в одном месте список, потом подгружаю.
    * названия в списке будут и названиями файлов, и путями к ним.
    */
    public static Vector<Textfile> loadTextfilesFromAList(String file)
    {
        Vector<Textfile> res = new Vector<>();
        String prefix = file.substring(0, file.lastIndexOf('/')+1);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                res.add( new Textfile(buffer.substring(0, buffer.lastIndexOf('.')), loadAsString(prefix + buffer)) );
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

}

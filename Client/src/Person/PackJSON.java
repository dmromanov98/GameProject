package Person;

import com.google.gson.Gson;

public class PackJSON {
    public static String pack(Person p) {
        Gson gson = new Gson();
        String json = gson.toJson(p);
        return json;
    }
}

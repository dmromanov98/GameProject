package Module;


import CreatorMapJavaFx.Modules.CustomImage;
import CreatorMapJavaFx.Modules.GettingImagesObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameCharacters {

    private static int currentCharacter = -1;

    private static ObservableList<CustomImage> characters = FXCollections.observableArrayList();

    private static final String charactersPath = "Editor/resources/characters";

    public static CustomImage setNextCharacter(){
        currentCharacter++;

        if(currentCharacter == characters.size()-1)
            currentCharacter = 0;

        return characters.get(currentCharacter);
    }

    public static CustomImage getCurrentCharacter(){
        return characters.get(currentCharacter);
    }

    public static ObservableList<CustomImage> getImages() {
        return characters;
    }

    public static void setCharactersPaths() {
        characters = GettingImagesObj.getPaths(charactersPath,"characters");
    }
}

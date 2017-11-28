package CreatorMapJavaFx.Modules;

import java.util.ArrayList;

import Module.GameCharacters;

//текстуры
public class TexturesInfo {
    private static String[] textures;

    public static String[] getTextures() {
        return textures;
    }

    public static void getAllTextures() {
        ArrayList<String> allTextures = new ArrayList<>();

        BackgroundCreatorJavaFx.setBackgroundPaths();
        DecalsCreatorJavaFx.setDecalsPaths();
        SpritesCreatorJavaFx.setSpritesPaths();

        GameCharacters.setCharactersPaths();

        int n = BackgroundCreatorJavaFx.getImages().size() +
                DecalsCreatorJavaFx.getImages().size() + SpritesCreatorJavaFx.getImages().size() +
                GameCharacters.getImages().size();

        textures = new String[n];

        for (int i = 0; i < BackgroundCreatorJavaFx.getImages().size(); i++) {
            allTextures.add(BackgroundCreatorJavaFx.getImages().get(i).getKey() + "|" + BackgroundCreatorJavaFx.getImages().get(i).getPath());
        }
        for (int i = 0; i < DecalsCreatorJavaFx.getImages().size(); i++) {
            allTextures.add(DecalsCreatorJavaFx.getImages().get(i).getKey() + "|" + DecalsCreatorJavaFx.getImages().get(i).getPath());
        }
        for (int i = 0; i < SpritesCreatorJavaFx.getImages().size(); i++) {
            allTextures.add(SpritesCreatorJavaFx.getImages().get(i).getKey() + "|" + SpritesCreatorJavaFx.getImages().get(i).getPath());
        }

        for (int i = 0; i < GameCharacters.getImages().size(); i++) {
            allTextures.add(GameCharacters.getImages().get(i).getKey() + "|" + GameCharacters.getImages().get(i).getPath());
        }

        for (int i = 0; i < n; i++)
            textures[i] = allTextures.get(i);
    }
}

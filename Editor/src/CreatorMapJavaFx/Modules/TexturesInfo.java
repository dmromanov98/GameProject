package CreatorMapJavaFx.Modules;

import java.util.ArrayList;



//текстуры для opengl
public class TexturesInfo {

    public static String[] getAllTextures(){
        ArrayList<String> allTextures = new ArrayList<>();
        BackgroundCreatorJavaFx.setBackgroundPaths();
        DecalsCreatorJavaFx.setDecalsPaths();
        SpritesCreatorJavaFx.setSpritesPaths();

        int n = BackgroundCreatorJavaFx.getImages().size()+
                DecalsCreatorJavaFx.getImages().size()+SpritesCreatorJavaFx.getImages().size();

        String [] textures = new String[n];

        for(int i = 0;i<BackgroundCreatorJavaFx.getImages().size();i++){
            allTextures.add(BackgroundCreatorJavaFx.getImages().get(i).getKey()+"|"+BackgroundCreatorJavaFx.getImages().get(i).getPath());
        }
        for(int i = 0;i<DecalsCreatorJavaFx.getImages().size();i++){
            allTextures.add(DecalsCreatorJavaFx.getImages().get(i).getKey()+"|"+DecalsCreatorJavaFx.getImages().get(i).getPath());
        }
        for(int i = 0;i<SpritesCreatorJavaFx.getImages().size();i++){
            allTextures.add(SpritesCreatorJavaFx.getImages().get(i).getKey()+"|"+SpritesCreatorJavaFx.getImages().get(i).getPath());
        }

        for(int i = 0;i<n;i++)
            textures[i] = allTextures.get(i);

        return textures;
    }
}

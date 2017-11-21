package Editor;

public class TestMain
{

    public static void main(String[] args) {
        String[] strings = {};
        EditorThread gt = new EditorThread(800, 600, 60, strings);

        /*Game game = gt.game;
        try {
            game.textureBank.addFromDisk("dick", "Engine/test_resources/dick.png");
            game.textureBank.addFromDisk("space", "Engine/test_resources/cassiopeia1280.jpeg");
            game.textureBank.addFromDisk("planet1", "Engine/test_resources/planet1.png");
            game.textureBank.addFromDisk("planet2", "Engine/test_resources/planet2.png");
            game.textureBank.addFromDisk("planet3", "Engine/test_resources/planet3.png");
            game.textureBank.addFromDisk("planet4", "Engine/test_resources/planet4.png");
        } catch (Exception e) { e.printStackTrace(); return;}

        try {
            gt.editor.addDecal(new Decal( new Transform(.5f, 90, 40), game.textureBank.Get("planet1").getTexture() ));

            Sprite sprite = new Sprite() {
                @Override
                public void update() {}
            };
            sprite.transform.setLayer(.51f).setScale(50,50).setPosition(20, 20);
            sprite.texture = game.textureBank.Get("dick").getTexture();
            gt.editor.addActor(sprite);
        } catch (Exception e){e.printStackTrace();}*/

        gt.start();
    }
}

package Main;

import Graphics.Texture;
import Utils.File;

import java.util.HashMap;

public class TextureBank
{
    public TextureBank()
    {
        bank = new HashMap<>();
    }

    public void addFromDisk(String name, String path) throws ThisNameIsEngagedException
    {
        if (!bank.containsKey(name)) {
            bank.put(name, new TextureWrap(path));
        } else {
            throw new ThisNameIsEngagedException("Texture called '" + name + "' is already exist.");
        }
    }

    public TextureWrap Get(String name) throws NonExistentTextureException
    {
        if (bank.containsKey(name))
            return bank.get(name);
        else
            throw new NonExistentTextureException("Texture called '" + name + "' is not exist.");
    }

    public static class TextureWrap
    {
        public TextureWrap(String path)
        {
            state = 0;
            this.path = path;
        }

        public final String path;
        private byte state; // 0 - on a disk, 1 - in RAM, 2 - ready to use
        private File.Image image;
        private Texture texture;

        public TextureWrap downloadToRAM()
        {
            image = new File.Image(path);
            state = 1;
            return this;
        }

        public TextureWrap prepareToUse()
        {
            if (state == 0)
                downloadToRAM();
            if (state == 1)
                texture = new Texture(image);
            state = 2;
            return this;
        }

        public TextureWrap deleteFromVideocard()
        {
            if (state == 2) {
                texture.delete();
                texture = null;
                state = 1;
            }
            return this;
        }

        public void deleteFromRAM()
        {
            deleteFromVideocard();
            image = null;
            state = 0;
        }

        public Texture getTexture()
        {
            if (state != 2)
                prepareToUse();
            return texture;
        }
    }

    public static class ThisNameIsEngagedException extends Exception
    {
        public ThisNameIsEngagedException(String message)
        {
            super(message);
        }
    }

    public static class NonExistentTextureException extends Exception
    {
        public NonExistentTextureException(String message)
        {
            super(message);
        }
    }

    private HashMap<String, TextureWrap> bank;
}

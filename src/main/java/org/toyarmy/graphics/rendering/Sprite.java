package org.toyarmy.graphics.rendering;

/**
 * Created by Ramon Brand on 4/15/2017.
 */
public class Sprite {

    private Texture texture;

    public Sprite(Texture texture){
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}

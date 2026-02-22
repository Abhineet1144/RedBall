package redball.engine.entity.components;

import redball.engine.renderer.texture.Texture;

public class SpriteRenderer extends Component {
    private Texture texture;

    public SpriteRenderer(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void update(float dt) {

    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
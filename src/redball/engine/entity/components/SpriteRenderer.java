package redball.engine.entity.components;

import redball.engine.renderer.texture.Texture;

public class SpriteRenderer extends Component {
    private Texture texture;
    public String filePath = "";

    public SpriteRenderer(Texture texture) {
        this.texture = texture;
        if (texture != null) {
            this.filePath = texture.getFilePath();
        }
    }

    @Override
    public void update(float dt) {

    }

    public Texture getTexture() {
        if (texture != null) {
            return texture;
        }
        return null;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        if (texture != null) {
            this.filePath = texture.getFilePath();
        }
    }

    public String getFilePath() {
        return filePath;
    }
}
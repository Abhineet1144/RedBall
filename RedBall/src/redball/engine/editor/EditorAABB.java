package redball.engine.editor;

public class EditorAABB {
    public float x;
    public float y;
    public float width;
    public float height;

    public EditorAABB(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + width &&
                y >= this.y && y <= this.y + height;
    }
}

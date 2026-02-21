package redball.engine.entity.components;

public class SpriteRenderer extends Component {
    public static final int[] QUAD = new int[] { 0, 1, 2, 2, 3, 0 };
    public static final int[] TRIANGLE = new int[] { 0, 1, 2 };

    public Vertex[] vertices;
    public int[] eboVal;

    public SpriteRenderer(Vertex[] vertices, int[] eboVal) {
        this.vertices = vertices;
        this.eboVal = eboVal;
    }

    @Override
    public void update(float dt) {

    }

    public static class Vertex {
        public float x;
        public float y;
        public float z;
        public float r;
        public float g;
        public float b;
        public float a;

        public Vertex(float x, float y, float z, float r, float g, float b, float a) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;        }
    }
}
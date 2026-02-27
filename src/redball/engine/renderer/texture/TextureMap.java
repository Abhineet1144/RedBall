package redball.engine.renderer.texture;

public enum TextureMap {

    BACKGROUND("resources/background.png"),
    BALL("resources/ball.png"),
    TEST1("resources/ground.png"),
    TEST2("resources/red.jpeg");

    private String filepath;

    TextureMap(String filepath) {
        this.filepath = filepath;
    }

    public String getFilePath() {
        return filepath;
    }
}

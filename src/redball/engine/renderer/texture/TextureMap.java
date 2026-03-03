package redball.engine.renderer.texture;

public enum TextureMap {

    BACKGROUND("src/redball/example/assets/background.png"),
    BALL("src/redball/example/assets/ball.png"),
    TEST1("src/redball/example/assets/ground.png"),
    TEST2("src/redball/example/assets/red.jpeg");

    private String filepath;

    TextureMap(String filepath) {
        this.filepath = filepath;
    }

    public String getFilePath() {
        return filepath;
    }
}

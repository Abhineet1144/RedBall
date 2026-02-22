package redball.engine.renderer.texture;

public enum TextureMap {

    BACKGROUND("resources/background.png"),
    BALL("resources/ball.png"),
    TEST1("resources/container.jpg"),
    TEST2("resources/red.jpeg");

    private String filepath;

    TextureMap(String filepath) {
        this.filepath = filepath;
    }

    public String getFilePath() {
        return filepath;
    }
}

package redball.engine.entity.components;

public class Tag extends Component {
    String tag;

    public Tag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public void update(float dt) {

    }
}

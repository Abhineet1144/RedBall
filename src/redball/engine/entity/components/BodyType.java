package redball.engine.entity.components;

import org.dyn4j.geometry.MassType;

public enum BodyType {
    STATIC(MassType.INFINITE),
    DYNAMIC(MassType.NORMAL),
    KINEMATIC(MassType.FIXED_LINEAR_VELOCITY);

    private final MassType massType;

    BodyType(MassType massType) {
        this.massType = massType;
    }

    public MassType getMassType() { return massType; }
}

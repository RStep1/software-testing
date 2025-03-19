package com.mycompany.app.domainmodel.mice;

/*
 * This class helps to create instance of the real service (OmnidimensionalCreature) under the guise of proxy (EarthMouse).
 */
public class MiceProjectionManager {
    private final Mouse mouse;
    private final Mouse earthMouse;

    public MiceProjectionManager() {
        this.mouse = new OmnidimensionalCreature();
        this.earthMouse = new EarthMouse(mouse);
    }

    public Mouse getEarthMouse() {
        return this.earthMouse;
    }

    public Mouse getMouse() {
        return this.mouse;
    }
}

package com.mycompany.app.domainmodel;

import org.junit.jupiter.api.Test;

import com.mycompany.app.domainmodel.entity.Meal;
import com.mycompany.app.domainmodel.entity.animate.Entity;
import com.mycompany.app.domainmodel.entity.animate.Human;
import com.mycompany.app.domainmodel.entity.intangible.Disease;
import com.mycompany.app.domainmodel.memento.History;
import com.mycompany.app.domainmodel.mice.EarthMouse;
import com.mycompany.app.domainmodel.mice.Mouse;
import com.mycompany.app.domainmodel.mice.OmnidimensionalCreature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProxyMouseTest {

    @Test
    public void givenTwoProxyMice_thenDescriptionEquals() {
        Mouse omnidimensionalMouse = new OmnidimensionalCreature();
        Mouse earthMouse1 = new EarthMouse(omnidimensionalMouse);
        Mouse earthMouse2 = new EarthMouse(omnidimensionalMouse);

        earthMouse1.eat(Meal.CHEESE, true);

        final String expected = "This is just a projection onto our dimension of huge hyper-intelligent omnidimensional beings.";

        final String actual1 = earthMouse1.getDescription();
        final String actual2 = earthMouse2.getDescription();

        assertEquals(expected, actual1);
        assertEquals(expected, actual2);
    }

    @Test
    public void whenMouseDiy_thenCumulativeEffectEquals() {
        Mouse omnidimensionalMouse = new OmnidimensionalCreature();
        Mouse earthMouse = new EarthMouse(omnidimensionalMouse);

        int actualCumulativeEffect = earthMouse.die(Disease.MYXOMATOSIS);

        final int expectedCumulativeEffect = 5;

        assertEquals(expectedCumulativeEffect, actualCumulativeEffect);
    }

    @Test
    public void givenHuman_whenMouseHit_thenLogContains() {
        Mouse omnidimensionalMouse = new OmnidimensionalCreature();
        Mouse earthMouse = new EarthMouse(omnidimensionalMouse);

        Entity bob = new Human("Bob", 0);
        History history = earthMouse.hit(bob);

        assertTrue(history.circumstances().contains("hit the com.mycompany.app.domainmodel.entity.animate.Human"));
    }

    
}

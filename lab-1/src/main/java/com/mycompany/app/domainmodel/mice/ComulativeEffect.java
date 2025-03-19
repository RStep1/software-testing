package com.mycompany.app.domainmodel.mice;

import java.util.Map;
import java.util.TreeMap;

public class ComulativeEffect {
    private int comulativeEffect;
    private static final Map<Integer, String> EFFECT_DESCRIPTION = new TreeMap<>();

    static {
        EFFECT_DESCRIPTION.put(5, "tiny");
        EFFECT_DESCRIPTION.put(10, "small");
        EFFECT_DESCRIPTION.put(15, "big");
        EFFECT_DESCRIPTION.put(20, "huge");
    }

    public ComulativeEffect() {
        comulativeEffect = 0;
    }

    public void applyToEffect(int amount) {
        comulativeEffect += amount;
    }

    public String getApproxDescription() {
        for (Map.Entry<Integer, String> entry : EFFECT_DESCRIPTION.entrySet()) {
            if (comulativeEffect < entry.getKey()) {
                return entry.getValue();
            }
        }
        return "enormous";
    }
}

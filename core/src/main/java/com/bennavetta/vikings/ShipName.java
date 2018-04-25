package com.bennavetta.vikings;

import com.badlogic.gdx.math.MathUtils;

/**
 * Procedurally-generated ship names
 */
public class ShipName
{
    private static final String[] DESCRIPTORS = {
            "Long",
            "Wide",
            "Hungry",
            "Angry",
            "Gold",
            "Red",
            "White",
            "Black",
            "Silver",
            "Sharp",
            "Fierce",
            "Odin's",
            "Thor's",
            "Frey's",
            "Baldur's",
            "Heimdall's",
            "Happy",
            "Thirsty",
            "Silent",
            "Nail"
    };

    private static final String[] BASES = {
            "Prow",
            "Keel",
            "Serpent",
            "Dragon",
            "Boar",
            "Wolf",
            "Snake",
            "Raven",
            "Eagle",
            "Farer",
            "Stallion",
            "Seal",
            "Narwhal",
            "Bear",
            "Ship",
            "Fish",
            "Boat",
            "Oak",
            "Oar",
            "Pine",
            "Ash"
    };

    public static String generate()
    {
        return DESCRIPTORS[MathUtils.random(DESCRIPTORS.length-1)] + " " + BASES[MathUtils.random(BASES.length-1)];
    }
}

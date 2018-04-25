package com.bennavetta.vikings;

import com.badlogic.gdx.math.MathUtils;

public class MonasteryName
{
    private static final String[] NAMES = {
            "Saint Anne's",
            "Saint Conant's",
            "Saint Marius'",
            "Saint Paul's",
            "Saint Olaf's",
            "Saint Margaret's",
            "Saint Stephen's",
            "Lindisfarne"
    };

    public static String generate()
    {
        String saint = NAMES[MathUtils.random(NAMES.length-1)];
        float r = MathUtils.random();
        if (r < 0.5)
        {
            return saint + " Abbey";
        }
        else
        {
            return saint + " Convent";
        }
    }
}

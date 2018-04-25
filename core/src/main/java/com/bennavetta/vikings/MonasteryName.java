package com.bennavetta.vikings;

import com.badlogic.gdx.math.MathUtils;

public class MonasteryName
{
    private static final String[] NAMES = {
            "Anne's",
            "Conant's",
            "Marius'",
            "Paul's",
            "Olaf's",
            "Margaret's",
            "Stephen's"
    };

    public static String generate()
    {
        String saint = "Saint " + NAMES[MathUtils.random(NAMES.length-1)];
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

package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Combat-related stats about an entity
 */
public class CombatComponent implements Component, Poolable
{
    /**
     * The strength (in abstract units) with which this entity can attack other entities. Generally, this will be
     * proportional to the number of warriors in a raiding party and 0 for settled structures.
     */
    public int attackStrength = 1;

    /**
     * The strength (in abstract units) with which this entity can defend against an attack. Generally, this is low
     * for raiding parties and dependent on the amount of fortification for structures.
     */
    public int defenseStrength = 1;

    /**
     * The intact-ness / number of remaining warriors / etc. possessed by an entity. In combat, this is reduced by
     * attacks in relation to the respective attack and defense strengths of the involved entities.
     */
    public int healthPoints = 1;

    public int maxHealthPoints = 1;

    /**
     * Whose "side" this entity is on. This is a pretty big oversimplification, but it's basically to capture which
     * entities the player can attack and which might want to attack the player themselves.
     */
    public Affinity affinity;

    @Override
    public void reset()
    {
        attackStrength = 1;
        defenseStrength = 1;
        healthPoints = 1;
        maxHealthPoints = 1;
        affinity = null;
    }

    /*
     * HOW TO IMPLEMENT:
     *
     * Have some component tagging raiding ships. The raiding system will, for each ship, look for targets within their
     * range and attack them. There can be per-entity policies on the component, like their attack range, the strength/health
     * difference necessary for them to attack vs. run, etc.
     *
     * When defeated, an entity transfers some proportion of its resources to the victor. With some probability, they
     * should either be reset to some low-ish health or destroyed (make ships more likely to be destroyed and emporia, etc.
     * more likely to remain)
     *
     * HOW TO DO MOVEMENT:
     * Ships have a simple route-to-destination AI. When they reach a destination, there's some measure of how long they
     * stay, and then the player can pick an new destination for them. How long to stay could be done by the raiding and
     * trading systems, as part of their calculations of whether or not there are juicy targets or trade partners nearby
     */

    /**
     * Represents which "side" an entity is on - basically, whether or not it would fight with the player or is a valid
     * target for the player to attack.
     */
    public enum Affinity
    {
        /**
         * The entity "belongs" to the player - it's one of their ships or settlements, and they can't attack it
         */
        PLAYER,

        /**
         * An enemy of the player, such as the military of a sufficiently-angered kingdom. Will attack the player.
         */
        HOSTILE,

        /**
         * A neutral party, like a merchant. Won't attack the player, but can be attacked.
         */
        NEUTRAL
    }
}

package com.bennavetta.vikings.engine.components;

/**
 * The kinds of entities in the game
 */
public enum EntityType
{
    CENTRAL_PLACE(true),
    SHIP(false),
    EMPORIUM(true),
    TOWN(true),
    MONASTERY(false)
    ;

    /**
     * Is this entity a non-mobile structure, like a monastery or town?
     */
    public final boolean isStructure;

    private EntityType(boolean isStructure)
    {
        this.isStructure = isStructure;
    }
}

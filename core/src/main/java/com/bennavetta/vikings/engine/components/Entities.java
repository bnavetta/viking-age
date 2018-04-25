package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.bennavetta.vikings.engine.systems.Combat;

/**
 * Helpers for creating new entities of common types.
 */
public class Entities
{
    private Entities() {}

    public static Entity newCentralPlace(PooledEngine engine, String name, float x, float y)
    {
        Entity ent = engine.createEntity();

        IdentityComponent id = engine.createComponent(IdentityComponent.class);
        id.type = EntityType.CENTRAL_PLACE;
        id.name = name;
        ent.add(id);

        PopulationComponent pop = engine.createComponent(PopulationComponent.class);
        ent.add(pop);

        ResourcesComponent res = engine.createComponent(ResourcesComponent.class);
        ent.add(res);

        LocationComponent loc = engine.createComponent(LocationComponent.class);
        loc.coordinates.set(x, y);
        ent.add(loc);

        CombatComponent combat = engine.createComponent(CombatComponent.class);
        combat.healthPoints = 200;
        combat.maxHealthPoints = 200;
        combat.attackStrength = 0;
        combat.defenseStrength = 100;
        combat.affinity = CombatComponent.Affinity.PLAYER;
        ent.add(combat);

        return ent;
    }

    public static Entity newShip(PooledEngine engine, String name, CombatComponent.Affinity affinity, float x, float y)
    {
        Entity ent = engine.createEntity();

        IdentityComponent id = engine.createComponent(IdentityComponent.class);
        id.type = EntityType.SHIP;
        id.name = name;
        ent.add(id);

        LocationComponent loc = engine.createComponent(LocationComponent.class);
        loc.coordinates.set(x, y);
        ent.add(loc);

        ResourcesComponent res = engine.createComponent(ResourcesComponent.class);
        ent.add(res);

        CombatComponent combat = engine.createComponent(CombatComponent.class);
        combat.affinity = affinity;
        ent.add(combat);

        NavigationComponent nav = engine.createComponent(NavigationComponent.class);
        nav.speed = 15f;
        ent.add(nav);

        return ent;
    }

    public static Entity newRaidingShip(PooledEngine engine, String name, CombatComponent.Affinity affinity, float x, float y)
    {
        Entity ent = newShip(engine, name, affinity, x, y);

        RaiderComponent raid = engine.createComponent(RaiderComponent.class);
        raid.attackRange = 30f;
        ent.add(raid);

        CombatComponent cc = Mappers.combat.get(ent);
        cc.healthPoints = 70;
        cc.maxHealthPoints = 70;
        cc.attackStrength = 30;
        cc.defenseStrength = 7;

        return ent;
    }

    public static Entity newEmporium(PooledEngine engine, String name, float x, float y)
    {
        Entity ent = engine.createEntity();
        ent.add(new EmporiumComponent());

        IdentityComponent id = engine.createComponent(IdentityComponent.class);
        id.type = EntityType.EMPORIUM;
        id.name = name;
        ent.add(id);

        LocationComponent location = engine.createComponent(LocationComponent.class);
        location.coordinates.set(x, y);
        ent.add(location);

        ResourcesComponent resources = engine.createComponent(ResourcesComponent.class);
        ent.add(resources);

        TradeComponent trade = engine.createComponent(TradeComponent.class);
        ent.add(trade);

        // You *can* attack an emporium, but it takes a while
        CombatComponent cc = engine.createComponent(CombatComponent.class);
        cc.affinity = CombatComponent.Affinity.NEUTRAL; // won't attack but can be attacked
        cc.healthPoints = 250;
        cc.maxHealthPoints = 250;
        cc.attackStrength = 0;
        cc.defenseStrength = 5;

        return ent;
    }

    public static Entity newTown(PooledEngine engine, String name, float x, float y)
    {
        Entity entity = engine.createEntity();
        entity.add(new TownComponent());

        IdentityComponent id = engine.createComponent(IdentityComponent.class);
        id.type = EntityType.TOWN;
        id.name = name;
        entity.add(id);

        LocationComponent location = engine.createComponent(LocationComponent.class);
        location.coordinates.set(x, y);
        entity.add(location);

        ResourcesComponent resources = engine.createComponent(ResourcesComponent.class);
        entity.add(resources);

        CombatComponent combat = engine.createComponent(CombatComponent.class);
        combat.affinity = CombatComponent.Affinity.HOSTILE;
        combat.healthPoints = 150;
        combat.maxHealthPoints = 150;
        combat.attackStrength = 0;
        combat.defenseStrength = 10;
        entity.add(combat);

        return entity;
    }

    public static Entity newMonastery(PooledEngine engine, String name, float x, float y)
    {
        Entity entity = engine.createEntity();

        IdentityComponent id = engine.createComponent(IdentityComponent.class);
        id.type = EntityType.MONASTERY;
        id.name = name;
        entity.add(id);

        LocationComponent location = engine.createComponent(LocationComponent.class);
        location.coordinates.set(x, y);
        entity.add(location);

        ResourcesComponent resources = engine.createComponent(ResourcesComponent.class);
        resources.silver = MathUtils.random(2000);
        resources.wood = MathUtils.random(500);
        resources.meat = MathUtils.random(700);
        entity.add(resources);

        CombatComponent combat = engine.createComponent(CombatComponent.class);
        combat.affinity = CombatComponent.Affinity.HOSTILE;
        combat.healthPoints = 100;
        combat.maxHealthPoints = 100;
        combat.attackStrength = 0;
        combat.defenseStrength = 5;
        entity.add(combat);

        return entity;
    }
}

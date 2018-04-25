package com.bennavetta.vikings.engine.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.bennavetta.vikings.engine.components.CombatComponent;
import com.bennavetta.vikings.engine.components.IdentityComponent;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.ResourcesComponent;

/**
 * Common functionality for implementing combat
 */
public class Combat
{
    public static void fight(Engine engine, Entity attacker, Entity target)
    {
        CombatComponent attackerCombat = Mappers.combat.get(attacker);
        CombatComponent targetCombat = Mappers.combat.get(target);

        int strengthDifference = attackerCombat.attackStrength - targetCombat.defenseStrength;

        // Upper bounds on the damage inflicted by the attacker and defender on the other, respectively
        int attackBound, defenseBound;

        if (strengthDifference > 30) // 31+
        {
            attackBound = attackerCombat.attackStrength;
            defenseBound = targetCombat.defenseStrength / 4;
        }
        else if (strengthDifference > 20) // 21 to 30
        {
            attackBound = (3 * attackerCombat.attackStrength) / 4;
            defenseBound = targetCombat.defenseStrength / 2;
        }
        else if (strengthDifference > 0) // 1 to 20
        {
            attackBound = attackerCombat.attackStrength / 4;
            defenseBound = targetCombat.defenseStrength / 4;
        }
        else if (strengthDifference > -10) // -19 to 0
        {
            attackBound = attackerCombat.attackStrength / 4;
            defenseBound = targetCombat.defenseStrength / 4;
        }
        else if (strengthDifference > -30) // -20 to -29
        {
            attackBound = attackerCombat.attackStrength / 2;
            defenseBound = (3 * targetCombat.defenseStrength) / 4;
        }
        else // -30 and down
        {
            attackBound = attackerCombat.attackStrength / 4;
            defenseBound = targetCombat.defenseStrength;
        }

        int targetDamage = MathUtils.random(attackBound);
        int attackerDamage = MathUtils.random(defenseBound);
        targetCombat.healthPoints -= targetDamage;
        attackerCombat.healthPoints -= attackerDamage;

        IdentityComponent aId = Mappers.id.get(attacker);
        IdentityComponent tId = Mappers.id.get(target);
        Gdx.app.log("Combat", String.format("Dealing %d damage to '%s' (%s)", attackerDamage, aId.name, aId.type));
        Gdx.app.log("Combat", String.format("Dealing %d damage to '%s' (%s)", targetDamage, tId.name, tId.type));

        boolean attackerDied = attackerCombat.healthPoints <= 0;
        boolean targetDied = targetCombat.healthPoints <= 0;

        if (attackerDied && targetDied)
        {
            handleDeath(engine, attacker);
            handleDeath(engine, target);
        }
        else if (attackerDied)
        {
            transferResources(attacker, target);
            handleDeath(engine, attacker);
        }
        else if (targetDied)
        {
            transferResources(target, attacker);
            handleDeath(engine, target);
        }
    }

    private static void transferResources(Entity from, Entity to)
    {
        ResourcesComponent fromRes = Mappers.resources.get(from);
        ResourcesComponent toRes = Mappers.resources.get(to);

        if (fromRes == null || toRes == null) return;

        toRes.wool += MathUtils.random(fromRes.wool);
        toRes.iron += MathUtils.random(fromRes.iron);
        toRes.meat += MathUtils.random(fromRes.meat);
        toRes.bread += MathUtils.random(fromRes.bread);
        toRes.silver += MathUtils.random(fromRes.silver);
        toRes.wood += MathUtils.random(fromRes.wood);
        toRes.mead += MathUtils.random(fromRes.mead);
    }

    private static void handleDeath(Engine engine, Entity entity)
    {
        IdentityComponent id = Mappers.id.get(entity);

        Gdx.app.log("Combat", String.format("Entity '%s' (%s) died", id.name, id.type));

        if (id.type.isStructure)
        {
            CombatComponent cc = Mappers.combat.get(entity);
            cc.healthPoints = cc.maxHealthPoints / 5;
        }
        else
        {
            engine.removeEntity(entity);
        }
    }
}

package com.bennavetta.vikings.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bennavetta.vikings.engine.components.*;

public class RaidingSystem extends IntervalIteratingSystem
{
    private static Family TARGET_FAMILY = Family.all(LocationComponent.class, CombatComponent.class).get();

    public RaidingSystem(float interval)
    {
        super(Family.all(LocationComponent.class, CombatComponent.class, RaiderComponent.class).get(), interval);
    }

    @Override
    protected void processEntity(Entity entity)
    {
        Vector2 location = Mappers.location.get(entity).coordinates;
        CombatComponent combat = Mappers.combat.get(entity);
        RaiderComponent raidConfig = Mappers.raider.get(entity);
        float maxDist2 = raidConfig.attackRange * raidConfig.attackRange;

        for (Entity target : getEngine().getEntitiesFor(TARGET_FAMILY))
        {
            Vector2 targetLocation = Mappers.location.get(target).coordinates;
            CombatComponent targetCombat = Mappers.combat.get(target);

            if (targetCombat.affinity == combat.affinity)
            {
                continue;
            }
            else if (targetCombat.affinity == CombatComponent.Affinity.NEUTRAL)
            {
                TradeComponent targetTrade = Mappers.trade.get(target);
                if (targetTrade != null)
                {
                    // If we're on decently good terms with the other entity, don't attack
                    float goodwill = targetTrade.goodwill.get(entity, 100f);
                    if (goodwill > 90 || MathUtils.random(goodwill) > 25) continue;
                }

                // Attack neutral targets randomly based on our hostility
                if (MathUtils.random() >= raidConfig.hostility) continue;
            }

            if (targetLocation.dst2(location) <= maxDist2)
            {
                Combat.fight(getEngine(), entity, target);
            }
        }
    }
}

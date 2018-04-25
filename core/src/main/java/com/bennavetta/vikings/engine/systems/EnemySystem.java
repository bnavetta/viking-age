package com.bennavetta.vikings.engine.systems;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.MonasteryName;
import com.bennavetta.vikings.ShipName;
import com.bennavetta.vikings.engine.components.*;

import java.awt.*;

/**
 * System to create hostile enemy entities
 */
public class EnemySystem extends EntitySystem
{
    private static final int MAX_SHIPS = 20;
    private static final float SHIP_PROB = 0.05f;
    private static final Family FAMILY = Family.all(LocationComponent.class, IdentityComponent.class, CombatComponent.class).get();

    private static final float MONASTERY_PROB = 0.01f;
    private static final float MAX_MONASTERIES = 7;

    private final Pixmap mapData;

    public EnemySystem()
    {
        Texture tex = Assets.getMap();
        tex.getTextureData().prepare();
        mapData = tex.getTextureData().consumePixmap();
//        mapData = new Pixmap(tex.getWidth(), tex.getHeight(), Pixmap.Format.RGB888);
//        tex.draw(mapData, 0, 0);
    }

    private boolean isLand(float x, float y)
    {
        float texX = mapData.getWidth() / 1000f * x;
        float texY = mapData.getHeight() / 500f * y;

        int pixel = mapData.getPixel(MathUtils.round(texX), MathUtils.round(texY));
        return pixel != Color.WHITE.getRGB();
    }

    @Override
    public void update(float deltaTime)
    {
        int shipCount = 0;
        int monasteryCount = 0;
        for (Entity entity : getEngine().getEntitiesFor(FAMILY))
        {
            IdentityComponent id = Mappers.id.get(entity);
            CombatComponent cc = Mappers.combat.get(entity);
            if (id.type == EntityType.SHIP && cc.affinity == CombatComponent.Affinity.HOSTILE)
            {
                shipCount++;
            }
            else if (id.type == EntityType.MONASTERY)
            {
                monasteryCount++;
            }
        }

        PooledEngine engine = (PooledEngine) getEngine();

        if (monasteryCount < MAX_MONASTERIES && MathUtils.random() < MONASTERY_PROB)
        {

            for (int i = 0; i < 10; i++)
            {
                int x = MathUtils.random(1000);
                int y = MathUtils.random(500);
                if (isLand(x, y))
                {
                    engine.addEntity(Entities.newMonastery(engine, MonasteryName.generate(), x, y));
                }
            }
        }

        if (shipCount < MAX_SHIPS && MathUtils.random() <= SHIP_PROB)
        {
            float x = MathUtils.random(1000);
            float y = MathUtils.random(500);
            Entity ship = Entities.newRaidingShip(engine, ShipName.generate(), CombatComponent.Affinity.HOSTILE, x, y);

            ResourcesComponent res = Mappers.resources.get(ship);
            res.wood = MathUtils.random(1000);
            res.meat = MathUtils.random(1000);
            res.mead = MathUtils.random(1000);
            res.wool = MathUtils.random(1000);
            res.iron = MathUtils.random(1000);
            res.silver = MathUtils.random(1000);
            res.bread = MathUtils.random(1000);

//            SteeringComponent st = engine.createComponent(SteeringComponent.class);
//            st.maxSpeed = 15;
//            EntitySteering steering = new EntitySteering(ship);
//            st.steeringBehavior = new Wander<>(steering)
//                    .setFaceEnabled(true)
//                    .setAlignTolerance(0.001f)
//                    .setDecelerationRadius(5)
//                    .setTimeToTarget(0.1f)
//                    .setWanderOffset(90)
//                    .setWanderOrientation(MathUtils.random(-MathUtils.PI, MathUtils.PI))
//                    .setWanderRadius(40)
//                    .setWanderRate(MathUtils.PI2 * 4);
//            ship.add(st);

            getEngine().addEntity(ship);
        }
    }
}

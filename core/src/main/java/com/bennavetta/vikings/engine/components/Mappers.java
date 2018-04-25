package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.ComponentMapper;

/**
 * Container for various {@link ComponentMapper} instances
 */
public class Mappers
{
    public static final ComponentMapper<ResourcesComponent> resources = ComponentMapper.getFor(ResourcesComponent.class);
    public static final ComponentMapper<PopulationComponent> population = ComponentMapper.getFor(PopulationComponent.class);
    public static final ComponentMapper<LocationComponent> location = ComponentMapper.getFor(LocationComponent.class);
    public static final ComponentMapper<RaiderComponent> raider = ComponentMapper.getFor(RaiderComponent.class);
    public static final ComponentMapper<CombatComponent> combat = ComponentMapper.getFor(CombatComponent.class);
    public static final ComponentMapper<IdentityComponent> id = ComponentMapper.getFor(IdentityComponent.class);
    public static final ComponentMapper<NavigationComponent> navigation = ComponentMapper.getFor(NavigationComponent.class);
    public static final ComponentMapper<SteeringComponent> steering = ComponentMapper.getFor(SteeringComponent.class);
    public static final ComponentMapper<TradeComponent> trade = ComponentMapper.getFor(TradeComponent.class);

    private Mappers() {}
}

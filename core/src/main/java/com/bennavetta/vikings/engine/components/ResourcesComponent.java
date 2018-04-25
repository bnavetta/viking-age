package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Tracks how many of various resources an entity (usually a settlement, monastery, etc.) has.
 */
public class ResourcesComponent implements Component, Poolable
{
    public static final ResourceHandle WOOD = new ResourceHandle()
    {
        @Override
        public float get(ResourcesComponent r)
        {
            return r.wood;
        }

        @Override
        public void set(ResourcesComponent r, float value)
        {
            r.wood = value;
        }
    };

    public static final ResourceHandle MEAT = new ResourceHandle()
    {
        @Override
        public float get(ResourcesComponent r)
        {
            return r.meat;
        }

        @Override
        public void set(ResourcesComponent r, float value)
        {
            r.meat = value;
        }
    };

    public static final ResourceHandle IRON = new ResourceHandle()
    {
        @Override
        public float get(ResourcesComponent r)
        {
            return r.iron;
        }

        @Override
        public void set(ResourcesComponent r, float value)
        {
            r.iron = value;
        }
    };

    public static final ResourceHandle WOOL = new ResourceHandle()
    {
        @Override
        public float get(ResourcesComponent r)
        {
            return r.wool;
        }

        @Override
        public void set(ResourcesComponent r, float value)
        {
            r.wool = value;
        }
    };

    public static final ResourceHandle MEAD = new ResourceHandle()
    {
        @Override
        public float get(ResourcesComponent r)
        {
            return r.mead;
        }

        @Override
        public void set(ResourcesComponent r, float value)
        {
            r.mead = value;
        }
    };

    public static final ResourceHandle BREAD = new ResourceHandle()
    {
        @Override
        public float get(ResourcesComponent r)
        {
            return r.bread;
        }

        @Override
        public void set(ResourcesComponent r, float value)
        {
            r.bread = value;
        }
    };

    public static final ResourceHandle SILVER = new ResourceHandle()
    {
        @Override
        public float get(ResourcesComponent r)
        {
            return r.silver;
        }

        @Override
        public void set(ResourcesComponent r, float value)
        {
            r.silver = value;
        }
    };

    public static final ResourceHandle[] ALL_RESOURCES = new ResourceHandle[]{
            WOOD, MEAT, IRON, WOOL, MEAD, BREAD, SILVER
    };

    public float wood = 0;
    public float meat = 0; // TODO: could break down?
    public float iron = 0;
    public float wool = 0;
    public float mead = 0;
    public float bread = 0;

    /**
     * An amount of silver, by weight. We're using silver instead of gold because of the relatively high amount of it
     * that flowed through Norse hands (i.e. dirhams)
     */
    public float silver = 0;

    @Override
    public void reset()
    {
        this.wood = 0;
        this.meat = 0;
        this.iron = 0;
        this.wool = 0;
        this.mead = 0;
        this.bread = 0;
        this.silver = 0;
    }

    /**
     * Abstraction for accessing individual resources
     */
    public interface ResourceHandle
    {
        float get(ResourcesComponent r);
        void set(ResourcesComponent r, float value);

        default void increment(ResourcesComponent r, float amount)
        {
            set(r, get(r) + amount);
        }
    }
}

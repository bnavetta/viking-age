package com.bennavetta.vikings.engine.data;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

/**
 * Serialization format for storing world info
 */
public class MapData
{
    public ArrayList<PlaceInfo> emporia;
    public ArrayList<PlaceInfo> towns;

    public static class PlaceInfo
    {
        public String name;
        public float x;
        public float y;
    }

    public static MapData load(FileHandle input)
    {
        Json json = new Json();
        json.setElementType(MapData.class, "emporia", PlaceInfo.class);
        json.setElementType(MapData.class, "towns", PlaceInfo.class);
        return json.fromJson(MapData.class, input);
    }
}

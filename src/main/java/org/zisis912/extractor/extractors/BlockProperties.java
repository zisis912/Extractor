package org.zisis912.extractor.extractors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.zisis912.extractor.JsonExtractor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class BlockProperties implements JsonExtractor {

    public String filename() {
        return "block_properties.json";
    }

    public JsonElement extractData() {
        JsonObject blockProperties = new JsonObject();

        for (Block block : BuiltInRegistries.BLOCK) {
            JsonObject properties = new JsonObject();
            blockProperties.add(block.getDescriptionId(),properties);

            for (Property<?> property : block.getStateDefinition().getProperties()) {
                JsonArray propertyValues = new JsonArray();
                properties.add(property.getName(), propertyValues);

                property.getAllValues().map(Property.Value::valueName).forEach(propertyValues::add);
            }

        }

        return blockProperties;
    }
}

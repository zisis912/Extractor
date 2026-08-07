package org.zisis912.extractor.extractors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.zisis912.extractor.JsonExtractor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.zisis912.extractor.Extractor.gson;

public class BlockProperties implements JsonExtractor {

    public String filename() {
        return "block_properties";
    }

    public JsonElement extractData() {
        Map<String, Map<String, List<String>>> blockProperties = new LinkedHashMap<>();

        for (Block block : BuiltInRegistries.BLOCK) {
            String blockName = block.getDescriptionId();
            Map<String,List<String>> properties = new LinkedHashMap<>();

            for (Property<?> property : block.getStateDefinition().getProperties()) {
                List<String> propertyValues = property.getAllValues().map(Property.Value::valueName).toList();

                properties.put(property.getName(), propertyValues);
            }

            blockProperties.put(blockName,properties);
        }

        return gson.toJsonTree(blockProperties);
    }
}

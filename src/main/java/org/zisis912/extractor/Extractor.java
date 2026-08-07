package org.zisis912.extractor;

import com.google.gson.Gson;
import net.fabricmc.api.ClientModInitializer;
import org.zisis912.extractor.extractors.BlockProperties;
import org.zisis912.extractor.extractors.Packets;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Extractor implements ClientModInitializer {

    static final JsonExtractor[] providers = {new BlockProperties(), new Packets()};

    @Override
    public void onInitializeClient() {

        Path outputDirectory = null;
        try {
            outputDirectory = Files.createDirectories(Paths.get("extractor_output"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        for (JsonExtractor extractor : providers) {
            Path out = outputDirectory.resolve(extractor.filename());

            try (FileWriter fileWriter = new FileWriter(out.toFile(), StandardCharsets.UTF_8)) {
                gson.toJson(extractor.extractData(), fileWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        System.exit(0);
    }

}

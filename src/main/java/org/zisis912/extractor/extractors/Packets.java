package org.zisis912.extractor.extractors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.ProtocolInfo;
import net.minecraft.network.protocol.configuration.ConfigurationProtocols;
import net.minecraft.network.protocol.game.GameProtocols;
import net.minecraft.network.protocol.handshake.HandshakeProtocols;
import net.minecraft.network.protocol.login.LoginProtocols;
import net.minecraft.network.protocol.status.StatusProtocols;
import org.zisis912.extractor.JsonExtractor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packets implements JsonExtractor {
    @Override
    public String filename() {
        return "packets.json";
    }

    @Override
    public JsonElement extractData() {
        JsonObject packets = new JsonObject();
        Stream.of(
                        HandshakeProtocols.SERVERBOUND_TEMPLATE,
                        StatusProtocols.CLIENTBOUND_TEMPLATE,
                        StatusProtocols.SERVERBOUND_TEMPLATE,
                        LoginProtocols.CLIENTBOUND_TEMPLATE,
                        LoginProtocols.SERVERBOUND_TEMPLATE,
                        ConfigurationProtocols.CLIENTBOUND_TEMPLATE,
                        ConfigurationProtocols.SERVERBOUND_TEMPLATE,
                        GameProtocols.CLIENTBOUND_TEMPLATE,
                        GameProtocols.SERVERBOUND_TEMPLATE
                )
                .map(ProtocolInfo.DetailsProvider::details)
                .collect(Collectors.groupingBy(ProtocolInfo.Details::id))
                .forEach((state, directions) -> {
                    JsonObject stateDirections = new JsonObject();
                    packets.add(state.id(), stateDirections);

                    directions.forEach(direction -> {
                        JsonObject directionPackets = new JsonObject();
                        stateDirections.add(direction.flow().id(), directionPackets);

                        direction.listPackets((type, networkId) -> {
                            directionPackets.addProperty(type.id().toString(), networkId);
                        });
                    });
                });
        return packets;
    }
}

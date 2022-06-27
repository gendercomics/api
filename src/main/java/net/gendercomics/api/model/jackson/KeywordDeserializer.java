package net.gendercomics.api.model.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import net.gendercomics.api.model.*;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.*;

public class KeywordDeserializer extends StdDeserializer<Keyword> {

    public KeywordDeserializer() {
        this(null);
    }

    public KeywordDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Keyword deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String id = null;
        if (node.get("id") != null) {
            id = node.get("id").asText();
        }

        JsonNode metaDataJson = node.get("metaData");
        MetaData metaData = new MetaData(dateFromJson(metaDataJson, "createdOn"),
                valueFromJson(metaDataJson, "createdBy"),
                dateFromJson(metaDataJson, "changedOn"),
                valueFromJson(metaDataJson, "changedBy"),
                Status.valueOf(metaDataJson.get("status").asText()));

        KeywordType type = KeywordType.valueOf(node.get("type").asText());

        Map<Language, KeywordValue> values = new HashMap<>();
        node.get("values").elements().forEachRemaining(v -> {
            Language lang = Language.valueOf(v.get("language").asText());
            KeywordValue kv = new KeywordValue(v.get("name").asText(), valueFromJson(v, "description"), lang);
            values.put(lang, kv);
        });

        List<RelationIds> relationIds = new ArrayList<>();
        node.get("relations").elements().forEachRemaining(r -> {
            relationIds.add(new RelationIds(
                    r.get("source").get("id").asText(),
                    r.get("predicate").get("id").asText(),
                    r.get("target").get("id").asText()));
        });

        return new Keyword(id, metaData, type, values, relationIds);
    }

    private Date dateFromJson(JsonNode json, String nodeName) {
        return json.get(nodeName).asText().equals("null") ? null : DatatypeConverter.parseDateTime(json.get(nodeName).asText()).getTime();
    }

    private String valueFromJson(JsonNode json, String nodeName) {
        return json.get(nodeName).asText().equals("null") ? null : json.get(nodeName).asText();
    }
}

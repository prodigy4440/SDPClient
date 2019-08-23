package com.fahdisa.sdpclient.util;

import com.fahdisa.sdpclient.model.authorization.AuthQueryList;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.util.*;

public class JsonUtil<T> {

    private static XmlMapper XML_MAPPER = new XmlMapper();
    private static ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static ObjectMapper getJsonMapper(){
        return JSON_MAPPER;
    }

    public static XmlMapper getXmlMapper(){
        return XML_MAPPER;
    }

    public static <T> T xmlToPojoViaJson(String xml, Class<T> type) throws IOException {
        String json = xmlToJson(xml);
        if(Objects.isNull(json) || json.isEmpty()){
            throw new IOException("Invalid json");
        }
        return JsonUtil.getJsonMapper().readValue(json, type);
    }

    public static <T> T xmlStraightToPojo(String xml, Class<T> type) throws  IOException{
        SimpleModule module = new SimpleModule().addDeserializer(Object.class,
                JsonUtil.Issue205FixedUntypedObjectDeserializer.getInstance());
        XmlMapper xmlMapper = (XmlMapper) new XmlMapper().registerModule(module);
        return xmlMapper.readValue(xml, type);
    }

    public static String xmlToJson(String xml){
        try {
            SimpleModule module = new SimpleModule().addDeserializer(Object.class,
                    JsonUtil.Issue205FixedUntypedObjectDeserializer.getInstance());
            XmlMapper xmlMapper = (XmlMapper) new XmlMapper().registerModule(module);
            JsonNode jsonNode = xmlMapper.readTree(xml);
            System.out.println(jsonNode);
            return getJsonMapper().writeValueAsString(xmlMapper.readTree(xml));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressWarnings({ "deprecation", "serial" })
    public static class Issue205FixedUntypedObjectDeserializer extends UntypedObjectDeserializer {

        private static final Issue205FixedUntypedObjectDeserializer INSTANCE = new Issue205FixedUntypedObjectDeserializer();

        private Issue205FixedUntypedObjectDeserializer() {}

        public static Issue205FixedUntypedObjectDeserializer getInstance() {
            return INSTANCE;
        }

        @Override
        @SuppressWarnings({ "unchecked", "rawtypes" })
        protected Object mapObject(JsonParser parser, DeserializationContext context) throws IOException {

            // Read the first key.
            @Nullable String firstKey;
            JsonToken token = parser.getCurrentToken();
            if (token == JsonToken.START_OBJECT) {
                firstKey = parser.nextFieldName();
            } else if (token == JsonToken.FIELD_NAME) {
                firstKey = parser.getCurrentName();
            } else {
                if (token != JsonToken.END_OBJECT) {
                    throw context.mappingException(handledType(), parser.getCurrentToken());
                }
                return Collections.emptyMap();
            }

            // Populate entries.
            Map<String, Object> valueByKey = new LinkedHashMap<>();
            String nextKey = firstKey;
            do {

                // Read the next value.
                parser.nextToken();
                Object nextValue = deserialize(parser, context);

                // Key conflict? Combine existing and current entries into a list.
                if (valueByKey.containsKey(nextKey)) {
                    Object existingValue = valueByKey.get(nextKey);
                    if (existingValue instanceof List) {
                        List<Object> values = (List<Object>) existingValue;
                        values.add(nextValue);
                    } else {
                        List<Object> values = new ArrayList<>();
                        values.add(existingValue);
                        values.add(nextValue);
                        valueByKey.put(nextKey, values);
                    }
                }

                // New key? Put into the map.
                else {
                    valueByKey.put(nextKey, nextValue);
                }

            } while ((nextKey = parser.nextFieldName()) != null);

            // Ship back the collected entries.
            return valueByKey;

        }

    }
}

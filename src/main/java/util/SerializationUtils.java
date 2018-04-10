package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import data.model.exception.SerializationException;

import java.io.IOException;

/**
 * .._  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/10/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
public class SerializationUtils {

    private static ObjectMapper mapper;

    private static ObjectMapper getMapper() {
        return mapper == null ? new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT) : mapper;
    }

    public static String toJson(Object toSerialize) {
        try {
            return getMapper().writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new SerializationException("SerializationUtils -> to Json serialization failed");
        }
    }

    public static <T> T fromJson(String toDeserialize, Class<T> classType) {
        try {
            return getMapper().readValue(toDeserialize, classType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SerializationException("SerializationUtils -> from Json deserialization failed");
        }
    }
}

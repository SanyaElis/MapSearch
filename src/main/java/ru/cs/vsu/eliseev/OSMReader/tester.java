package ru.cs.vsu.eliseev.OSMReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.sax.SAXException;
import ru.cs.vsu.eliseev.OSMReader.controller.OSMParser;
import ru.cs.vsu.eliseev.OSMReader.model.ElementOnMap;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class tester {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        OSMParser osmParser = new OSMParser();
        Map<String, ElementOnMap> result =  osmParser.parse(new File("src/main/java/ru/cs/vsu/eliseev/OSMReader/osmdata/test.osm"));
        System.out.println("Hello. world!");
        ObjectMapper objectMapper = new ObjectMapper();
        for ( Map.Entry<String, ElementOnMap> entry : result.entrySet() ) {
            String key = entry.getKey();
            ElementOnMap value = entry.getValue();
            System.out.println(objectMapper.writeValueAsString(value));
        }

    }
}

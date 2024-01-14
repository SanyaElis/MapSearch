package ru.cs.vsu.eliseev.OSMReader.controller;


import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import ru.cs.vsu.eliseev.OSMReader.model.ElementOnMap;
import ru.cs.vsu.eliseev.OSMReader.model.Node;
import ru.cs.vsu.eliseev.OSMReader.model.Relation;
import ru.cs.vsu.eliseev.OSMReader.model.Way;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OSMParser extends DefaultHandler {

    private Map<String, ElementOnMap> elements;

    private ElementOnMap current;

    public OSMParser() {
        super();
    }

    public Map<String, ElementOnMap> parse(File f) throws IOException, SAXException, ParserConfigurationException {
        //File check
        if (!f.exists() || !f.isFile()) {
            throw new FileNotFoundException();
        }

        if (!f.canRead()) {
            throw new IOException("Can't read file");
        }

        return parse(new InputSource(new FileReader(f)));
    }


    public Map<String, ElementOnMap> parse(InputSource input) throws SAXException, IOException, ParserConfigurationException {
        elements = new HashMap<>();
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        XMLReader xr = parser.getXMLReader();
        xr.setContentHandler(this);
        xr.setErrorHandler(this);
        xr.parse(input);

        return elements;
    }

    private String getId(String type, String ref) {

        return switch (type) {
            case "node" -> "N" + ref;
            case "way" -> "W" + ref;
            case "relation" -> "R" + ref;
            default -> throw new RuntimeException("Unknown element type: " + type);
        };
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (qName.equals("node") || qName.equals("way") || qName.equals("relation")) {
            if (current != null) {
                if ((qName.equals("way") && ((Way) current).getNodes().size() >= 2)
                        || qName.equals("node")
                        || (qName.equals("relation") && ((Relation) current).getMembers().size() > 0)) {
                    elements.put(current.getId(), current);
                }
                current = null;
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        switch (qName) {
            case "node":
                Node n = new Node(
                        Long.parseLong(attributes.getValue("id")),
                        Double.parseDouble(attributes.getValue("lat")),
                        Double.parseDouble(attributes.getValue("lon"))
                );
                n.setUser(attributes.getValue("user"));

                if (attributes.getValue("uid") != null) {
                    n.setUid(Long.parseLong(attributes.getValue("uid")));
                }

                n.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

                if (attributes.getValue("version") != null) {
                    n.setVersion(Integer.parseInt(attributes.getValue("version")));
                }

                if (attributes.getValue("changeset") != null) {
                    n.setChangeset(Long.parseLong(attributes.getValue("changeset")));
                }

                n.setTimestamp(attributes.getValue("timestamp"));
                current = n;
                break;
            case "way":
                Way w = new Way(Long.parseLong(attributes.getValue("id")));
                w.setUser(attributes.getValue("user"));

                if (attributes.getValue("uid") != null) {
                    w.setUid(Long.parseLong(attributes.getValue("uid")));
                }

                w.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

                if (attributes.getValue("version") != null) {
                    w.setVersion(Integer.parseInt(attributes.getValue("version")));
                }

                if (attributes.getValue("changeset") != null) {
                    w.setChangeset(Long.parseLong(attributes.getValue("changeset")));
                }

                w.setTimestamp(attributes.getValue("timestamp"));
                current = w;
                break;
            case "nd":
                ((Way) current).addNode((Node) elements.get("N" + attributes.getValue("ref")));
                break;
            case "relation":
                Relation r = new Relation(Long.parseLong(attributes.getValue("id")));
                r.setUser(attributes.getValue("user"));

                if (attributes.getValue("uid") != null) {
                    r.setUid(Long.parseLong(attributes.getValue("uid")));
                }

                r.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

                if (attributes.getValue("version") != null) {
                    r.setVersion(Integer.parseInt(attributes.getValue("version")));
                }

                if (attributes.getValue("changeset") != null) {
                    r.setChangeset(Long.parseLong(attributes.getValue("changeset")));
                }

                r.setTimestamp(attributes.getValue("timestamp"));
                current = r;
                break;
            case "member":
                String refMember = getId(attributes.getValue("type"), attributes.getValue("ref"));

                //If member isn't contained in data, create stub object
                ElementOnMap elemMember = null;
                if (!elements.containsKey(refMember)) {
                    switch (attributes.getValue("type")) {
                        case "node" -> elemMember = new Node(Long.parseLong(attributes.getValue("ref")), 0, 0);
                        case "way" -> elemMember = new Way(Long.parseLong(attributes.getValue("ref")));
                        case "relation" -> elemMember = new Relation(Long.parseLong(attributes.getValue("ref")));
                    }
                } else {
                    elemMember = elements.get(refMember);
                }

                //Add member
                ((Relation) current).addMember(
                        attributes.getValue("role"),
                        elemMember
                );
                break;
            //Case of tag
            case "tag":
                if (current != null) {
                    current.addTag(attributes.getValue("k"), attributes.getValue("v"));
                }
                break;
        }
    }
}
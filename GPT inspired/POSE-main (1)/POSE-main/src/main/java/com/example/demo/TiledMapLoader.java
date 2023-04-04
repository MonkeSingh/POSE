package com.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TiledObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TiledMapLoader {

    private String filePath;

    public TiledMapLoader(String filePath) {
        this.filePath = filePath;
    }

    public List<TiledObject> loadObjects() {
        List<TiledObject> objects = new ArrayList<>();
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList objectNodes = doc.getElementsByTagName("object");
            for (int i = 0; i < objectNodes.getLength(); i++) {
                Node objectNode = objectNodes.item(i);
                if (objectNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element objectElement = (Element) objectNode;
                    // Parse object properties and create TiledObject
                    // ...
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public Level load() {
        return null;
    }

    // Other methods for loading tilesets and layers can be added here
}

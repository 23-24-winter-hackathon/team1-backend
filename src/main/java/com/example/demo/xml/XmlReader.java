package com.example.demo.xml;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class XmlReader {
    private final String path = "src/main/resources/";
    private Map<String, Double> cpuModelTdps = new HashMap<>();
    private Map<String, Double> gpuModelTdps = new HashMap<>();

    public XmlReader() throws ParserConfigurationException, IOException, SAXException {
        String fileName = "data.xml";
        log.info("======================= Opening CPU - TDP File... : {} =======================", fileName);
        Document document = getDocument(path + fileName);
        convertDocToObject(document, cpuModelTdps);
    }

    private static void convertDocToObject(Document document, Map<String ,Double> processorModelTdps) {
        NodeList wayToCook = document.getElementsByTagName("RCP_WAY2");
        NodeList foodType = document.getElementsByTagName("RCP_PAT2");
        Set<String> wayToCooks = new HashSet<>();
        for (int i = 0; i < wayToCook.getLength(); i++) {
            wayToCooks.add(wayToCook.item(i).getTextContent());
        }
        for (String toCook : wayToCooks) {
            log.info("toCook : {}", toCook);
        }
        Set<String> foodTypes = new HashSet<>();
        for (int i = 0; i < foodType.getLength(); i++) {
            foodTypes.add(foodType.item(i).getTextContent());
        }
        for (String type : foodTypes) {
            log.info("type : {}", type);
        }
    }

    private static Document getDocument(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(fileName);
    }
}

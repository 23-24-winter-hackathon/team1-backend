package com.example.demo.xml;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
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
        NodeList ingredients = document.getElementsByTagName("RCP_PARTS_DTLS");
        for (int i = 0; i < ingredients.getLength(); i++) {
            log.info("ingredients : {}", ingredients.item(i).getTextContent());
        }
    }

    private static Document getDocument(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(fileName);
    }
}

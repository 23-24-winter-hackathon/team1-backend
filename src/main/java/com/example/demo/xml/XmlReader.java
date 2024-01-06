package com.example.demo.xml;

import com.example.demo.domain.Food;
import com.example.demo.domain.Nutrition;
import com.example.demo.domain.enums.FoodType;
import com.example.demo.domain.enums.WayToCook;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class XmlReader {
    private final String path = "src/main/resources/";

    public void readXml() throws ParserConfigurationException, IOException, SAXException {
        String fileName = "data.xml";
        Document document = getDocument(path + fileName);
        convertDocToObject(document);
    }

    private void convertDocToObject(Document document) {
        NodeList ingredients = document.getElementsByTagName("RCP_PARTS_DTLS");
        NodeList wayToCooks = document.getElementsByTagName("RCP_WAY2");
        NodeList foodNames = document.getElementsByTagName("RCP_NM");
        NodeList foodTypes = document.getElementsByTagName("RCP_PAT2");
        NodeList calories = document.getElementsByTagName("INFO_ENG");
        NodeList carbons = document.getElementsByTagName("INFO_CAR");
        NodeList proteins = document.getElementsByTagName("INFO_PRO");
        NodeList fats = document.getElementsByTagName("INFO_FAT");
        NodeList imgSrcs = document.getElementsByTagName("ATT_FILE_NO_MK");

        for (int i = 0; i < ingredients.getLength(); i++) {
            Integer id = i + 1;
            String ingredient = ingredients.item(i).getTextContent();
            WayToCook wayToCook = WayToCook.valueOf(wayToCooks.item(i).getTextContent());
            String foodName = foodNames.item(i).getTextContent();
            String foodTypeText = foodTypes.item(i).getTextContent();
            if(foodTypeText.equals("국&찌개")) {
                foodTypeText = "국_찌개";
            }
            FoodType foodType = FoodType.valueOf(foodTypeText);
            Double calorie = Double.valueOf(calories.item(i).getTextContent());
            Double carbon = Double.valueOf(carbons.item(i).getTextContent());
            Double protein = Double.valueOf(proteins.item(i).getTextContent());
            Double fat = Double.valueOf(fats.item(i).getTextContent());
            String imgSrc = imgSrcs.item(i).getTextContent();

            Nutrition nutrition = new Nutrition(calorie, carbon, protein, fat);
            Food food = new Food(foodName, foodType, wayToCook, id, imgSrc, nutrition, ingredient);
        }
    }

    private static Document getDocument(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(fileName);
    }
}

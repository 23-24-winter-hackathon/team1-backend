package com.example.demo.xml;

import com.example.demo.domain.Food;
import com.example.demo.domain.Nutrition;
import com.example.demo.domain.enums.FoodType;
import com.example.demo.domain.enums.WayToCook;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.NutritionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class XmlReader {
    private final String path = "src/main/resources/";
    private final FoodRepository foodRepository;
    private final NutritionRepository nutritionRepository;

    @PostConstruct
    public void readXml() throws ParserConfigurationException, IOException, SAXException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://openapi.foodsafetykorea.go.kr/api/21b0f4c6886d44648373/COOKRCP01/xml/";
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);

        String fileName = "data.xml";
        FileWriter fileWriter = new FileWriter(path + fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<COOKRCP01>\n" +
                "    <total_count>1124</total_count>\n" +
                "    <RESULT>\n" +
                "        <CODE>INFO-000</CODE>\n" +
                "        <MSG>정상처리되었습니다.</MSG>\n" +
                "    </RESULT>");
        for (int i = 1; i < 1200; i+=300) {
            String temp = url + i + "/" + (i + 299) + "/";
            String response = restTemplate.exchange(temp, HttpMethod.POST, null, String.class).getBody();
            int newlineIndex = response.indexOf("<row id=\"0\">");
                // 개행 문자 다음부터의 문자열을 추출
            int endIndex = response.indexOf("</COOKRCP01>");
            log.info("newlineIndex : {}, {}", newlineIndex, endIndex);
            if(newlineIndex == -1 || endIndex == -1) {
                i -= 50;
                continue;
            }
            response = response.substring(newlineIndex, endIndex - 1);
            printWriter.print(response);
        }
        printWriter.print("\n" +
                "</COOKRCP01>\n");
        printWriter.close();
        fileWriter.close();
        Document document = getDocument(path + fileName);
        convertDocToObject(document);
        File file = new File(path + fileName);
        file.delete();
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
            nutritionRepository.save(nutrition);
            Food food = new Food(foodName, foodType, wayToCook, id, imgSrc, nutrition, ingredient);
            foodRepository.save(food);
        }
    }

    private static Document getDocument(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(fileName);
    }
}

package com.campusdual.classroom;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Exercise27 {

    public static void main(String[] args) {
        try {
            // Crear el archivo XML
            createXML();
            // Crear el archivo JSON
            createJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para crear el archivo XML
    private static void createXML() throws ParserConfigurationException, IOException {
        // Lista de artículos con sus cantidades
        String[][] items = {
                {"Manzana", "2"},
                {"Leche", "1"},
                {"Pan", "3"},
                {"Huevo", "2"},
                {"Queso", "1"},
                {"Cereal", "1"},
                {"Agua", "4"},
                {"Yogur", "6"},
                {"Arroz", "2"}
        };

        // Configurar el generador de documentos XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // Crear nodo raíz <shoppinglist>
        Element rootElement = doc.createElement("shoppinglist");
        doc.appendChild(rootElement);

        // Crear nodo <items>
        Element itemsElement = doc.createElement("items");
        rootElement.appendChild(itemsElement);

        // Agregar artículos como nodos <item>
        for (String[] item : items) {
            Element itemElement = doc.createElement("item");
            itemElement.setAttribute("quantity", item[1]);  // Cantidad
            itemElement.setTextContent(item[0]);  // Artículo
            itemsElement.appendChild(itemElement);
        }

        // Guardar el archivo XML en la ubicación especificada
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            DOMSource source = new DOMSource(doc);

            // Archivo destino
            StreamResult result = new StreamResult(new File("src/main/resources/shoppingList.xml"));
            transformer.transform(source, result);
            System.out.println("Archivo XML creado: shoppingList.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para crear el archivo JSON
    private static void createJSON() throws IOException {
        // Lista de artículos con sus cantidades
        String[][] items = {
                {"Manzana", "2"},
                {"Leche", "1"},
                {"Pan", "3"},
                {"Huevo", "2"},
                {"Queso", "1"},
                {"Cereal", "1"},
                {"Agua", "4"},
                {"Yogur", "6"},
                {"Arroz", "2"}
        };

        // Crear el objeto JSON raíz
        JsonObject shoppingList = new JsonObject();
        JsonArray itemsArray = new JsonArray();

        // Agregar cada artículo como un objeto JSON al array
        for (String[] item : items) {
            JsonObject jsonItem = new JsonObject();
            jsonItem.addProperty("text", item[0]);  // Artículo
            jsonItem.addProperty("quantity", Integer.parseInt(item[1]));  // Cantidad
            itemsArray.add(jsonItem);
        }

        // Agregar el array de artículos al objeto raíz
        shoppingList.add("items", itemsArray);

        // Convertir el objeto JSON a string y guardarlo en el archivo
        Gson gson = new Gson();
        try (FileWriter file = new FileWriter("src/main/resources/shoppingList.json")) {
            gson.toJson(shoppingList, file);
            System.out.println("Archivo JSON creado: shoppingList.json");
        }
    }
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Программа для анализа данных о наличии товаров у продавцов и вычисления общего количества товаров каждого типа.
 */
public class InventoryCounter {

    /**
     * Основной метод программы.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        try {
            // Чтение данных из XML файлов
            Map<String, Integer> inventory = readInventoryData("inventory_data.xml");
            Map<String, String> productNames = readProductNames("products.xml");

            // Вычисление общего количества товара каждого типа
            Map<String, Integer> totalQuantity = calculateTotalQuantity(inventory);

            // Запись результатов в выходной файл
            writeResultToXML(totalQuantity, productNames, "total_inventory.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Чтение данных о наличии товаров у продавцов из XML файла.
     *
     * @param filename имя входного XML файла с данными о наличии товаров у продавцов
     * @return отображение (Map) с данными о наличии каждого товара у продавцов
     * @throws Exception если возникают проблемы при чтении файла
     */
    private static Map<String, Integer> readInventoryData(String filename) throws Exception {
        Map<String, Integer> inventory = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        NodeList inventoryList = document.getElementsByTagName("inventory");
        for (int i = 0; i < inventoryList.getLength(); i++) {
            Element inventoryElement = (Element) inventoryList.item(i);
            String productId = inventoryElement.getElementsByTagName("product_id").item(0).getTextContent();
            int quantity = Integer.parseInt(inventoryElement.getElementsByTagName("quantity").item(0).getTextContent());
            inventory.put(productId, inventory.getOrDefault(productId, 0) + quantity);
        }

        return inventory;
    }

    /**
     * Чтение наименований товаров из XML файла.
     *
     * @param filename имя входного XML файла с наименованиями товаров
     * @return отображение (Map) с соответствием идентификаторов товаров и их наименований
     * @throws Exception если возникают проблемы при чтении файла
     */
    private static Map<String, String> readProductNames(String filename) throws Exception {
        Map<String, String> productNames = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        NodeList productList = document.getElementsByTagName("product");
        for (int i = 0; i < productList.getLength(); i++) {
            Element productElement = (Element) productList.item(i);
            String productId = productElement.getElementsByTagName("product_id").item(0).getTextContent();
            String productName = productElement.getElementsByTagName("product_name").item(0).getTextContent();
            productNames.put(productId, productName);
        }

        return productNames;
    }

    /**
     * Вычисление общего количества товара каждого типа.
     *
     * @param inventory данные о наличии каждого товара у продавцов
     * @return отображение (Map) с общим количеством каждого товара
     */
    private static Map<String, Integer> calculateTotalQuantity(Map<String, Integer> inventory) {
        Map<String, Integer> totalQuantity = new HashMap<>();

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            totalQuantity.put(productId, totalQuantity.getOrDefault(productId, 0) + quantity);
        }

        return totalQuantity;
    }

    /**
     * Запись результатов в XML файл.
     *
     * @param totalQuantity общее количество каждого товара
     * @param productNames  наименования товаров
     * @param filename      имя выходного XML файла для записи результатов
     * @throws IOException если возникают проблемы при записи файла
     */
    private static void writeResultToXML(Map<String, Integer> totalQuantity, Map<String, String> productNames,
                                         String filename) throws IOException {
        File file = new File(filename);
        FileWriter writer = new FileWriter(file);

        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<total_inventory>\n");

        for (Map.Entry<String, Integer> entry : totalQuantity.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            String productName = productNames.getOrDefault(productId, "Unknown Product");

            writer.write("\t<product>\n");
            writer.write("\t\t<product_id>" + productId + "</product_id>\n");
            writer.write("\t\t<product_name>" + productName + "</product_name>\n");
            writer.write("\t\t<total_quantity>" + quantity + "</total_quantity>\n");
            writer.write("\t</product>\n");
        }

        writer.write("</total_inventory>");
        writer.close();
    }
}

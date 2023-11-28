import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AverageDailySales {

    /**
     * Основной метод программы.
     * Читает данные о продажах из XML файла, вычисляет среднее количество проданных товаров в каждый уникальный день и записывает результат в выходной XML файл.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        try {
            // Чтение данных из XML файла с продажами
            Map<Date, Double> averageSalesPerDay = readSalesData("sales.xml");

            // Запись результатов в выходной файл
            writeResultToXML(averageSalesPerDay, "average_daily_sales.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Читает данные о продажах из XML файла и возвращает мапу среднего количества проданных товаров в каждый день.
     *
     * @param filename имя XML файла с данными о продажах
     * @return мапа среднего количества проданных товаров в каждый день (ключ - дата, значение - среднее количество продаж)
     * @throws Exception если возникли ошибки при чтении файла или обработке данных
     */
    private static Map<Date, Double> readSalesData(String filename) throws Exception {
        Map<Date, Double> averageSalesPerDay = new HashMap<>();
        Map<String, Integer> uniqueSalesPerDay = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        NodeList salesList = document.getElementsByTagName("sale");
        for (int i = 0; i < salesList.getLength(); i++) {
            Element saleElement = (Element) salesList.item(i);
            int quantity = Integer.parseInt(saleElement.getElementsByTagName("quantity").item(0).getTextContent());
            String dateString = saleElement.getElementsByTagName("sale_date").item(0).getTextContent();

            // Обновление количества уникальных продаж для каждой уникальной даты
            String uniqueKey = dateString + "_" + saleElement.getElementsByTagName("sale_id").item(0).getTextContent();
            uniqueSalesPerDay.put(uniqueKey, quantity);
        }

        // Суммирование уникальных продаж для каждой уникальной даты
        for (Map.Entry<String, Integer> entry : uniqueSalesPerDay.entrySet()) {
            String uniqueKey = entry.getKey();
            int quantity = entry.getValue();
            String dateString = uniqueKey.split("_")[0];
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            averageSalesPerDay.put(date, averageSalesPerDay.getOrDefault(date, 0.0) + quantity);
        }

        // Расчет среднего количества проданных товаров в каждый день
        for (Map.Entry<Date, Double> entry : averageSalesPerDay.entrySet()) {
            Date date = entry.getKey();
            double totalSales = entry.getValue();
            int totalUniqueSales = 0;

            // Подсчет общего количества уникальных продаж для каждой даты
            for (String key : uniqueSalesPerDay.keySet()) {
                if (key.startsWith(new SimpleDateFormat("yyyy-MM-dd").format(date))) {
                    totalUniqueSales++;
                }
            }

            double average = totalSales / totalUniqueSales;
            averageSalesPerDay.put(date, average);
        }

        return averageSalesPerDay;
    }

    /**
     * Записывает результаты в XML файл.
     *
     * @param averageSalesPerDay мапа среднего количества проданных товаров в каждый день
     * @param filename           имя выходного XML файла
     * @throws IOException если возникли ошибки при записи в файл
     */
    private static void writeResultToXML(Map<Date, Double> averageSalesPerDay, String filename) throws IOException {
        File file = new File(filename);
        FileWriter writer = new FileWriter(file);

        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<average_daily_sales>\n");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<Date, Double> entry : averageSalesPerDay.entrySet()) {
            Date date = entry.getKey();
            double averageSales = entry.getValue();

            writer.write("\t<day>\n");
            writer.write("\t\t<date>" + sdf.format(date) + "</date>\n");
            writer.write("\t\t<average_sales>" + averageSales + "</average_sales>\n");
            writer.write("\t</day>\n");
        }

        writer.write("</average_daily_sales>");
        writer.close();
    }
}

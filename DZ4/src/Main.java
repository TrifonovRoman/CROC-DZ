import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Эта программа считывает текст из файла "input.txt", подсчитывает частоту
 * появления каждого символа, и записывает результат в файл "output.txt".
 * Пробелы и переводы строк исключаются из подсчета.
 */
public class Main{

    /**
     * Главный метод программы.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        try {
            // Проверка наличия файла input.txt
            File inputFile = new File("input.txt");
            if (!inputFile.exists()) {
                System.out.println("Файл input.txt не найден.");
                return;
            }

            // Проверка наличия текста в файле input.txt
            if (inputFile.length() == 0) {
                System.out.println("Файл input.txt пуст.");
                return;
            }

            // Чтение содержимого файла input.txt
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            StringBuilder text = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
            reader.close();

            // Удаление пробелов и переводов строк, подсчет частоты символов
            Map<Character, Integer> charFrequency = new HashMap<>();
            String cleanedText = text.toString().replaceAll("[\\s]", "");
            for (int i = 0; i < cleanedText.length(); i++) {
                char c = cleanedText.charAt(i);
                charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
            }

            // Сортировка результатов по частоте
            String sortedResult = charFrequency.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                    .map(entry -> String.format("'%s'=%d", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining(", "));

            // Запись результатов в файл output.txt
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write(sortedResult);
            writer.close();

            System.out.println("Результаты успешно записаны в файл output.txt.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

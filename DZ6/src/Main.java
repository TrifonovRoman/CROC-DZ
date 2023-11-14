import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Пример использования BlackListFilterImpl для фильтрации комментариев на основе черного списка слов.
 */
public class Main {
    /**
     * Главный метод программы, демонстрирующий работу фильтрации комментариев.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        // Создание экземпляра BlackListFilterImpl
        BlackListFilterImpl filter = new BlackListFilterImpl();

        // Пример списка комментариев
        List<String> comments = new ArrayList<>();
        comments.add("Это хороший комментарий");
        comments.add("Это плохой комментарий");
        comments.add("Ещё один хороший комментарий");
        comments.add("Здесь есть нежелательное слово: спам");

        // Создание черного списка слов
        Set<String> blackList = new HashSet<>();
        blackList.add("спам");
        blackList.add("плохой");

        // Фильтрация комментариев
        filter.filterComments(comments, blackList);

        // Вывод отфильтрованных комментариев
        System.out.println("Отфильтрованные комментарии:");
        for (String comment : comments) {
            System.out.println(comment);
        }
    }
}

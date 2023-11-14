import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса BlackListFilter для фильтрации комментариев на основе черного списка слов.
 */
public class BlackListFilterImpl implements BlackListFilter {

    /**
     * Из заданного списка комментариев удаляет те, которые содержат слова из черного списка.
     *
     * @param comments  список комментариев; каждый комментарий - последовательность слов, разделенных
     *                  пробелами, пунктуацией или переносами строк
     * @param blackList список слов, которые не должны присутствовать в комментарии
     */
    @Override
    public void filterComments(List<String> comments, Set<String> blackList) {
        if (comments == null || blackList == null) {
            throw new IllegalArgumentException("Comments or blacklist cannot be null");
        }

        Set<String> normalizedBlackList = normalizeBlackList(blackList);

        List<String> commentsToRemove = new ArrayList<>();
        for (String comment : comments) {
            if (containsBlacklistedWords(comment, normalizedBlackList)) {
                commentsToRemove.add(comment);
            }
        }

        comments.removeAll(commentsToRemove);
    }

    /**
     * Нормализует слова в черном списке к нижнему регистру для упрощения сравнения.
     *
     * @param blackList черный список слов
     * @return нормализованный черный список слов
     */
    private Set<String> normalizeBlackList(Set<String> blackList) {
        Set<String> normalizedBlackList = new HashSet<>();
        for (String word : blackList) {
            normalizedBlackList.add(word.toLowerCase().trim());
        }
        return normalizedBlackList;
    }

    /**
     * Проверяет наличие слов из черного списка в комментарии.
     *
     * @param comment  комментарий для проверки
     * @param blackList черный список слов
     * @return true, если комментарий содержит слова из черного списка, иначе false
     */
    private boolean containsBlacklistedWords(String comment, Set<String> blackList) {
        String[] words = comment.toLowerCase().split("[\\s.,?!;:]+");
        for (String word : words) {
            if (blackList.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
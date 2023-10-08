import java.util.*;
public class Main {
    /**
     * Метод для проверки, является ли строка палиндромом.
     *
     * @param input Строка для проверки.
     * @return true, если строка является палиндромом, false в противном случае.
     */
    public static boolean isPalindrome(String input) {
        /*
           [а-яА-Яa-zA-Z]: Это регулярное выражение, которое означает любой символ, находящийся в диапазоне букв кириллицы (а-яА-Я)
           и букв латиницы (a-zA-Z). ^ внутри квадратных скобок инвертирует этот диапазон
         */
        String cleanedInput = input.replaceAll("[^а-яА-Яa-zA-Z]", "").toLowerCase();

        int left = 0;
        int right = cleanedInput.length() - 1;

        // Сравниваем символы с обоих концов строки
        while (left < right) {
            if (cleanedInput.charAt(left) != cleanedInput.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * Точка входа в программу.
     *
     * @param args Аргументы командной строки (не используются в этом примере).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите строку для проверки на палиндром:");
        String input = scanner.nextLine();

        boolean isPalindrome = isPalindrome(input);

        if (isPalindrome) {
            System.out.println("Является палиндромом.");
        } else {
            System.out.println("Не является палиндромом.");
        }
    }
}
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

/**
 * Этот класс вычисляет определитель матрицы с использованием многопоточности в Java.
 */
public class Main {
    /**
     * Главный метод, который инициирует вычисление определителя матрицы и выводит результат на экран.
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        long[][] matrix = {
                {9, 0, 3, 6, 6, 2, 0, 1, 9, 2, 3, 7},
                {7, 9, 0, 6, 9, 6, 2, 6, 5, 6, 7, 6},
                {9, 0, 3, 9, 1, 1, 8, 5, 3, 5, 1, 3},
                {7, 2, 9, 2, 2, 9, 9, 5, 5, 6, 0, 7},
                {9, 5, 2, 4, 1, 3, 6, 1, 8, 1, 6, 2},
                {4, 0, 5, 7, 1, 4, 8, 0, 5, 9, 7, 4},
                {7, 2, 7, 6, 1, 5, 6, 8, 2, 1, 8, 7},
                {9, 9, 6, 5, 4, 0, 2, 9, 9, 1, 0, 0},
                {7, 6, 2, 9, 4, 2, 0, 9, 5, 5, 8, 8},
                {3, 1, 2, 3, 8, 0, 3, 1, 1, 4, 8, 9},
                {4, 7, 6, 2, 8, 1, 3, 6, 2, 8, 7, 5},
                {3, 8, 5, 5, 3, 3, 3, 4, 1, 8, 7, 2}
        };

        // Определяем количество доступных ядер процессора для оптимального использования потоков.
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Создаем задачу (Callable), которая будет вычислять определитель матрицы.
        Callable<Long> task = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return calculateDeterminant(matrix);
            }
        };

        // Запускаем задачу в пуле потоков.
        Future<Long> result = executorService.submit(task);

        try {
            // Получаем результат и выводим определитель матрицы.
            long determinant = result.get();
            System.out.println("Determinant of the matrix: " + determinant);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Завершаем работу пула потоков.
        executorService.shutdown();
    }

    /**
     * Метод для вычисления определителя матрицы разложением по строке.
     * @param matrix Квадратная матрица для вычисления определителя.
     * @return Определитель матрицы.
     */
    public static long calculateDeterminant(long[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) {
            throw new IllegalArgumentException("Matrix is not square.");
        }

        if (n == 1) {
            // Базовый случай: определитель матрицы 1x1 равен её единственному элементу.
            return matrix[0][0];
        }

        if (n == 2) {
            // Базовый случай: определитель матрицы 2x2 вычисляется по формуле.
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        long determinant = 0;

        for (int i = 0; i < n; i++) {
            // Для каждой строки i вычисляем минор (подматрицу без строки i и столбца i).
            long[][] subMatrix = new long[n - 1][n - 1];
            for (int j = 1; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (k < i) {
                        subMatrix[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        subMatrix[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            // Рекурсивно вычисляем определитель минора и добавляем его к определителю матрицы.
            long subDeterminant = matrix[0][i] * calculateDeterminant(subMatrix);
            if (i % 2 == 0) {
                determinant += subDeterminant;
            } else {
                determinant -= subDeterminant;
            }
        }

        return determinant;
    }
}

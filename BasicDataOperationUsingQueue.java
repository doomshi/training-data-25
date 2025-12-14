import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Клас BasicDataOperationUsingQueue реалізує роботу з PriorityQueue для даних типу float (Float).
 * 
 * <p>Основні функції класу:</p>
 * <ul>
 *   <li>{@link #runDataProcessing()} - Запускає комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив LocalDateTime.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить мінімальне і максимальне значення в масиві.</li>
 *   <li>{@link #findInQueue()} - Пошук значення в черзі LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInQueue()} - Знаходить граничні значення в черзі.</li>
 *   <li>{@link #performQueueOperations()} - Виконує операції peek і poll з чергою.</li>
 * </ul>
 * 
 */
public class BasicDataOperationUsingQueue {
    private final float floatValueToSearch;
    private final Float[] floatArray;
    private final Queue<Float> priorityQueue;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param floatValueToSearch Значення для пошуку
     * @param floatArray Масив Float[]
     */
    BasicDataOperationUsingQueue(float floatValueToSearch, Float[] floatArray) {
        this.floatValueToSearch = floatValueToSearch;
        this.floatArray = floatArray;
        this.priorityQueue = new PriorityQueue<>(Arrays.asList(floatArray));
    }
    
    /**
     * Запускає комплексну обробку даних з використанням черги.
     * 
     * Метод завантажує дані, виконує операції з чергою та масивом LocalDateTime.
     */
    public void runDataProcessing() {
        // спочатку обробляємо чергу
        findInQueue();
        locateMinMaxInQueue();
        performQueueOperations();

        // потім працюємо з масивом
        findInArrayLinear();
        locateMinMaxInArray();

        performArraySorting();

        findInArrayBinary();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(floatArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв LocalDateTime та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void performArraySorting() {
        // вимірюємо тривалість упорядкування масиву дати та часу
        long timeStart = System.nanoTime();

        Arrays.sort(floatArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву Float[]");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArrayLinear() {
        // відстежуємо час виконання пошуку в масиві
        long timeStart = System.nanoTime();

        int position = -1;
        for (int i = 0; i < floatArray.length; i++) {
            Float v = floatArray[i];
            if (v != null && Float.compare(v, floatValueToSearch) == 0) {
                position = i;
                break;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "лінійний пошук елемента в масивi Float[]");

        if (position >= 0) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в масиві.");
        }
    }

    private void findInArrayBinary() {
        long timeStart = System.nanoTime();

        int position = Arrays.binarySearch(this.floatArray, Float.valueOf(floatValueToSearch));

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук елемента в масивi Float[]");

        if (position >= 0) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в відсортованому масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в відсортованому масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві LocalDateTime.
     */
    private void locateMinMaxInArray() {
        if (floatArray == null || floatArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        // відстежуємо час на визначення граничних значень
        long timeStart = System.nanoTime();

        Float minValue = floatArray[0];
        Float maxValue = floatArray[0];

        for (Float current : floatArray) {
            if (current == null) continue;
            if (minValue == null || current < minValue) minValue = current;
            if (maxValue == null || current > maxValue) maxValue = current;
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в масивi Float[]");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Здійснює пошук конкретного значення в черзі дати та часу.
     */
    private void findInQueue() {
        // вимірюємо час пошуку в черзі
        long timeStart = System.nanoTime();

        boolean elementExists = this.priorityQueue.contains(Float.valueOf(floatValueToSearch));

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в PriorityQueue");

        if (elementExists) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в PriorityQueue");
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в PriorityQueue.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в черзі LocalDateTime.
     */
    private void locateMinMaxInQueue() {
        if (priorityQueue == null || priorityQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        // відстежуємо час пошуку граничних значень
        long timeStart = System.nanoTime();

        Float minValue = priorityQueue.peek(); // для PriorityQueue це мінімум
        Float maxValue = Collections.max(priorityQueue);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в PriorityQueue");

        System.out.println("Найменше значення в PriorityQueue: " + minValue);
        System.out.println("Найбільше значення в PriorityQueue: " + maxValue);
    }

    /**
     * Виконує операції peek і poll з чергою LocalDateTime.
     */
    private void performQueueOperations() {
        if (priorityQueue == null || priorityQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        Float headElement = priorityQueue.peek();
        System.out.println("Головний елемент черги (peek): " + headElement);

        headElement = priorityQueue.poll();
        System.out.println("Видалений елемент черги (poll): " + headElement);

        headElement = priorityQueue.peek();
        System.out.println("Новий головний елемент черги: " + headElement);
    }
}
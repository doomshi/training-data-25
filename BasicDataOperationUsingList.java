import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекціями типу ArrayList для даних LocalDateTime.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив елементів LocalDateTime.</li>
 *   <li>{@link #findInArray()} - Здійснює пошук елемента в масиві LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає найменше і найбільше значення в масиві.</li>
 *   <li>{@link #sortList()} - Сортує колекцію List з float.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {
    private Float floatValueToSearch;
    private Float[] floatArray;
    private List<Float> floatList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param floatValueToSearch Значення для пошуку
     * @param floatArray Масив float
     */
    BasicDataOperationUsingList(float floatValueToSearch, float[] floatArray) {
        this.floatValueToSearch = floatValueToSearch;
        Float[] boxedArray = new Float[floatArray.length];
        for (int i = 0; i < floatArray.length; i++) {
            boxedArray[i] = floatArray[i];
        }
        this.floatArray = boxedArray;
        this.floatList = new ArrayList<Float>(Arrays.asList(boxedArray));
    }
    
    /**
     * Виконує комплексні операції з структурами даних.
     * 
     * Метод завантажує масив і список об'єктів LocalDateTime, 
     * здійснює сортування та пошукові операції.
     */
    public void executeDataOperations() {
        // спочатку працюємо з колекцією List
        findInList();
        locateMinMaxInList();
        
        sortList();
        
        findInList();
        locateMinMaxInList();

        // потім обробляємо масив дати та часу
        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        
        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до окремого файлу
        DataFileHandler.writeArrayToFile(floatArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(floatArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.binarySearch(this.floatArray, floatValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дати i часу");

        if (position >= 0) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві дати та часу.
     */
    void locateMinMaxInArray() {
        if (floatArray == null || floatArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        Float minValue = floatArray[0];
        Float maxValue = floatArray[0];

        for (Float currentDateTime : floatArray) {
            if (floatValueToSearch < minValue) {
                minValue = currentDateTime;
            }
            if (floatValueToSearch > maxValue) {
                maxValue = currentDateTime;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Шукає конкретне значення дати та часу в колекції ArrayList.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = Collections.binarySearch(this.floatList, floatValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в List дати i часу");        

        if (position >= 0) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в Vector за позицією: " + position);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в Vector.");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції Vector з датами.
     */
    void locateMinMaxInList() {
        if (floatList == null || floatList.isEmpty()) {
            System.out.println("Колекція Vector є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        Float minValue = Collections.min(floatList);
        Float maxValue = Collections.max(floatList);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в List");

        System.out.println("Найменше значення в List: " + minValue);
        System.out.println("Найбільше значення в List: " + maxValue);
    }

    /**
     * Упорядковує колекцію List з об'єктами LocalDateTime за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        Collections.sort(floatList);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування Vector дати i часу");
    }
}
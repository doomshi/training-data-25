import java.time.LocalDateTime;
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
 *   <li>{@link #sortList()} - Сортує колекцію List з LocalDateTime.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {
    private LocalDateTime dateTimeValueToSearch;
    private LocalDateTime[] dateTimeArray;
    private List<LocalDateTime> dateTimeList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param dateTimeValueToSearch Значення для пошуку
     * @param dateTimeArray Масив LocalDateTime
     */
    BasicDataOperationUsingList(LocalDateTime dateTimeValueToSearch, LocalDateTime[] dateTimeArray) {
        this.dateTimeValueToSearch = dateTimeValueToSearch;
        this.dateTimeArray = dateTimeArray;
        this.dateTimeList = new ArrayList<>(Arrays.asList(dateTimeArray));
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
        DataFileHandler.writeArrayToFile(dateTimeArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        dateTimeArray = Arrays.stream(dateTimeArray)
                      .sorted()
                      .toArray(LocalDateTime[]::new);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.stream(dateTimeArray)
                .map(Arrays.asList(dateTimeArray)::indexOf)
                .filter(i -> dateTimeValueToSearch.equals(dateTimeArray[i]))
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дати i часу");

        if (position >= 0) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві дати та часу.
     */
    void locateMinMaxInArray() {
        if (dateTimeArray == null || dateTimeArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        // Використовуємо Stream API для пошуку мінімуму та максимуму
        LocalDateTime minValue = Arrays.stream(dateTimeArray)
                .min(LocalDateTime::compareTo)
                .orElse(null);
        
        LocalDateTime maxValue = Arrays.stream(dateTimeArray)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Шукає конкретне значення дати та часу в колекції ArrayList.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = dateTimeList.stream()
            .map(dateTimeList::indexOf)
            .filter(i -> dateTimeValueToSearch.equals(dateTimeList.get(i)))
            .findFirst()
            .orElse(-1);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в List дати i часу");        

        if (position >= 0) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в ArrayList за позицією: " + position);
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в ArrayList.");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції ArrayList з датами.
     */
    void locateMinMaxInList() {
        if (dateTimeList == null || dateTimeList.isEmpty()) {
            System.out.println("Колекція ArrayList є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        // Використовуємо Stream API для пошуку мінімуму та максимуму
        LocalDateTime minValue = dateTimeList.stream()
                .min(LocalDateTime::compareTo)
                .orElse(null);
        
        LocalDateTime maxValue = dateTimeList.stream()
                .max(LocalDateTime::compareTo)
                .orElse(null);

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

        dateTimeList = dateTimeList.stream()
                       .sorted()
                       .toList();

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування ArrayList дати i часу");
    }
}
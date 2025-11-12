import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множиною HashSet для LocalDateTime.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив LocalDateTime.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить граничні значення в масиві.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeArrayAndSet()} - Аналізує елементи масиву та множини.</li>
 * </ul>
 */
public class BasicDataOperationUsingSet {
    LocalDateTime dateTimeValueToSearch;
    LocalDateTime[] dateTimeArray;
    Set<LocalDateTime> dateTimeSet = new HashSet<>();

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param dateTimeValueToSearch Значення для пошуку
     * @param dateTimeArray Масив LocalDateTime
     */
    BasicDataOperationUsingSet(LocalDateTime dateTimeValueToSearch, LocalDateTime[] dateTimeArray) {
        this.dateTimeValueToSearch = dateTimeValueToSearch;
        this.dateTimeArray = dateTimeArray;
        this.dateTimeSet = new HashSet<>(Arrays.asList(dateTimeArray));
    }
    
    /**
     * Запускає комплексний аналіз даних з використанням множини HashSet.
     * 
     * Метод завантажує дані, виконує операції з множиною та масивом LocalDateTime.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множину дати та часу
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        // потім обробляємо масив
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(dateTimeArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();

        dateTimeArray = Arrays.stream(dateTimeArray)
                              .sorted()
                              .toArray(LocalDateTime[]::new);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
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
     * Визначає найменше та найбільше значення в масиві LocalDateTime.
     */
    private void locateMinMaxInArray() {
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
     * Здійснює пошук конкретного значення в множині дати та часу.
     */
    private void findInSet() {
        long timeStart = System.nanoTime();

        boolean elementExists = dateTimeSet.stream()
            .anyMatch(dateTime -> dateTime.equals(dateTimeValueToSearch));

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в HashSet дати i часу");

        if (elementExists) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в HashSet.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині LocalDateTime.
     */
    private void locateMinMaxInSet() {
        if (dateTimeSet == null || dateTimeSet.isEmpty()) {
            System.out.println("HashSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        // Використовуємо Stream API для пошуку мінімуму та максимуму
        LocalDateTime minValue = dateTimeSet.stream()
                .min(LocalDateTime::compareTo)
                .orElse(null);
        
        LocalDateTime maxValue = dateTimeSet.stream()
                .max(LocalDateTime::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в HashSet");

        System.out.println("Найменше значення в HashSet: " + minValue);
        System.out.println("Найбільше значення в HashSet: " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + dateTimeArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + dateTimeSet.size());

        // Використовуємо Stream API для перевірки наявності всіх елементів
        boolean allElementsPresent = Arrays.stream(dateTimeArray)
                .allMatch(dateTimeSet::contains);

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні в HashSet.");
        } else {
            System.out.println("Не всi елементи масиву наявні в HashSet.");
        }
    }
}
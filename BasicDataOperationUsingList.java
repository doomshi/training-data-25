import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекціями типу List:
 * ArrayList, LinkedList та Vector для даних типу float (Float).
 */
public class BasicDataOperationUsingList {
    private final Float floatValueToSearch;
    private final Float[] floatArray;

    private final ArrayList<Float> arrayList;
    private final LinkedList<Float> linkedList;
    private final Vector<Float> vector;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param floatValueToSearch Значення для пошуку
     * @param floatArray         Масив Float[]
     */
    BasicDataOperationUsingList(float floatValueToSearch, Float[] floatArray) {
        this.floatValueToSearch = floatValueToSearch;
        this.floatArray = floatArray;

        List<Float> base = Arrays.asList(floatArray);
        this.arrayList = new ArrayList<>(base);
        this.linkedList = new LinkedList<>(base);
        this.vector = new Vector<>(base);
    }

    /**
     * Виконує комплексні операції з структурами даних.
     * 
     * Метод завантажує масив і список об'єктів LocalDateTime,
     * здійснює сортування та пошукові операції.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з List-колекціями (лінійний пошук до сортування)
        findInListLinear(arrayList, "ArrayList");
        locateMinMaxInList(arrayList, "ArrayList");

        findInListLinear(linkedList, "LinkedList");
        locateMinMaxInList(linkedList, "LinkedList");

        findInListLinear(vector, "Vector");
        locateMinMaxInList(vector, "Vector");

        // Сортуємо та робимо бінарний пошук (Collections.binarySearch вимагає
        // сортований список)
        sortList(arrayList, "ArrayList");
        findInListBinary(arrayList, "ArrayList");

        sortList(linkedList, "LinkedList");
        findInListBinary(linkedList, "LinkedList");

        sortList(vector, "Vector");
        findInListBinary(vector, "Vector");

        // Потім обробляємо масив (Float[])
        findInArrayLinear();
        locateMinMaxInArray();

        performArraySorting();

        findInArrayBinary();
        locateMinMaxInArray();

        // Зберігаємо відсортований масив до окремого файлу
        DataFileHandler.writeArrayToFile(floatArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();
        floatArray = Arrays.stream(floatArray)
                .sorted()
                .toArray(Float[]::new);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву Float[]");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    void findInArrayLinear() {
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

    void findInArrayBinary() {
        long timeStart = System.nanoTime();

        int position = Arrays.stream(floatArray)
                .map(Arrays.asList(floatArray)::indexOf)
                .filter(i -> floatValueToSearch.equals(floatArray[i]))
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук елемента в масивi Float[]");

        if (position >= 0) {
            System.out.println(
                    "Елемент '" + floatValueToSearch + "' знайдено в відсортованому масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в відсортованому масиві.");
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

        for (Float current : floatArray) {
            if (current == null)
                continue;
            if (minValue == null || current < minValue)
                minValue = current;
            if (maxValue == null || current > maxValue)
                maxValue = current;
        }

        PerformanceTracker.displayOperationTime(timeStart,
                "визначення мiнiмального i максимального значення в масивi Float[]");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Шукає конкретне значення дати та часу в колекції ArrayList.
     */
    void findInListLinear(List<Float> list, String listName) {
        long timeStart = System.nanoTime();

        int position = list.stream()
                .map(list::indexOf)
                .filter(i -> floatValueToSearch.equals(list.get(i)))
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(timeStart, "лінійний пошук елемента в " + listName);

        if (position >= 0)

        {
            System.out.println(
                    "Елемент '" + floatValueToSearch + "' знайдено в " + listName + " за позицією: " + position);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в " + listName + ".");
        }
    }

    void findInListBinary(List<Float> list, String listName) {
        long timeStart = System.nanoTime();

        int position = Collections.binarySearch(list, Float.valueOf(floatValueToSearch));

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук елемента в " + listName);

        if (position >= 0) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в відсортованому " + listName
                    + " за позицією: " + position);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в відсортованому " + listName + ".");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції ArrayList з датами.
     */
    void locateMinMaxInList(List<Float> list, String listName) {
        if (list == null || list.isEmpty()) {
            System.out.println("Колекція " + listName + " є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        Float min = Arrays.stream(floatArray)
                .minValue(Float::compareTo)
                .orElse(null);

        Float max = Arrays.stream(floatArray)
                .maxValue(Float::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart,
                "визначення мiнiмального i максимального значення в " + listName);

        System.out.println("Найменше значення в " + listName + ": " + minValue);
        System.out.println("Найбільше значення в " + listName + ": " + maxValue);
    }

    /**
     * Упорядковує колекцію List з об'єктами LocalDateTime за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList(List<Float> list, String listName) {
        long timeStart = System.nanoTime();

        list = list.stream()
                .sorted()
                .collect(Collectors.toCollection(Vector::new));

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування " + listName);
    }
}
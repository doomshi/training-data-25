import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множинами:
 * HashSet, TreeSet та LinkedHashSet для даних типу float (Float).
 */
public class BasicDataOperationUsingSet {
    private final float floatValueToSearch;
    private final Float[] floatArray;

    private final Set<Float> hashSet;
    private final TreeSet<Float> treeSet;
    private final Set<Float> linkedHashSet;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param floatValueToSearch Значення для пошуку
     * @param floatArray         Масив Float[]
     */
    BasicDataOperationUsingSet(float floatValueToSearch, Float[] floatArray) {
        this.floatValueToSearch = floatValueToSearch;
        this.floatArray = floatArray;

        this.hashSet = new HashSet<>(Arrays.asList(floatArray));
        this.treeSet = new TreeSet<>(Arrays.asList(floatArray));
        this.linkedHashSet = new LinkedHashSet<>(Arrays.asList(floatArray));
    }

    /**
     * Запускає комплексний аналіз даних з використанням множини HashSet.
     * 
     * Метод завантажує дані, виконує операції з множиною та масивом LocalDateTime.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множини
        findInSet(hashSet, "HashSet");
        locateMinMaxInSet(hashSet, "HashSet");

        findInSet(treeSet, "TreeSet");
        locateMinMaxInSet(treeSet, "TreeSet");

        findInSet(linkedHashSet, "LinkedHashSet");
        locateMinMaxInSet(linkedHashSet, "LinkedHashSet");

        analyzeArrayAndSets();

        // потім обробляємо масив
        findInArrayLinear();
        locateMinMaxInArray();

        performArraySorting();

        findInArrayBinary();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(floatArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(floatArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву Float[]");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArrayLinear() {
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
            System.out.println(
                    "Елемент '" + floatValueToSearch + "' знайдено в відсортованому масивi за позицією: " + position);
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

        long timeStart = System.nanoTime();

        Float minValue = Set.stream()
                .min(Float::compareTo)
                .orElse(null);

        Float maxValue = Set.stream()
                .max(Float::compareTo)
                .orElse(null);

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
     * Здійснює пошук конкретного значення в множині дати та часу.
     */
    private void findInSet(Set<Float> set, String setName) {
        long timeStart = System.nanoTime();

        boolean elementExists = set.stream()
            .anyMatch(float -> float.equals(floatValueToSearch));

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в " + setName);

        if (elementExists) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в " + setName);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в " + setName + ".");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині LocalDateTime.
     */
    private void locateMinMaxInSet(Set<Float> set, String setName) {
        if (set == null || set.isEmpty()) {
            System.out.println(setName + " є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        Float minValue;
        Float maxValue;

        if (set instanceof TreeSet) {
            TreeSet<Float> ts = (TreeSet<Float>) set;
            minValue = ts.first();
            maxValue = ts.last();
        } else {
            minValue = Collections.min(set);
            maxValue = Collections.max(set);
        }

        PerformanceTracker.displayOperationTime(timeStart,
                "визначення мiнiмального i максимального значення в " + setName);

        System.out.println("Найменше значення в " + setName + ": " + minValue);
        System.out.println("Найбільше значення в " + setName + ": " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSets() {
        System.out.println("Кiлькiсть елементiв в масивi: " + floatArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + hashSet.size());
        System.out.println("Кiлькiсть елементiв в TreeSet: " + treeSet.size());
        System.out.println("Кiлькiсть елементiв в LinkedHashSet: " + linkedHashSet.size());

        boolean allElementsPresent = Arrays.stream(floatArray)
                .allMatch(hashSet::contains);

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні у всіх множинах (HashSet/TreeSet/LinkedHashSet).");
        } else {
            System.out.println("Не всi елементи масиву наявні у всіх множинах.");
        }
    }
}
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для
 * зберігання пар ключ-значення.
 * 
 * <p>
 * Методи класу:
 * </p>
 * <ul>
 * <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними
 * Map.</li>
 * <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 * <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 * <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 * <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 * <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 * <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 * <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    // Ключ/значення для додавання (з умови)
    private final Tortoise KEY_TO_ADD = new Tortoise("Казка", 3.3);
    private final String VALUE_TO_ADD = "Аркадій";

    // Ключ/значення для пошуку і видалення (з умови)
    private final Tortoise KEY_TO_SEARCH_AND_DELETE = new Tortoise("Броня", 3.1);
    private final String VALUE_TO_SEARCH_AND_DELETE = "Микола";

    private HashMap<Tortoise, String> hashMap;
    private TreeMap<Tortoise, String> treeMap;

    /**
     * Компаратор для сортування Map.Entry за значеннями String.
     * Використовує метод String.compareTo() для порівняння імен власників.
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Tortoise, String>> {
        @Override
        public int compare(Map.Entry<Tortoise, String> e1, Map.Entry<Tortoise, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null)
                return 0;
            if (v1 == null)
                return -1;
            if (v2 == null)
                return 1;
            return v1.compareTo(v2);
        }
    }

    private static final Comparator<Tortoise> PET_COMPARATOR = Comparator.comparing(Tortoise::nickname)
            .thenComparing(Tortoise::shellThickness, Comparator.reverseOrder());

    /**
     * Record Tortoise для зберігання інформації про домашню тварину (сухопутна
     * черепаха).
     */
    public record Tortoise(String nickname, Double shellThickness) {
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashMap HashMap з початковими даними (ключ: Tortoise, значення: ім'я
     *                власника)
     * @param treeMap TreeMap з початковими даними (ключ: Tortoise, значення: ім'я
     *                власника)
     */
    BasicDataOperationUsingMap(HashMap<Tortoise, String> hashMap, TreeMap<Tortoise, String> treeMap) {
        this.hashMap = hashMap;
        this.treeMap = treeMap;
    }

    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та
     * сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з HashMap
        System.out.println("========= Операції з HashMap =========");
        System.out.println("Початковий розмір HashMap: " + hashMap.size());

        // Пошук до сортування
        findByKeyInHashMap();
        findByValueInHashMap();

        printHashMap();

        addEntryToHashMap();

        removeByKeyFromHashMap();
        removeByValueFromHashMap();

        System.out.println("Кінцевий розмір HashMap: " + hashMap.size());

        // Потім обробляємо TreeMap
        System.out.println("\n\n========= Операції з TreeMap =========");
        System.out.println("Початковий розмір TreeMap: " + treeMap.size());

        findByKeyInTreeMap();
        findByValueInTreeMap();

        printTreeMap();

        addEntryToTreeMap();

        removeByKeyFromTreeMap();
        removeByValueFromTreeMap();

        System.out.println("Кінцевий розмір TreeMap: " + treeMap.size());
    }

    // ===== Методи для HashMap =====

    /**
     * Виводить вміст HashMap (HashMap не гарантує порядку елементів).
     */
    private void printHashMap() {
        System.out.println("\n=== Map<домашня тварина, власник (власниця)> (HashMap) ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Tortoise, String> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в HashMap");
    }

    /**
     * Здійснює пошук елемента за ключем в HashMap.
     * Використовує Tortoise.hashCode() та Tortoise.equals() для пошуку.
     */
    void findByKeyInHashMap() {
        long timeStart = System.nanoTime();

        boolean found = hashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в HashMap");

        if (found) {
            String value = hashMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в HashMap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Tortoise, String>> entries = new ArrayList<>(hashMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Tortoise, String> searchEntry = new Map.Entry<Tortoise, String>() {
            public Tortoise getKey() {
                return null;
            }

            public String getValue() {
                return VALUE_TO_SEARCH_AND_DELETE;
            }

            public String setValue(String value) {
                return null;
            }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в HashMap");

        if (position >= 0) {
            Map.Entry<Tortoise, String> foundEntry = entries.get(position);
            System.out.println(
                    "Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Tortoise: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Додає новий запис до HashMap.
     */
    void addEntryToHashMap() {
        long timeStart = System.nanoTime();

        hashMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до HashMap");

        System.out.println("Додано новий запис: Tortoise='" + KEY_TO_ADD + "' -> '" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з HashMap за ключем.
     */
    void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = hashMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");

        if (removedValue != null) {
            System.out.println(
                    "Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з HashMap за значенням.
     */
    void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();

        List<Tortoise> keysToRemove = new ArrayList<>();
        for (Map.Entry<Tortoise, String> entry : hashMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (Tortoise key : keysToRemove) {
            hashMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");

        System.out.println(
                "Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для TreeMap =====

    /**
     * Виводить вміст TreeMap.
     * TreeMap автоматично відсортована за ключами (Tortoise: nickname за
     * зростанням, shellThickness за зростанням).
     */
    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Tortoise, String> entry : treeMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в TreeMap");
    }

    /**
     * Здійснює пошук елемента за ключем в TreeMap.
     * Використовує Tortoise.compareTo() для навігації по дереву.
     */
    void findByKeyInTreeMap() {
        long timeStart = System.nanoTime();

        boolean found = treeMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в TreeMap");

        if (found) {
            String value = treeMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в TreeMap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInTreeMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Tortoise, String>> entries = new ArrayList<>(treeMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Tortoise, String> searchEntry = new Map.Entry<Tortoise, String>() {
            public Tortoise getKey() {
                return null;
            }

            public String getValue() {
                return VALUE_TO_SEARCH_AND_DELETE;
            }

            public String setValue(String value) {
                return null;
            }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в TreeMap");

        if (position >= 0) {
            Map.Entry<Tortoise, String> foundEntry = entries.get(position);
            System.out.println(
                    "Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Tortoise: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    /**
     * Додає новий запис до TreeMap.
     */
    void addEntryToTreeMap() {
        long timeStart = System.nanoTime();

        treeMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до TreeMap");

        System.out.println("Додано новий запис: Tortoise='" + KEY_TO_ADD + "' -> '" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з TreeMap за ключем.
     */
    void removeByKeyFromTreeMap() {
        long timeStart = System.nanoTime();

        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з TreeMap");

        if (removedValue != null) {
            System.out.println(
                    "Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з TreeMap за значенням.
     */
    void removeByValueFromTreeMap() {
        long timeStart = System.nanoTime();

        List<Tortoise> keysToRemove = new ArrayList<>();
        for (Map.Entry<Tortoise, String> entry : treeMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (Tortoise key : keysToRemove) {
            treeMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");

        System.out.println(
                "Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Tortoise, значення: власник/власниця) - з
        // умови
        HashMap<Tortoise, String> hashMap = new HashMap<>();
        hashMap.put(new Tortoise("Атлант", 2.5), "Руслан");
        hashMap.put(new Tortoise("Броня", 3.1), "Олеся");
        hashMap.put(new Tortoise("Вічність", 4.2), "Микола");
        hashMap.put(new Tortoise("Гном", 1.8), "Аліна");
        hashMap.put(new Tortoise("Броня", 2.9), "Тимур");
        hashMap.put(new Tortoise("Дзвін", 3.7), "Микола");
        hashMap.put(new Tortoise("Еон", 4.5), "Софія");
        hashMap.put(new Tortoise("Жук", 2.2), "Віталій");
        hashMap.put(new Tortoise("Зевс", 3.9), "Олеся");
        hashMap.put(new Tortoise("Ікар", 2.7), "Надія");

        TreeMap<Tortoise, String> treeMap = new TreeMap<>(PET_COMPARATOR);
        treeMap.put(new Tortoise("Атлант", 2.5), "Руслан");
        treeMap.put(new Tortoise("Броня", 3.1), "Олеся");
        treeMap.put(new Tortoise("Вічність", 4.2), "Микола");
        treeMap.put(new Tortoise("Гном", 1.8), "Аліна");
        treeMap.put(new Tortoise("Броня", 2.9), "Тимур");
        treeMap.put(new Tortoise("Дзвін", 3.7), "Микола");
        treeMap.put(new Tortoise("Еон", 4.5), "Софія");
        treeMap.put(new Tortoise("Жук", 2.2), "Віталій");
        treeMap.put(new Tortoise("Зевс", 3.9), "Олеся");
        treeMap.put(new Tortoise("Ікар", 2.7), "Надія");

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashMap, treeMap);
        operations.executeDataOperations();
    }

}

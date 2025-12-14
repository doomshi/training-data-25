import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
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
            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return -1;
            if (v2 == null) return 1;
            return v1.compareTo(v2);
        }
    }

    /**
     * Внутрішній клас Tortoise для зберігання інформації про домашню тварину (сухопутна черепаха).
     * 
     * Характеристики:
     * - nickname (кличка)
     * - shellThickness (товщина панциря, Double)
     *
     * Сортування (природний порядок):
     * - кличка (nickname) — за зростанням
     * - товщина панциря (shellThickness) — за зростанням
     */
    public static class Tortoise implements Comparable<Tortoise> {
        private final String nickname;
        private final Double shellThickness;

        public Tortoise(String nickname) {
            this.nickname = nickname;
            this.shellThickness = null;
        }

        public Tortoise(String nickname, Double shellThickness) {
            this.nickname = nickname;
            this.shellThickness = shellThickness;
        }

        public String getNickname() { 
            return nickname; 
        }

        public Double getShellThickness() {
            return shellThickness;
        }

        /**
         * Порівнює цей об'єкт Tortoise з іншим для визначення порядку сортування.
         * 
         * @param other Tortoise об'єкт для порівняння
         * @return негативне число, якщо цей Tortoise < other;
         *         0, якщо цей Tortoise == other;
         *         позитивне число, якщо цей Tortoise > other
         * 
         * Критерій порівняння: nickname (за зростанням), потім shellThickness (за зростанням).
         * 
         * Цей метод використовується:
         * - TreeMap для автоматичного сортування ключів Tortoise
         * - Collections.sort() для сортування ключів
         * - Collections.binarySearch() для пошуку в відсортованих колекціях
         */
        @Override
        public int compareTo(Tortoise other) {
            if (other == null) return 1;
            
            // Спочатку порівнюємо за кличкою (за зростанням)
            int nicknameComparison = 0;
            if (this.nickname == null && other.nickname == null) {
                nicknameComparison = 0;
            } else if (this.nickname == null) {
                nicknameComparison = -1;
            } else if (other.nickname == null) {
                nicknameComparison = 1;
            } else {
                nicknameComparison = this.nickname.compareTo(other.nickname);
            }
            
            // Якщо клички різні, повертаємо результат
            if (nicknameComparison != 0) {
                return nicknameComparison;
            }
            
            // Якщо клички однакові, порівнюємо за товщиною панциря (за зростанням)
            if (this.shellThickness == null && other.shellThickness == null) return 0;
            if (this.shellThickness == null) return -1;
            if (other.shellThickness == null) return 1;
            return this.shellThickness.compareTo(other.shellThickness);
        }

        /**
         * Перевіряє рівність цього Tortoise з іншим об'єктом.
         * Два Tortoise вважаються рівними, якщо їх nickname та shellThickness однакові.
         * 
         * @param obj об'єкт для порівняння
         * @return true, якщо об'єкти рівні; false в іншому випадку
         * 
         * Критерій рівності: nickname та shellThickness.
         * 
         * Важливо: метод узгоджений з compareTo() - якщо equals() повертає true,
         * то compareTo() повертає 0, оскільки обидва методи порівнюють за nickname та shellThickness.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Tortoise tortoise = (Tortoise) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(tortoise.nickname) : tortoise.nickname == null;
            boolean shellEquals = shellThickness != null ? shellThickness.equals(tortoise.shellThickness) : tortoise.shellThickness == null;
            
            return nicknameEquals && shellEquals;
        }

        /**
         * Повертає хеш-код для цього Tortoise.
         * 
         * @return хеш-код, обчислений на основі nickname та shellThickness
         * 
         * Базується на полях nickname та shellThickness для узгодженості з equals().
         * 
         * Важливо: узгоджений з equals() - якщо два Tortoise рівні за equals(),
         * вони матимуть однаковий hashCode().
         */
        @Override
        public int hashCode() {
            // Початкове значення: хеш-код поля nickname (або 0, якщо nickname == null)
            int result = nickname != null ? nickname.hashCode() : 0;
            
            // Комбінуємо хеш-коди полів за формулою: result = 31 * result + hashCode(поле)
            // Множник 31 - просте число, яке дає хороше розподілення хеш-кодів
            // і оптимізується JVM як (result << 5) - result
            // Додаємо хеш-код товщини панциря (або 0, якщо shellThickness == null) до загального результату
            result = 31 * result + (shellThickness != null ? shellThickness.hashCode() : 0);
            
            return result;
        }

        /**
         * Повертає строкове представлення Tortoise.
         * 
         * @return nickname, shellThickness та hashCode
         */
        @Override
        public String toString() {
            return "Tortoise{nickname='" + nickname + "', shellThickness=" + shellThickness + ", hashCode=" + hashCode() + "}";
        }
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashMap HashMap з початковими даними (ключ: Tortoise, значення: ім'я власника)
     * @param treeMap TreeMap з початковими даними (ключ: Tortoise, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(HashMap<Tortoise, String> hashMap, TreeMap<Tortoise, String> treeMap) {
        this.hashMap = hashMap;
        this.treeMap = treeMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
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
            public Tortoise getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в HashMap");

        if (position >= 0) {
            Map.Entry<Tortoise, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Tortoise: " + foundEntry.getKey());
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
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
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

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для TreeMap =====

    /**
     * Виводить вміст TreeMap.
     * TreeMap автоматично відсортована за ключами (Tortoise: nickname за зростанням, shellThickness за зростанням).
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
            public Tortoise getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в TreeMap");

        if (position >= 0) {
            Map.Entry<Tortoise, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Tortoise: " + foundEntry.getKey());
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
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
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

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Tortoise, значення: власник/власниця) - з умови
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

        TreeMap<Tortoise, String> treeMap = new TreeMap<Tortoise, String>() {{
            put(new Tortoise("Атлант", 2.5), "Руслан");
            put(new Tortoise("Броня", 3.1), "Олеся");
            put(new Tortoise("Вічність", 4.2), "Микола");
            put(new Tortoise("Гном", 1.8), "Аліна");
            put(new Tortoise("Броня", 2.9), "Тимур");
            put(new Tortoise("Дзвін", 3.7), "Микола");
            put(new Tortoise("Еон", 4.5), "Софія");
            put(new Tortoise("Жук", 2.2), "Віталій");
            put(new Tortoise("Зевс", 3.9), "Олеся");
            put(new Tortoise("Ікар", 2.7), "Надія");
        }};

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashMap, treeMap);
        operations.executeDataOperations();
    }

}

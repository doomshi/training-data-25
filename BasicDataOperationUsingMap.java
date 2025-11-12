import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    private final Pet KEY_TO_SEARCH_AND_DELETE = new Pet("Луна", "Полярна сова");
    private final Pet KEY_TO_ADD = new Pet("Кір", "Сова вухата");

    private final String VALUE_TO_SEARCH_AND_DELETE = "Олена";
    private final String VALUE_TO_ADD = "Богдан";

    private Hashtable<Pet, String> hashtable;
    private TreeMap<Pet, String> treeMap;

    /**
     * Внутрішній клас Pet для зберігання інформації про домашню тварину.
     * 
     * Реалізує Comparable<Pet> для визначення природного порядку сортування.
     * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (species) за спаданням.
     */
    public static class Pet implements Comparable<Pet> {
        private final String nickname;
        private final String species;

        public Pet(String nickname) {
            this.nickname = nickname;
            this.species = null;
        }

        public Pet(String nickname, String species) {
            this.nickname = nickname;
            this.species = species;
        }

        public String getNickname() { 
            return nickname; 
        }

        public String getSpecies() {
            return species;
        }

        /**
         * Порівнює цей об'єкт Pet з іншим для визначення порядку сортування.
         * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (species) за спаданням.
         * 
         * @param other Pet об'єкт для порівняння
         * @return негативне число, якщо цей Pet < other; 
         *         0, якщо цей Pet == other; 
         *         позитивне число, якщо цей Pet > other
         * 
         * Критерій порівняння: поля nickname (кличка) за зростанням та species (вид) за спаданням.
         * 
         * Цей метод використовується:
         * - TreeMap для автоматичного сортування ключів Pet за nickname (зростання), потім за species (спадання)
         * - Collections.sort() для сортування Map.Entry за ключами Pet
         * - Collections.binarySearch() для пошуку в відсортованих колекціях
         */
        @Override
        public int compareTo(Pet other) {
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
            
            // Якщо клички однакові, порівнюємо за видом (за спаданням - інвертуємо результат)
            if (this.species == null && other.species == null) return 0;
            if (this.species == null) return 1;  // null йде в кінець при спаданні
            if (other.species == null) return -1;
            return other.species.compareTo(this.species);  // Інвертоване порівняння для спадання
        }

        /**
         * Перевіряє рівність цього Pet з іншим об'єктом.
         * Два Pet вважаються рівними, якщо їх клички (nickname) та види (species) однакові.
         * 
         * @param obj об'єкт для порівняння
         * @return true, якщо об'єкти рівні; false в іншому випадку
         * 
         * Критерій рівності: поля nickname (кличка) та species (вид).
         * 
         * Важливо: метод узгоджений з compareTo() - якщо equals() повертає true,
         * то compareTo() повертає 0, оскільки обидва методи порівнюють за nickname та species.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Pet pet = (Pet) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(pet.nickname) : pet.nickname == null;
            boolean speciesEquals = species != null ? species.equals(pet.species) : pet.species == null;
            
            return nicknameEquals && speciesEquals;
        }

        /**
         * Повертає хеш-код для цього Pet.
         * 
         * @return хеш-код, обчислений на основі nickname та species
         * 
         * Базується на полях nickname та species для узгодженості з equals().
         * 
         * Важливо: узгоджений з equals() - якщо два Pet рівні за equals()
         * (мають однакові nickname та species), вони матимуть однаковий hashCode().
         */
        @Override
        public int hashCode() {
            // Початкове значення: хеш-код поля nickname (або 0, якщо nickname == null)
            int result = nickname != null ? nickname.hashCode() : 0;
            
            // Комбінуємо хеш-коди полів за формулою: result = 31 * result + hashCode(поле)
            // Множник 31 - просте число, яке дає хороше розподілення хеш-кодів
            // і оптимізується JVM як (result << 5) - result
            // Додаємо хеш-код виду (або 0, якщо species == null) до загального результату
            result = 31 * result + (species != null ? species.hashCode() : 0);
            
            return result;
        }

        /**
         * Повертає строкове представлення Pet.
         * 
         * @return кличка тварини (nickname), вид (species) та hashCode
         */
        @Override
        public String toString() {
            if (species != null) {
                return "Pet{nickname='" + nickname + "', species='" + species + "', hashCode=" + hashCode() + "}";
            }
            return "Pet{nickname='" + nickname + "', hashCode=" + hashCode() + "}";
        }
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashtable Hashtable з початковими даними (ключ: Pet, значення: ім'я власника)
     * @param treeMap TreeMap з початковими даними (ключ: Pet, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(Hashtable<Pet, String> hashtable, TreeMap<Pet, String> treeMap) {
        this.hashtable = hashtable;
        this.treeMap = treeMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з Hashtable
        System.out.println("========= Операції з Hashtable =========");
        System.out.println("Початковий розмір Hashtable: " + hashtable.size());
        
        // Пошук до сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        // Пошук після сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        addEntryToHashtable();
        
        removeByKeyFromHashtable();
        removeByValueFromHashtable();
               
        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());

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


    // ===== Методи для Hashtable =====

    /**
     * Виводить вміст Hashtable без сортування.
     * Hashtable не гарантує жодного порядку елементів.
     */
    private void printHashtable() {
        System.out.println("\n=== Пари ключ-значення в Hashtable ===");
        long timeStart = System.nanoTime();

        hashtable.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в Hashtable");
    }

    /**
     * Сортує Hashtable за ключами.
     * Використовує Stream API з природним порядком Pet (Pet.compareTo()).
     * Перезаписує hashtable відсортованими даними.
     */
    private void sortHashtable() {
        long timeStart = System.nanoTime();

        // Використовуємо Stream API для сортування та створення нової Hashtable
        hashtable = hashtable.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        Hashtable::new
                ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування Hashtable за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в Hashtable.
     * Використовує Pet.hashCode() та Pet.equals() для пошуку.
     */
    void findByKeyInHashtable() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в Hashtable");

        if (found) {
            String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням in Hashtable.
     * Використовує Stream API для фільтрації та пошуку.
     */
    void findByValueInHashtable() {
        long timeStart = System.nanoTime();

        // Використовуємо Stream API для пошуку за значенням
        Map.Entry<Pet, String> foundEntry = hashtable.entrySet().stream()
                .filter(entry -> VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue()))
                .findFirst()
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в Hashtable");

        if (foundEntry != null) {
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Pet: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Додає новий запис до Hashtable.
     */
    void addEntryToHashtable() {
        long timeStart = System.nanoTime();

        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до Hashtable");

        System.out.println("Додано новий запис: Pet='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з Hashtable за ключем.
     */
    void removeByKeyFromHashtable() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з Hashtable");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з Hashtable за значенням.
     */
    void removeByValueFromHashtable() {
        long timeStart = System.nanoTime();

        // Використовуємо Stream API для пошуку ключів для видалення
        List<Pet> keysToRemove = hashtable.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        keysToRemove.forEach(hashtable::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з Hashtable");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для TreeMap =====

    /**
     * Виводить вміст TreeMap.
     * TreeMap автоматично відсортована за ключами (Pet nickname за зростанням, species за спаданням).
     */
    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");

        long timeStart = System.nanoTime();
        treeMap.forEach((key, value) ->
            System.out.println("  " + key + " -> " + value)
        );

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в TreeMap");
    }

    /**
     * Здійснює пошук елемента за ключем в TreeMap.
     * Використовує Pet.compareTo() для навігації по дереву.
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
     * Використовує Stream API для фільтрації та пошуку.
     */
    void findByValueInTreeMap() {
        long timeStart = System.nanoTime();

        // Використовуємо Stream API для пошуку за значенням
        Map.Entry<Pet, String> foundEntry = treeMap.entrySet().stream()
                .filter(entry -> VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue()))
                .findFirst()
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в TreeMap");

        if (foundEntry != null) {
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Pet: " + foundEntry.getKey());
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

        System.out.println("Додано новий запис: Pet='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
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

        // Використовуємо Stream API для пошуку ключів для видалення
        List<Pet> keysToRemove = treeMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        keysToRemove.forEach(treeMap::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Pet, значення: ім'я власника)
        Hashtable<Pet, String> hashtable = new Hashtable<>();
        hashtable.put(new Pet("Тум", "Сова вухата"), "Андрій");
        hashtable.put(new Pet("Луна", "Полярна сова"), "Ірина");
        hashtable.put(new Pet("Барсик", "Сова сіра"), "Олена");
        hashtable.put(new Pet("Боні", "Сипуха"), "Олена");
        hashtable.put(new Pet("Тайсон", "Сова болотяна"), "Ірина");
        hashtable.put(new Pet("Барсик", "Сичик-горобець"), "Андрій");
        hashtable.put(new Pet("Ґуфі", "Сова болотяна"), "Тимофій");
        hashtable.put(new Pet("Боні", "Сова яструбина"), "Поліна");
        hashtable.put(new Pet("Муся", "Сова білолиця"), "Стефанія");
        hashtable.put(new Pet("Чіпо", "Сичик-хатник"), "Ярослав");

        TreeMap<Pet, String> treeMap = new TreeMap<Pet, String>() {{
            put(new Pet("Тум", "Сова вухата"), "Андрій");
            put(new Pet("Луна", "Полярна сова"), "Ірина");
            put(new Pet("Барсик", "Сова сіра"), "Олена");
            put(new Pet("Боні", "Сипуха"), "Олена");
            put(new Pet("Тайсон", "Сова болотяна"), "Ірина");
            put(new Pet("Барсик", "Сичик-горобець"), "Андрій");
            put(new Pet("Ґуфі", "Сова болотяна"), "Тимофій");
            put(new Pet("Боні", "Сова яструбина"), "Поліна");
            put(new Pet("Муся", "Сова білолиця"), "Стефанія");
            put(new Pet("Чіпо", "Сичик-хатник"), "Ярослав");
        }};

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, treeMap);
        operations.executeDataOperations();
    }
}

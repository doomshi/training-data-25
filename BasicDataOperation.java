/**
 * Загальний клас BasicDataOperation координує роботу різних структур даних.
 *
 * Варіант:
 * - тип даних: float
 * - масив: Float[]
 * - колекції: Vector<Float>, TreeSet<Float>, PriorityQueue<Float>
 * - файл з даними: float.data
 * - значення для пошуку: -31388.32
 */
public class BasicDataOperation {
    static final String PATH_TO_DATA_FILE = "list/float.data";
    static final float DEFAULT_VALUE_TO_SEARCH = -31388.32f;

    private float valueToSearch;
    private Float[] floatArray;

    private static final String SEPARATOR = "\n" + "=".repeat(80) + "\n";
    private static final String USAGE_MESSAGE = "Використання: java BasicDataOperation [пошукове-значення]\n" +
            "Якщо значення не вказано, буде використано значення за замовчуванням: -31388.32\n" +
            "Приклад:\n" +
            "  java BasicDataOperation -31388.32";

    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println(USAGE_MESSAGE);
            return;
        }

        BasicDataOperation coordinator = new BasicDataOperation();
        coordinator.executeOperations(args);
    }

    /**
     * Координує виконання операцій залежно від обраного типу.
     * 
     * @param args Аргументи командного рядка
     */
    private void executeOperations(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("🚀 РОЗПОЧАТО АНАЛІЗ ДАНИХ float 🚀");

        float searchValue = DEFAULT_VALUE_TO_SEARCH;
        if (args.length == 1) {
            try {
                searchValue = Float.parseFloat(args[0]);
            } catch (NumberFormatException nfe) {
                System.out.println("Помилка: Невірний формат числа. Приклад: -31388.32");
                return;
            }
        }

        this.valueToSearch = searchValue;
        System.out.println("Пошуковий параметр: " + this.valueToSearch);
        System.out.println(SEPARATOR);

        // Завантажуємо дані з файлу у Float[]
        floatArray = DataFileHandler.loadFloatArrayFromFile(PATH_TO_DATA_FILE);

        runAllOperations();

        System.out.println(SEPARATOR);
        System.out.println("✅ АНАЛІЗ ЗАВЕРШЕНО ✅");
        System.out.println(SEPARATOR);
    }

    /**
     * Запускає операції з колекцією List.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runListOperations() {
        System.out.println("📋 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ LIST");
        System.out.println("-".repeat(50));

        try {
            // Створення екземпляру класу з передаванням даних
            BasicDataOperationUsingList listProcessor = new BasicDataOperationUsingList(valueToSearch, floatArray);
            listProcessor.executeDataOperations();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з List: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з колекцією Queue.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runQueueOperations() {
        System.out.println("🔄 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ QUEUE");
        System.out.println("-".repeat(50));

        try {
            // Створення екземпляру класу з передаванням даних
            BasicDataOperationUsingQueue queueProcessor = new BasicDataOperationUsingQueue(valueToSearch, floatArray);
            queueProcessor.runDataProcessing();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з Queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з колекцією Set.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runSetOperations() {
        System.out.println("🔍 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ SET");
        System.out.println("-".repeat(50));

        try {
            // Створення екземпляру класу з передаванням даних
            BasicDataOperationUsingSet setProcessor = new BasicDataOperationUsingSet(valueToSearch, floatArray);
            setProcessor.executeDataAnalysis();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з Set: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з усіма типами колекцій для порівняння.
     * 
     * @param args Аргументи для передачі до класів
     */
    private void runAllOperations() {
        System.out.println("🎯 КОМПЛЕКСНИЙ АНАЛІЗ ВСІХ СТРУКТУР ДАНИХ");
        System.out.println("=".repeat(60));

        // Обробка List
        runListOperations();
        System.out.println("\n" + "~".repeat(60) + "\n");

        // Обробка Queue
        runQueueOperations();
        System.out.println("\n" + "~".repeat(60) + "\n");

        // Обробка Set
        runSetOperations();
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Клас DataFileHandler управляє роботою з файлами даних типу float (Float).
 */
public class DataFileHandler {
    private static final int INITIAL_CAPACITY = 1024;

    /**
     * Завантажує масив Float з файлу (по одному числу в рядку).
     *
     * @param filePath Шлях до файлу з даними.
     * @return Масив Float (wrapper-тип для float).
     */
    public static Float[] loadArrayFromFile(String filePath) {
        Float[] temporaryArray = new Float[INITIAL_CAPACITY];
        int currentIndex = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                // Видаляємо можливі невидимі символи та BOM
                currentLine = currentLine.trim().replaceAll("^\\uFEFF", "");
                if (currentLine.isEmpty()) {
                    continue;
                }

                float parsedValue = Float.parseFloat(currentLine);

                if (currentIndex >= temporaryArray.length) {
                    Float[] expanded = new Float[temporaryArray.length * 2];
                    System.arraycopy(temporaryArray, 0, expanded, 0, temporaryArray.length);
                    temporaryArray = expanded;
                }

                temporaryArray[currentIndex++] = parsedValue;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Float[] resultArray = new Float[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);
        return resultArray;
    }

    /**
     * Зберігає масив Float у файл (по одному числу в рядку).
     *
     * @param floatArray Масив Float.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Float[] floatArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Float value : floatArray) {
                if (value == null) {
                    continue;
                }
                fileWriter.write(value.toString());
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

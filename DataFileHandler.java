import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Клас DataFileHandler управляє роботою з файлами даних LocalDateTime.
 */
public class DataFileHandler {
    /**
     * Завантажує масив об'єктів LocalDateTime з файлу.
     * 
     * @param filePath Шлях до файлу з даними.
     * @return Масив об'єктів LocalDateTime.
     */
    public static Float[] loadArrayFromFile(String filePath) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    .map(currentLine -> currentLine.trim().replaceAll("^\\uFEFF", ""))
                    .filter(currentLine -> !currentLine.isEmpty())
                    .map(currentLine -> Float.parseFloat(currentLine))
                    .toArray(Float[]::new);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка читання даних з файлу: " + filePath, ioException);
        }
    }

    /**
     * Зберігає масив об'єктів LocalDateTime у файл.
     * 
     * @param dateTimeArray Масив об'єктів LocalDateTime.
     * @param filePath      Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Float[] floatArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            String content = Arrays.stream(floatArray)
                    .map(String::valueOf)
                    .collect(Collectors.joining(System.lineSeparator()));

            fileWriter.write(content);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

package checkpoint1.br.com.fiap.twoespwx.libunclepresser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        String inputPath = "C:\\Users\\Lenovo\\OneDrive\\check_point_fiap_esp_wx_s2_2024\\checkpoint_1\\libunclepresser\\inputs\\input1.txt";
        String outputPath = "C:\\Users\\Lenovo\\OneDrive\\check_point_fiap_esp_wx_s2_2024\\checkpoint_1\\libunclepresser\\outputs\\OUTPUT1.TXT";

        try {
            String inputData = readInputFile(inputPath);
            String compressedData = runLengthEncode(inputData);
            writeOutputFile(outputPath, compressedData);

            Map<Character, Integer> frequencies = calculateFrequencies(inputData);

            displayInterface(inputPath, outputPath, inputData, compressedData, frequencies);

        } catch (IOException e) {
            System.out.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

    public static String readInputFile(String inputPath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(inputPath));
        return new String(bytes, StandardCharsets.UTF_8).replaceAll("\\s+", "").toUpperCase();
    }


    public static String runLengthEncode(String data) {
        StringBuilder result = new StringBuilder();
        int count = 1;


        for (int i = 1; i < data.length(); i++) {
            if (data.charAt(i) == data.charAt(i - 1)) {
                count++;
            } else {
                result.append(count).append(data.charAt(i - 1));
                count = 1;
            }
        }
        result.append(count).append(data.charAt(data.length() - 1));

        return result.toString();
    }


    public static Map<Character, Integer> calculateFrequencies(String data) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : data.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }


    public static void writeOutputFile(String outputPath, String data) throws IOException {
        Files.write(Paths.get(outputPath), data.getBytes(StandardCharsets.UTF_8));
    }

    public static void displayInterface(String inputPath, String outputPath, String inputData, String compressedData, Map<Character, Integer> frequencies) {
        int inputSize = inputData.length();
        int outputSize = compressedData.length();
        double compressionRate = ((double) outputSize / inputSize) * 100;

        System.out.println(" -----------------------------------------------------------");
        System.out.println("|           LIB UNCLE PRESSER - GRUPO SJ                    |");
        System.out.println("|-----------------------------------------------------------|");
        System.out.println("| INPUT FILENAME: " + inputPath.substring(inputPath.lastIndexOf("\\") + 1) + " |");
        System.out.println("| OUTPUT FILENAME: " + outputPath.substring(outputPath.lastIndexOf("\\") + 1) + " |");
        System.out.println("|                                                           |");
        System.out.println("| INPUT FILE SIZE: " + inputSize + " characters             |");
        System.out.println("|                                                           |");
        System.out.println("| FREQUENCIES:                                              |");
        frequencies.forEach((key, value) -> {
            double percentage = ((double) value / inputSize) * 100;
            System.out.printf("| %c: %d (%.2f%%)                                        |\n", key, value, percentage);
        });
        System.out.println("|                                                           |");
        System.out.println("| ALGORITHM: Run-Length Encoding (RLE)                      |");
        System.out.println("| COMPRESSION RATE: " + String.format("%.2f", compressionRate) + "%             |");
        System.out.println("|                                                           |");
        System.out.println("| OUTPUT FILE SIZE: " + outputSize + " characters           |");
        System.out.println(" -----------------------------------------------------------");
        System.out.println("|                                                           |");
        System.out.println("| SCORE: WELL-DONE                                          |");
        System.out.println("|                                                           |");
        System.out.println(" -----------------------------------------------------------");
    }
}
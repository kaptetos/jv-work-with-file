package core.basesyntax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final String OPERATION_SUPPLY = "supply";
    private static final String OPERATION_BUY = "buy";
    private static final String SEPARATOR = ",";

    public void getStatistic(String fromFileName, String toFileName) {
        try {
            String[] lines = readFile(fromFileName);
            int totalSupply = 0;
            int totalBuy = 0;

            for (String line : lines) {
                String[] fields = line.split(SEPARATOR);
                String operation = fields[0];
                int amount = Integer.parseInt(fields[1]);
                if (OPERATION_SUPPLY.equals(operation)) {
                    totalSupply += amount;
                } else if (OPERATION_BUY.equals(operation)) {
                    totalBuy += amount;
                }
            }

            int result = calculateResult(totalSupply, totalBuy);
            String report = generateReport(totalSupply, totalBuy, result);
            writeToFile(report, toFileName);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file", e);
        }
    }

    private String[] readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines().toArray(String[]::new);
        }
    }

    private String generateReport(int totalSupply, int totalBuy, int result) {
        StringBuilder report = new StringBuilder();
        report.append(OPERATION_SUPPLY).append(SEPARATOR).append(totalSupply).append("\n");
        report.append(OPERATION_BUY).append(SEPARATOR).append(totalBuy).append("\n");
        report.append("result").append(SEPARATOR).append(result).append("\n");
        return report.toString();
    }

    private void writeToFile(String report, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }

    private int calculateResult(int totalSupply, int totalBuy) {
        return totalSupply - totalBuy;
    }
}

package statistic;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Statistical {
    private static final List<Result> results = new ArrayList<>();
    private final List<Double> accuracy;
    private final List<Double> reduction;
    private final List<Double> time;
    private File folder;
    private String name;
    private String algorithm;

    private Statistical() {
        accuracy = new ArrayList<>();
        reduction = new ArrayList<>();
        time = new ArrayList<>();
    }

    public Statistical(String folderName, String algorithm) {
        this();
        this.folder = new File(folderName);
        this.name = folder.getName();
        this.algorithm = algorithm;
    }

    public static void createValuesData() {
        Map<String, List<Result>> resultMap = results.stream().collect(Collectors.groupingBy(Result::getName));
        System.out.println(resultMap);

        System.out.println("Accuracy Array");

        resultMap.forEach((key, value) -> {
            System.out.print("{");
            for (int i = 0; i < value.size(); i++) {
                Result result = value.get(i);
                if (i == 0) System.out.print(result.mAccuracy);
                else System.out.print("," + result.mAccuracy);
            }
            System.out.println("},");
        });

        System.out.println("Reduction Array");
        resultMap.forEach((key, value) -> {
            System.out.print("{");
            for (int i = 0; i < value.size(); i++) {
                Result result = value.get(i);
                if (i == 0) System.out.print(result.mReduction);
                else System.out.print("," + result.mReduction);
            }
            System.out.println("},");
        });

        System.out.println("Time Array");
        resultMap.forEach((key, value) -> {
            System.out.print("{");
            for (int i = 0; i < value.size(); i++) {
                Result result = value.get(i);
                if (i == 0) System.out.print(result.mTime);
                else System.out.print(", " + result.mTime);
            }
            System.out.println("},");
        });
    }

    public void begin() {
        processDirectory(folder);

        if (accuracy.isEmpty()) {
            System.out.println("Acc is empty at " + name);
        }

        if (reduction.isEmpty()) {
            System.out.println("Red is empty at " + name);
        }

        if (time.isEmpty()) {
            System.out.println("Tim is empty at " + name);
        }

        Map.Entry<Double, Double> accuracyStats  = applyStatistics(accuracy);
        Map.Entry<Double, Double> reductionStats = applyStatistics(reduction);
        Map.Entry<Double, Double> timeStats      = applyStatistics(time);

        Result result = new Result(
                name, algorithm,
                accuracyStats.getKey(), reductionStats.getKey(), timeStats.getKey(),
                accuracyStats.getValue(), reductionStats.getValue(), timeStats.getValue()
        );

        results.add(result);



        //System.out.println(accuracy);
        //System.out.println(reduction);
        //System.out.println(time);

        //System.out.println("Accuracy:");
        //printEntry(accuracyStats);
        //System.out.println("Reduction:");
        //printEntry(reductionStats);
        //System.out.println("Time:");
        //printEntry(timeStats);

        System.out.println(name + "\t" + accuracyStats.getKey() + "\t" + accuracyStats.getValue() + "\t" + reductionStats.getKey() + "\t" + reductionStats.getValue() + "\t" + timeStats.getKey() + "\t" + timeStats.getValue() + "\t");
    }

    private void printEntry(Map.Entry<Double,Double> entry) {
        System.out.println("Mean: " + entry.getKey() + "  \tStandard Deviation: " + entry.getValue());
    }

    private Map.Entry<Double, Double> applyStatistics(List<Double> values) {
        double mean = values.stream().mapToDouble(Double::doubleValue).average().orElse(-1);
        double sum  = values.stream().mapToDouble(value -> Math.pow(value - mean, 2)).sum();
        if (mean == -1) {
            throw new RuntimeException("Everything is wrong");
        }
        return new AbstractMap.SimpleEntry<>(mean, Math.sqrt(sum/values.size()));
    }

    private void processDirectory(File folder) {
        //noinspection ConstantConditions
        List<File> files = Arrays.asList(folder.listFiles());
        files.forEach(this::processFile);
    }

    private void processFile(File file) {
        if (file.isDirectory()) {
            //System.out.println("Directory: " + file.getName());
            if (!file.getName().startsWith(name)) {
                System.out.println(file.getName() + " is in " + name);
                System.exit(0);
                return;
            }

            processDirectory(file);
            return;
        }

        if (!file.getName().startsWith("FUN")) {
            //System.out.println("File: " + file.getName());
            return;
        }

        try {
            Files.lines(file.toPath()).filter(str -> !str.trim().isEmpty()).map(LineValues::fromString).forEach(this::consumeLineValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consumeLineValue(LineValues value) {
        if (value.timer) time.add(value.time);
        else {
            accuracy.add(value.accuracy);
            reduction.add(value.reduction);
        }
    }

    private static class LineValues {
        boolean timer;
        double accuracy;
        double reduction;
        double time;

        LineValues(double time) {
            this.time = time;
            this.timer = true;
        }

        LineValues(double accuracy, double reduction) {
            this.accuracy = accuracy;
            this.reduction = reduction;
        }

        static LineValues fromString(String line) {
            if (line.startsWith("Time")) {
                double time = Double.parseDouble(line.split(" ")[1]);
                return new LineValues(time);
            }

            String[] values = line.split(" ");
            double accuracy = Double.parseDouble(values[0]) * -1;
            double reduction = Double.parseDouble(values[1]) * -1;

            return new LineValues(accuracy, reduction);
        }
    }

    private static class Result {
        final String name;
        final String algorithm;
        double mAccuracy;
        double mReduction;
        double mTime;
        double sAccuracy;
        double sReduction;
        double sTime;

        Result(String name, String algorithm, double mAccuracy, double mReduction, double mTime, double sAccuracy, double sReduction, double sTime) {
            this.name = name;
            this.algorithm = algorithm;
            this.mAccuracy = mAccuracy;
            this.mReduction = mReduction;
            this.mTime = mTime;
            this.sAccuracy = sAccuracy;
            this.sReduction = sReduction;
            this.sTime = sTime;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public String getName() {
            return name;
        }

        public double getAccuracyMean() {
            return mAccuracy;
        }

        @Override
        public String toString() {
            return name + " " + algorithm;
        }
    }
}

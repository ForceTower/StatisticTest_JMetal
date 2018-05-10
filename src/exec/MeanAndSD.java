package exec;

import statistic.Statistical;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeanAndSD {
    public static void main(String[] args) throws IOException {
        //File mainFolder = new File("C:\\Users\\joaop\\Desktop\\Small\\small_exec_all_second\\data - Copia");
        Path mainFolder = Paths.get("C:\\Users\\joaop\\Desktop\\Small\\small_exec_all_second\\data - Copia");
        Files.list(mainFolder).forEach(path -> {
            File file = path.toFile();
            try {
                Files.list(path).forEach(subPath -> {
                    File data = subPath.toFile();
                    new Statistical(data.getPath(), file.getName()).begin();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /*
        //noinspection ConstantConditions
        for (File f : mainFolder.listFiles()) {
            System.out.println("In folder: " + f.getName());
            //noinspection ConstantConditions
            for (File l : f.listFiles()) {
                System.out.println("Dataset: " + l.getName());
                new Statistical(l.getPath(), f.getName()).begin();
            }
        }
        */
        Statistical.createValuesData();
    }

    public static Stream<Double> tokenize(Stream<String> str) {
        return str.map(Double::parseDouble);
    }
}

package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
    private final String folderPath;
    private static int numberOfLevels;

    public FileIO(String folderPath) {
        this.folderPath = folderPath;

    }

    public List<Level> loadLevels() {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files == null) {
            throw new IllegalArgumentException("Invalid folder path or no files found.");
        }

        List<Level> levels = new ArrayList<>();

        for (File file : files) {
            if (file.isFile() && file.getName().startsWith("Level") && file.getName().endsWith(".csv")) {
                Level level = readLevelFromFile(file.getAbsolutePath());
                levels.add(level);
            }
        }

        return levels;
    }

    private Level readLevelFromFile(String fileName) {
        Level level = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // First line for level size
            String[] dimensions = line.split(",");
            int rows = Integer.parseInt(dimensions[0].trim());
            int cols = Integer.parseInt(dimensions[1].trim());

            Tile[][] tiles = new Tile[rows][cols];

            String valuesLine;
            int rowIndex = 0;

            while ((valuesLine = reader.readLine()) != null) {
                String[] values = valuesLine.split(",");
                for (int colIndex = 0; colIndex < values.length; colIndex++) {
                    int value = Integer.parseInt(values[colIndex].trim());
                    tiles[rowIndex][colIndex] = new Tile(value);
                }
                rowIndex++;
            }

            level = new Level(tiles,numberOfLevels);
            numberOfLevels++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return level;
    }
    public void saveScoreToFile (Object score){
    }
}
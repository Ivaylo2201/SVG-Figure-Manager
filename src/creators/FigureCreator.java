package creators;

import managers.FileManager;

import java.io.*;

public class FigureCreator {
    private static final FileManager fm = FileManager.getInstance();

    public void create(String figure) {
        try {
            String line;
            int idx;
            BufferedReader reader = new BufferedReader(new FileReader(fm.file));
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            reader.close();

            if ((idx = content.indexOf("</svg>")) != -1) {
                content.insert(idx, figure);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(fm.file));
            writer.write(content.toString());
            writer.close();

        } catch (IOException e) {
            System.out.println("An error has occurred!");
        }
    }
}
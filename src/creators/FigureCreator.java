package creators;

import interfaces.Figure;
import managers.FileManager;

import java.io.*;

/**
 * A utility class for creating figures in an SVG file.
 */
public class FigureCreator {
    private static final FileManager fm = FileManager.getInstance();

    /**
     * Inserts the specified figure into the current SVG file.
     *
     * @param figure The figure object
     */
    public void create(Figure figure) {
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
                content.insert(idx, figure.toString());
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(fm.file));
            writer.write(content.toString());
            writer.close();

        } catch (IOException e) {
            System.out.println("An error has occurred!");
        }
    }
}

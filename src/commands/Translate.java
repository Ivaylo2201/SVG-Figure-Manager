package commands;

import contracts.CommandWithParams;
import figures.Circle;
import figures.Line;
import figures.Rectangle;
import managers.FileManager;
import processors.CircleProcessor;
import processors.LineProcessor;
import processors.RectangleProcessor;

import java.io.*;
import java.util.List;

public class Translate implements CommandWithParams {
    private static final FileManager fm = FileManager.getInstance();
    private static final RectangleProcessor rp = new RectangleProcessor();
    private static final CircleProcessor cp = new CircleProcessor();
    private static final LineProcessor lp = new LineProcessor();
    private static StringBuilder translatedContent;

    private void translateAll(List<String> figures, int verticalTranslation, int horizontalTranslation) {
        String figure;

        for (String f : figures) {
            figure = f.trim();

            if (figure.startsWith("<rect"))
                translatedContent.append(new Rectangle(rp.getProperties(figure)).translate(verticalTranslation, horizontalTranslation));
            else if (figure.startsWith("<circle"))
                translatedContent.append(new Circle(cp.getProperties(figure)).translate(verticalTranslation, horizontalTranslation));
            else if (figure.startsWith("<line"))
                translatedContent.append(new Line(lp.getProperties(figure)).translate(verticalTranslation, horizontalTranslation));
        }
        System.out.println("Successfully translated all figures!");
    }

    private void translateOne(List<String> figures, int translateIndex, int verticalTranslation, int horizontalTranslation) {
        String figure;

        if (translateIndex <= 0 || translateIndex > figures.size()) {
            System.out.println("Invalid figure index!");
        } else {
            System.out.println("Figure " + translateIndex + " translated successfully!");
        }

        for (int i = 0; i < figures.size(); i++) {
            figure = figures.get(i);

            if (i == translateIndex - 1) {
                if (figure.trim().startsWith("<rect"))
                    translatedContent.append(new Rectangle(rp.getProperties(figure)).translate(verticalTranslation, horizontalTranslation));
                else if (figure.trim().startsWith("<circle"))
                    translatedContent.append(new Circle(cp.getProperties(figure)).translate(verticalTranslation, horizontalTranslation));
                else if (figure.trim().startsWith("<line"))
                    translatedContent.append(new Line(lp.getProperties(figure)).translate(verticalTranslation, horizontalTranslation));
            } else {
                translatedContent.append(figure).append("\n");
            }
        }
    }

    @Override
    public void execute(List<String> args) {
        if (fm.file == null) {
            System.out.println("No file is opened!");
            return;
        }

        if (args.isEmpty()) {
            System.out.println("Arguments were not provided!");
            return;
        }

        boolean translateOne = args.size() == 3;

        try {
            List<String> figures = fm.getFigures();

            translatedContent = new StringBuilder().append("<?xml version=\"1.0\" standalone=\"no\"?>").append("\n").append("<!DOCTYPE svg PUBLIC>").append("\n").append("<svg>").append("\n");

            if (translateOne) {
                this.translateOne(figures, Integer.parseInt(args.getFirst()), Integer.parseInt(args.get(1).split("=")[1]), Integer.parseInt(args.get(2).split("=")[1]));
            } else {
                this.translateAll(figures, Integer.parseInt(args.getFirst().split("=")[1]), Integer.parseInt(args.get(1).split("=")[1]));
            }

            translatedContent.append("</svg>");

            BufferedWriter writer = new BufferedWriter(new FileWriter(fm.file));
            writer.write(translatedContent.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("An error has occurred!");
        }
    }
}
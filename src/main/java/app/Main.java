package app;

import equation.LinearEquation;
import equation.LinearEquationsSystem;
import number.ComplexParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    private static final Arguments arguments = new Arguments();

    public static void main(String[] args) {
        arguments.processArguments(args);

        try {
            LinearEquationsSystem equationsSystem = readEquationsSystem(arguments.getInputFile());
            equationsSystem.solve();
            writeSolution(arguments.getOutputFile(), equationsSystem.getSolution());
        } catch (IOException e) {
            System.out.println("Error occurred when reading/writing file");
        }
    }

    private static LinearEquationsSystem readEquationsSystem(String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename), StandardCharsets.UTF_8)) {
            int numberOfUnknowns = scanner.nextInt();
            int numberOfEquations = scanner.nextInt();

            ComplexParser parser = new ComplexParser();
            LinearEquationsSystem equationsSystem = new LinearEquationsSystem(numberOfUnknowns, numberOfEquations);
            for (int i = 0; i < numberOfEquations; i++) {
                LinearEquation equation = new LinearEquation(numberOfUnknowns);
                for (int j = 0; j <= numberOfUnknowns; j++) {
                    equation.addCoefficient(parser.parse(scanner.next()));
                }
                equationsSystem.addEquation(equation);
            }

            return equationsSystem;
        }
    }

    private static void writeSolution(String filename, String solution) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.write(solution);
        }
    }
}

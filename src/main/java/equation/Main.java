package equation;

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
            double[] solution = equationsSystem.solve();
            writeEquationsSystem(arguments.getOutputFile(), solution);
        } catch (IOException e) {
            System.out.println("Error occurred when reading/writing file");
        }
    }

    private static LinearEquationsSystem readEquationsSystem(String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename), StandardCharsets.UTF_8)) {
            int numberOfEquations = scanner.nextInt();

            LinearEquationsSystem equationsSystem = new LinearEquationsSystem(numberOfEquations);
            for (int i = 0; i < numberOfEquations; i++) {
                LinearEquation equation = new LinearEquation(numberOfEquations);
                for (int j = 0; j <= numberOfEquations; j++) {
                    equation.addCoefficient(scanner.nextDouble());
                }
                equationsSystem.addEquation(equation);
            }

            return equationsSystem;
        }
    }

    private static void writeEquationsSystem(String filename, double[] solution) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (double value : solution) {
                writer.write(value + System.lineSeparator());
            }
        }
    }

    private static void printEquationsSystem(LinearEquationsSystem equationsSystem) {
        for (int i = 0; i < equationsSystem.getNumberOfEquations(); i++) {
            LinearEquation equation = equationsSystem.getEquation(i);
            for (int j = 0; j <= equation.getUnknownsNumber(); j++) {
                System.out.print(equation.getCoefficient(j) + " ");
            }
            System.out.println();
        }
    }
}

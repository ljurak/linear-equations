package equation;

public class LinearEquationsSystem {

    private final int numberOfEquations;

    private final LinearEquation[] equations;

    private int currentEquation = 0;

    public LinearEquationsSystem(int numberOfEquations) {
        this.numberOfEquations = numberOfEquations;
        this.equations = new LinearEquation[numberOfEquations];
    }

    public int getNumberOfEquations() {
        return numberOfEquations;
    }

    public void addEquation(LinearEquation equation) {
        if (currentEquation >= numberOfEquations) {
            throw new IllegalStateException("The equations system has already all equations entered");
        }
        equations[currentEquation++] = equation;
    }

    public LinearEquation getEquation(int index) {
        if (index < 0 || index >= numberOfEquations) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }

        return equations[index];
    }

    public double[] solve() {
        if (numberOfEquations != currentEquation) {
            throw new IllegalStateException("Equations system has not been entered correctly");
        }

        for (int i = 0; i < numberOfEquations; i++) {
            printEquationsSystem();
            System.out.println();
            LinearEquation equation = getEquation(i);

            if (equation.getCoefficient(i) != 1) {
                equation.multiplyByScalar(1 / equation.getCoefficient(i));
            }

            for (int j = i + 1; j < numberOfEquations; j++) {
                LinearEquation currentEquation = getEquation(j);
                currentEquation.addEquation(equation, -1 * currentEquation.getCoefficient(i));
            }
        }

        for (int i = numberOfEquations - 1; i >= 0; i--) {
            printEquationsSystem();
            System.out.println();
            LinearEquation equation = getEquation(i);

            for (int j = i - 1; j >= 0; j--) {
                LinearEquation currentEquation = getEquation(j);
                currentEquation.addEquation(equation, -1 * currentEquation.getCoefficient(i));
            }
        }

        double[] solution = new double[numberOfEquations];
        for (int i = 0; i < numberOfEquations; i++) {
            solution[i] = getEquation(i).getCoefficient(numberOfEquations);
        }

        return solution;
    }

    public void printEquationsSystem() {
        for (int i = 0; i < numberOfEquations; i++) {
            LinearEquation equation = getEquation(i);
            for (int j = 0; j <= equation.getUnknownsNumber(); j++) {
                System.out.printf("%6.2f ", equation.getCoefficient(j));
            }
            System.out.println();
        }
    }
}

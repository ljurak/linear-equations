package equation;

import java.util.ArrayList;
import java.util.List;

public class LinearEquationsSystem {

    private enum State {
        UNSOLVED,
        NO_SOLUTIONS,
        SINGLE_SOLUTION,
        INFINITE_SOLUTIONS
    }

    private final int numberOfUnknowns;

    private final int numberOfEquations;

    private final LinearEquation[] equations;

    private final List<ColumnSwap> columnSwaps = new ArrayList<>();

    private int currentEquation = 0;

    private State state = State.UNSOLVED;

    public LinearEquationsSystem(int numberOfUnknowns, int numberOfEquations) {
        this.numberOfUnknowns = numberOfUnknowns;
        this.numberOfEquations = numberOfEquations;
        this.equations = new LinearEquation[numberOfEquations];
    }

    public void addEquation(LinearEquation equation) {
        if (equation.getNumberOfUnknowns() != numberOfUnknowns) {
            throw new IllegalArgumentException("The equation has incorrect number of unknowns");
        }
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

    public void solve() {
        if (numberOfEquations != currentEquation) {
            throw new IllegalStateException("Equations system has not been entered correctly");
        }

        for (int i = 0; i < numberOfUnknowns && i < numberOfEquations; i++) {
            LinearEquation equation = getEquation(i);

            if (equation.getCoefficient(i) == 0) {
                int nonZeroRow = findNonZeroRow(i);
                if (nonZeroRow != -1) {
                    swapEquations(i, nonZeroRow);
                    equation = getEquation(i);
                    equation.multiplyByScalar(1 / equation.getCoefficient(i));
                } else {
                    int nonZeroColumn = findNonZeroColumn(i);
                    if (nonZeroColumn != -1) {
                        swapColumns(i, nonZeroColumn);
                        equation.multiplyByScalar(1 / equation.getCoefficient(i));
                    } else {
                        RowColumn rowColumn = findNonZeroRowColumn(i);
                        if (rowColumn != null) {
                            swapEquations(i, rowColumn.row);
                            swapColumns(i, rowColumn.col);
                            equation = getEquation(i);
                            equation.multiplyByScalar(1 / equation.getCoefficient(i));
                        } else {
                            break;
                        }
                    }
                }
            } else if (equation.getCoefficient(i) != 1) {
                equation.multiplyByScalar(1 / equation.getCoefficient(i));
            }

            for (int j = i + 1; j < numberOfEquations; j++) {
                LinearEquation currentEquation = getEquation(j);
                currentEquation.addEquation(equation, -1 * currentEquation.getCoefficient(i));
            }
        }

        int significantEquations = countSignificantEquations();

        if (checkForContradiction(significantEquations)) {
            state = State.NO_SOLUTIONS;
            return;
        }

        for (int i = significantEquations - 1; i > 0; i--) {
            LinearEquation equation = getEquation(i);

            for (int j = i - 1; j >= 0; j--) {
                LinearEquation currentEquation = getEquation(j);
                currentEquation.addEquation(equation, -1 * currentEquation.getCoefficient(i));
            }
        }

        if (significantEquations < numberOfUnknowns) {
            state = State.INFINITE_SOLUTIONS;
            return;
        }

        state = State.SINGLE_SOLUTION;
        printEquationsSystem();
    }

    public String getSolution() {
        if (state == State.UNSOLVED) {
            throw new IllegalStateException("Equations system is unsolved");
        }
        if (state == State.NO_SOLUTIONS) {
            return "No solutions";
        }
        if (state == State.INFINITE_SOLUTIONS) {
            return "Infinitely many solutions";
        }

        return getSingleSolution();
    }

    private int findNonZeroRow(int i) {
        for (int m = i + 1; m < numberOfEquations; m++) {
            if (getEquation(m).getCoefficient(i) != 0) {
                return m;
            }
        }
        return -1;
    }

    private int findNonZeroColumn(int i) {
        LinearEquation equation = getEquation(i);
        for (int n = i + 1; n < numberOfUnknowns; n++) {
            if (equation.getCoefficient(n) != 0) {
                return n;
            }
        }
        return -1;
    }

    private RowColumn findNonZeroRowColumn(int i) {
        for (int m = i + 1; m < numberOfEquations; m++) {
            LinearEquation equation = getEquation(m);
            for (int n = i + 1; n < numberOfUnknowns; n++) {
                if (equation.getCoefficient(n) != 0) {
                    return new RowColumn(m, n);
                }
            }
        }
        return null;
    }

    private void swapEquations(int i, int j) {
        LinearEquation temp = equations[i];
        equations[i] = equations[j];
        equations[j] = temp;
    }

    private void swapColumns(int i, int j) {
        for (int k = 0; k < numberOfEquations; k++) {
            getEquation(k).swapCoefficients(i, j);
        }
        columnSwaps.add(new ColumnSwap(i, j));
    }

    private int countSignificantEquations() {
        int significantEquations = 0;
        for (int i = 0; i < numberOfEquations && i < numberOfUnknowns; i++) {
            LinearEquation equation = getEquation(i);
            for (int j = 0; j < numberOfUnknowns; j++) {
                if (equation.getCoefficient(j) != 0) {
                    significantEquations++;
                    break;
                }
            }
        }
        return significantEquations;
    }

    private boolean checkForContradiction(int significantEquations) {
        for (int i = significantEquations; i < numberOfEquations; i++) {
            if (getEquation(i).getCoefficient(numberOfUnknowns) != 0) {
                return true;
            }
        }
        return false;
    }

    private String getSingleSolution() {
        double[] solution = new double[numberOfUnknowns];

        for (int i = 0; i < numberOfUnknowns; i++) {
            solution[i] = getEquation(i).getCoefficient(numberOfUnknowns);
        }

        if (!columnSwaps.isEmpty()) {
            for (int i = columnSwaps.size() - 1; i >= 0; i--) {
                ColumnSwap columnSwap = columnSwaps.get(i);
                double temp = solution[columnSwap.a];
                solution[columnSwap.a] = solution[columnSwap.b];
                solution[columnSwap.b] = temp;
            }
        }

        StringBuilder result = new StringBuilder();
        for (double variable : solution) {
            result.append(variable).append(System.lineSeparator());
        }

        return result.toString();
    }

    private void printEquationsSystem() {
        for (int i = 0; i < numberOfEquations; i++) {
            LinearEquation equation = getEquation(i);
            for (int j = 0; j <= equation.getNumberOfUnknowns(); j++) {
                System.out.printf("%6.2f ", equation.getCoefficient(j));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static final class ColumnSwap {

        private int a;
        private int b;

        ColumnSwap(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    private static final class RowColumn {

        private int row;
        private int col;

        RowColumn(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}

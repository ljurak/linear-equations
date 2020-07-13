package equation;

public class LinearEquation {

    private final int numberOfUnknowns;

    private final double[] coefficients;

    private int currentCoefficient = 0;

    public LinearEquation(int numberOfUnknowns) {
        this.numberOfUnknowns = numberOfUnknowns;
        this.coefficients = new double[numberOfUnknowns + 1];
    }

    public int getNumberOfUnknowns() {
        return numberOfUnknowns;
    }

    public double getCoefficient(int index) {
        if (index < 0 || index > numberOfUnknowns) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return coefficients[index];
    }

    public void addCoefficient(double coefficient) {
        if (currentCoefficient > numberOfUnknowns) {
            throw new IllegalStateException("The equation has already all coefficients entered");
        }
        coefficients[currentCoefficient++] = coefficient;
    }

    public void addEquation(LinearEquation equation, double multiplier) {
        if (numberOfUnknowns != equation.getNumberOfUnknowns()) {
            throw new IllegalArgumentException("Cannot add equation because of different size");
        }

        for (int i = 0; i <= numberOfUnknowns; i++) {
            coefficients[i] += equation.getCoefficient(i) * multiplier;
        }
    }

    public void multiplyByScalar(double multiplier) {
        for (int i = 0; i <= numberOfUnknowns; i++) {
            coefficients[i] *= multiplier;
        }
    }

    public void swapCoefficients(int i, int j) {
        if (i < 0 || i > numberOfUnknowns - 1) {
            throw new IndexOutOfBoundsException("Invalid column index: " + i);
        }
        if (j < 0 || j > numberOfUnknowns - 1) {
            throw new IndexOutOfBoundsException("Invalid column index: " + j);
        }

        double temp = coefficients[i];
        coefficients[i] = coefficients[j];
        coefficients[j] = temp;
    }
}

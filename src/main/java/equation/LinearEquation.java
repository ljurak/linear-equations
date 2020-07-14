package equation;

import number.Complex;

public class LinearEquation {

    private final int numberOfUnknowns;

    private final Complex[] coefficients;

    private int currentCoefficient = 0;

    public LinearEquation(int numberOfUnknowns) {
        this.numberOfUnknowns = numberOfUnknowns;
        this.coefficients = new Complex[numberOfUnknowns + 1];
    }

    public int getNumberOfUnknowns() {
        return numberOfUnknowns;
    }

    public Complex getCoefficient(int index) {
        if (index < 0 || index > numberOfUnknowns) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return coefficients[index];
    }

    public void addCoefficient(Complex coefficient) {
        if (currentCoefficient > numberOfUnknowns) {
            throw new IllegalStateException("The equation has already all coefficients entered");
        }
        coefficients[currentCoefficient++] = coefficient;
    }

    public void addEquation(LinearEquation equation, Complex multiplier) {
        if (numberOfUnknowns != equation.getNumberOfUnknowns()) {
            throw new IllegalArgumentException("Cannot add equation because of different size");
        }

        for (int i = 0; i <= numberOfUnknowns; i++) {
            coefficients[i] = coefficients[i].add(equation.getCoefficient(i).multiply(multiplier));
        }
    }

    public void multiplyByScalar(Complex multiplier) {
        for (int i = 0; i <= numberOfUnknowns; i++) {
            coefficients[i] = coefficients[i].multiply(multiplier);
        }
    }

    public void divideByScalar(Complex divider) {
        for (int i = 0; i <= numberOfUnknowns; i++) {
            coefficients[i] = coefficients[i].divide(divider);
        }
    }

    public void swapCoefficients(int i, int j) {
        if (i < 0 || i > numberOfUnknowns - 1) {
            throw new IndexOutOfBoundsException("Invalid column index: " + i);
        }
        if (j < 0 || j > numberOfUnknowns - 1) {
            throw new IndexOutOfBoundsException("Invalid column index: " + j);
        }

        Complex temp = coefficients[i];
        coefficients[i] = coefficients[j];
        coefficients[j] = temp;
    }
}

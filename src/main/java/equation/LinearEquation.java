package equation;

public class LinearEquation {

    private final int unknownsNumber;

    private final double[] coefficients;

    private int currentCoefficient = 0;

    public LinearEquation(int unknownsNumber) {
        this.unknownsNumber = unknownsNumber;
        this.coefficients = new double[unknownsNumber + 1];
    }

    public int getUnknownsNumber() {
        return unknownsNumber;
    }

    public double getCoefficient(int index) {
        if (index < 0 || index > unknownsNumber) {
            throw new IndexOutOfBoundsException("Index value: " + index + " out of range [0, " + unknownsNumber + "]");
        }
        return coefficients[index];
    }

    public void addCoefficient(double coefficient) {
        if (currentCoefficient > unknownsNumber) {
            throw new IllegalStateException("The equation has already all coefficients entered");
        }
        coefficients[currentCoefficient++] = coefficient;
    }

    public void addEquation(LinearEquation equation, double multiplier) {
        if (unknownsNumber != equation.getUnknownsNumber()) {
            throw new IllegalArgumentException("Cannot add equation because of different size");
        }

        for (int i = 0; i <= unknownsNumber; i++) {
            coefficients[i] += equation.getCoefficient(i) * multiplier;
        }
    }

    public void multiplyByScalar(double multiplier) {
        for (int i = 0; i <= unknownsNumber; i++) {
            coefficients[i] *= multiplier;
        }
    }
}

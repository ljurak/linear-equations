package equation;

import static org.junit.jupiter.api.Assertions.*;

import number.Complex;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class LinearEquationsSystemTest {

    @Test
    public void shouldSolveSystemWithSingleSolution() {
        // given
        LinearEquationsSystem system = new LinearEquationsSystem(3, 3);

        LinearEquation equation1 = new LinearEquation(3);
        equation1.addCoefficient(new Complex(1, 0));
        equation1.addCoefficient(new Complex(-2, 0));
        equation1.addCoefficient(new Complex(3, 0));
        equation1.addCoefficient(new Complex(-7, 0));
        system.addEquation(equation1);

        LinearEquation equation2 = new LinearEquation(3);
        equation2.addCoefficient(new Complex(3, 0));
        equation2.addCoefficient(new Complex(1, 0));
        equation2.addCoefficient(new Complex(4, 0));
        equation2.addCoefficient(new Complex(5, 0));
        system.addEquation(equation2);

        LinearEquation equation3 = new LinearEquation(3);
        equation3.addCoefficient(new Complex(2, 0));
        equation3.addCoefficient(new Complex(5, 0));
        equation3.addCoefficient(new Complex(1, 0));
        equation3.addCoefficient(new Complex(18, 0));
        system.addEquation(equation3);

        // when
        system.solve();
        double[] results = Arrays.stream(system.getSolution().split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();

        // then
        assertEquals(2.0, results[0], 0.001);
        assertEquals(3.0, results[1], 0.001);
        assertEquals(-1.0, results[2], 0.001);
    }

    @Test
    public void shouldSolveSystemWithNoSolutions() {
        // given
        LinearEquationsSystem system = new LinearEquationsSystem(4, 3);

        LinearEquation equation1 = new LinearEquation(4);
        equation1.addCoefficient(new Complex(1, 0));
        equation1.addCoefficient(new Complex(2, 0));
        equation1.addCoefficient(new Complex(-1, 0));
        equation1.addCoefficient(new Complex(-1, 0));
        equation1.addCoefficient(new Complex(1, 0));
        system.addEquation(equation1);

        LinearEquation equation2 = new LinearEquation(4);
        equation2.addCoefficient(new Complex(1, 0));
        equation2.addCoefficient(new Complex(1, 0));
        equation2.addCoefficient(new Complex(1, 0));
        equation2.addCoefficient(new Complex(3, 0));
        equation2.addCoefficient(new Complex(2, 0));
        system.addEquation(equation2);

        LinearEquation equation3 = new LinearEquation(4);
        equation3.addCoefficient(new Complex(3, 0));
        equation3.addCoefficient(new Complex(5, 0));
        equation3.addCoefficient(new Complex(-1, 0));
        equation3.addCoefficient(new Complex(1, 0));
        equation3.addCoefficient(new Complex(3, 0));
        system.addEquation(equation3);

        // when
        system.solve();

        // then
        assertEquals("No solutions", system.getSolution());
    }

    @Test
    public void shouldSolveSystemWithInfinitelyManySolutions() {
        // given
        LinearEquationsSystem system = new LinearEquationsSystem(4, 3);

        LinearEquation equation1 = new LinearEquation(4);
        equation1.addCoefficient(new Complex(2, 0));
        equation1.addCoefficient(new Complex(1, 0));
        equation1.addCoefficient(new Complex(-1, 0));
        equation1.addCoefficient(new Complex(1, 0));
        equation1.addCoefficient(new Complex(1, 0));
        system.addEquation(equation1);

        LinearEquation equation2 = new LinearEquation(4);
        equation2.addCoefficient(new Complex(0, 0));
        equation2.addCoefficient(new Complex(1, 0));
        equation2.addCoefficient(new Complex(3, 0));
        equation2.addCoefficient(new Complex(-3, 0));
        equation2.addCoefficient(new Complex(1, 0));
        system.addEquation(equation2);

        LinearEquation equation3 = new LinearEquation(4);
        equation3.addCoefficient(new Complex(1, 0));
        equation3.addCoefficient(new Complex(1, 0));
        equation3.addCoefficient(new Complex(1, 0));
        equation3.addCoefficient(new Complex(-1, 0));
        equation3.addCoefficient(new Complex(1, 0));
        system.addEquation(equation3);

        // when
        system.solve();

        // then
        assertEquals("Infinitely many solutions", system.getSolution());
    }
}

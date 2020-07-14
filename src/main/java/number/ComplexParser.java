package number;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexParser {

    private static final Pattern complexPattern = Pattern.compile("(-?\\d+(\\.\\d+)?)|(-?(\\d+(\\.\\d+)?)?i)|(-?\\d+(\\.\\d+)?[+-](\\d+(\\.\\d+)?)?i)");

    public Complex parse(String token) {
        Matcher matcher = complexPattern.matcher(token);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid token: " + token);
        }

        String real = matcher.group(1);
        if (real != null) {
            return new Complex(Double.parseDouble(real), 0);
        }

        String imaginary = matcher.group(3);
        if (imaginary != null) {
            if ("i".equals(imaginary)) {
                return new Complex(0, 1);
            }
            if ("-i".equals(imaginary)) {
                return new Complex(0, -1);
            }
            return new Complex(0, Double.parseDouble(imaginary.replace("i", "")));
        }

        String complex = matcher.group(6);
        int signIndex = Math.max(complex.indexOf("+", 1), complex.indexOf("-", 1));
        real = complex.substring(0, signIndex);
        imaginary = complex.substring(signIndex);

        if ("+i".equals(imaginary)) {
            return new Complex(Double.parseDouble(real), 1);
        }
        if ("-i".equals(imaginary)) {
            return new Complex(Double.parseDouble(real), -1);
        }
        return new Complex(Double.parseDouble(real), Double.parseDouble(imaginary.replace("i", "")));
    }
}

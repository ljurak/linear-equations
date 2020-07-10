package equation;

import java.util.Set;

public class Arguments {

    private final Set<String> validArgumentNames = Set.of("-in", "-out");

    private String inputFile;

    private String outputFile;

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void processArguments(String[] arguments) {
        String currentArgumentName = null;
        boolean isArgumentValueExpected = false;

        for (String argument : arguments) {
            if (isArgumentValueExpected) {
                if (argument.startsWith("-")) {
                    throw new IllegalArgumentException("Missing argument value: " + currentArgumentName);
                } else {
                    setArgumentValue(currentArgumentName, argument);
                    isArgumentValueExpected = false;
                }
            } else {
                if (argument.startsWith("-")) {
                    if (validateArgumentName(argument)) {
                        currentArgumentName = argument;
                        isArgumentValueExpected = true;
                    } else {
                        throw new IllegalArgumentException("Invalid argument name: " + argument);
                    }
                }
            }
        }

        if (isArgumentValueExpected) {
            throw new IllegalArgumentException("Missing argument value: " + currentArgumentName);
        }

        validateArguments();
    }

    private void validateArguments() {
        if (inputFile == null) {
            throw new IllegalArgumentException("Missing argument value: -in" );
        }
        if (outputFile == null) {
            throw new IllegalArgumentException("Missing argument value: -out" );
        }
    }

    private boolean validateArgumentName(String name) {
        return validArgumentNames.contains(name);
    }

    private void setArgumentValue(String name, String value) {
        if ("-in".equals(name)) {
            inputFile = value;
        } else if ("-out".equals(name)) {
            outputFile = value;
        }
    }
}

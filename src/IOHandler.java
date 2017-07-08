import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOHandler {

    private Scanner scanner;
    private String error = "";

    /*
    * Instantiates scanner object
    * IOHandler handles reading and writing to the console
    * */
    public IOHandler() {
        scanner = new Scanner(System.in);
    }

    /*
    * Prints out initial message
    * */
    public void initialMessage() {
        System.out.print("### JAVA CONSOLE CALCULATOR ###\n" +
                "# \n" +
                "# List of available operations:\n" +
                "# \n" +
                "# - addition: '+'\n" +
                "# - subtraction: '-'\n" +
                "# - multiplication: '*'\n" +
                "# - division: '/'\n" +
                "# \n" +
                "# Only round brackets are allowed.\n" +
                "# To pass a floating point numbers use '.'\n" +
                "# Type 'exit' to close.\n");
    }

    /*
    * Prints given result string
    *
    * @param message String a response for given input
    * */
    public void print(String message) {
        System.out.println(message);
    }

    /*
    * Sets error attribute value
    *
    * @param error String value of an error to be appended to current error value
    * */
    public void setError(String error) {
        this.error += error;
        // static?
    }

    /*
    * Prints out an error.
    * */
    public void printError() {
        print("ERROR: " + error);
        error = "";
    }

    /*
    * Prints out a message asking for user input
    *
    * @return List of parsed user input
    * */
    public List getInput() {
        System.out.print("\nInput: ");
        return read();
    }

    /*
    * Reads user input
    *
    * @return List of parsed user input
    * */
    public List read() {
        return parseInput(scanner.nextLine());
    }

    /*
    * Parses input string into the following objects: Integer, String, Character
    *
    * @param input String
    *
    * @return List of parsed user input
    * */
    private List parseInput(String input) {
        input = input.replaceAll("\\s+", "");

        Pattern negativeDoubles = Pattern.compile("^([(]-\\d+\\.\\d+[)])");
        Pattern negativeIntegers = Pattern.compile("^([(]-\\d+[)])");
        Pattern doubles = Pattern.compile("^(\\d+)\\.(\\d+)");
        Pattern integers = Pattern.compile("^(\\d)+");
        Pattern operators = Pattern.compile("^[()+\\-*/]");
        Pattern strings = Pattern.compile("^[^0-9()+\\-*/]+");

        List<Object> parsedInput = new LinkedList<>();

        while (input.length() != 0) {
            Matcher negativeDoublesMatcher = negativeDoubles.matcher(input);
            Matcher negativeIntegersMatcher = negativeIntegers.matcher(input);
            Matcher doublesMatcher = doubles.matcher(input);
            Matcher integersMatcher = integers.matcher(input);
            Matcher operatorsMatcher = operators.matcher(input);
            Matcher stringsMatcher = strings.matcher(input);

            if (negativeDoublesMatcher.find()) {
                String negativeDouble = negativeDoublesMatcher.group();
                input = input.substring(negativeDouble.length(), input.length());
                negativeDouble = negativeDouble.replaceAll("[()]", "");
                parsedInput.add(Double.parseDouble(negativeDouble));
            }
            else if (negativeIntegersMatcher.find()) {
                String negativeInteger = negativeIntegersMatcher.group();
                input = input.substring(negativeInteger.length(), input.length());
                negativeInteger = negativeInteger.replaceAll("[()]", "");
                parsedInput.add(Double.parseDouble(negativeInteger));
            }
            else if (doublesMatcher.find()) {
                String doubleNumber = doublesMatcher.group();
                input = input.substring(doubleNumber.length(), input.length());
                parsedInput.add(Double.parseDouble(doubleNumber));
            }
            else if (integersMatcher.find()) {
                String integerNumber = integersMatcher.group();
                input = input.substring(integerNumber.length(), input.length());
                parsedInput.add(Double.parseDouble(integerNumber));
            }
            else if (operatorsMatcher.find()) {
                String operator = operatorsMatcher.group();
                input = input.substring(operator.length(), input.length());
                parsedInput.add(operator.charAt(0));
            }
            else if (stringsMatcher.find()) {
                String text = stringsMatcher.group();
                input = input.substring(text.length(), input.length());
                parsedInput.add(text);
            }
        }
        return parsedInput;
    }
}

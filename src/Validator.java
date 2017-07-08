import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private IOHandler ioHandler;
//    private String basicOperators = "+-*/";

    /*
    * Saves reference to IOHandler instance
    *
    * @param ioHandler IOHandler
    * */
    public Validator(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    /*
    * Returns true if the input has met all the requirements.
    *
    * @param input List
    *
    * @return boolean
    * */
    public boolean valid(List input) {

        if (requestEmpty(input)) return false;
        if (forbiddenInput(input)) return false;
        if (parenthesisInconsistency(input)) return false;
        if (invalidCombinationOfOperators(input)) return false;

        return true;
    }

    /*
    * Returns true if the request is empty
    *
    * @param input List of parsed elements
    *
    * @return boolean
    * */
    private boolean requestEmpty(List input) {
        if (input.isEmpty()) {
            ioHandler.setError("Empty request.");
            return true;
        }
        return false;
    }

    /*
    * Returns true if the request contains forbidden/invalid text input
    *
    * @param input List of parsed elements
    *
    * @return boolean
    * */
    private boolean forbiddenInput(List input) {
        for (Object o : input) {
            if (o instanceof String) {
                ioHandler.setError("Contains forbidden/invalid input.");
                return true;
            }
        }
        return false;
    }

    /*
    * Return true if the parenthesis occur in the wrong order
    * Returns true if the request contains uneven number of parentheses
    *
    * @param input List of parsed elements
    *
    * @return boolean
    * */
    private boolean parenthesisInconsistency(List input) {
        int parenthesisCount = 0;
        for (Object o : input) {
            if (o instanceof Character) {
                if ((char)o == 40) parenthesisCount --;
                if ((char)o == 41) parenthesisCount ++;
            }
            if (parenthesisCount > 0) {
                ioHandler.setError("Invalid order of parenthesis. Closing parenthesis occurred before an opening one.");
                return true;
            }
        }
        if (parenthesisCount != 0) {
            ioHandler.setError("Uneven number of parentheses.");
            return true;
        }
        return false;
    }

    /*
    * Returns true if the request contains a standalone operator
    * Returns true if the request contains a combination of operators that is not allowed
    *
    * @param input List of parsed elements
    *
    * @return boolean
    * */
    private boolean invalidCombinationOfOperators(List input) {
        if (input.size() == 1) {
            if (!(input.get(0) instanceof Double)) {
                ioHandler.setError("No operands provided.");
                return true;
            }
        }
        else {
            Object previous = input.get(0);
            for (int i = 1; i < input.size(); i++) {
                if (previous instanceof Character && input.get(i) instanceof Character) {
                    String operators = String.valueOf(previous) + String.valueOf(input.get(i));
//                    System.out.print("DO TESTU - Validator - invalidCombinationOfOperators: " + operators);

                    List<Pattern> forbiddenPatterns = new ArrayList<>();

                    forbiddenPatterns.add(Pattern.compile("[+\\-*/][+\\-*/]"));
                    forbiddenPatterns.add(Pattern.compile("[(][+\\-*/]"));
                    forbiddenPatterns.add(Pattern.compile("[+\\-*/][)]"));
                    forbiddenPatterns.add(Pattern.compile("[(][)]"));

                    for (Pattern p : forbiddenPatterns) {
                        Matcher matcher = p.matcher(operators);
                        if (matcher.find()) {
                            ioHandler.setError("Invalid sequence of operators.");
                            return true;
                        }
                    }

//                    Pattern allowed = Pattern.compile("([(+\\-*/)][()+\\-*/])");                                // <------
//                    Matcher matcher = allowed.matcher(operators);

//                    if (!matcher.find()) {
//                        ioHandler.setError("Invalid sequence of operators.");
//                        return true;
//                    }
                }
                previous = input.get(i);
            }
        }
        return false;
    }
}

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private IOHandler ioHandler;

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
    public void validate(List input) {
        requestEmpty(input);
        forbiddenInput(input);
        parenthesisInconsistency(input);
        invalidCombinationOfOperators(input);
    }

    /*
    * Returns true if the request is empty
    *
    * @param input List of parsed elements
    *
    * @return boolean
    * */
    private void requestEmpty(List input) {
        if (input.isEmpty()) {
            throw new InputMismatchException("Empty request.");
        }
    }

    /*
    * Returns true if the request contains forbidden/invalid text input
    *
    * @param input List of parsed elements
    *
    * @return boolean
    * */
    private void forbiddenInput(List input) {
        for (Object o : input) {
            if (o instanceof String) {
                throw new InputMismatchException("Contains forbidden / invalid input.");
            }
        }
    }

    /*
    * Return true if the parenthesis occur in the wrong order
    * Returns true if the request contains uneven number of parentheses
    *
    * @param input List of parsed elements
    *
    * @return boolean
    * */
    private void parenthesisInconsistency(List input) {
        int parenthesisCount = 0;
        for (Object o : input) {
            if (o instanceof Character) {
                if ((char)o == 40) parenthesisCount --;
                if ((char)o == 41) parenthesisCount ++;
            }
            if (parenthesisCount > 0) {
                throw new InputMismatchException("Invalid order of parenthesis. Closing parenthesis occurred before an opening one.");
            }
        }
        if (parenthesisCount != 0) {
            throw new InputMismatchException("Uneven number of parentheses.");
        }
    }

    /*
    * Returns true if the request contains a standalone operator
    * Returns true if the request contains a combination of operators that is not allowed
    *
    * @param input List of parsed elements
    *
    * @return boolean
    * */
    private void invalidCombinationOfOperators(List input) {
        if (input.size() == 1) {
            if (!(input.get(0) instanceof Double)) {
                throw new InputMismatchException("No operands provided.");
            }
        }
        else {
            Object previous = input.get(0);
            for (int i = 1; i < input.size(); i++) {
                if (previous instanceof Character && input.get(i) instanceof Character) {
                    String operators = String.valueOf(previous) + String.valueOf(input.get(i));

                    List<Pattern> forbiddenPatterns = new ArrayList<>();

                    forbiddenPatterns.add(Pattern.compile("[+\\-*/][+\\-*/]"));
                    forbiddenPatterns.add(Pattern.compile("[(][+\\-*/]"));
                    forbiddenPatterns.add(Pattern.compile("[+\\-*/][)]"));
                    forbiddenPatterns.add(Pattern.compile("[(][)]"));

                    for (Pattern p : forbiddenPatterns) {
                        Matcher matcher = p.matcher(operators);
                        if (matcher.find()) {
                            throw new InputMismatchException("Invalid sequence of operators.");
                        }
                    }
                }
                previous = input.get(i);
            }
        }
    }
}

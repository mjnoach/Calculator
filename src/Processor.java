import java.util.*;

public class Processor {

    private IOHandler ioHandler;
    private Map<Character, Integer> priorityMap = new HashMap<>(6);

    /*
    * Saves reference to IOHandler instance
    * Sets priorityMap elements
    *
    * @param ioHandler IOHandler
    * */
    public Processor(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
        priorityMap.put('(', 0);
        priorityMap.put(')', 0);
        priorityMap.put('+', 1);
        priorityMap.put('-', 1);
        priorityMap.put('*', 2);
        priorityMap.put('/', 2);
    }

    /*
    * Returns a result if the input was correct or prints error otherwise
    *
    * @param input List validated list of parsed user input
    * */
    public void process(List input) {
        input = reversePolishNotation(input);
        Object result = calculate(input);
        if (result == null) {
            ioHandler.printError();
        }
        else {
            if ((Double) result % 1.0 == 0) {
                result = ((Double) result).intValue();
            }
            ioHandler.print("Result: " + result);
        }
    }

    /*
    * Calculates given request
    *
    * @param input List postfix expression
    *
    * @return Integer result of the calculation
    * */
    private Double calculate(List input) {
        while (input.size() != 1) {
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i) instanceof Character) {
                    try {
                        double a = (Double) input.remove(i-2);
                        double b = (Double) input.remove(i-2);
                        char c = (Character) input.get(i-2);

                        input.set(i-2, doOperation(a, b, c));
                    }
                    catch (IndexOutOfBoundsException e) {
                        ioHandler.setError("Contains invalid input.");
                        return null;
                    }
                    break;
                }
            }
        }
        return (Double)input.get(0);
    }

    /*
    * Does basic arithmetical operations
    *
    * @param operator Character
    * @param a Integer
    * @param b Integer
    *
    * @return Integer
    * */
    private Double doOperation(Double a, Double b, Character operator) {
        if (operator == '+') return a + b;
        else if (operator == '-') return a - b;
        else if (operator == '*') return a * b;
        else {
            if (b == 0) {
                ioHandler.setError("Attempt to divide by zero.");
                return null;
            }
            return a / b;
        }
    }

    /*
    * Transforms an infix input expression to postfix
    *
    * @param input List infix expression
    *
    * @return List postfix expression
    * */
    private List reversePolishNotation(List input) {
        List rvpExpression = new LinkedList();
        Stack<Character> stack = new Stack();
        while (!input.isEmpty()) {
            Object element = input.remove(0);
            if (element instanceof Double) {
                rvpExpression.add(element);
            }
            else {
                if ((Character) element == '(') {
                    stack.push((Character) element);
                }
                else if ((Character) element == ')') {
                    rvpExpression.add(stack.pop());
                    stack.pop();                            // pop '(' from the stack
                }
                else if (stack.isEmpty()) {
                    stack.push((Character) element);
                }
                else if (comparePriorityOfOperators((Character) element, stack.peek()) > 0) {
                    stack.push((Character) element);
                }
                else rvpExpression.add(element);
            }
        }
        while (!stack.isEmpty()) {
            Character element = stack.pop();
            if (element == '(' || element == ')')
                continue;
            else rvpExpression.add(element);
        }
        return rvpExpression;
    }

    /*
    * Compares priority of operators for the need of RPN algorithm
    * */
    private int comparePriorityOfOperators(Character a, Character b) {
        return priorityMap.get(a) - priorityMap.get(b);
    }
}

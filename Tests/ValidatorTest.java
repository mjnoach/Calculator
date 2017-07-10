import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator validator = new Validator(new IOHandler());

    @Test
    void throwsExceptionWhenInputIsEmpty() {
        List input = new LinkedList();
        assertThrows(InputMismatchException.class,() -> validator.validate(input));
    }

    @Test
    void throwsExceptionWhenInputContainsForbiddenText() {
        List input = new LinkedList();
        input.add("2 + two =");
        assertThrows(InputMismatchException.class,() -> validator.validate(input));
    }

    @Test
    void throwsExceptionWhenInvalidOrderOfParentheses() {
        List input = new LinkedList();
        input.addAll(Arrays.asList(new Object[] {')', '('}));
        assertThrows(InputMismatchException.class,() -> validator.validate(input));
    }

    @Test
    void throwsExceptionWhenUnevenNumberOfParentheses() {
        List input = new LinkedList();
        input.addAll(Arrays.asList(new Object[] {'(', '(', ')'}));
        assertThrows(InputMismatchException.class,() -> validator.validate(input));
    }

    @Test
    void throwsExceptionWhenNoOperandsProvvided() {
        List input = new LinkedList();
        input.addAll(Arrays.asList(new Object[] {'+'}));
        assertThrows(InputMismatchException.class,() -> validator.validate(input));
    }

    @Test
    void throwsExceptionWhenInvalidSequenceOfOperators() {
        List input = new LinkedList();
        input.addAll(Arrays.asList(new Object[] {2, '+', '-', 5}));
        assertThrows(InputMismatchException.class,() -> validator.validate(input));
    }
}
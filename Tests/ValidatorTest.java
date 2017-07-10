import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void validReturnsFalseForIncorrectInput() {
        Validator validator = new Validator(new IOHandler());

        // request empty
        List input = new LinkedList();
        assertFalse(validator.valid(input));

        // invalid input
        input.add("2 + two =");
        assertFalse(validator.valid(input));

        // invalid order of parentheses
        input.clear();
        input.addAll(Arrays.asList(new Object[] {')', '('}));
        assertFalse(validator.valid(input));

        // uneven number of parentheses
        input.clear();
        input.addAll(Arrays.asList(new Object[] {'(', '(', ')'}));
        assertFalse(validator.valid(input));

        // no operands
        input.clear();
        input.addAll(Arrays.asList(new Object[] {'+'}));
        assertFalse(validator.valid(input));

        // invalid sequence of operators
        input.clear();
        input.addAll(Arrays.asList(new Object[] {2, '+', '-', 5}));
        assertFalse(validator.valid(input));

        input.clear();
        input.addAll(Arrays.asList(new Object[] {2, '+', '(', ')', 3}));
        assertFalse(validator.valid(input));
    }

}
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorTest {

    private Processor processor = new Processor(new IOHandler());

    @Test
    void throwsExceptionWhenRequestDidntHaveTwoArguments() {
        List input = new LinkedList();
        input.addAll(Arrays.asList(new Object[] {2.0, '+'}));
        assertThrows(InputMismatchException.class, () -> processor.process(input));
    }

    @Test
    void throwsExceptionWhenDividingByZero() {
        List input = new LinkedList();
        input.addAll(Arrays.asList(new Object[] {2.0, '/', 0.0}));
        assertThrows(InputMismatchException.class, () -> processor.process(input));
    }
}
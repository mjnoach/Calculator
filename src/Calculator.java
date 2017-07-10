import java.util.List;

/*
* Calculator is a console calculator that allows for simple
* mathematical operations on floating point numbers
*
* @author Andrzej Sienkiewicz
* */
public class Calculator {

    private IOHandler ioHandler;
    private Validator validator;
    private Processor processor;

    /*
    * Instantiates helper objects and stores them as parameters
    * */
    public Calculator() {
        ioHandler = new IOHandler();
        validator = new Validator(ioHandler);
        processor = new Processor(ioHandler);
    }

    /*
    * Initializes an operable calculator
    * */
    public void startCalc() {
        ioHandler.initialMessage();
        while (true) {
            List input = ioHandler.getInput();
            if (input.size() == 1 && input.get(0) instanceof String && ((String)input.get(0)).equalsIgnoreCase("exit"))
                return;
            if (!validator.valid(input))
                ioHandler.printError();
            else {
                processor.process(input);
            }
        }
    }
}

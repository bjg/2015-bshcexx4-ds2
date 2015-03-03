package server;

import SquareCalculation.*;

public class SquareCalculatorServant extends _SquareCalculatorImplBase {
    private static final String ACKNOWLEDGEMENT_MESSAGE = "Request received for square";

    @Override
    public double getSquare(float value) {
        System.out.println(ACKNOWLEDGEMENT_MESSAGE);
        return Math.pow(value, 2);
    }
}

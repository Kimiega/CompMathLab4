package linal;

import java.util.function.Function;

public interface Equation {
    Double[] coefs();
    Function<Double, Double> equation();
    String equationToString();
    String fullEquationToString();
}

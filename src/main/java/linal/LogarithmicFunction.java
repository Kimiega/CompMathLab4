package linal;

import java.util.function.Function;

public class LogarithmicFunction extends ApproximationMethod {


    public LogarithmicFunction(Dot[] table) {
        super(table);
    }

    @Override
    public Equation findApproximation() {
        convertXToLn();
        Double[][] matrix = createLinMatrix();
        Double[] matrixSolution = calcCoefs(matrix);
        double a = matrixSolution[0];
        double b = matrixSolution[1];
        return getFunction(a, b);
    }
    protected Equation getFunction(double a, double b) {
        return new Equation() {
            @Override
            public Double[] coefs() {return new Double[]{b, a};};
            @Override
            public Function<Double, Double> equation() {
                return (Double x) -> b + a * Math.log(x);
            }

            @Override
            public String equationToString() {
                return String.format("%.3f + (%.3f)*ln(x)", b,a);
            }
            @Override
            public String fullEquationToString() {
                return String.format("%f + (%f)*ln(x)", b,a);
            }
        };
    }

    @Override
    public String methodName() {
        return "Logarithmic";
    }
}



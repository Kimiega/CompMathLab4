package linal;

import java.util.function.Function;

public class ExponentialFunction extends ApproximationMethod {


    public ExponentialFunction(Dot[] table) {
        super(table);
    }

    @Override
    public Equation findApproximation() {
        convertYToLn();
        Double[][] matrix = createLinMatrix();
        Double[] matrixSolution = calcCoefs(matrix);
        double a = Math.exp(matrixSolution[0]);
        double b = matrixSolution[1];
        return getFunction(a, b);
    }
    protected Equation getFunction(double a, double b) {
        return new Equation() {
            @Override
            public Double[] coefs() {return new Double[]{a, b};};
            @Override
            public Function<Double, Double> equation() {
                return (Double x) -> a * Math.exp(b*x);
            }

            @Override
            public String equationToString() {
                return String.format("%.3f*exp(%.3f*x)",a,b);
            }
            @Override
            public String fullEquationToString() {
                return String.format("%f*exp(%f*x)",a,b);
            }
        };
    }

    @Override
    public String methodName() {
        return "Exponential";
    }
}



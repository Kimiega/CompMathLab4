package linal;

import java.util.function.Function;

public class LinearFunction extends ApproximationMethod {


    public LinearFunction(Dot[] table) {
        super(table);
    }

    @Override
    public Equation findApproximation() {
        Double[][] matrix = createLinMatrix();
        Double[] matrixSolution = calcCoefs(matrix);
        double a = matrixSolution[0];
        double b = matrixSolution[1];
        return getFunction(a, b);
    }
    private Equation getFunction(double a, double b) {
        return new Equation() {
            @Override
            public Double[] coefs() {return new Double[]{b, a};};
            @Override
            public Function<Double, Double> equation() {
                return (Double x) -> b + a * x;
            }
            @Override
            public String equationToString() {
                return String.format("%.3f + (%.3f)*x",b,a);
            }
            @Override
            public String fullEquationToString() {
                return String.format("%f + (%f)*x",b,a);
            }
        };
    }

    @Override
    public String methodName() {
        return "Linear";
    }
}



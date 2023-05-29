package linal;

import java.util.function.Function;

public class CubicFunction extends ApproximationMethod {


    public CubicFunction(Dot[] table) {
        super(table);
    }

    @Override
    public Equation findApproximation() {
        Double[][] matrix = createCubicMatrix();
        Double[] matrixSolution = calcCoefs(matrix);
        double a0 = matrixSolution[0];
        double a1 = matrixSolution[1];
        double a2 = matrixSolution[2];
        double a3 = matrixSolution[3];
        return getFunction(a0, a1, a2, a3);
    }
    private Equation getFunction(double a0, double a1, double a2, double a3) {
        return new Equation() {
            @Override
            public Double[] coefs() {return new Double[]{a0, a1, a2, a3};};
            @Override
            public Function<Double, Double> equation() {
                return (Double x) -> a0 + a1*x + a2*x*x + a3*x*x*x;
            }
            @Override
            public String equationToString() {
                return String.format("%.3f + (%.3f)*x + (%.3f)*x^2 + (%.3f)*x^3",a0,a1,a2,a3);
            }
            @Override
            public String fullEquationToString() {
                return String.format("%f + (%f)*x + (%f)*x^2 + (%f)*x^3",a0,a1,a2,a3);
            }
        };
    }

    @Override
    public String methodName() {
        return "Cubic";
    }
}



package linal;

import java.util.function.Function;

public class QuadFunction extends ApproximationMethod {


    public QuadFunction(Dot[] table) {
        super(table);
    }

    @Override
    public Equation findApproximation() {
        Double[][] matrix = createQuadMatrix();
        Double[] matrixSolution = calcCoefs(matrix);
        double a0 = matrixSolution[0];
        double a1 = matrixSolution[1];
        double a2 = matrixSolution[2];
        return getFunction(a0, a1, a2);
    }
    private Equation getFunction(double a0, double a1, double a2) {
        return new Equation() {
            @Override
            public Double[] coefs() {return new Double[]{a0, a1, a2};};
            @Override
            public Function<Double, Double> equation() {
                return (Double x) -> a0 + a1*x + a2*x*x;
            }

            @Override
            public String equationToString() {
                return String.format("%.3f + (%.3f)*x + (%.3f)*x^2",a0,a1,a2);
            }
            @Override
            public String fullEquationToString() {
                return String.format("%f + (%f)*x + (%f)*x^2",a0,a1,a2);
            }
        };
    }

    @Override
    public String methodName() {
        return "Quad";
    }
}



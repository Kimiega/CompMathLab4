package linal;

import java.util.function.Function;

public class PowerFunction extends ApproximationMethod {


    public PowerFunction(Dot[] table) {
        super(table);
    }

    @Override
    public Equation findApproximation() {
        convertXToLn();
        convertYToLn();
        Double[][] matrix = createLinMatrix();
        Double[] matrixSolution = calcCoefs(matrix);
        double a = Math.exp(matrixSolution[1]);
        double b = matrixSolution[0];
        return getFunction(a, b);
    }
    protected Equation getFunction(double a, double b) {
        return new Equation() {
            @Override
            public Double[] coefs() {return new Double[]{a, b};};
            @Override
            public Function<Double, Double> equation() {
                return (Double x) -> a * Math.pow(x, b);
            }

            @Override
            public String equationToString() {
                return String.format("%.3f*x^(%.3f)",a,b);
            }
            @Override
            public String fullEquationToString() {
                return String.format("%f*x^(%f)",a,b);
            }
        };
    }

    @Override
    public String methodName() {
        return "Power";
    }
}



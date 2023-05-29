package linal;

import java.util.Arrays;

public abstract class ApproximationMethod {
    private final Dot[] table;
    private final GaussSolver gaussSolver;

    protected ApproximationMethod(Dot[] table) {
        this.table = new Dot[table.length];
        for (int i = 0; i < table.length; ++i) {
            this.table[i] = new Dot(table[i].x(), table[i].y());
        }
        gaussSolver = new GaussSolver();
    }
    public abstract Equation findApproximation();

    protected double calcSX() {
        return Arrays.stream(table).map(Dot::x).reduce(0d, Double::sum);
    }
    protected double calcSXX() {
        return Arrays.stream(table).map(dot-> dot.x()*dot.x()).reduce(0d, Double::sum);
    }
    protected double calcSXXX() {
        return Arrays.stream(table).map(dot-> dot.x()*dot.x()*dot.x()).reduce(0d, Double::sum);
    }
    protected double calcSXXXX() {
        return Arrays.stream(table).map(dot-> dot.x()*dot.x()*dot.x()*dot.x()).reduce(0d, Double::sum);
    }
    protected double calcSXXXXX() {
        return Arrays.stream(table).map(dot-> dot.x()*dot.x()*dot.x()*dot.x()*dot.x()).reduce(0d, Double::sum);
    }
    protected double calcSXXXXXX() {
        return Arrays.stream(table).map(dot-> dot.x()*dot.x()*dot.x()*dot.x()*dot.x()*dot.x()).reduce(0d, Double::sum);
    }
    protected double calcSY() {
        return Arrays.stream(table).map(Dot::y).reduce(0d, Double::sum);
    }
    protected double calcSXY() {
        return Arrays.stream(table).map(dot-> dot.x()*dot.y()).reduce(0d, Double::sum);
    }
    protected double calcSXXY() {
        return Arrays.stream(table).map(dot-> dot.x()*dot.x()*dot.y()).reduce(0d, Double::sum);
    }
    protected double calcSXXXY() {
        return Arrays.stream(table).map(dot-> dot.x()*dot.x()*dot.x()*dot.y()).reduce(0d, Double::sum);
    }
    protected void convertXToLn() {
        for (Dot dot : table) {
            dot.setX(Math.signum(dot.x())*Math.log(Math.abs(dot.x())));
            if (Double.isNaN(dot.x()))
                dot.setX(-15);
        }
    }
    protected void convertYToLn() {
        for (Dot dot : table) {
            dot.setY(Math.signum(dot.y()) * Math.log(Math.abs(dot.y())));
            if (Double.isNaN(dot.y()))
                dot.setY(-15);
        }
    }
    protected Double[][] createLinMatrix() {
        return new Double[][]{
                {calcSXX(), calcSX(), calcSXY()},
                {calcSX(), (double)table.length, calcSY()}};
    }
    protected Double[][] createQuadMatrix() {
        return new Double[][]{
                {(double)table.length, calcSX(), calcSXX(), calcSY()},
                {calcSX(), calcSXX(), calcSXXX(), calcSXY()},
                {calcSXX(), calcSXXX(), calcSXXXX(), calcSXXY()}
        };
    }
    protected Double[][] createCubicMatrix() {
        return new Double[][]{
                {(double)table.length, calcSX(), calcSXX(),calcSXXX(), calcSY()},
                {calcSX(), calcSXX(), calcSXXX(),calcSXXXX(), calcSXY()},
                {calcSXX(), calcSXXX(), calcSXXXX(), calcSXXXXX(), calcSXXY()},
                {calcSXXX(), calcSXXXX(), calcSXXXXX(), calcSXXXXXX(), calcSXXXY()},
        };
    }
    protected Double[] calcCoefs(Double[][] matrix) {
        return gaussSolver.solve(matrix);
    }
    public abstract String methodName();
    public void drawDots() {
        for (var dot : table)
            System.out.println(dot.toString());
    }
}

package linal;

import graph.DrawerFunctions;

public class SuperMegaSolver {

    public static String solveAllProblems(Double[][] dotsRaw) {
        int n = dotsRaw.length;
        Dot[] dots = new Dot[n];
        for (int i = 0; i < n; ++i) {
            dots[i] = new Dot(dotsRaw[i][0], dotsRaw[i][1]);
        }
        return solveAllProblems(dots);
    }
    public static String drawPerfect(Double[][] dotsRaw) {
        int n = dotsRaw.length;
        Dot[] dots = new Dot[n];
        for (int i = 0; i < n; ++i) {
            dots[i] = new Dot(dotsRaw[i][0], dotsRaw[i][1]);
        }
        return drawPerfect(dots);
    }

    public static String solveAllProblems(Dot[] dots) {
        StringBuilder sb = new StringBuilder();
        double xmin = dots[0].x();
        double xmax = dots[0].x();
        double ymin = dots[0].y();
        double ymax = dots[0].y();
        for (var dot : dots) {
            if (xmin > dot.x())
                xmin = dot.x();
            if (xmax < dot.x())
                xmax = dot.x();
            if (ymin > dot.y())
                ymin = dot.y();
            if (ymax < dot.y())
                ymax = dot.y();
        }
        if (xmin == xmax) {
            xmin-=5;
            xmax+=5;
        }
        if (ymin == ymax) {
            ymin -=5;
            ymax +=5;
        }
        DrawerFunctions drawer = new DrawerFunctions(xmin, xmax, ymin, ymax);


        sb.append("x: ");
        for (var dot : dots)
            sb.append(String.format("%8.4f ", dot.x()));
        sb.append("\n");
        sb.append("y: ");
        for (var dot : dots)
            sb.append(String.format("%8.4f ", dot.y()));
        sb.append("\n\n");
        drawer.printDots(dots);
        double r = Math.abs(LinalUtils.linCorrelation(dots));
        sb.append(String.format("Lin correlation: %f%n", r));
        if (r == 0)
            sb.append("svyazi net");
        else if (r < 0.3)
            sb.append("svyaz' slabaya");
        else if (0.3 <= r && r < 0.5)
            sb.append("svyaz' umerennaya");
        else if (0.5 <= r && r < 0.7)
            sb.append("svyaz' zametnaya");
        else if (0.7 <= r && r < 0.9)
            sb.append("svyaz' visokaya");
        else if (0.9 <= r && r < 0.99)
            sb.append("svyaz' ves'ma zametnaya");
        else if (0.99 <= r && r < 1)
            sb.append("nalichie lin svyazi pravomerno");
        else if (r == 1)
            sb.append("strogaya lin svyaz'");
        else sb.append("Smth wrong with lin correlation");
        sb.append("\n\n");
        ApproximationMethod[] methods = {
                        new LinearFunction(dots), new ExponentialFunction(dots), new LogarithmicFunction(dots),
                        new PowerFunction(dots), new QuadFunction(dots), new CubicFunction(dots)
                };
        double skoMin = Double.MAX_VALUE;
        Equation bestEquation = null;

        for (var method : methods) {
            Equation equation = method.findApproximation();
            sb.append(method.methodName()).append(" ").append(equation.fullEquationToString()).append("\n");
            if (("Quad".equals(method.methodName()) || "Cubic".equals(method.methodName()))
                    && Math.abs(equation.coefs()[equation.coefs().length-1]) < 0.01 ) {
                sb.append("This solution has some troubles and will be skipped\n\n");
                continue;
            }
            new Thread(() -> drawer.drawFunction(equation)).start();
            sb.append("phi(x_i):  ");
            for (var dot : dots)
                sb.append(String.format("%8.4f ",equation.equation().apply(dot.x())));
            sb.append("\n");
            sb.append("eps_(x_i): ");
            for (var dot : dots)
                sb.append(String.format("%8.4f ",equation.equation().apply(dot.x()) - dot.y()));
            sb.append("\n");
            double eps_sum = 0d;
            double n_sum = 0;
            for (var dot : dots) {
                double t = Math.pow(equation.equation().apply(dot.x()) - dot.y(), 2);
                if (!Double.isNaN(t)) {
                    n_sum++;
                    eps_sum+=t;
                }
            }
            sb.append(String.format("S: %f%n", eps_sum));
            eps_sum = Math.sqrt(eps_sum/n_sum);
            if (eps_sum < skoMin || bestEquation == null) {
                skoMin = eps_sum;
                bestEquation = equation;
            }
            sb.append(String.format("SKO: %.5f%n", eps_sum));
            sb.append("\n\n");
        }
        if (bestEquation == null) {
            sb.append("SMTH WRONG WITH ALL SOLUTIONS\n");
            return sb.toString();
        }
        sb.append("BEST EQUATION: ").append(bestEquation.fullEquationToString()).append("\n");
        sb.append(String.format("WITH SKO: %f%n", skoMin));
        return sb.toString();
    }

    public static String drawPerfect(Dot[] dots) {
        StringBuilder sb = new StringBuilder();
        double xmin = dots[0].x();
        double xmax = dots[0].x();
        double ymin = dots[0].y();
        double ymax = dots[0].y();
        for (var dot : dots) {
            if (xmin > dot.x())
                xmin = dot.x();
            if (xmax < dot.x())
                xmax = dot.x();
            if (ymin > dot.y())
                ymin = dot.y();
            if (ymax < dot.y())
                ymax = dot.y();
        }
        DrawerFunctions drawer = new DrawerFunctions(xmin, xmax, ymin, ymax);
        ApproximationMethod[] methods = {
                new LinearFunction(dots), new ExponentialFunction(dots), new LogarithmicFunction(dots),
                new PowerFunction(dots), new QuadFunction(dots), new CubicFunction(dots)
        };
        double skoMin = Double.MAX_VALUE;
        Equation bestEquation = null;

        for (var method : methods) {
            Equation equation = method.findApproximation();
            if (("Quad".equals(method.methodName()) || "Cubic".equals(method.methodName()))
                    && Math.abs(equation.coefs()[equation.coefs().length-1]) < 0.1) {
                continue;
            }
            double eps_sum = 0d;
            double n_sum = 0;
            for (var dot : dots) {
                double t = Math.pow(equation.equation().apply(dot.x()) - dot.y(), 2);
                if (!Double.isNaN(t)) {
                    n_sum++;
                    eps_sum+=t;
                }
            }
            eps_sum = Math.sqrt(eps_sum/n_sum);
            if (eps_sum < skoMin || bestEquation == null) {
                skoMin = eps_sum;
                bestEquation = equation;
            }
        }
        drawer.printDots(dots);
        Equation finalBestEquation = bestEquation;
        new Thread(() -> drawer.drawFunction(finalBestEquation)).start();
        sb.append("BEST EQUATION: ").append(bestEquation.equationToString()).append("\n");
        sb.append(String.format("WITH SKO: %f%n", skoMin));
        return sb.toString();
    }
}

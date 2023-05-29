package linal;

public class GaussSolver {

    public Double[] solve(Double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length - 1;
        Double[] x = new Double[n];

        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                double factor = matrix[j][i] / matrix[i][i];

                for (int k = i; k < m + 1; ++k) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0d;
            for (int j = i + 1; j < n; ++j) {
                sum += matrix[i][j] * x[j];
            }
            x[i] = (matrix[i][m] - sum) / matrix[i][i];
        }

        return x;
    }
}

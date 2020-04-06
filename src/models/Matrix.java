package models;

import java.text.DecimalFormat;

public class Matrix {

    private int rows;
    private int cols;
    private double[][] matrix;

    public Matrix() {}

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];
        randomize();
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return this.cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public double getValueMatrix(int row, int col) {
        return this.matrix[row][col];
    }

    public void setValueMatrix(double value, int row, int col) {
        this.matrix[row][col] = value;
    }

    public void randomize() {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.matrix[i][j] = Math.round(Math.random()*10);
            }
        }
    }

    public static void addScalar(Matrix m, double n) {

        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                double sum = m.getValueMatrix(i,j) + n;
                m.setValueMatrix(sum, i, j);
            }
        }

    }

    public static void addMatrix(Matrix m, Matrix n) {

        if(m.getRows() == n.getRows() && m.getCols() == n.getCols()) {
            for(int i = 0; i < m.getRows(); i++) {
                for(int j = 0; j < m.getCols(); j++) {
                    double sum = m.getValueMatrix(i,j) * n.getValueMatrix(i,j);
                    m.setValueMatrix(sum, i, j);
                }
            }
        }

    }

    public static void multiplyScalar(Matrix m, double n) {

        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                double prod = m.getValueMatrix(i,j) * n;
                m.setValueMatrix(prod, i, j);
            }
        }

    }

    public static Matrix multiplyMatrix(Matrix m, Matrix n) {

        Matrix result = new Matrix();

        if(m.getCols() == n.getRows()) {
            result = new Matrix(m.getRows(), n.getCols());

            for(int i = 0; i < result.getRows(); i++) {
                for(int j = 0; j < result.getCols(); j++) {
                    double sum = 0;
                    for(int k = 0; k < m.getCols(); k++) {
                        sum += m.getValueMatrix(i,k) * n.getValueMatrix(k,j);
                    }
                    result.setValueMatrix(sum,i,j);
                }
            }
        }

        return result;
    }

    public void printMatrix() {

        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                System.out.print(this.matrix[i][j] + "\t");
            }
            System.out.println();
        }

    }

}

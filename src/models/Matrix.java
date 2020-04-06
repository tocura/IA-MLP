package models;

public class Matrix {

    private int rows;
    private int cols;
    private double[][] matrix;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];
        initMatrix();
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

    public void initMatrix() {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    public static void addScalar(Matrix m, double n) {

        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                m.matrix[i][j] += n;
            }
        }

    }

    public static void multiplyScalar(Matrix m, double n) {

        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                m.matrix[i][j] *= n;
            }
        }

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

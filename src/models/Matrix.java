package models;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

    private int rows;
    private int cols;
    private double[][] data;

    public Matrix() {}

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
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
        return this.data[row][col];
    }

    public void setValueMatrix(double value, int row, int col) {
        this.data[row][col] = value;
    }

    //gera valores aleatorios de -1 a 1
    public static void randomize(Matrix m) {
        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                m.setValueMatrix(Math.random()*2 - 1, i, j);
            }
        }
    }

    //adicao de matriz
    public static void addMatrix(Matrix m, Matrix n) {

        if(m.getRows() == n.getRows() && m.getCols() == n.getCols()) {
            for(int i = 0; i < m.getRows(); i++) {
                for(int j = 0; j < m.getCols(); j++) {
                    double sum = m.getValueMatrix(i,j) + n.getValueMatrix(i,j);
                    m.setValueMatrix(sum, i, j);
                }
            }
        }

    }

    //subtracao de matriz
    public static Matrix subtractMatrix(Matrix m, Matrix n) {

        Matrix result = new Matrix(m.getRows(), m.getCols());

        if(m.getRows() == n.getRows() && m.getCols() == n.getCols()) {
            for(int i = 0; i < m.getRows(); i++) {
                for(int j = 0; j < m.getCols(); j++) {
                    double sub = m.getValueMatrix(i,j) - n.getValueMatrix(i,j);
                    result.setValueMatrix(sub, i, j);
                }
            }
        }

        return result;
    }

    //multiplicacao de uma matriz por um valor
    public static void multiplyScalar(Matrix m, double n) {

        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                double prod = m.getValueMatrix(i,j) * n;
                m.setValueMatrix(prod, i, j);
            }
        }

    }

    //multiplicacao de matrizes
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

    /*
    metodo que realiza multiplicacao de matrizes por seus elementos, tambem conhecido como Hadamard Product
    https://en.wikipedia.org/wiki/Hadamard_product_(matrices)
     */
    public static Matrix multiplyElementWise(Matrix m, Matrix n) {

        Matrix result = new Matrix(m.getRows(), m.getCols());

        if(m.getRows() == n.getRows() && m.getCols() == n.getCols()) {
            for(int i = 0; i < m.getRows(); i++) {
                for(int j = 0; j < m.getCols(); j++) {
                    double prod = m.getValueMatrix(i,j) * n.getValueMatrix(i,j);
                    result.setValueMatrix(prod, i, j);
                }
            }
        }

        return result;
    }

    //metodo para transpor uma matriz
    public static Matrix transpose(Matrix m) {
        Matrix result = new Matrix(m.getCols(), m.getRows());

        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                result.setValueMatrix(m.getValueMatrix(i,j), j, i);
            }
        }

        return result;
    }

    //transforma uma List<Double> em uma matriz
    public static Matrix fromArray(List<Double> arr) {

        Matrix m = new Matrix(arr.size(), 1);

        for(int i = 0; i < arr.size(); i++) {
            m.setValueMatrix(arr.get(i), i, 0);
        }

        return m;
    }

    //metodo para transformar uma lista em matriz, utilizado no teste com ruido
    public void toMatrix(List<Double> list) {

        int k = 0;

        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.data[i][j] = list.get(k);
                k++;
            }
        }

    }

    //transforma uma matriz em um List<Double>
    public List<Double> toArray() {

        List<Double> result = new ArrayList<>();

        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                result.add(this.getValueMatrix(i, j));
            }
        }

        return result;
    }

    //metodo de para printar a Matriz
    public String printMatrix() {

        StringBuffer str = new StringBuffer();

        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                str.append(this.getValueMatrix(i,j) + ",");
            }
            str.append("\n");
        }

        return str.toString();

    }

}

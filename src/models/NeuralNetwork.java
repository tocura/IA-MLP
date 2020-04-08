package models;

import java.util.List;

public class NeuralNetwork {

    private Matrix weights_ih; //pesos entre a input layer e hidden layer
    private Matrix weights_ho; //pesos entre a hidden layer e output layer
    private Matrix bias_h; //bias dos hidden nodes
    private Matrix bias_o; //bias dos output node
    private double learning_rate;

    public NeuralNetwork() {}

    public NeuralNetwork(int size_input, int size_target, int size_hidden) {
        //cria as matrizes de pesos
        weights_ih = new Matrix(size_hidden, size_input);
        weights_ho = new Matrix(size_target, size_hidden);

        //cria os bias
        bias_h = new Matrix(size_hidden, 1);
        bias_o = new Matrix(size_target, 1);

        //inicializa a taxa de aprendizado
        learning_rate = 0.1;
    }

    public static void activationFunction(Matrix m) {
        //como a matriz passada como parametro so tem uma unica coluna
        //o "x" da funcao de ativacao sera o valor de cada linha da matriz
        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                double x = m.getValueMatrix(i, j);
                double result = 1 / (1 + Math.exp(-x));
                m.setValueMatrix((result), i, j);
            }
        }

    }

    public static Matrix gradientFunction(Matrix m) {

        Matrix res = new Matrix(m.getRows(), m.getCols());

        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                double result = m.getValueMatrix(i, j) * (1 - m.getValueMatrix(i, j));
                res.setValueMatrix(result, i, j);
            }
        }

        return res;
    }

    public List<Double> feedforward(List<Double> input) {
        //transforma a lista de inputs em uma matriz de inputs
        //sempre sera uma matriz com uma unica coluna
        Matrix inputs = Matrix.fromArray(input);

        //multiplicacao dos pesos e inputs que ficarao armazenados na matriz hidden que sempre sera uma matriz i,1
        //ou seja, sempre tera uma unica coluna, e "i" eh o numero de linhas
        Matrix hidden = Matrix.multiplyMatrix(this.weights_ih, inputs);
        //adiciona o bias, a matriz hidden fica com (i,1)
        Matrix.addMatrix(hidden, this.bias_h);
        //gerando o output dos hidden nodes (i,1)
        NeuralNetwork.activationFunction(hidden);

        //Multiplicacao dos pesos e hidden nodes que ficarao armazenados na matriz output, que sempre tera
        //uma unica coluna
        Matrix output = Matrix.multiplyMatrix(this.weights_ho, hidden);
        //adiciona o bias (i,1)
        Matrix.addMatrix(output, this.bias_o);
        //gera o output dos outputs nodes (i,1)
        NeuralNetwork.activationFunction(output);

        return output.toArray();
    }

    //metodo que faz o backpropagation dos erros
    public void train(List<Double> input, List<Double> target) {
        //FEEDFORWARD
        //transforma a lista de inputs em uma matriz de inputs
        //sempre sera uma matriz com uma unica coluna
        Matrix inputs = Matrix.fromArray(input);

        //multiplicacao dos pesos e inputs que ficarao armazenados na matriz hidden que sempre sera uma matriz i,1
        //ou seja, sempre tera uma unica coluna, e "i" eh o numero de linhas
        Matrix hidden = Matrix.multiplyMatrix(this.weights_ih, inputs);
        //adiciona o bias
        Matrix.addMatrix(hidden, this.bias_h);
        //gerando o output dos hidden nodes
        NeuralNetwork.activationFunction(hidden);

        //Multiplicacao dos pesos e hidden nodes que ficarao armazenados na matriz output, que sempre tera
        //uma unica coluna
        Matrix output = Matrix.multiplyMatrix(this.weights_ho, hidden);
        //adiciona o bias
        Matrix.addMatrix(output, this.bias_o);
        //gera o output dos outputs nodes
        NeuralNetwork.activationFunction(output);

        //BACKPROPAGATION
        //transforma as listas em matrize
        Matrix targets = Matrix.fromArray(target);

        //calculo do erro do output
        // erro = target - output
        Matrix output_errors = Matrix.subtractMatrix(targets, output);

        //calculo do gradiente do output
        Matrix output_gradient = NeuralNetwork.gradientFunction(output);
        Matrix output_gradient_aux = Matrix.multiplyMatrix(output_gradient, output_errors);
        Matrix.multiplyScalar(output_gradient_aux, this.learning_rate);

        //calculo dos deltas do hidden->output layer
        Matrix hidden_trans = Matrix.transpose(hidden);
        Matrix weights_ho_deltas = Matrix.multiplyMatrix(output_gradient_aux, hidden_trans);

        //ajuste dos pesos da matriz do hidden->output layer
        Matrix.addMatrix(this.weights_ho, weights_ho_deltas);

        //ajuste do bias do output layer
        Matrix.addMatrix(this.bias_o, output_gradient_aux);

        //matriz transposta dos pesos entre a hidden layer e a output layer
        Matrix weight_ho_trans = Matrix.transpose(this.weights_ho);
        //calculo do erro da hidden layer
        Matrix hidden_errors = Matrix.multiplyMatrix(weight_ho_trans, output_errors);

        //calculo do gradiente do hidden layer
        Matrix hidden_gradient = NeuralNetwork.gradientFunction(hidden);
        Matrix hidden_gradient_aux = Matrix.multiplyMatrix(hidden_gradient, hidden_errors);
        Matrix.multiplyScalar(hidden_gradient_aux, this.learning_rate);

        //calculo dos deltas do input->hidden
        Matrix input_trans = Matrix.transpose(inputs);
        Matrix weights_ih_deltas = Matrix.multiplyMatrix(hidden_gradient_aux, input_trans);

        //ajuste dos pesos da matriz input->hidden layer
        Matrix.addMatrix(this.weights_ih, weights_ih_deltas);

        //ajuste dos bias do hidden layer
        Matrix.addMatrix(this.bias_h, hidden_gradient_aux);

    }

}

package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private Matrix weights_ih; //pesos entre a input layer e hidden layer
    private Matrix weights_ho; //pesos entre a hidden layer e output layer
    private Matrix bias_h; //bias dos hidden nodes
    private Matrix bias_o; //bias dos output node
    private double learning_rate; //taxa de aprendizado

    private WriteData write;

    //este construtor sera usado somente para o teste com o arquivo com os caracteres ruidos
    public NeuralNetwork() {

        //inicializa o learning rate
        this.learning_rate = 0.1;

        //inicializa o write
        write = new WriteData();
    }

    public NeuralNetwork(int size_input, int size_target, int size_hidden) throws IOException {
        //cria as matrizes de pesos
        weights_ih = new Matrix(size_hidden, size_input);
        weights_ho = new Matrix(size_target, size_hidden);

        //inicializa as matrizes de pesos
        Matrix.randomize(weights_ih);
        Matrix.randomize(weights_ho);

        //cria os bias
        bias_h = new Matrix(size_hidden, 1);
        bias_o = new Matrix(size_target, 1);

        //inicializ os bias
        Matrix.randomize(bias_h);
        Matrix.randomize(bias_o);

        //inicializa o write
        write = new WriteData();

        //inicializa o learning rate
        this.learning_rate = 0.1;

        //escrita dos arquivos com os valores iniciais dos bias
        write.writeBias(bias_h, 'h', 'b');
        write.writeBias(bias_o, 'o', 'b');

        //escrita dos arquivos com os valores iniciais dos pesos
        write.writeWeight(weights_ih, 'h', 'b');
        write.writeWeight(weights_ho, 'o', 'b');
    }

    public Matrix getWeights_ih() {
        return this.weights_ih;
    }

    public void setWeights_ih(Matrix weights_ih) { this.weights_ih = weights_ih; }

    public Matrix getWeights_ho() {
        return this.weights_ho;
    }

    public void setWeights_ho(Matrix weights_ho) { this.weights_ho = weights_ho; }

    public Matrix getBias_h() {
        return this.bias_h;
    }

    public void setBias_h(Matrix bias_h) { this.bias_h = bias_h; }

    public Matrix getBias_o() { return this.bias_o; }

    public void setBias_o(Matrix bias_o) { this.bias_o = bias_o; }

    public double getLearning_rate() { return this.learning_rate; }

    //funcao de ativacao sigmoide f(x) = 1 / (1 + exp(-x))
    public static void activationFunction(Matrix m) {
        /*
        o "x" da funcao de ativacao sera o valor de cada linha da matriz
         */
        for(int i = 0; i < m.getRows(); i++) {
            for(int j = 0; j < m.getCols(); j++) {
                double x = m.getValueMatrix(i, j);
                double result = 1 / (1 + Math.exp(-x));
                m.setValueMatrix((result), i, j);
            }
        }

    }

    //funcao de gradiente, derivada da funcao sigmoide f'(x) = f(x) * (1 - f(x))
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

    /*
    Metodo que serve para os testes depois de treinar a rede neural
     */
    public List<Double> test(List<Double> input, String fileName) throws IOException {
        /*
        transforma a lista de inputs em uma matriz de inputs.
        sempre sera uma matriz com uma unica coluna
         */
        Matrix inputs = Matrix.fromArray(input);

        /*
        - multiplicacao dos pesos e inputs que ficarao armazenados na matriz hidden
        - adiciona o bias ao resultado da multiplicacao entre os pesos e inputs
        - gera o output dos hidden nodes atraves da funcao de ativacao
         */
        Matrix hidden = Matrix.multiplyMatrix(this.weights_ih, inputs);
        Matrix.addMatrix(hidden, this.bias_h);
        NeuralNetwork.activationFunction(hidden);

        /*
        - multiplicacao dos pesos e hidden nodes que ficarao armazenados na matriz output
        - adiciona o bias ao resultado da multiplicacao entre os pesos e o output gerado pelos hidden nodes
        - gera o output dos output nodes atraves da funcao de ativacao
         */
        Matrix output = Matrix.multiplyMatrix(this.weights_ho, hidden);
        Matrix.addMatrix(output, this.bias_o);
        NeuralNetwork.activationFunction(output);

        /*
        arredondamento dos outputs, para obte-los em 0 e 1
         */
        List<Double> aux = output.toArray();
        List<Double> outputs = new ArrayList<>();

        for(int i = 0; i < aux.size(); i++) {
            outputs.add((double) Math.round(aux.get(i)));
        }

        //escrita dos outputs nos arquivos de saida
        write.writeOutput(output.toArray(), fileName);

        return outputs;
    }

    /*
    Metodo que faz o Feedforward, e, logo apos, o Brackpropagation
     */
    public void train(List<Double> input, List<Double> target) {
        /*********        FEEDFORWARD         ************/

        /*
        transforma a lista de inputs em uma matriz de inputs.
        sempre sera uma matriz com uma unica coluna
         */
        Matrix inputs = Matrix.fromArray(input);

        /*
        - multiplicacao dos pesos e inputs que ficarao armazenados na matriz hidden
        - adiciona o bias ao resultado da multiplicacao entre os pesos e inputs
        - gera o output dos hidden nodes atraves da funcao de ativacao
         */
        Matrix hidden = Matrix.multiplyMatrix(this.weights_ih, inputs);
        Matrix.addMatrix(hidden, this.bias_h);
        NeuralNetwork.activationFunction(hidden);

        /*
        - multiplicacao dos pesos e hidden nodes que ficarao armazenados na matriz output
        - adiciona o bias ao resultado da multiplicacao entre os pesos e o output gerado pelos hidden nodes
        - gera o output dos output nodes atraves da funcao de ativacao
         */
        Matrix output = Matrix.multiplyMatrix(this.weights_ho, hidden);
        Matrix.addMatrix(output, this.bias_o);
        NeuralNetwork.activationFunction(output);

        /*********        BACKPROPAGATION         ************/

        /*
        transforma a lista de target em uma matriz de targets.
        sempre sera uma matriz com uma unica coluna
         */
        Matrix targets = Matrix.fromArray(target);

        /*        Calculo do Erro do Output gerado pelo feedforward         */
        // ERRO = Target - Output
        Matrix output_errors = Matrix.subtractMatrix(targets, output);

        /*        Calculo do gradiente do output         */
        //output_gradient_aux = learning_rate * Erro * f'(x)
        Matrix output_gradient = NeuralNetwork.gradientFunction(output);
        Matrix output_gradient_aux = Matrix.multiplyElementWise(output_gradient, output_errors);
        Matrix.multiplyScalar(output_gradient_aux, this.learning_rate);

        /*        Calculo dos deltas do hidden->output layer         */
        // Delta(W_ho) = output_gradient_aux * Hidden(t)
        Matrix hidden_trans = Matrix.transpose(hidden);
        Matrix weights_ho_deltas = Matrix.multiplyMatrix(output_gradient_aux, hidden_trans);

        /*        Ajuste dos pesos da matriz do hidden->output layer         */
        Matrix.addMatrix(this.weights_ho, weights_ho_deltas);

        /*        Ajuste dos bias do output layer         */
        Matrix.addMatrix(this.bias_o, output_gradient_aux);

        /*        Calculo do erro dos hidden nodes         */
        //matriz transposta dos pesos entre a hidden layer e a output layer
        Matrix weight_ho_trans = Matrix.transpose(this.weights_ho);
        Matrix hidden_errors = Matrix.multiplyMatrix(weight_ho_trans, output_errors);

        /*        Calculo do gradiente do hidden layer         */
        //hidden_gradient_aux = learning_rate * Erro * f'(x)
        Matrix hidden_gradient = NeuralNetwork.gradientFunction(hidden);
        Matrix hidden_gradient_aux = Matrix.multiplyElementWise(hidden_gradient, hidden_errors);
        Matrix.multiplyScalar(hidden_gradient_aux, this.learning_rate);

        /*        Calculo dos deltas do input->hidden layer         */
        // Delta(W_ih) = hidden_gradient_aux * Input(t)
        Matrix input_trans = Matrix.transpose(inputs);
        Matrix weights_ih_deltas = Matrix.multiplyMatrix(hidden_gradient_aux, input_trans);

        /*        Ajuste dos pesos da matriz input->hidden layer         */
        Matrix.addMatrix(this.weights_ih, weights_ih_deltas);

        /*        Ajuste dos bias do hidden layer         */
        Matrix.addMatrix(this.bias_h, hidden_gradient_aux);

    }

}

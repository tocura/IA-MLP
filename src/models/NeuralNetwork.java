package models;

import java.util.List;

public class NeuralNetwork {

    private List<Double> input_Neuron;
    private double[] hidden_Neuron;
    private int[] output_Neuron;
    private List<Integer> target;
    private Matrix weights_ih; //pesos entre a input layer e hidden layer
    private Matrix weights_ho; //pesos entre a hidden layer e output layer
    private Matrix bias_h; //bias dos hidden nodes
    private Matrix bias_o; //bias dos output node

    public NeuralNetwork() {}

    public NeuralNetwork(List<Double> input_Neuron, List<Integer> target) {

        this.input_Neuron = input_Neuron;
        this.target = target;

        //numero de neuronios que tera a hidden layer
        hidden_Neuron = new double[Math.round((input_Neuron.size() + target.size())/2)];

        //numero de neuronios que tera o output layer
        output_Neuron = new int[target.size()];

        //cria as matrizes de pesos
        weights_ih = new Matrix(hidden_Neuron.length, input_Neuron.size());
        weights_ho = new Matrix(output_Neuron.length, hidden_Neuron.length);

        //cria os bias
        bias_h = new Matrix(hidden_Neuron.length, 1);
        bias_o = new Matrix(output_Neuron.length, 1);

    }

    public static void activationFunction(Matrix m) {
        //como a matriz passada como parametro so tem uma unica coluna
        //o "x" da funcao de ativacao sera o valor de cada linha da matriz
        for(int i = 0; i < m.getRows(); i++) {
            double result = 1 / (1 + Math.exp(-m.getValueMatrix(i, 0)));
            m.setValueMatrix((result), i, 0);
        }

    }

    public List<Double> feedforward(List<Double> input) {
        //transforma a lista de inputs em uma matriz de inputs
        //sempre sera uma matriz com uma unica coluna
        Matrix inputs = Matrix.fromArray(input);

        //multiplicacao dos pesos e inputs que ficarao armazenados na matriz hidden que sempre sera uma matriz i,1
        //ou seja, sempre tera uma unica coluna, e "i" eh o numero de linhas
        Matrix hidden = Matrix.multiplyMatrix(weights_ih, inputs);
        //adiciona o bias
        Matrix.addMatrix(hidden, bias_h);
        //gerando o output dos hidden nodes
        NeuralNetwork.activationFunction(hidden);

        //Multiplicacao dos pesos e hidden nodes que ficarao armazenados na matriz output, que sempre tera
        //uma unica coluna
        Matrix output = Matrix.multiplyMatrix(weights_ho, hidden);
        //adiciona o bias
        Matrix.addMatrix(output, bias_o);
        //gera o output dos outputs nodes
        NeuralNetwork.activationFunction(output);

        return output.toArray();
    }

}

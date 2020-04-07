package models;

import java.util.List;

public class NeuralNetwork {

    private List<Double> input_Neuron;
    private double[] hidden_Neuron;
    private int[] output_Neuron;
    private List<Double> target;
    private Matrix weights_ih; //pesos entre a input layer e hidden layer
    private Matrix weights_ho; //pesos entre a hidden layer e output layer

    public NeuralNetwork() {}

    public NeuralNetwork(List<Double> input_Neuron, List<Double> target) {

        this.input_Neuron = input_Neuron;
        this.target = target;

        //numero de neuronios que tera a hidden layer
        hidden_Neuron = new double[Math.round((input_Neuron.size() + target.size())/2)];

        //numero de neuronios que tera o output layer
        output_Neuron = new int[target.size()];

        weights_ih = new Matrix(hidden_Neuron.length, input_Neuron.size());
        weights_ho = new Matrix(output_Neuron.length, hidden_Neuron.length);
        
    }

}

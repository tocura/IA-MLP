package models;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private int value;
    private List<Double> target = new ArrayList<>(); //resultado esperado
    private List<Double> input = new ArrayList<>(); //neuronios de entrada

    public Neuron() {}

    public Neuron(List<Double> input, List<Double> target) {
        this.input = input;
        this.target = target;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Double> getInput() {
        return this.input;
    }

    public List<Double> getTarget() {
        return this.target;
    }

    public String toString() {
        return "{input: " + input.toString() + "}, {target: " + target.toString() + "}\n";
    }
}

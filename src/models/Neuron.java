package models;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private int value;
    private List<Integer> target = new ArrayList<>(); //resultado esperado
    private List<Integer> input = new ArrayList<>(); //neuronios de entrada

    public Neuron() {}

    public Neuron(List<Integer> input, List<Integer> target) {
        this.input = input;
        this.target = target;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Integer> getInput() {
        return this.input;
    }

    public List<Integer> getTarget() {
        return this.target;
    }

    public String toString() {
        return "{input: " + input.toString() + "}, {target: " + target.toString() + "}\n";
    }
}

package models;

import java.io.*;
import java.util.List;

public class WriteData {

    private String path = "src/output_files";
    private BufferedWriter writer;

    public void writeBias(Matrix bias, char tipo, char momento) throws IOException {

        if(momento == 'b') {
            if(tipo == 'h') {
                File file = new File(path + "Bias_hidden_layer_before.txt");
                FileWriter fWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fWriter);
                printWriter.println(bias.printMatrix());
            }
            else if(tipo == 'o') {
                File file = new File(path + "Bias_output_layer_before.txt");
                FileWriter fWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fWriter);
                printWriter.println(bias.printMatrix());
            }
        }
        else if (momento == 'a') {
            if(tipo == 'h') {
                File file = new File(path + "Bias_hidden_layer_after.txt");
                FileWriter fWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fWriter);
                printWriter.println(bias.printMatrix());
            }
            else if(tipo == 'o') {
                File file = new File(path + "Bias_output_layer_after.txt");
                FileWriter fWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fWriter);
                printWriter.println(bias.printMatrix());
            }
        }

    }

    public void writeWeight(Matrix weight, char tipo, char momento) throws IOException {

        if(momento == 'b') {
            if(tipo == 'h') {
                File file = new File(path + "Weight_input-hidden_before.txt");
                FileWriter fWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fWriter);
                printWriter.println(weight.printMatrix());
            }
            else if(tipo == 'o') {
                File file = new File(path + "Weight_hidden-output_before.txt");
                FileWriter fWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fWriter);
                printWriter.println(weight.printMatrix());
            }
        }
        else if(momento == 'a') {
            if(tipo == 'h') {
                File file = new File(path + "Weight_input-hidden_after.txt");
                FileWriter fWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fWriter);
                printWriter.println(weight.printMatrix());
            }
            else if(tipo == 'o') {
                File file = new File(path + "Weight_hidden-output_after.txt");
                FileWriter fWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fWriter);
                printWriter.println(weight.printMatrix());
            }
        }



    }

    public void writeOutput(List<Double> output) throws IOException {

        File file = new File(path + "Output.txt");
        FileWriter fWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fWriter);
        printWriter.println(output.toString() + "\n");

    }

    public void writeError(Matrix erro, char c) throws IOException {

        if(c == 'h') {
            File file = new File(path + "Hidden_error.txt");
            FileWriter fWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fWriter);
            printWriter.println(erro.printMatrix());
        }
        else if(c == 'o') {
            File file = new File(path + "Output_error.txt");
            FileWriter fWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fWriter);
            printWriter.println(erro.printMatrix());
        }

    }

    public void writeInput(List<Neuron> input) throws IOException {

        File file = new File(path + "Input.txt");
        FileWriter fWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fWriter);
        printWriter.println(input.toString() + "\n");

    }

}

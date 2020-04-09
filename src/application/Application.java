package application;

import models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws IOException {

        //leitura do nome do arquivo que sera usado na MLP
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do arquivo:");
        System.out.println("OBS.: o arquivo necessariamente precisa estar no diretório src/data.");

        String fileName = scanner.nextLine();

        scanner.close();

        //leitura dos dados do arquivo
        ReadData read = new ReadData();

        //lista de neuronios de entrada
        List<Neuron> input = read.readDataFile(fileName);

        //escrita dos dados nos arquivos
        WriteData write = new WriteData();

        //escreve os inputs no txt
        write.writeInput(input);

        String fname = "";

        if(fileName.contains("AND")) {
            fname = "Output_AND.txt";
        }
        else if(fileName.contains("mOR")) {
            fname = "Output_OR.txt";
        }
        else if(fileName.contains("XOR")) {
            fname = "Output_XOR.txt";
        }
        else if(fileName.contains("limpo")) {
            fname = "Output_Caracteres_limpo.txt";
        }
        else if(fileName.contains("ruido")) {
            fname = "Output_Caracteres_ruido.txt";
        }

        /*
        formula para inicializar o numero de neuronios da hidden layer
        eh a (input + output)/2 arredondado para cima
         */
        int input_size = input.get(0).getInput().size();
        int output_size = input.get(0).getTarget().size();

        int sum = input_size + output_size;
        int hidden_size = ((sum)/2) + ((sum)%2);

        /*
        Se o nome do arquivo for o caracteres-ruido.csv entao rodaremos a nossa rede neural
        com o valor obtido pelo treinamento com o arquivo caracteres-limpo.csv.
        Eh importante salientar que sempre deve-se rodar o arquivo com ruido apos rodar o limpo
        pois seus resultados estarao gravados no arquivo de saida
         */
        if(fileName.equals("caracteres-ruido.csv")) {

            //inicializa os bias e matrizes de peso com os resultados obtidos do treinamento com os caracteres limpos
            Matrix bias_h = read.readOutputFile("Bias_hidden_layer_after.txt", hidden_size, 1);
            Matrix bias_o = read.readOutputFile("Bias_output_layer_after.txt", output_size, 1);
            Matrix weights_ih = read.readOutputFile("Weight_input-hidden_after.txt", hidden_size, input_size);
            Matrix weights_ho = read.readOutputFile("Weight_hidden-output_after.txt", output_size, hidden_size);

            NeuralNetwork neural = new NeuralNetwork();
            List<List<Double>> outputs = new ArrayList<>();

            write.writeInitialParam(hidden_size, neural.getLearning_rate());

            neural.setBias_h(bias_h);
            neural.setBias_o(bias_o);
            neural.setWeights_ih(weights_ih);
            neural.setWeights_ho(weights_ho);

            //rodar o teste para a rede neural
            for(Neuron n : input) {
                outputs.add(neural.test(n.getInput(), fname));
            }

            for(List<Double> l : outputs) {
                write.writeOutput(l, fname);
            }

        }
        else {
            //inicializa a rede neural com o tamanho do input e target e com a escolha de quantos nos tera a hidden layer
            //como os inputs e os targets terao o mesmo tamanho tamnho podemos inicializar o contrutor da forma abaixo
            NeuralNetwork neural = new NeuralNetwork(input_size, output_size, hidden_size);

            List<List<Double>> outputs = new ArrayList<>();

            write.writeInitialParam(hidden_size, neural.getLearning_rate());

            //rodara 50000 epocas
            for(int i = 0; i < 50000; i++) {

                for(Neuron n : input) {
                    neural.train(n.getInput(), n.getTarget());
                }

            }

            //escrita dos arquivos com os bias finais
            write.writeBias(neural.getBias_h(), 'h', 'a');
            write.writeBias(neural.getBias_o(), 'o', 'a');

            //escrita dos arquivos com os valors dos pesos finais
            write.writeWeight(neural.getWeights_ih(), 'h', 'a');
            write.writeWeight(neural.getWeights_ho(), 'o', 'a');

            //rodar o teste para a rede neural
            for(Neuron n : input) {
                outputs.add(neural.test(n.getInput(), fname));
            }

            for(List<Double> l : outputs) {
                write.writeOutput(l, fname);
            }
        }

    }

}

package application;

import models.Matrix;
import models.NeuralNetwork;
import models.Neuron;
import models.ReadData;

import java.io.IOException;
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

        System.out.println(input.toString());

        System.out.println("--------------------");

        //inicializa a rede neural com o primeiro input e target
        NeuralNetwork neural = new NeuralNetwork(input.get(0).getInput().size(), input.get(0).getTarget().size(), 35);


        //rodara 50000 epocas
        for(int i = 0; i < 50000; i++) {

            for(Neuron n : input) {
                neural.train(n.getInput(), n.getTarget());
            }

        }



        //lista que ira mostrar os resultados
        List<Double> result;
        for(Neuron n : input) {
            result = neural.feedforward(n.getInput());
            System.out.println(result.toString());
        }

    }

}

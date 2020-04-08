package application;

import models.NeuralNetwork;
import models.Neuron;
import models.ReadData;
import models.WriteData;

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

        //escrita dos dados nos arquivos
        WriteData write = new WriteData();

        //escreve os inputs no txt
        write.writeInput(input);

        //inicializa a rede neural com o tamanho do input e target e com a escolha de quantos nos tera a hidden layer
        //como os inputs e os targets terao o mesmo tamanho tamnho podemos inicializar o contrutor da forma abaixo
        NeuralNetwork neural = new NeuralNetwork(input.get(0).getInput().size(), input.get(0).getTarget().size(), 35);

        //rodara 50000 epocas
        for(int i = 0; i < 50000; i++) {

            for(Neuron n : input) {
                neural.train(n.getInput(), n.getTarget());
            }

        }

        //escrita dos arquivos com os bias iniciais
        write.writeWeight(neural.getBias_h(), 'h', 'a');
        write.writeWeight(neural.getBias_o(), 'o', 'a');

        //escrita dos arquivos com os valors dos pesos finais
        write.writeWeight(neural.getWeights_ih(), 'h', 'a');
        write.writeWeight(neural.getWeights_ho(), 'o', 'a');

        //rodar o teste para a rede neural
        for(Neuron n : input) {
            neural.test(n.getInput());
        }

    }

}

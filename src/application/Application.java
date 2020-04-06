package application;

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

        //teste
        System.out.println(input.toString());
    }

}

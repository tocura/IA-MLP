package application;

import models.Matrix;
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

        Matrix m = new Matrix(2,3);
        m.printMatrix();

        System.out.println("---------------------");

        Matrix.addScalar(m, 1);
        m.printMatrix();

        System.out.println("---------------------");

        Matrix.multiplyScalar(m, 2);
        m.printMatrix();

        System.out.println("---------------------");

        Matrix n = new Matrix(3,2);
        n.printMatrix();

        System.out.println("---------------------");

        Matrix.addMatrix(m, n);
        m.printMatrix();
        n.printMatrix();

        System.out.println("---------------------");

        Matrix c = Matrix.multiplyMatrix(m, n);
        c.printMatrix();

        System.out.println("---------------------");

        Matrix d = Matrix.transpose(c);
        d.printMatrix();
        c.printMatrix();
    }

}

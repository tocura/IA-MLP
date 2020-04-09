package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReadData {

    private String pathInput = "src/data/";
    private String pathOutput = "src/output_files/";

    /*
    Metodo para ler os dados dos arquivos de entrada
     */
    public List<Neuron> readDataFile(String fileName) throws IOException {

        String line = "";

        List<Neuron> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(pathInput + fileName));

        //loop para percorrer todas as linhas do arquivo
        while((line = reader.readLine()) != null) {
            List<Double> input = new ArrayList<>();
            List<Double> target = new ArrayList<>();

            List<String> content = Arrays.asList(line.split(","));
            int contentLenght = content.size();

            /*
            inicializa os neuronios de entrada com seu respectivo valor
            ha uma verificacao do tamanho de cada conteudo porque havia alguns caracteres ocultos atrapalhando
            a conversao da string para int
             */
            for(int i = 0; i <= contentLenght - 2; i++) {

                if (content.get(i).length() > 2) {
                    String str = content.get(i).substring(1);
                    input.add(Double.parseDouble(str));
                } else {
                    input.add(Double.parseDouble(content.get(i)));
                }

            }

            /*
            inicializa o resultado esperado dos problems AND, OR, XOR - um unico neuronio de saida
            caso nao seja nenhum desses problemas entao sera o problema dos caracteres, que tera sete neuronios de saida
            como nao eh possivel fazer a rede neural responder com a String em si do caracter, usaremos um ArrayList de inteiros
            que dependendo de onde o neuronio 1 aparecer determinara o caracter, ou seja, se a saida for
            1000000, significa que a rede neural induziu que aquela entrada representa o caracter "A" e assim por diante.
             */
            if(content.get(contentLenght-1).contains("1") || content.get(contentLenght-1).contains("0")) {
                target.add(Double.parseDouble(content.get(contentLenght-1)));
            }
            else {
                target = targetAux(content.get(contentLenght-1));
            }

            list.add(new Neuron(input, target));

        }

        reader.close();

        return list;
    }

    /*
    Metodo auxiliar para definir o valor do target visto que nao seria possivel o nosso neuronio de saida
    devolver um char
     */
    private List<Double> targetAux(String content) {
        Double[] a = {1.0,0.0,0.0,0.0,0.0,0.0,0.0};
        Double[] b = {0.0,1.0,0.0,0.0,0.0,0.0,0.0};
        Double[] c = {0.0,0.0,1.0,0.0,0.0,0.0,0.0};
        Double[] d = {0.0,0.0,0.0,1.0,0.0,0.0,0.0};
        Double[] e = {0.0,0.0,0.0,0.0,1.0,0.0,0.0};
        Double[] j = {0.0,0.0,0.0,0.0,0.0,1.0,0.0};
        Double[] k = {0.0,0.0,0.0,0.0,0.0,0.0,1.0};

        List<Double> tAux = new ArrayList<>();

        switch(content) {

            case "A":
                tAux = Arrays.asList(a);
                break;
            case "B":
                tAux = Arrays.asList(b);
                break;
            case "C":
                tAux = Arrays.asList(c);
                break;
            case "D":
                tAux = Arrays.asList(d);
                break;
            case "E":
                tAux= Arrays.asList(e);
                break;
            case "J":
                tAux = Arrays.asList(j);
                break;
            case "K":
                tAux = Arrays.asList(k);
                break;
        }

        return tAux;
    }

    /*
    metodo para ler os arquivos de saida que serao posteriormente usados para
    o teste com o arquivo de caracteres-ruido.csv
     */
    public Matrix readOutputFile(String fileName, int rows, int cols) throws IOException {

        String line = "";

        BufferedReader reader = new BufferedReader(new FileReader(pathOutput + fileName));

        List<Double> data = new ArrayList<>();

        Matrix m = new Matrix(rows, cols);

        while((line = reader.readLine()) != null) {
            List<String> content = Arrays.asList(line.split(","));

            for(int i = 0; i < content.size(); i++) {
                if(!content.get(i).isEmpty()) {
                    data.add(Double.parseDouble(content.get(i)));
                }
            }

        }

        m.toMatrix(data);

        return m;
    }

}

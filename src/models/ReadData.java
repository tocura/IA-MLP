package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReadData {

    private String path = "src/data/";

    /*
    Metodo para ler os dados dos arquivos de entrada
     */
    public List<InputNeuron> readDataFile(String fileName) throws IOException {

        String line = "";

        List<InputNeuron> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(path + fileName));

        while((line = reader.readLine()) != null) {
            List<Integer> input = new ArrayList<>();
            List<Integer> target = new ArrayList<>();

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
                    input.add(Integer.parseInt(str));
                } else {
                    input.add(Integer.parseInt(content.get(i)));
                }

            }

            /*
            inicializa o resultado esperado dos problems AND, OR, XOR - um unico neuronio de saida
            caso nao seja nenhum desses problemas entao sera o problema dos caracteres, que tera sete neuronios de saida
            como nao eh possivel fazer a rede neural responder com a String em si do caracter, usaremos um ArrayList de inteiros
            que dependendo de onde o neuronio 1 aparecer determinara o caracter, ou seja, se a saida for
            1000000, significa que a rede neural induziu que aquela entrada representa o caracter "A" e assim por diante.
             */
            if(content.get(contentLenght-1).contains("1") || content.get(contentLenght-1).contains("-1") || content.get(contentLenght-1).contains("0")) {
                target.add(Integer.parseInt(content.get(contentLenght-1)));
            }
            else {
                target = targetAux(content.get(contentLenght-1));
            }

            list.add(new InputNeuron(input, target));

        }

        reader.close();

        return list;
    }

    /*
    Metodo auxiliar para definir o valor do target visto que nao seria possivel o nosso neuronio de saida
    devolver um char
     */
    private List<Integer> targetAux(String content) {
        Integer[] a = {1,0,0,0,0,0,0};
        Integer[] b = {0,1,0,0,0,0,0};
        Integer[] c = {0,0,1,0,0,0,0};
        Integer[] d = {0,0,0,1,0,0,0};
        Integer[] e = {0,0,0,0,1,0,0};
        Integer[] j = {0,0,0,0,0,1,0};
        Integer[] k = {0,0,0,0,0,0,1};

        List<Integer> tAux = new ArrayList<>();

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
            case " ":
                tAux = Arrays.asList(j);
                break;
            case "K":
                tAux = Arrays.asList(k);
                break;
        }

        return tAux;
    }

}
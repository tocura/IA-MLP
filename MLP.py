# Bibliotecas
import numpy as np  # usada para converter dados de excel em matrizes
import pandas as pd  # usada para ler arquivos Excel
from sklearn.neural_network import MLPClassifier  # biblioteca usada que implementa a MLP


# Esse método é usado para rodar a MLP quando o arquivo passado
# como treinamento é o de caracteres(caracteres-limpo ou caracteres-ruido)
def run_mlp_caracteres(x):
    # retira todas as colunas da matriz(criada a partir da tabela Excel) e deixa somente a coluna de output
    y = np.delete(x, np.s_[0:63], 1)

    # o contrário de y, retira a coluna de output e deixa as colunas de input
    x = np.delete(x, 63, 1)

    # a classe MLPClassifier é instânciada com os valores passados como parâmetro
    clf = MLPClassifier(solver='lbfgs', alpha=1e-5, hidden_layer_sizes=(35,), learning_rate='adaptive', random_state=1,
                        activation='logistic', max_iter=1000)

    # método que treina o modelo, a partir do input e output passados como parâmetro
    clf.fit(x, y.ravel())

    print("Deseja testar o treinamento com o arquivo caracteres_ruido.csv?")
    print("[0]: NÃO")
    print("[1]: SIM")
    resp = int(input())
    name_part = "limpo"
    if resp == 1:
        # lê o arquivo caracteres-ruido.csv, que será usado como teste
        x = pd.read_csv("data/caracteres-ruido.csv", header=None)

        # transforma a tabela Excel em matriz
        x = x.to_numpy()

        # retira todas as colunas da matriz(criada a partir da tabela Excel) e deixa somente a coluna de output
        y = np.delete(x, np.s_[0:63], 1)

        # o contrário de y, retira a coluna de output e deixa as colunas de input
        x = np.delete(x, 63, 1)

        name_part = "ruido"

    # executa o teste com o input passado
    predict = clf.predict(x)

    # cria um arquivo .txt com o resultado da predição
    np.savetxt("output/predição_caracteres_" + name_part + ".txt", predict, fmt='%s')

    # cria e atualiza um arquivo com o índice de acerto da predição
    f = open("output/indice_acerto_caracteres_" + name_part + ".txt", "w")
    f.write(str(clf.score(x, y)))


# função que retorna o operador de bit, para composição dos arquivos de saída
def return_op(name):
    if "AND" in name:
        name_part = "AND"
    elif "mOR" in name:
        name_part = "OR"
    elif "XOR" in name:
        name_part = "XOR"
    return name_part


# Esse método é usado para rodar a MLP quando o arquivo passado
# como treinamento é o de operadores bit(AND, OR, XOR)
def run_mlp_bit_operators(x, name):

    # retira todas as colunas da matriz(criada a partir da tabela Excel) e deixa somente a coluna de output
    y = np.delete(x, [0, 1], 1)

    # o contrário de y, retira a coluna de output e deixa as colunas de input
    x = np.delete(x, 2, 1)

    # a classe MLPClassifier é instânciada com os valores passados como parâmetro
    clf = MLPClassifier(solver='lbfgs', alpha=1e-5, hidden_layer_sizes=(2,), learning_rate='adaptive', random_state=1,
                        activation='logistic', max_iter=1000)

    # método que treina o modelo, a partir do input e output passados como parâmetro
    clf.fit(x, y.ravel())

    # executa o teste com o input passado
    predict = clf.predict(x)

    # retorna o operador
    name_part = return_op(name)

    # cria um arquivo .txt com o resultado da predição
    np.savetxt("output/predição_" + name_part + ".txt", predict, fmt='%s')

    # cria e atualiza um arquivo com o índice de acerto da predição
    f = open("output/indice_acerto_" + name_part + ".txt", "w")
    f.write(str(clf.score(x, y)))


def main():

    # lê o arquivo
    arq_name = input("Digite o nome do arquivo(ele deve estar em IA/data):")
    arq_name = "data/" + arq_name
    x = pd.read_csv(arq_name, header=None)

    # transforma o Excel em matriz
    x = x.to_numpy()

    # verifica se o arquivo é sobre o treinamento para classificação de caracteres ou de operações de bit
    if "caracteres" in arq_name:
        run_mlp_caracteres(x)
    else:
        run_mlp_bit_operators(x, arq_name)


main()

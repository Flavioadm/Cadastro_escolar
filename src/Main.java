/*
Desenvolvido por:
Daniel Carmo dos Santos - MAT 202108164984
Flávio Castro Lucas - MAT 202107381809
Rafael Figliuolo Nascimento - MAT 202108700037
Raudiney Andrade dos Santos - MAT 202108700614
Renato Apolinário da Silva - MAT 202104299508
Uillian Queiroz da Conceição - MAT 202109534238
*/


import java.text.ParseException; // Corrige problemas ao misturar chamadas de nextLine e next com nextInt e nextDouble
import java.text.SimpleDateFormat; // É uma classe concreta para formatação e análise de datas de maneira sensível à localidade
import java.util.ArrayList;
import java.util.Calendar; // fazer aritmética de datas, calcular Idade
import java.util.Date; // fornece a data e a hora
import java.util.HashMap; // arrays associativos úteis quando precisamos pesquisar e atualizar elementos correspondentes com base em uma chave específica para determinado valor.
// “Mapeia” os valores contidos no conjunto de dados tomando as chaves por referência, usei no Keyset() na opção 4 Consultar Turmas.
import java.util.List; // coleção ordenada de elementos. Esses elementos podem variar em relação ao tipo de dado — números inteiros, caracteres, booleanos ou decimais.
import java.util.Map; // É usada para armazenar pares de chaves de valor
import java.util.Scanner;
import java.text.Normalizer; //Usar a classe Normalizer junto com expressões regulares para substituir os caracteres acentuados por suas formas não acentuadas.


//TODO Criar cadastro de professor e matéria ensinada

// TODO Cadastro de aluno futuramente terá endereço(com API consulta CEP), CPF, RG, nome do pai e da mãe além de telefone de contato e nome social
class Aluno {
    private String nome;
    private String dataNascimento;
    private int idade;
    private String turma;

    public Aluno(String nome, String dataNascimento, int idade, String turma) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.turma = turma;
    }

    public String getNome() {
        return nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public int getIdade() {
        return idade;
    }

    public String getTurma() {
        return turma;
    }
}

//TODO Futuramente colocarei a opção de consultar alunos por sexo, calcular a idade média da turma, alunos com idade acima ou abaixo da media da idade da turma ou de uma idade de referência
// esses dados são impostantes para saber se alunos estão dentro ou fora dafaixa de idade para serie, além de saber o percetual de alunos por sexo
class Turma {
    private String nomeTurma;
    private int capacidade;//Toda classe tem uma quantidade máxima de alunos que podem ser matriculados
    private List<Aluno> alunos;

    public Turma(String nomeTurma, int capacidade) {
        this.nomeTurma = nomeTurma;
        this.capacidade = capacidade;
        this.alunos = new ArrayList<>();
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public String nomeTurma() {
        return nomeTurma;
    }
//TODO criar classe matérias por serie

//TODO criar classe notas e média.

//TODO criar classe frequência
    public int getCapacidade() {
        return capacidade;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public boolean adicionarAluno(Aluno aluno) { //Função para verificar se há capacidade na turma de cadastrar novo aluno
        if (alunos.size() < capacidade) {
            alunos.add(aluno);
            return true;
        } else {
            return false; // Turma atingiu a capacidade máxima.
        }
    }
}

class CadastroAlunos {
    private List<Aluno> alunos = new ArrayList<>();
    Map<String, Turma> turmas = new HashMap<>(); //Usei o HashMap para armazenar pares de chave e valor(Turma e capacidade) o ArrayList armazena apenas valor ou elemento
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private CadastroAlunos cadastro;

    public void cadastrarAluno(String nome, String dataNascimento, String turmaNome) {
        turmaNome = turmaNome.toUpperCase();
        if (nome.length() > 0 && dataNascimento.length() > 0 && turmaNome.length() > 0) {//Caso algum dado seja nulo o aluno não é cadastrado e vai direto para o ultimo else.
            String nomeFormatado = formatarNome(nome);
            String dataNascimentoFormatada = formatarDataNascimento(dataNascimento);
            int idade = calcularIdade(dataNascimento);

            if (idade >= 5) {
                Turma turma = turmas.get(turmaNome);
                if (turma != null) {
                    Aluno aluno = new Aluno(nomeFormatado, dataNascimentoFormatada, idade, turmaNome);
                    if (turma.adicionarAluno(aluno)) {
                        alunos.add(aluno);
                        System.out.println("Aluno cadastrado na turma: " + turmaNome);
                    } else {
                        System.out.println("A turma atingiu a capacidade máxima de " + turma.getCapacidade()+ " alunos. O aluno não foi cadastrado, cadastre nova turma ou exclua um aluno da turma.");
                    }
                } else {
                    System.out.println("\n\nTurma não encontrada. O aluno não foi cadastrado.");
                    if (turmaNome.length() > 1){
                        System.out.println("\n\nEssa turma não está cadastrada, consulte a opção 4 e consulte as turmas cadastradas: \n\n");
                    }else{
                        System.out.println("\n\nAinda não existem turmas cadastradas\n\n");
                    }
                }
            } else {
                System.out.println("A criança tem " + idade + " de idade, não matriculamos criança com menos de 5 anos.");
            }
        } else {
            System.out.println("Nenhum dado de entrada não pode ser nulo. O aluno não foi cadastrado, por favor refaça o cadastro.");
        }
    }

    public void cadastrarTurma(String nome, int capacidade) {
        if (nome != null && !nome.isEmpty() && capacidade > 0) {
            Turma turma = new Turma(nome.toUpperCase(), capacidade);
            turmas.put(nome.toUpperCase(), turma);
            System.out.println("Turma cadastrada: " + nome);
        } else {
            System.out.println("Dados de entrada inválidos. A turma não foi cadastrada.");
        }
    }

    public void removerAluno(String nomeRemover) {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getNome().equals(nomeRemover)) {
                String turmaNome = alunos.get(i).getTurma();
                Turma turma = turmas.get(turmaNome);
                System.out.println("       _______ ______ _   _  _____          ____  \n" +
                        "    /\\|__   __|  ____| \\ | |/ ____|   /\\   / __ \\ \n" +
                        "   /  \\  | |  | |__  |  \\| | |       /  \\ | |  | |\n" +
                        "  / /\\ \\ | |  |  __| | . ` | |      / /\\ \\| |  | |\n" +
                        " / ____ \\| |  | |__| |\\  | |__ / ____ \\ |__| |\n" +
                        "/_/    \\_\\_|  |______|_| \\_|\\_____/_/    \\_\\____/ \n" +
                        "                                                  \n\n");
                System.out.println("Você está prestes a excluir o aluno: " + alunos.get(i).getNome() + "da turma" + alunos.get(i).getTurma());
                System.out.print("Deseja continuar? (S para confirmar, qualquer outra tecla para cancelar): ");
                Scanner scanner = new Scanner(System.in);
                String confirmacao = scanner.nextLine();
                if (confirmacao.equalsIgnoreCase("S")) {
                    turma.getAlunos().remove(alunos.get(i));
                    alunos.remove(i);
                    System.out.println("Aluno removido com sucesso.");
                } else {
                    System.out.println("A exclusão do aluno foi cancelada.");
                }
            }
        }
        System.out.println("Aluno não encontrado. Nenhum aluno foi removido.");
    }

    public void excluirTurma(String nome) {
        if (turmas.containsKey(nome)) {
            Turma turma = turmas.get(nome);
            System.out.println("       _______ ______ _   _  _____          ____  \n" +
                    "    /\\|__   __|  ____| \\ | |/ ____|   /\\   / __ \\ \n" +
                    "   /  \\  | |  | |__  |  \\| | |       /  \\ | |  | |\n" +
                    "  / /\\ \\ | |  |  __| | . ` | |      / /\\ \\| |  | |\n" +
                    " / ____ \\| |  | |__| |\\  | |__ / ____ \\ |__| |\n" +
                    "/_/    \\_\\_|  |______|_| \\_|\\_____/_/    \\_\\____/ \n" +
                    "                                                  \n\n");
            System.out.println("Você está prestes a excluir a turma: " + nome);
            System.out.print("Deseja continuar? (S para confirmar, qualquer outra tecla para cancelar): ");
            Scanner scanner = new Scanner(System.in);
            String confirmacao = scanner.nextLine();
            if (confirmacao.equalsIgnoreCase("S")) {
                turmas.remove(nome);
                System.out.println("Turma " + nome + " excluída com sucesso.");
            } else {
                System.out.println("A exclusão da turma foi cancelada.");
            }
        } else {
            System.out.println("Turma não encontrada. Nenhuma turma foi excluída.");
        }
    }

    public List<Aluno> consultarAlunos(String chave) {
        List<Aluno> resultados = new ArrayList<>();

        if (chave != null) {
            for (Aluno aluno : alunos) {
                chave = chave.substring(0,1).toUpperCase().concat(chave.substring(1));
                if (aluno.getNome().contains(chave) || aluno.getDataNascimento().contains(chave) || aluno.getTurma().contains(chave)) {
                    resultados.add(aluno);
                }
            }
        } else {
            System.out.println("Chave de consulta não pode ser nula.");
        }

        return resultados;
    }

    public List<Aluno> AlunosPorTurma(String turma) {
        List<Aluno> resultados = new ArrayList<>();

        for (Aluno aluno : alunos) {
            if (aluno.getTurma().equals(turma)) {
                resultados.add(aluno);
            }
        }

        return resultados;
    }


    private String formatarNome(String nome) {//Usei para colocar a primeira letra em maiúscula e as outra minúsculas.
        if (nome != null && !nome.isEmpty()) {
            String[] palavras = nome.split(" ");// Separa o nome(String) util para pesquisar o por nome ou sobrenome
            StringBuilder nomeFormatado = new StringBuilder();
            for (String palavra : palavras) {
                if (nomeFormatado.length() > 2) {//Usei uma segunda verificação, se o nome tiver menos de 2 caracteres não haverá necessidade de separar o nome
                    nomeFormatado.append(" ");
                }
                nomeFormatado.append(palavra.substring(0, 1).toUpperCase());
                nomeFormatado.append(palavra.substring(1).toLowerCase());
            }
            return nomeFormatado.toString();
        }
        return null;
    }

    private String formatarDataNascimento(String dataNascimento) {
        if (dataNascimento != null && !dataNascimento.isEmpty()) {
            try {
                Date data = dateFormat.parse(dataNascimento);
                return dateFormat.format(data);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. A data de nascimento não foi formatada.");
            }
        }
        return null;
    }

    private int calcularIdade(String dataNascimento) {
        if (dataNascimento != null && !dataNascimento.isEmpty()) {
            try {
                Date dataNasc = dateFormat.parse(dataNascimento);
                Calendar dataNascimentoCal = Calendar.getInstance();
                dataNascimentoCal.setTime(dataNasc);

                Calendar hoje = Calendar.getInstance();

                int idade = hoje.get(Calendar.YEAR) - dataNascimentoCal.get(Calendar.YEAR);
                if (hoje.get(Calendar.MONTH) < dataNascimentoCal.get(Calendar.MONTH)
                        || (hoje.get(Calendar.MONTH) == dataNascimentoCal.get(Calendar.MONTH)
                        && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimentoCal.get(Calendar.DAY_OF_MONTH))) {
                    idade--;
                }

                return idade;
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. A idade não foi calculada.");
            }
        }
        return -1; // Em caso de erro, retorna -1 como valor de idade.
    }
}

public class Main {
    public static void main(String[] args) {
        CadastroAlunos cadastro = new CadastroAlunos();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar turma");
            System.out.println("2. Cadastrar aluno");
            System.out.println("3. Consultar turmas");
            System.out.println("4. Consultar alunos");
            System.out.println("5. Consultar alunos por turma");
            System.out.println("6. Excluir aluno");
            System.out.println("7. Excluir turma");
            System.out.println("8. Sair\n\n");

            String opcao = scanner.next();//Coloquei como String pois quando colocava Int e era inserido uma letra ou simbolo dava erro e terminava o programa

            if (opcao.equals("1")) {
                scanner.nextLine();
                System.out.print("Digite o nome da turma: ");
                String nomeTurma = scanner.nextLine();
                System.out.print("Digite a capacidade da turma: ");
                int capacidadeTurma = scanner.nextInt();
                cadastro.cadastrarTurma(nomeTurma, capacidadeTurma);

            } else if (opcao.equals("2")) { // Função foi escrita com equals pois o TRY CATCH não consegui fazer que ele retornasse para a escolha da opção
                scanner.nextLine(); // Avança para a próxima linha
                System.out.print("Digite o nome do aluno: ");
                String nome = scanner.nextLine();
                System.out.print("Digite a data de nascimento do aluno (dd/mm/aaaa): ");
                String dataNascimento = scanner.nextLine();
                System.out.print("Digite o nome da turma do aluno: ");
                String turma = scanner.nextLine();
                cadastro.cadastrarAluno(nome, dataNascimento, turma);

            } else if (opcao.equals("3")) {
                if (cadastro.turmas.size() > 0) {//A opção length() não funcionou, usei size.
                    for (String turmaNome : cadastro.turmas.keySet()) { // O KeySet basicamente retorna uma visualização de conjunto do mapa hash
                        System.out.println("\nTurma cadastrada: " + turmaNome + "\n");
                    }
                } else {
                    System.out.println("\n\nAinda não há nenhuma turma cadastrada");
                }


            } else if (opcao.equals("4")) {
                scanner.nextLine();
                System.out.print("Digite o nome, data de nascimento ou turma a ser consultado: ");
                String chave = scanner.nextLine();
                List<Aluno> resultados = cadastro.consultarAlunos(chave);

                if (resultados.isEmpty()) {
                    System.out.println("Nenhum aluno encontrado.");
                } else {
                    System.out.println("Alunos encontrados:");
                    for (Aluno aluno : resultados) {
                        System.out.println("Nome: " + aluno.getNome() + ", Idade: " + aluno.getIdade() + " anos, Turma: " + aluno.getTurma());
                    }
                }

            } else if (opcao.equals("5")) {
                scanner.nextLine();
                System.out.print("Digite o nome da turma para consultar os alunos: ");
                String nomeTurma = scanner.nextLine();
                nomeTurma = nomeTurma.toUpperCase();

                List<Aluno> alunosPorTurma = cadastro.AlunosPorTurma(nomeTurma);

                if (alunosPorTurma.isEmpty()) {
                    System.out.println("Nenhum aluno encontrado na turma: " + nomeTurma);
                } else {
                    System.out.println("Alunos encontrados na turma " + nomeTurma + ":");
                    for (Aluno aluno : alunosPorTurma) {
                        System.out.println("Nome: " + aluno.getNome() + ", Idade: " + aluno.getIdade() + " anos");
                    }
                }
            } else if (opcao.equals("6")) {
                scanner.nextLine();
                System.out.print("Digite o nome do aluno a ser removido: ");
                String nomeAluno = scanner.nextLine();
                cadastro.removerAluno(nomeAluno);
            } else if (opcao.equals("7")) {
                scanner.nextLine();
                System.out.print("Digite o nome da turma a ser excluída: ");
                String nomeTurma = scanner.nextLine().toUpperCase();
                cadastro.excluirTurma(nomeTurma);
            } else if (opcao.equals("8")) {
                System.out.println("\n\nObrigado por usar o cadastro de alunos.\n\n");
                System.out.println("   Desenvolvido por:\n\n" +
                        "   Daniel Carmo dos Santos - MAT 202108164984\n" +
                        "   Flávio Castro Lucas - MAT 202107381809\n" +
                        "   Rafael Figliuolo Nascimento - MAT 202108700037\n"+
                        "   Raudiney Andrade dos Santos - MAT 202108700614\n" +
                        "   Renato Apolinário da Silva - MAT 202104299508\n" +
                        "   Uillian Queiroz da Conceição - MAT 202109534238\n\n\n");

                break;
            } else {
                System.out.println("       _______ ______ _   _  _____          ____  \n" +
                        "    /\\|__   __|  ____| \\ | |/ ____|   /\\   / __ \\ \n" +
                        "   /  \\  | |  | |__  |  \\| | |       /  \\ | |  | |\n" +
                        "  / /\\ \\ | |  |  __| | . ` | |      / /\\ \\| |  | |\n" +
                        " / ____ \\| |  | |__| |\\  | |__ / ____ \\ |__| |\n" +
                        "/_/    \\_\\_|  |______|_| \\_|\\_____/_/    \\_\\____/ \n" +
                        "                                                  \n\n");
                System.out.println(opcao + "  É uma opção inválida, é necessário que informe um número de 1 a 8. Tente novamente.\n\n");
            }
        }

        scanner.close();// Isso é importante porque libera recursos do sistema que foram alocados para a instância do Scanner.
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe principal para o gerenciamento de tarefas
public class codigoRevisao {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager("src/tasks.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Gerenciamento de Tarefas ===");
        while (true) {
            System.out.println("Selecione uma opção:\n" +
                    "[0] Sair\n" +
                    "[1] Adicionar Tarefa\n" +
                    "[2] Listar Tarefas Salvas\n" +
                    "[3] Salvar Tarefas\n" +
                    "[4] Remover Tarefa");

            int option = scanner.nextInt();
            scanner.nextLine();  // Limpar buffer do scanner

            switch (option) {
                case 0:
                    System.out.println("=== Saindo... ===");
                    scanner.close();
                    return;
                case 1:
                    addTask(scanner, taskManager);
                    break;
                case 2:
                    taskManager.listTasks();
                    break;
                case 3:
                    taskManager.saveTasks();
                    break;
                case 4:
                    removeTask(scanner, taskManager);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    // Método para adicionar uma tarefa
    private static void addTask(Scanner scanner, TaskManager taskManager) {
        System.out.println("=== Adicionar Tarefa ===");
        System.out.print("Digite uma nova tarefa: ");
        String task = scanner.nextLine();
        taskManager.addTask(task);
        System.out.println("=== Tarefa adicionada com sucesso ===");
    }

    // Método para remover uma tarefa
    private static void removeTask(Scanner scanner, TaskManager taskManager) {
        System.out.println("=== Remover Tarefa ===");
        System.out.print("Digite o nome da tarefa a remover: ");
        String task = scanner.nextLine();
        if (taskManager.removeTask(task)) {
            System.out.println("Tarefa removida com sucesso.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }
}

// Classe responsável pelo gerenciamento das tarefas
class TaskManager {
    private List<String> tasks;
    private String filePath;

    public TaskManager(String filePath) {
        this.filePath = filePath;
        this.tasks = new ArrayList<>();
        loadTasksFromFile();
    }

    // Adiciona uma nova tarefa à lista
    public void addTask(String task) {
        tasks.add(task);
    }

    // Remove uma tarefa da lista
    public boolean removeTask(String task) {
        boolean removed = tasks.remove(task);
        if (removed) {
            saveTasks();
        }
        return removed;
    }

    // Lista as tarefas salvas no arquivo
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            System.out.println("=== Tarefas Salvas ===");
            for (String task : tasks) {
                System.out.println(task);
            }
            System.out.println("======================");
        }
    }

    // Salva as tarefas no arquivo
    public void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String task : tasks) {
                bw.write(task);
                bw.newLine();
            }
            System.out.println("=== Tarefas salvas com sucesso ===");
        } catch (IOException e) {
            System.out.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    // Carrega as tarefas do arquivo
    private void loadTasksFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar tarefas: " + e.getMessage());
        }
    }
}

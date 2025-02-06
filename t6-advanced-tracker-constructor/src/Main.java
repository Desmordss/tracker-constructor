import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создаем задачи
        System.out.println("Создание и тестирование задач...");
        Task task1 = new Task("Задача 1", "Мы всей семьёй переедем в другой город!");
        manager.createTask(task1); manager.getTaskById(task1.getId());
        Task task2 = new Task("Задача 2", "Сделать план переезда");
        manager.createTask(task2); manager.getTaskById(task2.getId());
        //
        Task task3 = new Task("Задача 3", "Освоиться в новом городе");
        manager.createTask(task3); manager.getTaskById(task3.getId());
        Task task4 = new Task("Задача 4", "Найти работу в новом городе");
        manager.createTask(task4); manager.getTaskById(task4.getId());

        // Создаем эпик с подзадачами
        System.out.println("\nСоздание эпика с подзадачами...");
        Epic epic1 = new Epic("Эпик 1", "Детальный план переезда");
        manager.createEpic(epic1); manager.getEpicById(epic1.getId());

        Subtask subtask1 = new Subtask("Подзадача 1", "Собрать коробки", epic1.getId());
        manager.createSubtask(subtask1); manager.getSubtaskById(subtask1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Упаковать кошку", epic1.getId());
        manager.createSubtask(subtask2); manager.getSubtaskById(subtask2.getId());

        // Создаем эпик с одной подзадачей
        System.out.println("\nСоздание эпика с одной подзадачей...");
        Epic epic2 = new Epic("Эпик 2", "Тут должна была быть задача переезда!");
        manager.createEpic(epic2); manager.getEpicById(epic2.getId());
        Subtask subtask3 = new Subtask("Подзадача 3", "Сказать слова прощания", epic2.getId());
        manager.createSubtask(subtask3); manager.getSubtaskById(subtask3.getId());

        // Печатаем списки задач
        System.out.println("\nСписок всех задач:");
        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());

        // Обновляем статусы
        System.out.println("\nОбновление статусов:");
        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);

        System.out.println("Статус эпика 1: " + epic1.getStatus());

        //
        System.out.println("\nУдалим лишние задачи...");
        manager.deleteTaskById(task1.getId());
        manager.updateTask(task1);
        manager.deleteEpicById(epic2.getId());
        manager.updateEpic(epic2);
        manager.deleteSubtaskById(subtask1.getId());
        manager.updateSubtask(subtask1);
        //

        /*
        System.out.println("\nИтоговый список задач:");
        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println(" ↳ Подзадачи эпика: " + manager.getEpicSubtasks(epic1.getId()));
        System.out.println("Все подзадачи: " + manager.getAllSubtasks());
        */
        System.out.println("\nВсё готово к переезду! =)");

        // Удаляем все задачи, подзадачи и эпики
        /*
        manager.deleteAllTasks();
        manager.deleteAllSubtasks();
        manager.deleteAllEpics();
        System.out.println("\nА если мы передумаем переезжать? Наши планы будут такие:");
        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());
        */

        // Тестируем историю просмотров
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        int i = 1;
        System.out.println("\nЗадачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(i + ". " + task);
            i++;
        }
        i = 1;
        System.out.println("\n\t↳ Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(i + ". " + epic);
            i++;

            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        i = 1;
        System.out.println("\n\t\t↳ Все подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(i + ". " + subtask);
            i++;
        }

        i = 1;
        System.out.println("\n\n\t\t\t\t\t\t\tИстория:");
        for (Task task : manager.getHistory()) {
            System.out.println(i + ". " + task);
            i++;
        }
    }
}
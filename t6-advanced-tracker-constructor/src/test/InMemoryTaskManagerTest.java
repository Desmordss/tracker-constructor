package test;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    void createTaskTest() {
        Task task = new Task("Задача 1", "Мы всей семьёй переедем в другой город!");
        taskManager.createTask(task);

        Task savedTask = taskManager.getTaskById(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void shouldLimitHistoryTo10Tasks() {
        for (int i = 0; i < 15; i++) {
            Task task = new Task("Задача " + i, "Описание: " + i);
            taskManager.createTask(task);
            taskManager.getTaskById(task.getId());
        }

        List<Task> history = taskManager.getHistory();
        assertEquals(10, history.size(), "История должна содержать максимум 10 задач");
    }

    @Test
    void historyManagerTest() {
        Task task = new Task("Тестовое задание", "Погладить собаку");
        taskManager.createTask(task);

        // Добавляем в историю через просмотр
        taskManager.getTaskById(task.getId());

        List<Task> history = taskManager.getHistory();
        assertNotNull(history, "История не должна быть пустой (null)");
        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task, history.get(0), "Задача в истории не совпадает с добавленной");
    }

    @Test
    void updateTaskTest() {
        Task task = new Task("Задача 3", "Выбрать новый дом на сайте объявлений");
        taskManager.createTask(task);

        task.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);

        Task updatedTask = taskManager.getTaskById(task.getId());
        assertNotNull(updatedTask, "Обновленная задача не найдена");
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus(), "Статус задачи не обновился");
    }

    @Test
    void deleteTaskTest() {
        Task task = new Task("Задача 4", "Подготовиться к переезду");
        taskManager.createTask(task);

        taskManager.deleteTaskById(task.getId());

        assertNull(taskManager.getTaskById(task.getId()), "Задача не была удалена");
        assertTrue(taskManager.getAllTasks().isEmpty(), "Список задач должен быть пустым");
    }


    @Test
    void shouldAddAndFindDifferentTaskTypes() {
        Task task = new Task("Задача", "Тест описание");
        Epic epic = new Epic("Подзадача", "Тест описание");

        taskManager.createTask(task);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача", "Тест описание", epic.getId());
        taskManager.createSubtask(subtask);

        assertEquals(task, taskManager.getTaskById(task.getId()));
        assertEquals(epic, taskManager.getEpicById(epic.getId()));
        assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()));
    }

    @Test
    void shouldHandleCustomAndGeneratedIds() {
        // Создаем задачу со сгенерированным id
        Task task1 = new Task("Задача 1", "Тест описание");
        taskManager.createTask(task1);
        int generatedId = task1.getId();

        // Создаем вторую задачу
        Task task2 = new Task("Задача 2", "Тест описание");
        taskManager.createTask(task2);

        // Проверяем, что задачи получили разные id
        assertNotNull(taskManager.getTaskById(generatedId),
                "Задача с сгенерированным id должна найдена");
        assertNotNull(taskManager.getTaskById(task2.getId()),
                "Задача с вторым id найдена");
        assertNotEquals(task1.getId(), task2.getId(),
                "Задачи должны иметь разные id");
    }

    @Test
    void shouldPreserveTaskDataWhenAdding() {
        Task originalTask = new Task("Задача 1", "Тест описание");
        originalTask.setStatus(TaskStatus.IN_PROGRESS);

        taskManager.createTask(originalTask);
        Task retrievedTask = taskManager.getTaskById(originalTask.getId());

        assertEquals(originalTask.getName(), retrievedTask.getName());
        assertEquals(originalTask.getStatus(), retrievedTask.getStatus());
    }

    @Test
    void shouldPreserveHistoryData() {
        // Создаем задачу
        Task task = new Task("Задача", "Очень важная задача");
        taskManager.createTask(task);

        // Получаем задачу первый раз (добавится в историю)
        Task firstView = taskManager.getTaskById(task.getId());

        // Создаем копию для сравнения
        Task expectedTask = new Task(firstView.getName(), firstView.getDescription());
        expectedTask.setId(firstView.getId());
        expectedTask.setStatus(firstView.getStatus());

        // Изменяем оригинальную задачу
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);

        // Получаем задачу из истории
        Task historyTask = taskManager.getHistory().get(0);

        // Сравниваем с сохраненной копией
        assertEquals(expectedTask.getStatus(), historyTask.getStatus(),
                "В истории должна сохраниться начальная версия задачи");
        assertEquals(expectedTask.getName(), historyTask.getName(),
                "Имя задачи в истории не должно измениться");
        assertEquals(expectedTask.getDescription(), historyTask.getDescription(),
                "Описание задачи в истории не должно измениться");
    }
}
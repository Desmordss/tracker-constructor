package test;

import history.HistoryManager;
import history.InMemoryHistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Task;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void addTaskToHistoryTest() {
        Task task = new Task("Тест", "Описание теста");
        task.setId(1);

        historyManager.add(task);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не должна быть null");
        assertEquals(1, history.size(), "Размер истории должен быть 1");
        assertEquals(task, history.get(0), "Задача в истории не совпадает с добавленной");
    }

    @Test
    void addNullTaskTest() {
        historyManager.add(null);
        List<Task> history = historyManager.getHistory();

        assertTrue(history.isEmpty(), "История должна быть пустой при добавлении null");
    }

    @Test
    void historyLimitTest() {
        // Добавляем 11 задач (больше лимита в 10)
        for (int i = 1; i <= 11; i++) {
            Task task = new Task("Задача " + i, "Описание " + i);
            task.setId(i);
            historyManager.add(task);
        }

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size(), "Размер истории не должен превышать 10");
        assertEquals(2, history.get(0).getId(), "Первая задача должна быть удалена");
    }

    @Test
    void getEmptyHistoryTest() {
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не должна быть пустой (null)");
        assertTrue(history.isEmpty(), "История должна быть пустой");
    }

    @Test
    void historyShouldReturnCopy() {
        Task task = new Task("Тест", "Описание");
        task.setId(1);
        historyManager.add(task);

        List<Task> history1 = historyManager.getHistory();
        List<Task> history2 = historyManager.getHistory();

        assertNotSame(history1, history2, "Метод должен возвращать копию истории");
    }

    @Test
    void shouldAddTasksToHistory() {
        TaskManager manager = Managers.getDefault();

        // Создаем задачи
        Task task = new Task("Задача 1", "Собрать вещи");
        Epic epic = new Epic("Эпик 1", "Детальный план переезда");
        manager.createTask(task);
        manager.createEpic(epic);

        // Просматриваем задачи
        manager.getTaskById(task.getId());
        manager.getEpicById(epic.getId());

        // Проверяем историю
        List<Task> history = manager.getHistory();
        assertFalse(history.isEmpty(), "История не должна быть пустой");
        assertEquals(2, history.size(), "В истории должно быть 2 задачи");
        assertEquals(task, history.get(0), "Первой должна быть обычная задача");
        assertEquals(epic, history.get(1), "Второй должен быть эпик");
    }
}
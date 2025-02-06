package test;

import history.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {

    @Test
    void getDefaultTest() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Менеджер задач не создался");
    }

    @Test
    void getDefaultHistoryTest() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер истории не создался");
    }

    @Test
    void shouldReturnDifferentTaskManagerInstances() {
        TaskManager taskManager1 = Managers.getDefault();
        TaskManager taskManager2 = Managers.getDefault();

        assertNotSame(taskManager1, taskManager2, "Менеджеры задач должны быть разными объектами");
    }

    @Test
    void shouldReturnDifferentHistoryManagerInstances() {
        HistoryManager historyManager1 = Managers.getDefaultHistory();
        HistoryManager historyManager2 = Managers.getDefaultHistory();

        assertNotSame(historyManager1, historyManager2, "Менеджеры истории должны быть разными объектами");
    }
}
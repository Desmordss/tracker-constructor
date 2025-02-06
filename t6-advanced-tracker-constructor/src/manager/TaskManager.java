package manager;

import task.Task;
import task.Epic;
import task.Subtask;
import java.util.List;

public interface TaskManager {
    int generateId();

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    void createTask(task.Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void updateTask(task.Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void updateEpicStatus(Epic epic);

    void deleteSubtaskById(int id);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    List<Subtask> getEpicSubtasks(int epicId);

    List<Task> getHistory();
}

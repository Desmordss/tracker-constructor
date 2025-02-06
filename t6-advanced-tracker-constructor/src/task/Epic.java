package task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    protected List<Integer> subtaskIds;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskIds = new ArrayList<>();
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(Integer subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void removeSubtaskId(Integer subtaskId) {
        subtaskIds.remove(subtaskId);
    }

    // Переопределим метод, и для удобства - названия переменных будем писать на английском
    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + " " +
                ", description='" + description + " " +
                ", status=" + status +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}
package ru.practicum.kanban.service;

import ru.practicum.kanban.models.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int HISTORY_SIZE = 10;

    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            Task snapshotTask = new Task(task.getName(), task.getDescription());
            snapshotTask.setId(task.getId());
            snapshotTask.setTaskStatus(task.getTaskStatus());
            history.addLast(snapshotTask);
            checkHistory();
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryHistoryManager that = (InMemoryHistoryManager) o;
        return Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(history);
    }

    private void checkHistory() {
        if (history.size() > HISTORY_SIZE) {
            history.poll();
        }
    }

}

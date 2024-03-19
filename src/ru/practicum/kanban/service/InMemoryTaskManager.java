package ru.practicum.kanban.service;

import ru.practicum.kanban.models.enums.TaskStatus;
import ru.practicum.kanban.models.Epic;
import ru.practicum.kanban.models.Subtask;
import ru.practicum.kanban.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InMemoryTaskManager implements TaskManager {

    private static final int NOT_INITIALIZED_ID = 0;
    private static int taskCount = 0;

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        addToHistory(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        addToHistory(subtask);
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        addToHistory(epic);
        return epic;
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
            int epicId = entry.getValue().getEpic().getId();
            getEpic(epicId).getSubtasks().remove(entry.getValue());
            updateEpicStatus(getEpic(epicId));
        }
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            for (Subtask subtask : entry.getValue().getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.clear();
    }

    @Override
    public void createTask(Task task, TaskStatus taskStatus) {
        setTaskId(task);
        task.setTaskStatus(taskStatus);
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubtask(Subtask subtask, TaskStatus taskStatus) {
        setTaskId(subtask);
        subtask.setTaskStatus(taskStatus);
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void createEpic(Epic epic) {
        setTaskId(epic);
        updateEpicStatus(epic);
        epics.put(epic.getId(), epic);
        for (Subtask subtask : epic.getSubtasks()) {
            createSubtask(subtask, subtask.getTaskStatus());
        }
    }

    @Override
    public void updateTask(Task task) {
        createTask(task, task.getTaskStatus());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        createSubtask(subtask, subtask.getTaskStatus());
        updateEpicStatus(subtask.getEpic());
    }

    @Override
    public void updateEpic(Epic epic) {
        createEpic(epic);
    }

    @Override
    public Task removeTask(int id) {
        historyManager.remove(id);
        return tasks.remove(id);
    }

    @Override
    public Subtask removeSubtask(int id) {
        historyManager.remove(id);
        Subtask subtask = subtasks.get(id);
        epics.get(subtask.getEpic().getId()).getSubtasks().remove(subtask);
        updateEpic(epics.get(subtask.getEpic().getId()));
        return subtasks.remove(id);
    }

    @Override
    public Epic removeEpic(int id) {
        for (Subtask subtask : epics.get(id).getSubtasks()) {
            historyManager.remove(subtask.getId());
            subtasks.remove(subtask.getId());
        }
        historyManager.remove(id);
        return epics.remove(id);
    }

    @Override
    public List<Subtask> getEpicsSubtasks(int epicsId) {
        return epics.get(epicsId).getSubtasks();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager that = (InMemoryTaskManager) o;
        return Objects.equals(tasks, that.tasks) && Objects.equals(subtasks, that.subtasks) && Objects.equals(epics, that.epics) && Objects.equals(historyManager, that.historyManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, subtasks, epics, historyManager);
    }

    private void updateEpicStatus(Epic epic) {
        if (!epic.getSubtasks().isEmpty()) {
            boolean isNew = true;
            boolean isDone = true;
            for (Subtask subtask : epic.getSubtasks()) {
                if (subtask.getTaskStatus().equals(TaskStatus.NEW)) {
                    isDone = false;
                } else if (subtask.getTaskStatus().equals(TaskStatus.DONE)) {
                    isNew = false;
                } else {
                    isDone = false;
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                epic.setTaskStatus(TaskStatus.NEW);
            } else if (isDone) {
                epic.setTaskStatus(TaskStatus.DONE);
            } else {
                epic.setTaskStatus(TaskStatus.IN_PROGRESS);
            }
        } else {
            epic.setTaskStatus(TaskStatus.IN_PROGRESS);
        }
    }

    private void addToHistory(Task task) {
        historyManager.add(task);
    }

    private static void setTaskId(Task task) {
        if (task.getId() == NOT_INITIALIZED_ID) {
            task.setId(++taskCount);
        }
    }

}

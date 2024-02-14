package ru.practicum.kanban.service;

import ru.practicum.kanban.enums.TaskStatus;
import ru.practicum.kanban.models.Epic;
import ru.practicum.kanban.models.Subtask;
import ru.practicum.kanban.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaskManager {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
            int epicId = entry.getValue().getEpic().getId();
            getEpic(epicId).getSubtasks().remove(entry.getValue());
            updateEpicStatus(getEpic(epicId));
        }
        subtasks.clear();
    }

    public void removeAllEpics() {
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            for (Subtask subtask : entry.getValue().getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.clear();
    }

    public void createTask(Task task, TaskStatus taskStatus) {
        task.setTaskStatus(taskStatus);
        tasks.put(task.getId(), task);
    }

    public void createSubtask(Subtask subtask, TaskStatus taskStatus) {
        subtask.setTaskStatus(taskStatus);
        subtasks.put(subtask.getId(), subtask);
    }

    public void createEpic(Epic epic) {
        updateEpicStatus(epic);
        epics.put(epic.getId(), epic);
        for (Subtask subtask : epic.getSubtasks()) {
            createSubtask(subtask, subtask.getTaskStatus());
        }
    }

    public void updateTask(Task task) {
        createTask(task, task.getTaskStatus());
    }

    public void updateSubtask(Subtask subtask) {
        createSubtask(subtask, subtask.getTaskStatus());
        updateEpicStatus(subtask.getEpic());
    }

    public void updateEpic(Epic epic) {
        createEpic(epic);
    }

    public Task removeTask(int id) {
        return tasks.remove(id);
    }

    public Subtask removeSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        epics.get(subtask.getEpic().getId()).getSubtasks().remove(subtask);
        updateEpic(epics.get(subtask.getEpic().getId()));
        return subtasks.remove(id);
    }

    public Epic removeEpic(int id) {
        for (Subtask subtask : epics.get(id).getSubtasks()) {
            subtasks.remove(subtask.getId());
        }
        return epics.remove(id);
    }

    public Set<Subtask> getEpicsSubtasks(int epicsId) {
        return epics.get(epicsId).getSubtasks();
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

}

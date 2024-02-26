package ru.practicum.kanban.service;

import ru.practicum.kanban.models.Epic;
import ru.practicum.kanban.models.Subtask;
import ru.practicum.kanban.models.Task;
import ru.practicum.kanban.models.enums.TaskStatus;

import java.util.List;

public interface TaskManager {

    List<Task> getAllTasks();

    List<Subtask> getAllSubtasks();

    List<Epic> getAllEpics();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    void removeAllTasks();

    void removeAllSubtasks();

    void removeAllEpics();

    void createTask(Task task, TaskStatus taskStatus);

    void createSubtask(Subtask subtask, TaskStatus taskStatus);

    void createEpic(Epic epic);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    Task removeTask(int id);

    Subtask removeSubtask(int id);

    Epic removeEpic(int id);

    List<Subtask> getEpicsSubtasks(int epicsId);

    List<Task> getHistory();

}

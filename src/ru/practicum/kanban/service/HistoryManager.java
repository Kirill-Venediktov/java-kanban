package ru.practicum.kanban.service;

import ru.practicum.kanban.models.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();

}

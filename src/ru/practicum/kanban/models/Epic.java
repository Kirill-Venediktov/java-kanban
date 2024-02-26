package ru.practicum.kanban.models;

import java.util.List;

public class Epic extends Task {

    private final List<Subtask> subtasks;

    public Epic(String name, String description, List<Subtask> subtasks) {
        super(name, description);
        this.subtasks = subtasks;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

}

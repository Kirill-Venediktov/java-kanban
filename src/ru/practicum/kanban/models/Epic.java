package ru.practicum.kanban.models;

import java.util.Set;

public class Epic extends Task {

    private final Set<Subtask> subtasks;

    public Epic(String name, String description, Set<Subtask> subtasks) {
        super(name, description);
        this.subtasks = subtasks;
    }

    public Set<Subtask> getSubtasks() {
        return subtasks;
    }

}

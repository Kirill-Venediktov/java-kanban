package ru.practicum.kanban.models;

import ru.practicum.kanban.enums.TaskStatus;

import java.util.Objects;

public class Task {

    private static int count = 0;
    private final int id;
    private String name;
    private String description;
    private TaskStatus taskStatus;

    public Task(String name, String description) {
        id = ++count;
        this.name = name;
        this.description = description;
        taskStatus = TaskStatus.NEW;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", taskStatus=" + taskStatus +
            '}';
    }

}
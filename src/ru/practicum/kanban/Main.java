package ru.practicum.kanban;

import ru.practicum.kanban.models.enums.TaskStatus;
import ru.practicum.kanban.models.Epic;
import ru.practicum.kanban.models.Subtask;
import ru.practicum.kanban.models.Task;
import ru.practicum.kanban.service.Managers;
import ru.practicum.kanban.service.TaskManager;

import java.util.ArrayList;

public class Main {

    private static final String HISTORY_STRING = "history: %s %n";
    private static final String HISTORY_AFTER_REMOVING_STRING = "history after removing: %s %n";

    public static void main(String[] args) {
        Task task1 = new Task("task1", "task one");
        Task task2 = new Task("task2", "task two");

        Epic epic1 = new Epic("epic1", "epic one", new ArrayList<>());
        Epic epic2 = new Epic("epic2", "epic two", new ArrayList<>());

        Subtask sub1 = new Subtask("sub1", "sub one", epic1);
        Subtask sub2 = new Subtask("sub2", "sub two", epic1);
        Subtask sub3 = new Subtask("sub3", "sub three", epic2);

        epic1.getSubtasks().add(sub1);
        epic1.getSubtasks().add(sub2);
        epic1.getSubtasks().add(sub3);

        TaskManager inMemoryTaskManager = Managers.getDefault();

        inMemoryTaskManager.createTask(task1, TaskStatus.NEW);
        inMemoryTaskManager.createTask(task2, TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createEpic(epic2);

        System.out.printf(HISTORY_STRING, inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getTask(1);
        System.out.printf(HISTORY_STRING, inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(3);
        System.out.printf(HISTORY_STRING, inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubtask(5);
        System.out.printf(HISTORY_STRING, inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(7);
        System.out.printf(HISTORY_STRING, inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubtask(5);
        System.out.printf(HISTORY_STRING, inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getTask(1);
        System.out.printf(HISTORY_STRING, inMemoryTaskManager.getHistory());

        inMemoryTaskManager.removeTask(1);
        System.out.printf(HISTORY_AFTER_REMOVING_STRING, inMemoryTaskManager.getHistory());

        inMemoryTaskManager.removeEpic(3);
        System.out.printf(HISTORY_AFTER_REMOVING_STRING, inMemoryTaskManager.getHistory());

        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println(inMemoryTaskManager.getAllSubtasks());
    }

}

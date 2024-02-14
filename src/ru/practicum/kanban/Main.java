package ru.practicum.kanban;

import ru.practicum.kanban.enums.TaskStatus;
import ru.practicum.kanban.models.Epic;
import ru.practicum.kanban.models.Subtask;
import ru.practicum.kanban.models.Task;
import ru.practicum.kanban.service.TaskManager;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task("task1", "task one");
        Task task2 = new Task("task2", "task two");

        Epic epic1 = new Epic("epic1", "epic one", new HashSet<>());
        Epic epic2 = new Epic("epic2", "epic two", new HashSet<>());

        Subtask sub1 = new Subtask("sub1", "sub one", epic1);
        Subtask sub2 = new Subtask("sub2", "sub two", epic1);
        Subtask sub3 = new Subtask("sub3", "sub three", epic2);

        epic1.getSubtasks().add(sub1);
        epic1.getSubtasks().add(sub2);
        epic2.getSubtasks().add(sub3);

        TaskManager taskManager = new TaskManager();

        taskManager.createTask(task1, TaskStatus.NEW);
        taskManager.createTask(task2, TaskStatus.IN_PROGRESS);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println("!");
        sub2.setTaskStatus(TaskStatus.DONE);
        sub1.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(sub2);
        taskManager.updateSubtask(sub1);
        task1.setTaskStatus(TaskStatus.DONE);
        taskManager.updateTask(task1);
        System.out.println(taskManager.getEpicsSubtasks(epic1.getId()));
        System.out.println("!");
        taskManager.removeAllEpics();
        System.out.println("!");




    }
}

package ru.practicum.kanban;

import ru.practicum.kanban.models.enums.TaskStatus;
import ru.practicum.kanban.models.Epic;
import ru.practicum.kanban.models.Subtask;
import ru.practicum.kanban.models.Task;
import ru.practicum.kanban.service.Managers;
import ru.practicum.kanban.service.TaskManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task("task1", "task one");
        Task task2 = new Task("task2", "task two");

        Epic epic1 = new Epic("epic1", "epic one", new ArrayList<>());
        Epic epic2 = new Epic("epic2", "epic two", new ArrayList<>());

        Subtask sub1 = new Subtask("sub1", "sub one", epic1);
        Subtask sub2 = new Subtask("sub2", "sub two", epic1);
        Subtask sub3 = new Subtask("sub3", "sub three", epic2);

        epic1.getSubtasks().add(sub1);
        epic1.getSubtasks().add(sub2);
        epic2.getSubtasks().add(sub3);

        TaskManager inMemoryTaskManager = Managers.getDefault();

        inMemoryTaskManager.createTask(task1, TaskStatus.NEW);
        inMemoryTaskManager.createTask(task2, TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createEpic(epic2);

        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println(inMemoryTaskManager.getAllSubtasks());
        System.out.println("!");
        System.out.println("history:" + inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getEpic(3);
        inMemoryTaskManager.getSubtask(5);
        System.out.println("history:" + inMemoryTaskManager.getHistory());
        System.out.println("!");

        sub2.setTaskStatus(TaskStatus.DONE);
        sub1.setTaskStatus(TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(sub2);
        inMemoryTaskManager.updateSubtask(sub1);
        task1.setTaskStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateTask(task1);
        System.out.println(inMemoryTaskManager.getEpicsSubtasks(epic1.getId()));
        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println(inMemoryTaskManager.getAllSubtasks());
        System.out.println("!");
        inMemoryTaskManager.removeAllEpics();
        System.out.println("!");





    }
}

package ru.practicum.kanban.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.kanban.models.Epic;
import ru.practicum.kanban.models.Subtask;
import ru.practicum.kanban.models.Task;
import ru.practicum.kanban.models.enums.TaskStatus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static final String TASK_NAME = "name";
    private static final String TASK_DESCRIPTION = "description";
    private static final int NOT_CORRECT_ID = 0;

    private InMemoryTaskManager manager;

    @BeforeEach
    private void managerInit() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void createTaskShouldAddTaskToMapWhenInputIsTaskAndTaskStatus() throws NoSuchFieldException, IllegalAccessException {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createTask(task, status);
        Map<Integer, Task> taskMap = new HashMap<>();
        task.setTaskStatus(status);
        taskMap.put(task.getId(), task);
        Field tasks = manager.getClass().getDeclaredField("tasks");
        tasks.setAccessible(true);
        Map<Integer, Task> taskMapFromManager = (Map<Integer, Task>) tasks.get(manager);
        assertEquals(taskMap, taskMapFromManager);
    }

    @Test
    void createSubtaskShouldAddSubtaskTaskToMapWhenInputIsSubtaskAndTaskStatus() throws NoSuchFieldException, IllegalAccessException {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        Subtask subtask = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createSubtask(subtask, status);
        subtask.setTaskStatus(status);
        Map<Integer, Subtask> subtaskMap = new HashMap<>();
        subtaskMap.put(subtask.getId(), subtask);
        Field subtasks = manager.getClass().getDeclaredField("subtasks");
        subtasks.setAccessible(true);
        Map<Integer, Subtask> subtaskMapFromManager = (Map<Integer, Subtask>) subtasks.get(manager);
        assertEquals(subtaskMap, subtaskMapFromManager);
    }

    @Test
    void createEpicShouldAddEpicToMapWhenInputIsEpic() throws NoSuchFieldException, IllegalAccessException {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        manager.createEpic(epic);
        Map<Integer, Epic> epicMap = new HashMap<>();
        epicMap.put(epic.getId(), epic);
        Field epics = manager.getClass().getDeclaredField("epics");
        epics.setAccessible(true);
        Map<Integer, Epic> epicMapFromManager = (Map<Integer, Epic>) epics.get(manager);
        assertEquals(epicMap, epicMapFromManager);
    }

    @Test
    void getTaskShouldGetTaskWhenInputIsTaskId() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createTask(task, status);
        assertEquals(task, manager.getTask(task.getId()));
    }

    @Test
    void getTaskShouldGetNullWhenInputIsNotCorrectId() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createTask(task, status);
        assertNull(manager.getTask(NOT_CORRECT_ID));
    }

    @Test
    void getEpicShouldGetEpicWhenInputIsEpicId() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        manager.createEpic(epic);
        assertEquals(epic, manager.getEpic(epic.getId()));
    }

    @Test
    void getEpicShouldGetNullWhenInputIsNotCorrectId() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        manager.createEpic(epic);
        assertNull(manager.getEpic(NOT_CORRECT_ID));
    }

    @Test
    void getSubtaskShouldGetSubtaskWhenInputIsSubtaskId() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        Subtask subtask = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        manager.createSubtask(subtask, TaskStatus.IN_PROGRESS);
        assertEquals(subtask, manager.getSubtask(subtask.getId()));
    }

    @Test
    void getSubtaskShouldGetNullWhenInputIsNotCorrectId() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        Subtask subtask = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        manager.createSubtask(subtask, TaskStatus.IN_PROGRESS);
        assertNull(manager.getSubtask(NOT_CORRECT_ID));
    }

    @Test
    void getAllTasksShouldGetListOfTasks() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createTask(task, status);
        List<Task> taskList = new ArrayList<>(Collections.singletonList(task));
        assertEquals(taskList, manager.getAllTasks());
    }

    @Test
    void getAllSubtasksShouldGetListOfSubtasks() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        Subtask subtask = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createSubtask(subtask, status);
        List<Subtask> subtaskList = new ArrayList<>(Collections.singletonList(subtask));
        assertEquals(subtaskList, manager.getAllSubtasks());
    }

    @Test
    void getAllEpicsShouldGetListOfEpics() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        manager.createEpic(epic);
        List<Epic> epicList = new ArrayList<>(Collections.singletonList(epic));
        assertEquals(epicList, manager.getAllEpics());
    }

    @Test
    void removeAllTasksShouldGetEmptyList() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createTask(task, status);
        manager.removeAllTasks();
        assertEquals(new ArrayList<>(), manager.getAllTasks());
    }

    @Test
    void removeAllSubtasksShouldGetEmptyList() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        Subtask subtask = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createSubtask(subtask, status);
        manager.createEpic(epic);
        manager.removeAllSubtasks();
        assertEquals(new ArrayList<>(), manager.getAllSubtasks());
    }

    @Test
    void removeAllEpicsShouldGetEmptyList() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        manager.createEpic(epic);
        manager.removeAllEpics();
        assertEquals(new ArrayList<>(), manager.getAllEpics());
    }

    @Test
    void updateTaskShouldUpdateTaskInMapWhenInputIsTask() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createTask(task, status);
        task.setTaskStatus(TaskStatus.DONE);
        manager.updateTask(task);
        assertEquals(task.getTaskStatus(), manager.getTask(task.getId()).getTaskStatus());
    }

    @Test
    void updateSubtaskShouldUpdateSubtaskWhenInputIsSubtask() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        Subtask subtask = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        manager.createSubtask(subtask, TaskStatus.IN_PROGRESS);
        subtask.setTaskStatus(TaskStatus.DONE);
        manager.updateSubtask(subtask);
        assertEquals(subtask.getTaskStatus(), manager.getSubtask(subtask.getId()).getTaskStatus());
    }

    @Test
    void updateEpicShouldUpdateEpicWhenInputIsEpic() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        manager.createEpic(epic);
        epic.setDescription("CHANGED");
        manager.updateEpic(epic);
        assertEquals(epic.getDescription(), manager.getEpic(epic.getId()).getDescription());
    }

    @Test
    void removeTaskShouldReturnNullWhenInputIsTaskId() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createTask(task, status);
        manager.removeTask(task.getId());
        assertNull(manager.getTask(task.getId()));
    }

    @Test
    void removeEpicShouldReturnNullWhenInputIsEpicId() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        manager.createEpic(epic);
        manager.removeEpic(epic.getId());
        assertNull(manager.getEpic(epic.getId()));
    }

    @Test
    void removeSubtaskShouldReturnNullWhenInputIsNotCorrectId() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        Subtask subtask = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        manager.createSubtask(subtask, TaskStatus.IN_PROGRESS);
        manager.createEpic(epic);
        manager.removeSubtask(subtask.getId());
        assertNull(manager.getSubtask(subtask.getId()));
    }

    @Test
    void getEpicsSubtasksShouldGetListOfSubtasksWhenInputIsEpicId() {
        Epic epic = new Epic(TASK_NAME, TASK_DESCRIPTION, new ArrayList<>());
        Subtask subtask1 = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        Subtask subtask2 = new Subtask(TASK_NAME, TASK_DESCRIPTION, epic);
        epic.getSubtasks().add(subtask1);
        epic.getSubtasks().add(subtask2);
        manager.createEpic(epic);
        List<Subtask> subtasks = new ArrayList<>(Arrays.asList(subtask1, subtask2));
        assertEquals(subtasks, manager.getEpicsSubtasks(epic.getId()));
    }

    @Test
    void getHistoryShouldGetHistoryList() {
        Task task1 = new Task(TASK_NAME, TASK_DESCRIPTION);
        Task task2 = new Task(TASK_NAME, TASK_DESCRIPTION);
        TaskStatus status = TaskStatus.IN_PROGRESS;
        manager.createTask(task1, status);
        manager.createTask(task2, status);
        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        task1.setTaskStatus(TaskStatus.DONE);
        manager.updateTask(task1);
        manager.getTask(task1.getId());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task1);
        assertNotEquals(tasks, manager.getHistory());
    }

}

package ru.practicum.kanban.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private static final String TASK_NAME = "name";
    private static final String TASK_DESCRIPTION = "description";

    @Test
    void tasksShouldEqualsWhenIdsIsEquals() {

        Task task1 = new Task(TASK_NAME, TASK_DESCRIPTION);
        Task task2 = new Task(TASK_NAME, TASK_DESCRIPTION);
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2);
    }

}

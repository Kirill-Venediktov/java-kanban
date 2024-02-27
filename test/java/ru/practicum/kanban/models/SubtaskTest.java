package ru.practicum.kanban.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtaskTest {

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Test
    void subtasksShouldEqualsWhenIdsIsEquals() {
        Epic epic = new Epic(NAME, DESCRIPTION, new ArrayList<>());
        Subtask subtask1 = new Subtask(NAME, DESCRIPTION, epic);
        Subtask subtask2 = new Subtask(NAME, DESCRIPTION, epic);
        subtask1.setId(1);
        subtask2.setId(1);
        assertEquals(subtask1, subtask2);
    }

}

package ru.practicum.kanban.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    private static final String EPIC_NAME = "name";
    private static final String EPIC_DESCRIPTION = "description";

    @Test
    void epicsShouldEquals_whenIdsIsEquals() {
        Epic epic1 = new Epic(EPIC_NAME, EPIC_DESCRIPTION, new ArrayList<>());
        Epic epic2 = new Epic(EPIC_NAME, EPIC_DESCRIPTION, new ArrayList<>());
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2);
    }

}

package com.badlogic.UniSim2.headless;

import com.badlogic.UniSim2.events.ConditionalThoughtEvent;
import com.badlogic.UniSim2.events.Event;
import com.badlogic.UniSim2.events.EventManager;
import com.badlogic.UniSim2.events.SimpleThoughtEvent;
import com.badlogic.UniSim2.satisfaction.Satisfaction;
import com.badlogic.UniSim2.satisfaction.Thought;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTests extends AbstractHeadlessGdxTest {
    EventManager eventManager;
    Satisfaction satisfaction;
    boolean condition;

    @BeforeEach
    public void setup() {
        //super.setup();
        eventManager = new EventManager();
        eventManager.getEvents().clear();
        satisfaction = new Satisfaction();
    }

    @Test
    public void testSimpleThoughEvent() {
        Thought thought = new Thought("Test", "", 0);

        Event event = new SimpleThoughtEvent("Test", 10, thought, 20);

        eventManager.getEvents().add(event);

        eventManager.updateEvents(0);

        assertNull(satisfaction.getThought("Test"), "Event triggered early");

        eventManager.updateEvents(10);

        assertNotNull(satisfaction.getThought("Test"), "Event didn't trigger on time");

        eventManager.updateEvents(30);

        assertNull(satisfaction.getThought("Test"), "Event didn't end");
        assertEquals(0, eventManager.getEvents().size(), "Event wasn't removed");
    }

    @Test
    public void testConditionalThoughEvent() {
        condition = false;

        Thought satisfiedthought = new Thought("Test", "", -1);
        Thought unsatisfiedThought = new Thought("Test", "", 1);

        Event event = new ConditionalThoughtEvent("Test", 10, satisfiedthought, unsatisfiedThought, () -> condition, 20);

        eventManager.getEvents().add(event);

        eventManager.updateEvents(0);

        assertNull(satisfaction.getThought("Test"), "Event triggered early");

        eventManager.updateEvents(10);

        assertNotNull(satisfaction.getThought("Test"), "Event didn't trigger on time");

        assertEquals(-1, satisfaction.getThought("Test").getModification(), "Satisfied thought triggered when unsatisfied");

        condition = true;

        eventManager.updateEvents(10);

        assertEquals(1, satisfaction.getThought("Test").getModification(), "Unsatisfied thought triggered when satisfied");

        eventManager.updateEvents(30);

        assertNull(satisfaction.getThought("Test"), "Event didn't end");
        assertEquals(0, eventManager.getEvents().size(), "Event wasn't removed");
    }
}

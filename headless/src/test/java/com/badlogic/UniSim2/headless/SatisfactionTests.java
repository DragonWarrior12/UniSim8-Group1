package com.badlogic.UniSim2.headless;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.satisfaction.Satisfaction;
import com.badlogic.UniSim2.satisfaction.Thought;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SatisfactionTests {
    Satisfaction bar;

    @BeforeEach
    void setup() {
        bar = new Satisfaction();
    }

    @Test
    public void testSetGetThought() {
        Thought thought = new Thought("Test", "", 10);

        bar.setThought("test", thought);

        assertEquals(thought, bar.getThought("test"));
        assertEquals(Consts.SATISFACTION_BAR_BASE_VALUE + 10, bar.getTarget(), 0.0001);
    }

    @Test
    public void testRemoveThought() {
        Thought thought = new Thought("Test", "", 10);

        bar.setThought("test", thought);

        bar.removeThought("test");

        assertNull(bar.getThought("test"));
    }

    @Test
    public void testMinTarget() {
        Thought thought = new Thought("Test", "", -1_000);

        bar.setThought("test", thought);

        assertEquals(0, bar.getTarget(), 0.0001);
    }

    @Test
    public void testMaxTarget() {
        Thought thought = new Thought("Test", "", 1_000);

        bar.setThought("test", thought);

        assertEquals(100, bar.getTarget(), 0.0001);
    }

    @Test
    public void testManyThoughtTarget() {
        int[] modifications = {10, -5, 3, 7, -10, 4};

        for (int mod : modifications) {
            Thought thought = new Thought("%d".formatted(mod), "", mod);
            bar.setThought("%d".formatted(mod), thought);
        }

        assertEquals(Consts.SATISFACTION_BAR_BASE_VALUE + Arrays.stream(modifications).sum(), bar.getTarget(), 0.0001);
    }

    @Test
    public void testUpdateScore() {
        Thought thought = new Thought("Test", "", 1);

        bar.setThought("test", thought);

        bar.updateScore();

        assertEquals(Consts.SATISFACTION_BAR_BASE_VALUE + Consts.SATISFACTION_BAR_SPEED, bar.getScore(), 0.0001);

        bar.updateScore();

        assertEquals(Consts.SATISFACTION_BAR_BASE_VALUE + 2 * Consts.SATISFACTION_BAR_SPEED, bar.getScore(), 0.0001);

        for (int i = 0; i < 100; i++) {
            bar.updateScore();
        }

        assertEquals(Consts.SATISFACTION_BAR_BASE_VALUE + 1, bar.getScore(), 0.0001);

        thought = new Thought("Test", "", -1);

        bar.setThought("test", thought);

        for (int i = 0; i < 100; i++) {
            bar.updateScore();
        }

        assertEquals(Consts.SATISFACTION_BAR_BASE_VALUE - 1, bar.getScore(), 0.0001);
    }
}

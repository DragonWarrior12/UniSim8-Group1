package com.badlogic.UniSim2.events;

// new class

import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.GUImanager.GameScreen;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.satisfaction.Thought;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Event> events;

    public EventManager() {
        events = new ArrayList<>();
        initializeEvents();
    }

    private void initializeEvents() {
        // Create thoughts for each event
        Thought freshersThought = new Thought(
            "Freshers Week",
            "Students are enjoying Freshers Week",
            20
        );

        Thought christmasThought = new Thought(
            "Christmas Break",
            "Many students are home for Christmas",
            15
        );

        Thought examStressThought = new Thought(
            "Exam Week Stress",
            "Students are stressing about exams and need more study space",
            -25
        );

        Thought examSatisfiedThought = new Thought(
                "Exam Week",
                "Students have plenty of study space for exam prep",
                25
        );

        events.add(new SimpleThoughtEvent("Freshers Week", 2.0f, freshersThought, 30f));
        events.add(new SimpleThoughtEvent("Christmas Break", 60.0f, christmasThought, 80f));
        events.add(new ConditionalThoughtEvent("Exam Week", 0.0f, examStressThought, examSatisfiedThought, () -> BuildingMenu.buildingCounts[2] > 3, 110f));

        for (Building.BuildingTypes type : new Building.BuildingTypes[] {
                Building.BuildingTypes.Course,
                Building.BuildingTypes.Accomodation,
                Building.BuildingTypes.LectureHall,
                Building.BuildingTypes.Recreational}){
            String unsatisfiedDescription = switch (type) {
                case Course -> "Not enough Course buildings";
                case Accomodation -> "Not enough Accomodation";
                case LectureHall -> "Not enough Lecture Halls";
                case Recreational -> "Not enough Recreation buildings";
                default -> "";
            };

            // empty description so it doesn't show up when fulfilled as these events are permanent and there is limited space in the table
            Thought satisfiedThought = new Thought(
                    type.name(),
                    "",
                    10
            );

            Thought unsatisfiedThought = new Thought(
                    type.name(),
                    unsatisfiedDescription,
                    -10
            );

            // require two of each listed building
            Event event = new ConditionalThoughtEvent(type.name(), 0f, unsatisfiedThought, satisfiedThought, () -> BuildingMenu.buildingCounts[type.ordinal()] > 1);
            events.add(event);
        }
    }

    public void updateEvents() {
        for (Event event : getEvents()) {
            event.update(GameScreen.gameScreen.timer.getElapsedTime());
        }

        events.removeIf((event) -> event.isFinished);
    }

    public List<Event> getEvents() {
        return events;
    }
}

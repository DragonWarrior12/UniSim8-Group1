package com.badlogic.UniSim2.events;

// new class

import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.satisfaction.Thought;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Event> events;

    public EventManager() {
        events = new ArrayList<>();
        createEvents();
    }

    private void createEvents() {
        // Scheduled events
        Thought freshersThought = new Thought(
            "Freshers Week",
            "Students are enjoying Freshers Week",
            30
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

        events.add(new SimpleThoughtEvent("Freshers Week", 0.0f, freshersThought, 20f));
        events.add(new SimpleThoughtEvent("Christmas Break", 60.0f, christmasThought, 80f));
        events.add(new ConditionalThoughtEvent("Exam Week", 90.0f, examStressThought, examSatisfiedThought, () -> BuildingMenu.buildingCounts[2] > 3, 110f));

        // Building count requirements implemented as permanent events
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
                    5
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

    public void updateEvents(float time) {
        for (Event event : getEvents()) {
            event.update(time);
        }

        events.removeIf((event) -> event.isFinished);
    }

    public List<Event> getEvents() {
        return events;
    }
}

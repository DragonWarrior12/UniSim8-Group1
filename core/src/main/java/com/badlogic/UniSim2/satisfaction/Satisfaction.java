package com.badlogic.UniSim2.satisfaction;

// new class

import com.badlogic.UniSim2.resources.Consts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

public class Satisfaction {
    private float value;
    private float target;
    private final Map<String, Thought> thoughts;

    public Satisfaction(){
        thoughts = new HashMap<>();
        resetScore();
        calculateTarget();
    }

    private void resetScore(){
        value = Consts.SATISFACTION_BAR_BASE_VALUE;
    }

    public float getScore(){
        return value;
    }

    public float getTarget(){
        return target;
    }

    public void updateScore(){
        float difference = target - value;
        if (abs(difference) < Consts.SATISFACTION_BAR_SPEED) value = target;
        else {
            float direction = (difference < 0) ? -1 : 1;
            value += Consts.SATISFACTION_BAR_SPEED * direction;
        }
    }

    public void setThought(String key, Thought thought){
        thoughts.put(key, thought);
        calculateTarget();
    }

    public Thought getThought(String key){
        return thoughts.get(key);
    }

    public List<Thought> listThoughts(){
        return thoughts.values().stream().toList();
    }

    public void removeThought(String key){
        thoughts.remove(key);
        calculateTarget();
    }

    private void calculateTarget(){
        target = Consts.SATISFACTION_BAR_BASE_VALUE;

        for (String key : thoughts.keySet()) {
            target += thoughts.get(key).getModification();
        }

        target = min(max(target, 0), 100);
    }
}

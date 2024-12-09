public class achievements {
    private Texture achievementTexture;
    private String name;
    public float scoreIncrease;
    public boolean unlocked;

    public achievements(Texture achievementText, String achievementName, float score){
        achievementTexture = achievementText;
        name = achievementName;
        scoreIncrease = score;
        unlocked = false;
    }

    public String getAchievement() {
        return name;
    }
    public float getScoreIncrease() {
        return scoreIncrease;
    }
    public boolean isAchievement(){
        return unlocked;
    }
    public void unlockAchievement(){
        unlocked = true;
    }
    private void achievementLabel(){
        achievementLabel = new Label("Achievement Unlocked!");
        achievementLabel.setFontScale(3); 
        achievementLabel.setAlignment(Align.center);
        achievementLabel.setColor(Consts.TIMER_COLOR);
    }
}
achievement greenThumb = new achievements(achievementPopupTexture,"Green Thumb", 1);

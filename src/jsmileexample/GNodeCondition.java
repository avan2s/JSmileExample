/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsmileexample;

/**
 *
 * @author Andy
 */
public class GNodeCondition {
    private String levelName;
    private String condition;

    public GNodeCondition() {
    }
    
    public GNodeCondition(GNodeCondition levelCondition) {
        this.setCondition(levelCondition.getCondition());
        this.setLevelName(levelCondition.getLevelName());
    }

    public GNodeCondition(String levelName, String condition) {
        this.levelName = levelName;
        this.condition = condition;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(levelName).append("=")
                .append(condition).append(" ");
        return builder.toString();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsmileexample;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andy
 */
// Zuordnung von values
public class GNodeValue {

    private String nodeName;
    private double value;

    // Utility x kommt durch Entscheidung "E1",{"A","B"} bedingte  für Entscheidung 1
    private List<GNodeCondition> conditions;

    public GNodeValue(String nodeName, double value) {
        this.nodeName = nodeName;
        this.value = value;
    }

    public GNodeValue() {
        this.conditions = new ArrayList<>();
    }

    public GNodeValue(GNodeValue nodeValue) {
        this.conditions = new ArrayList<>(nodeValue.getConditions());
        this.value = nodeValue.value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<GNodeCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<GNodeCondition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(GNodeCondition condition) {
        this.conditions.add(condition);
    }

    public void addCondition(String levelName, String condition) {
        this.conditions.add(new GNodeCondition(levelName, condition));
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean hasConditions() {
        return !this.conditions.isEmpty();
    }

    public String optimizationVariableName() {
        final StringBuilder builder = new StringBuilder("X");
        builder.append(this.nodeName).append("|")
                .append(this.conditions);
        return builder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("GNode Value: ").append(value)
                .append(conditions);
        return builder.toString();
    }

}

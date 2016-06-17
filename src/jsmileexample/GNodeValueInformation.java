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
public class GNodeValueInformation {
    private String nodeName;
    private List<GNodeValue> values;

    public GNodeValueInformation() {
        this.values = new ArrayList<>();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<GNodeValue> getValues() {
        return values;
    }

    public void setValues(List<GNodeValue> values) {
        this.values = values;
    }
    
    public void addUtilityValue(GNodeValue value){
        this.values.add(value);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsmileexample;

import smile.Network;

/**
 *
 * @author Andy
 */
public class UtilityExtractor {
    private final Network solvedNet;
    private String nodeName;
    private GNodeValueInformation utilityInformation;
    private int wIndex;

    public UtilityExtractor(Network solvedNet) {
        this.solvedNet = solvedNet;
    }
    
    public GNodeValueInformation extract(String nodeName){
        this.resetExtractor();
        this.utilityInformation.setNodeName(nodeName);
        
        int[] levelIndices = this.solvedNet.getValueIndexingParents(nodeName);
        int numberOfLevels = levelIndices.length;
        
        this.rekursiv(1,nodeName, numberOfLevels, new GNodeValue());
        
        return this.utilityInformation;
    }
    
    private void resetExtractor(){
        this.utilityInformation = null;
        this.utilityInformation = new GNodeValueInformation();
        this.wIndex = 0;
        this.nodeName = null;
    }
    
    private void rekursiv(final int level, String nodeName, int numberOfLevels, final GNodeValue parentNodeValue) {
        parentNodeValue.setNodeName(nodeName);
        if (level <= numberOfLevels) {
            int levelIndex = solvedNet.getValueIndexingParents(nodeName)[level-1];
            String levelName = solvedNet.getNodeName(levelIndex);
            int levelOutcomeAnzahl = solvedNet.getOutcomeCount(levelName);
            
            for(int conditionIndex=0; conditionIndex< levelOutcomeAnzahl; conditionIndex++){
                String condition = solvedNet.getOutcomeId(levelName, conditionIndex);
                GNodeCondition levelCondition = new GNodeCondition(levelName,condition);
                GNodeValue eGNodeValue = new GNodeValue(parentNodeValue);
                eGNodeValue.addCondition(levelCondition);
                
                rekursiv(level+1,nodeName, numberOfLevels, eGNodeValue);
            }
        }
        else {
            // lowest level -> fill item
            double expectedUtility = solvedNet.getNodeValue(nodeName)[wIndex];
            parentNodeValue.setValue(expectedUtility);
            
            System.out.println(parentNodeValue);
            System.out.println(parentNodeValue.optimizationVariableName());
            this.utilityInformation.addUtilityValue(parentNodeValue);
            wIndex++; // immer null, als Objekt darstellen          
            
            //double expectedUtility = solvedNet.getNodeValue(nodeName)[wIndex];
        }
    }
    
}

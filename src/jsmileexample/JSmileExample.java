/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsmileexample;

import java.awt.Color;
import java.util.Map;
import smile.Network;
import smile.SMILEException;

/**
 *
 * @author Andy
 */
public class JSmileExample {
    

    public static GNodeValue rec(Network net, String nodeName) {
        return null;
    }

    public static void main(String[] args) {
        //buildBayesianNetwork();
        //upgradeToInfluenceDiagram();
        //inferenceWithInfluenceDiagram2();
        // Loading and updating the influence diagram: 
        Network net = new Network();
        net.readFile("test2.net");
        net.updateBeliefs();
        String nodeName = "U2";
        UtilityExtractor extractor = new UtilityExtractor(net);
        GNodeValueInformation utilityInformation = extractor.extract(nodeName);
        int x = 0;
    }

    public static void buildBayesianNetwork() {
        Network net = new Network();
        net.addNode(Network.NodeType.Cpt, "Success");

        // "node Id", state index, "state name"
        net.setOutcomeId("Success", 0, "Success");
        net.setOutcomeId("Success", 1, "Failure");

        net.addNode(Network.NodeType.Cpt, "Forecast");
        net.addOutcome("Forecast", "Good");
        net.addOutcome("Forecast", "Moderate");
        net.addOutcome("Forecast", "Poor");
        net.deleteOutcome("Forecast", 0);
        net.deleteOutcome("Forecast", 0);

        net.addArc("Success", "Forecast");

        double[] aSuccessDef = {0.2, 0.8};
        net.setNodeDefinition("Success", aSuccessDef);

        // Changing the nodes' spacial and visual attributes:
        net.setNodePosition("Success", 20, 20, 80, 30);
        net.setNodeBgColor("Success", Color.red);
        net.setNodeTextColor("Success", Color.white);
        net.setNodeBorderColor("Success", Color.black);
        net.setNodeBorderWidth("Success", 2);
        net.setNodePosition("Forecast", 30, 100, 60, 30);
        net.writeFile("tutorial_a.xdsl");
    }

    public static void upgradeToInfluenceDiagram() {
        try {
            Network net = new Network();
            net.readFile("tutorial_a.xdsl");

            // Creating node "Invest" and setting/adding outcomes:
            net.addNode(Network.NodeType.List, "Invest");
            net.setOutcomeId("Invest", 0, "Invest");
            net.setOutcomeId("Invest", 1, "DoNotInvest");

            // Creating the value node "Gain":
            net.addNode(Network.NodeType.Table, "Gain");

            // Adding an arc from "Invest" to "Gain":
            net.addArc("Invest", "Gain");

            // Adding an arc from "Success" to "Gain":
            net.getNode("Success");
            net.addArc("Success", "Gain");

            // Filling in the utilities for the node "Gain". The utilities are:
            // U("Invest" = Invest, "Success" = Success) = 10000
            // U("Invest" = Invest, "Success" = Failure) = -5000
            // U("Invest" = DoNotInvest, "Success" = Success) = 500
            // U("Invest" = DoNotInvest, "Success" = Failure) = 500
            double[] aGainDef = {10000, -5000, 500, 500};
            net.setNodeDefinition("Gain", aGainDef);

            net.writeFile("tutorial_b.xdsl");
        } catch (SMILEException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void inferenceWithInfluenceDiagram() {
        try {
            // Loading and updating the influence diagram: 
            Network net = new Network();
            net.readFile("tutorial_b.xdsl");
            net.updateBeliefs();

            // Getting the utility node's handle:
            int node = net.getNode("Gain");

            // Getting the handle and the name of value indexing parent (decision node):
            int[] aValueIndexingParents = net.getValueIndexingParents("Gain");
            int nodeDecision = aValueIndexingParents[0];
            String decisionName = net.getNodeName(nodeDecision);

            // Displaying the possible expected values:
            System.out.println("These are the expected utilities:");
            for (int i = 0; i < net.getOutcomeCount(nodeDecision); i++) {
                String parentOutcomeId = net.getOutcomeId(nodeDecision, i);
                double expectedUtility = net.getNodeValue("Gain")[i];

                System.out.print("  - \"" + decisionName + "\" = " + parentOutcomeId + ": ");
                System.out.println("Expected Utility = " + expectedUtility);
            }
        } catch (SMILEException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void inferenceWithInfluenceDiagram2() {
        try {
            // Loading and updating the influence diagram: 
            Network net = new Network();
            net.readFile("test2.net");
            net.updateBeliefs();
            String nodeName = "U2";
            double[] nodeValue = net.getNodeValue(nodeName);

            // Getting the utility node's handle:
            //rec(net, "U2");
            //int node = net.getNode("U2");
            // Getting the handle and the name of value indexing parent (decision node):
            // indexierte Eltern des Values von U3 (decision node(s)) z.B. E1
            int[] aValueIndexingParents = net.getValueIndexingParents(nodeName);

            int ebene1Index = aValueIndexingParents[0];
            int ebene2Index = aValueIndexingParents[1];

            String ebene1Name = net.getNodeName(aValueIndexingParents[0]);
            String ebene2Name = net.getNodeName(aValueIndexingParents[1]);
            int anzahlValueParentEbenen = aValueIndexingParents.length;
            
            // rekutsiver Methodentest
            rekursiv(1, net, nodeName, 0,anzahlValueParentEbenen,new GNodeValue());
            

            
            // Displaying the possible expected values:
            System.out.println("These are the expected utilities:");

            // E1 hat A und B als Werte im ParentIndex
            int d1OutcomeEbene1Anzahl = net.getOutcomeCount(ebene1Name);
            int d2OutcomeEbene2Anzahl = net.getOutcomeCount(ebene2Name);
            int wIndex = 0;
            for (int i1 = 0; i1 < d1OutcomeEbene1Anzahl; i1++) { // E1 Ebene
                for (int i2 = 0; i2 < d2OutcomeEbene2Anzahl; i2++) { // E2 Ebene
                    String parentOutcomeIdEbene1 = net.getOutcomeId(ebene1Name, i1);
                    String parentOutcomeIdEbene2 = net.getOutcomeId(ebene2Name, i2);
                    double expectedUtility = net.getNodeValue(nodeName)[wIndex];
                    System.out.print("  - \"" + ebene1Name + "\" = " + parentOutcomeIdEbene1 + ", " + ebene2Name + "=" + parentOutcomeIdEbene2 + ": ");
                    System.out.println("Expected Utility = " + expectedUtility);
                    wIndex++;
                }
            }

            int x = 0;
        } catch (SMILEException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public static void rekursiv(final int level, Network solvedNet, String nodeName,  int wIndex, int numberOfLevels, final GNodeValue parentNodeValue) {
        //parentNodeValue.setNodeName(nodeName);
        if (level <= numberOfLevels) {
            int levelIndex = solvedNet.getValueIndexingParents(nodeName)[level-1];
            String levelName = solvedNet.getNodeName(levelIndex);
            int levelOutcomeAnzahl = solvedNet.getOutcomeCount(levelName);
            
            for(int conditionIndex=0; conditionIndex< levelOutcomeAnzahl; conditionIndex++){
                String condition = solvedNet.getOutcomeId(levelName, conditionIndex);
                GNodeCondition levelCondition = new GNodeCondition(levelName,condition);
                GNodeValue eGNodeValue = new GNodeValue(parentNodeValue);
                eGNodeValue.addCondition(levelCondition);
                
                rekursiv(level+1,solvedNet, nodeName, wIndex, numberOfLevels, eGNodeValue);
            }
        }
        else {
            // lowest level -> fill item
            double expectedUtility = solvedNet.getNodeValue(nodeName)[wIndex];
            parentNodeValue.setValue(expectedUtility);
            System.out.println(parentNodeValue);
            System.out.println(parentNodeValue.optimizationVariableName());
            wIndex++; // immer null, als Objekt darstellen          
            
            //double expectedUtility = solvedNet.getNodeValue(nodeName)[wIndex];
        }
    }

//    public static void rekursiv(Network net, String name, int level) {
//
//        if (level > 0) {
//            rekursiv(net, name, --level);
//        }
//        else {
//            // do it
//            net.getValueIndexingParents(nodeName)[level-1];
//        }
//    }
}

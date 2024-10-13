package org.example.ProblemDefinition;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProblemInitializer {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;

    public List<Integer> getNodeCosts() {
        return nodeCosts;
    }
    public List<List<Integer>> getDistanceMatrix() {
        return distanceMatrix;
    }

    public ProblemInitializer() {
        distanceMatrix = new ArrayList<>();
        nodeCosts = new ArrayList<>();
        CreateDistanceMatrix();
    }

    public List<GraphNode> ReadFile(String path)
    {
        List<GraphNode> graphNodes = new ArrayList<>();
        String csvFile = "src/main/resources/" + path;
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                var Values = line[0].split(";");
                System.out.println(Values[0] + " " + Values[1] + " " + Values[2]);
                graphNodes.add(new GraphNode(Integer.parseInt(Values[0]), Integer.parseInt(Values[1]), Integer.parseInt(Values[2])));
                nodeCosts.add(Integer.parseInt(Values[2]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graphNodes;
    }

    public void CreateDistanceMatrix (){
        CreateDistanceMatrix("TSPA.csv");
    }

    public void CreateDistanceMatrix(String fileName)
    {
        List<GraphNode> graphNodes = ReadFile((fileName));

        for (int i = 0; i < graphNodes.size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < graphNodes.size(); j++) {
                row.add((int) Math.round(Math.sqrt(Math.pow(graphNodes.get(i).getX() - graphNodes.get(j).getX(), 2)
                        + Math.pow(graphNodes.get(i).getY() - graphNodes.get(j).getY(), 2))));

            }
            distanceMatrix.add(row);
        }
    }

    public void PrintMatrix ()
    {
        for (int i = 0; i < distanceMatrix.size(); i++) {
            for (int j = 0; j < distanceMatrix.get(i).size(); j++) {
                System.out.print(distanceMatrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}

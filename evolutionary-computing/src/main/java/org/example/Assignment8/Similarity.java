package org.example.Assignment8;

import com.opencsv.CSVWriter;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment3.LocalSearch;
import org.example.Assignment3.MoveType;
import org.example.Assignment3.TypeOfLocalSearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Similarity {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private LocalSearch LS;

    public Similarity(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
        this.LS = new LocalSearch(distanceMatrix, nodeCosts);
    }

    public void Solve(String problemInstance) {
        List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
        List<List<Integer>> greedySolutions = new ArrayList<>();
        List<Integer> greedyDistances = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            var randomSolution = new ArrayList<>(solution);
            var greedySolution = LS.GenerateSolution(randomSolution, TypeOfLocalSearch.Greedy, MoveType.EXCHANGE_EDGES);
            greedySolutions.add(greedySolution.getKey());
            greedyDistances.add(CalculateDistance(greedySolution.getKey()));
        }

        File file = new File(String.format("outputs/%d/%s", 8, "output.csv"));

        if (!file.exists()) {
            CreateFile("output.csv", 8);
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
            String[] data = {"Objective", problemInstance, greedyDistances.toString()};
            writer.writeNext(data);

            var bestSolutionIndex = greedyDistances.indexOf(greedyDistances.stream().min(Integer::compare).get());

            var versions = VersionOfSimilarity.values();
            var measures = SimilarityMeasure.values();

            for (int i = 0; i < versions.length; i++) {
                for (int j = 0; j < measures.length; j++) {
                    List<Double> result = new ArrayList<>();
                    if (versions[i] == VersionOfSimilarity.Best) {
                        result = ComputeSimilarityBest(greedySolutions, bestSolutionIndex, measures[j]);
                    } else {
                        result = ComputeSimilarityAverage(greedySolutions, measures[j]);
                    }

                    String[] writeData = {versions[i].toString() + " - " + measures[j].toString(), problemInstance, result.toString()};
                    writer.writeNext(writeData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double ComputeSimilarity(List<Integer> solution, List<Integer> compareTo, SimilarityMeasure similarityMeasure) {
        double similarity = 0;

        if (similarityMeasure == SimilarityMeasure.Nodes) {
            for (int j = 0; j < solution.size(); j++) {
                if (compareTo.contains(solution.get(j))) {
                    similarity++;
                }
            }
        } else {
            for (int j = 0; j < solution.size() - 1; j++) {
                var nextSolIndex = compareTo.lastIndexOf(solution.get(j));
                if (nextSolIndex == compareTo.size()-1) {
                    nextSolIndex = 0;
                } else if (nextSolIndex != -1) {
                    nextSolIndex++;
                }
                var prevSolIndex = nextSolIndex - 2;

                if(prevSolIndex == -1 || prevSolIndex == -2) {
                    prevSolIndex = compareTo.size() + prevSolIndex;
                }

                if(nextSolIndex != -1 &&
                (
                    compareTo.get(nextSolIndex).equals(solution.get(j + 1)) ||
                    compareTo.get(prevSolIndex).equals(solution.get(j + 1))
                )) {
                    similarity++;
                }
            }

            var nextSolIndex = compareTo.lastIndexOf(solution.get(solution.size() - 1));
            if (nextSolIndex == compareTo.size()-1) {
                nextSolIndex = 0;
            } else if (nextSolIndex != -1) {
                nextSolIndex++;
            }
            var prevSolIndex = nextSolIndex - 2;

            if(prevSolIndex == -1 || prevSolIndex == -2) {
                prevSolIndex = compareTo.size() + prevSolIndex;
            }

            if(nextSolIndex != -1 &&
            (
                compareTo.get(nextSolIndex).equals(solution.get(0)) ||
                compareTo.get(prevSolIndex).equals(solution.get(0))
            )) {
                similarity++;
            }
        }

        return similarity/solution.size();
    }

    private List<Double> ComputeSimilarityBest(List<List<Integer>> greedySolutions, Integer bestSolutionIndex, SimilarityMeasure similarityMeasure) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < greedySolutions.size(); i++) {
            if (i != bestSolutionIndex) {
                var similarity = ComputeSimilarity(greedySolutions.get(i), greedySolutions.get(bestSolutionIndex), similarityMeasure);
                result.add(similarity);
            }
        }

        return result;
    }

    private List<Double> ComputeSimilarityAverage(List<List<Integer>> greedySolutions, SimilarityMeasure similarityMeasure) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < greedySolutions.size(); i++) {
            double similarity = 0;
            for (int j = 0; j < greedySolutions.size(); j++) {
                if (i != j) {
                    similarity += ComputeSimilarity(greedySolutions.get(i), greedySolutions.get(j), similarityMeasure);
                }
            }
            result.add(similarity/greedySolutions.size());
        }

        return result;
    }

    private int CalculateDistance(List<Integer> solution)
    {
        int distance = nodeCosts.get(solution.get(0));
        for (int i = 0; i < solution.size() - 1; i++) {

            distance += distanceMatrix.get(solution.get(i)).get(solution.get(i + 1));
            distance += nodeCosts.get(solution.get(i + 1));
        }
        distance += distanceMatrix.get(solution.get(solution.size() - 1)).get(solution.get(0));
        return distance;
    }

    private static void CreateFile(String fileName, int assignmentNumber) {
        File file = new File(String.format("outputs/%d/%s", assignmentNumber, fileName));

        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                String[] header = {"Name", "Instance", "Values"};
                writer.writeNext(header);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("CSV file already exists.");
        }
    }
}

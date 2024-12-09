package org.example.assignment5;


import org.example.Assignment1.RandomSolution;
import org.example.ProblemDefinition.SolutionChecker;

import java.util.*;

public class ListOfMoves {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private SortedSet<PreviousMove> ListOfMoves;

    public ListOfMoves(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
        this.ListOfMoves = new TreeSet<PreviousMove>();
    }

    public List<List<Integer>> solve(int iterations) {
        List<List<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
            var newSolution = generateSolution(solution);
            solutions.add(newSolution);
        }
        return solutions;
    }

    public List<Integer> generateSolution(List<Integer> solution)
    {
        this.ListOfMoves = new TreeSet<PreviousMove>();
        createStartingList(solution);
        while(true){
            boolean foundBetterSolution = false;
            PreviousMove chosen = null;
            for (var pm : ListOfMoves){
                if (isApplicable(pm, solution)){
                    foundBetterSolution = true;
                    solution = pm.applyMove(solution);
                    chosen = pm;
                    break;
                }
                else if (isReverseApplicable(pm, solution)){
                    Collections.reverse(solution);
                    foundBetterSolution = true;
                    solution = pm.applyMove(solution);
                    chosen = pm;
                    break;
                }
            }
            if (foundBetterSolution){
                deleteMoves(chosen.getEdge1(), chosen.getEdge2());
                createNewMoves(solution, chosen.getNewEdge1(), chosen.getNewEdge2());
            }
            else{
                break;
            }
        }
        return solution;
    }

    public void deleteMoves(Edge removedEdge1, Edge removedEdge2)
    {
        List<PreviousMove> toRemove = new ArrayList<>();

        for (var pm : ListOfMoves) {
            var edge1 = pm.getEdge1();
            var edge2 = pm.getEdge2();

            if(edge1.getStart() == removedEdge1.getStart() && edge1.getEnd() == removedEdge1.getEnd()
                || edge2.getStart() == removedEdge2.getStart() && edge2.getEnd() == removedEdge2.getEnd()){
                toRemove.add(pm);
            }
        }
        ListOfMoves.removeAll(toRemove);
    }

    public void createNewMoves(List<Integer> solution, Edge createdEdge1, Edge createdEdge2)
    {
        var id1 = solution.indexOf(createdEdge1.getStart());
        var id2 = solution.indexOf(createdEdge2.getStart());

        for(int i = 0; i < solution.size(); i++) {
            if (Math.abs(id1 - i) > 1 && Math.abs(id1 - i + 1) > 1
                    && Math.abs(id2 - i) > 1 && Math.abs(id2 - i + 1) > 1) {
                int nextI = i + 1 < solution.size() ? i + 1 : 0;
                var node1 = solution.get(i);
                var nextNode1 = solution.get(nextI);
                Edge edge1 = new Edge(node1, nextNode1, distanceMatrix.get(node1).get(nextNode1), nodeCosts.get(node1), nodeCosts.get(nextNode1));
                Edge newEdge1 = new Edge(node1, createdEdge1.getStart(), distanceMatrix.get(node1).get(createdEdge1.getStart()), nodeCosts.get(node1), nodeCosts.get(createdEdge1.getStart()));
                Edge newEdge2 = new Edge(nextNode1, createdEdge1.getEnd(), distanceMatrix.get(nextNode1).get(createdEdge1.getEnd()), nodeCosts.get(nextNode1), nodeCosts.get(createdEdge1.getEnd()));
                PreviousMove pm = new PreviousMove(edge1, createdEdge1, newEdge1, newEdge2, true);
                if (pm.getObjectiveChange() < 0){
                    ListOfMoves.add(pm);
                }
            }
            if (Math.abs(id2 - i) > 1 && Math.abs(id2 - i + 1) > 1
                    && Math.abs(id1 - i) > 1 && Math.abs(id1 - i + 1) > 1) {
                int nextI = i + 1 < solution.size() ? i + 1 : 0;
                var node1 = solution.get(i);
                var nextNode1 = solution.get(nextI);
                Edge edge1 = new Edge(node1, nextNode1, distanceMatrix.get(node1).get(nextNode1), nodeCosts.get(node1), nodeCosts.get(nextNode1));
                Edge newEdge1 = new Edge(node1, createdEdge2.getStart(), distanceMatrix.get(node1).get(createdEdge2.getStart()), nodeCosts.get(node1), nodeCosts.get(createdEdge2.getStart()));
                Edge newEdge2 = new Edge(nextNode1, createdEdge2.getEnd(), distanceMatrix.get(nextNode1).get(createdEdge2.getEnd()), nodeCosts.get(nextNode1), nodeCosts.get(createdEdge2.getEnd()));
                PreviousMove pm = new PreviousMove(edge1, createdEdge2, newEdge1, newEdge2, true);
                if (pm.getObjectiveChange() < 0){
                    ListOfMoves.add(pm);
                }
            }
        }

        for (int i = 0; i < solution.size(); i++)
        {
            int prevI = i - 1 >= 0 ? i - 1 : solution.size() - 1;
            int nextI = i + 1 < solution.size() ? i + 1 : 0;
            var edge1 = new Edge(solution.get(prevI), solution.get(i), distanceMatrix.get(solution.get(prevI)).get(solution.get(i)), nodeCosts.get(solution.get(prevI)), nodeCosts.get(solution.get(i)));
            var edge2 = new Edge(solution.get(i), solution.get(nextI), distanceMatrix.get(solution.get(i)).get(solution.get(nextI)), nodeCosts.get(solution.get(i)), nodeCosts.get(solution.get(nextI)));

//            for (var edge : List.of(createdEdge1, createdEdge2))
//            {
//                var newEdge1 = new Edge(solution.get(prevI), edge.getEnd(), distanceMatrix.get(solution.get(prevI)).get(edge.getEnd()), nodeCosts.get(solution.get(prevI)), nodeCosts.get(edge.getEnd()));
//                var newEdge2 = new Edge(edge.getEnd(), solution.get(nextI), distanceMatrix.get(edge.getEnd()).get(solution.get(nextI)), nodeCosts.get(edge.getEnd()), nodeCosts.get(solution.get(nextI)));
//
//                PreviousMove pm = new PreviousMove(edge1, edge2, newEdge1, newEdge2, false);
//                if (pm.getObjectiveChange() < 0) {
//                    ListOfMoves.add(pm);
//                }
//            }

            if (edge1.getStart() == createdEdge1.getStart() && edge1.getEnd() == createdEdge1.getEnd()
                    || edge1.getStart() == createdEdge2.getStart() && edge1.getEnd() == createdEdge2.getEnd()
                    || edge2.getStart() == createdEdge1.getStart() && edge2.getEnd() == createdEdge1.getEnd()
                    || edge2.getStart() == createdEdge2.getStart() && edge2.getEnd() == createdEdge2.getEnd()){
                for (int j = 0; j < nodeCosts.size(); j ++){
                    if (! solution.contains(j))
                    {
                        Edge newEdge1 = new Edge(edge1.getStart(), j, distanceMatrix.get(edge1.getStart()).get(j), nodeCosts.get(edge1.getStart()), nodeCosts.get(j));
                        Edge newEdge2 = new Edge(j, edge2.getEnd(), distanceMatrix.get(j).get(edge2.getEnd()), nodeCosts.get(j), nodeCosts.get(edge2.getEnd()));
                        PreviousMove pm = new PreviousMove(edge1, edge2, newEdge1, newEdge2, false);
                        if (pm.getObjectiveChange() < 0){
                            ListOfMoves.add(pm);
                        }
                    }
                }
            }
        }
    }

    public boolean isApplicable(PreviousMove pm, List<Integer> solution)
    {
        var edge1 = pm.getEdge1();
        var edge2 = pm.getEdge2();

        var indexStart1 = solution.indexOf(edge1.getStart());
        var indexStart2 = solution.indexOf(edge2.getStart());
        var indexEnd1 = solution.indexOf(edge1.getEnd());
        var indexEnd2 = solution.indexOf(edge2.getEnd());

        if (indexEnd1 - indexStart1 == 1){
            if (indexEnd2 - indexStart2 == 1){
                return true;
            }
        }
        return false;
    }

    public boolean isReverseApplicable(PreviousMove pm, List<Integer> solution)
    {
        var edge1 = pm.getEdge1();
        var edge2 = pm.getEdge2();

        var indexStart1 = solution.indexOf(edge1.getStart());
        var indexStart2 = solution.indexOf(edge2.getStart());
        var indexEnd1 = solution.indexOf(edge1.getEnd());
        var indexEnd2 = solution.indexOf(edge2.getEnd());

        if (indexEnd1 - indexStart1 == -1){
            if (indexEnd2 - indexStart2 == -1){
                return true;
            }
        }
        return false;
    }

    public void createStartingList(List<Integer> solution)
    {
        for (int i = 0 ; i < solution.size(); i++){
            for (int j = 0; j < solution.size(); j++){
                if (Math.abs(i - j) > 1){
                    int nextI = i + 1 < solution.size() ? i + 1 : 0;
                    int nextJ = j + 1 < solution.size() ? j + 1 : 0;

                    var node1 = solution.get(i);
                    var node2 = solution.get(j);
                    var nextNode1 = solution.get(nextI);
                    var nextNode2 = solution.get(nextJ);

                    Edge edge1 = new Edge(node1, nextNode1, distanceMatrix.get(node1).get(nextNode1), nodeCosts.get(node1), nodeCosts.get(nextNode1));
                    Edge edge2 = new Edge(node2, nextNode2, distanceMatrix.get(node2).get(nextNode2), nodeCosts.get(node2), nodeCosts.get(nextNode2));

                    Edge newEdge1 = new Edge(node1, node2, distanceMatrix.get(node1).get(node2), nodeCosts.get(node1), nodeCosts.get(node2));
                    Edge newEdge2 = new Edge(nextNode1, nextNode2, distanceMatrix.get(nextNode1).get(nextNode2), nodeCosts.get(nextNode1), nodeCosts.get(nextNode2));

                    PreviousMove pm = new PreviousMove(edge1, edge2, newEdge1, newEdge2, true);
                    if (pm.getObjectiveChange() < 0){
                        ListOfMoves.add(pm);
                    }
                }
            }
        }
        for (int i = 0; i < solution.size(); i++) {
            for (int j = 0; j < nodeCosts.size(); j++) {
                int prevI = i - 1 >= 0 ? i - 1 : solution.size() - 1;
                int nextI = i + 1 < solution.size() ? i + 1 : 0;

                var edge1 = new Edge(solution.get(prevI), solution.get(i), distanceMatrix.get(solution.get(prevI)).get(solution.get(i)), nodeCosts.get(solution.get(prevI)), nodeCosts.get(solution.get(i)));
                var edge2 = new Edge(solution.get(i), solution.get(nextI), distanceMatrix.get(solution.get(i)).get(solution.get(nextI)), nodeCosts.get(solution.get(i)), nodeCosts.get(solution.get(nextI)));

                var newEdge1 = new Edge(solution.get(prevI), j, distanceMatrix.get(solution.get(prevI)).get(i), nodeCosts.get(solution.get(prevI)), nodeCosts.get(j));
                var newEdge2 = new Edge(j, solution.get(nextI), distanceMatrix.get(i).get(solution.get(nextI)), nodeCosts.get(j), nodeCosts.get(solution.get(nextI)));

                PreviousMove pm = new PreviousMove(edge1, edge2, newEdge1, newEdge2, false);
                if (pm.getObjectiveChange() < 0){
                    ListOfMoves.add(pm);
                }
            }
        }
    }
}

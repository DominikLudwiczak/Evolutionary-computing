# Problem description

We are given three columns of integers with a row for each node. The first two columns contain x
and y coordinates of the node positions in a plane. The third column contains node costs. The goal is
to select exactly 50% of the nodes (if the number of nodes is odd we round the number of nodes to
be selected up) and form a Hamiltonian cycle (closed path) through this set of nodes such that the
sum of the total length of the path plus the total cost of the selected nodes is minimized.
The distances between nodes are calculated as Euclidean distances rounded mathematically to
integer values. The distance matrix should be calculated just after reading an instance and then only
the distance matrix (no nodes coordinates) should be accessed by optimization methods to allow
instances defined only by distance matrices.

# Graphs

## TSPA

### AVARAGE SIMILARITY

Common edges:
![a](Average%20-%20Edges%20-%20TSPA.png)

Common nodes:
![a](Average%20-%20Nodes%20-%20TSPA.png)

### SIMILARITY TO BEST SOLUTION

Common edges:
![a](Best%20-%20Edges%20-%20TSPA.png)

Common nodes:
![a](Best%20-%20Nodes%20-%20TSPA.png)

## TSPB

### AVARAGE SIMILARITY

Common edges:
![a](Average%20-%20Edges%20-%20TSPB.png)

Common nodes:
![a](Average%20-%20Nodes%20-%20TSPB.png)

### SIMILARITY TO BEST SOLUTION

Common edges:
![a](Best%20-%20Edges%20-%20TSPB.png)

Common nodes:
![a](Best%20-%20Nodes%20-%20TSPB.png)

# Conclusions:

- Negative Correlation with Objective Function: Across all plots, a negative correlation is observed. Solutions more similar to either the best solution or the average solution tend to have lower objective function values.

- High Node Similarity: The average similarity between solutions in terms of nodes is very high, indicating that most local optima converge on using the same set of nodes.

- Low Edge Similarity: The average similarity in terms of edges is much lower, suggesting that the primary differences between local optima lie in how the nodes are connected rather than in the nodes themselves.

- Better Similarity to Best Solutions: For solutions with the lowest objective function values, their similarity to the best solution is slightly higher than their similarity to the average solution.

- Discontinuous Similarities: The similarities to the best solution are not continuous, indicating variability in the extent to which local optima resemble the best solution.

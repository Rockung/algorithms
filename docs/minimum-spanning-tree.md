# Minimum Spanning Tree



## What is a Spanning Tree?

Given an undirected and connected graph G = (V, E) , a spanning tree of the graph G is a tree that spans G (that is, it includes every vertex of G) and is a subgraph of G (every edge in the tree belongs to G).



## What is a Minimum Spanning Tree?

The cost of spanning tree is the sum of weights of all edges in the tree. There can be many spanning trees. Minimum spanning tree is the spanning tree where the cost is minimum among all the spanning trees. There also can be many minimum spanning trees.

Minimum spanning tree has direct application in the design of networks. It is used in algorithm approximating the traveling salesman problem, multi-terminal minimum cut problem and minimum-cost weighted perfect matching. Other practical applications are:

1. Cluster Analysis
2. Handwriting recognition
3. Image segmentation



---

here are two famous algorithms for finding the Minimum Spanning Tree:

## Kruskal’s Algorithm

Kruskal’s Algorithm builds the spanning tree by adding edges one by one into a growing spanning tree. Kruskal's algorithm follows greedy approach as it finds an edge which has least weight and add it to the growing spanning tree  in each iteration.

Maintain an array of numbers of set for each vertexes.

Sort the edges by weight from small to big

Iterate on the edges, fetch an edge with smaller weight

Add it to the MST and unify the set number for the MST, if the two ends of the edge have different set numbers.

In Kruskal’s algorithm, most time consuming operation is sorting because the total complexity of the Disjoint-Set operations will be O(E·logV), which is the overall Time Complexity of the algorithm.




## Prim’s Algorithm

Prim’s Algorithm also use greedy approach to find the minimum spanning tree. In Prim’s Algorithm, we grow the spanning tree from a starting position. Unlike an edge in Kruskal's, we add vertex to the growing spanning tree in Prim's.

Maintain two disjoint sets of vertexes. One containing vertexes that are in the growing spanning tree and other that are not in the growing spanning tree.

Select the cheapest vertex that is connected to the growing spanning tree and is not in the growing spanning tree and add it into the growing spanning tree. This can be done using priority queues. Insert the vertexes, that are connected to growing spanning tree, into the priority queue.

Check for cycles. To do that, mark the nodes which have been already selected and insert only those nodes in the priority queue that are not marked.

The time complexity of the Prim’s Algorithm is O((V + E)logV) because each vertex is inserted in the priority queue only once and insertion in priority queue take logarithmic time.


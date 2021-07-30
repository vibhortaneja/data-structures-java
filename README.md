# Question: Where did I put my keys?

## Approach to solve the question:

Step-1: Understanding the problem statement. The first step was to identify and illustrate all the paths in form of a diagram from the given example.
 
Step-2: On to Code. As we are provided with the config file having predefined paths, next step would be to write a method which will read the file from local system, line by line.
Pseudo-code
•	Read the file and store each line in an Array List.
•	Remove the special characters from the lines and store them as words.
•	ArrayList of ArrayList will be formed with all paths
Result -
 
Step-3: We will create a map from above list which will store all the values in (key, value) format.
Pseudo-code
•	Initialize a map, HashMap<Integer, String>.
•	Iterate over the list.
•	Store the distinct values of list in map.
•	Key in map is an integer that starts with 0 with an increment of +1 every iteration and Value is a string, whose value is taken from the list.
Result –
 
Step-4: Create an adjacency list. It shows which nodes are connected to which nodes.
Pseudo-code
•	Initialize the list.
•	Iterate over the path given.
•	Add edges by mentioning the source and destination.
•	input -> x, y                        // Here x, y denotes there is an edge between x,
edge[x].add(y)
edge[y].add(x)

Step-5: Now we have an unweighted graph, a source, and a destination(Object), we will find the shortest path from room to the object in the graph in the shortest way.
In this approach we used BFS algorithm with little modification to it. Here we keep storing the predecessor of a given vertex while doing the breadth-first search. 
•	We first initialize an array distance[0, 1, …., v-1] such that distance[i] stores the distance of vertex i from the source vertex and array predecessor[0, 1, ….., v-1] such that predecessor[i] represents the immediate predecessor of the vertex i in the breadth-first search starting from the source.
•	Now we get the length of the path from source to any other vertex in O(1) time from array d, and for printing the path from source to any vertex we can use array p and that will take O(V) time in worst case as V is the size of array P. So, most of the time of the algorithm is spent in doing the Breadth-first search from a given source which we know takes O(V+E) time. Thus, the time complexity of our algorithm is O(V+E).
Pseudo-code
•	procedure BFS(G,s)
•	for each vertex v ∈ V[G] do
•	explored[v] ← false
•	d[v] ← ∞
•	end for
•	explored[s] ← true
•	d[s] ← 0
•	Q:= a queue data structure, initialized with s
•	while Q 6= φ do
•	u ← remove vertex from the front of Q
•	for each v adjacent to u do
•	if not explored[v] then
•	explored[v] ← true
•	d[v] ← d[u] + 1
•	insert v to the end of Q
•	end if
•	end for
•	end while
•	end procedure

Working of the Program
Note: To run the program, open ShortestPath.java and run as Java Application.
In the program we are using scanner class to take input from the user, here user will enter the Source.
•	Source can be from [ESTATE, HOUSE, BASEMENT, KITCHEN, STAIRS, BEDROOM, HALLWAY, GARDEN, TOOLSHED]
•	Object can be from [KNIFE, PILLOW, KEYS]
Output
•	 


•	 
-------------------------------------------------------------------------------------------------------------------------------
Bonus question #1: How would you modify your program if we knew that the gentleman had multiple ways to get to some of the rooms?
In BFS algorithm,  graph has all edges having equal weight, but here in this scenario we will assign weight to the edges. Some edges may have 0 weight, and some may have 1 weight. 
Here, we will not use Boolean array to mark visited nodes but at each step we will check for the optimal distance condition. Instead of simple queue, we use double ended queue to store the node. 
While executing the logic, if an edge having weight = 0 is found node is pushed at front of double ended queue and if an edge having weight = 1 is found, it is pushed at back of double ended queue.
The minimum weight vertex pops first among remaining vertices. If there is a 0 weight vertex adjacent to it, then this adjacent has same distance. If there is a 1 weight adjacent, then this adjacent has maximum distance among all vertices in dequeue because all other vertices are either adjacent of currently popped vertex or adjacent of previously popped vertices.
Algorithm:- 
vector<int> d(n, INF);
d[s] = 0;
deque<int> q;
q.push_front(s);
while (!q.empty()) {
    int v = q.front();
    q.pop_front();
    for (auto edge : adj[v]) {
        int u = edge.first;
        int w = edge.second;
        if (d[v] + w < d[u]) {
            d[u] = d[v] + w;
            if (w == 1)
                q.push_back(u);
            else
                q.push_front(u);
        }
    }
}
 


Bonus question #2: Assuming multiple paths, how would you modify the program if walking up the staircase was more strenuous than walking down the same?
 
Here we assume that moving down on stairs has less weight(cost) i.e. 0 and while moving up the stairs the weight(cost) is more i.e. 1. Then if an edge having weight = 0 is found node is pushed at front of double ended queue and if an edge having weight = 1 is found, it is pushed at back of double ended queue.
The minimum weight vertex pops first among remaining vertices. If there is a 0 weight vertex adjacent to it, then this adjacent has same distance. If there is a 1 weight adjacent, then this adjacent has maximum distance among all vertices.
Algorithm:- 
vector<int> d(n, INF);
d[s] = 0;
deque<int> q;
q.push_front(s);
while (!q.empty()) {
    int v = q.front();
    q.pop_front();
    for (auto edge : adj[v]) {
        int u = edge.first;
        int w = edge.second;
        if (d[v] + w < d[u]) {
            d[u] = d[v] + w;
            if (w == 1)
                q.push_back(u);
            else
                q.push_front(u); 
}}}
Note: 
•	Import the program in Eclipse as General project. 
•	To run the program, open ShortestPath.java and run as Java Application.

Tools Used :
•	IDE – Eclipse
•	Graph Creation - https://graphonline.ru/en/ 

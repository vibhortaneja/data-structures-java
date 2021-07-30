/*
 * Name: Vibhor Taneja
 * Email: vtaneja127@gmail.com
 * Date: 11/07/2021
 */

package main.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ShortestPath {

	// Main Program
	public static void main(String args[]) throws IOException
	{
		HashMap<Integer, String> mapofElements = new HashMap<>();
		ArrayList<ArrayList<String>> dataList =	new ArrayList<ArrayList<String>>();

		//In this function reading file and arranging the data in arrayList
		dataList = arrangeData();

		//Returning a map of values by creating a distinct list for elements and adding elements to the map
		mapofElements = createMap(dataList);

		//No of vertices
		int verticeSize = mapofElements.size();

		// Create Adjacency list and adding edge
		ArrayList<ArrayList<Integer>> adjList = createAdjacencyList(mapofElements, dataList, verticeSize);


		// Taking input from user
		// Create a Scanner object
		Scanner myObj = new Scanner(System.in);  

		//Taking source from user
		System.out.println("Enter Source : ESTATE, HOUSE, BASEMENT, KITCHEN, STAIRS, BEDROOM, HALLWAY, GARDEN, TOOLSHED");
		String sourceString = myObj.nextLine();

		//Taking object(destination) from user
		System.out.println("Enter Object : KNIFE, PILLOW, KEYS");
		String objectString = myObj.nextLine();
		myObj.close();

		//Condition to check whether input by user is correct or not.
		if(mapofElements.containsValue(sourceString.toUpperCase())&& mapofElements.containsValue(objectString.toUpperCase())) {
			int source = getKey(mapofElements,sourceString.toUpperCase()), destination = getKey(mapofElements,objectString.toUpperCase());
			printShortestDistance(adjList, source, destination, verticeSize, mapofElements);
		}
		else {
			System.out.println("Invalid values entered");
		}

	}

	//Creating Adjacency list and adding edge
	private static ArrayList<ArrayList<Integer>> createAdjacencyList(HashMap<Integer, String> mapofElements,
			ArrayList<ArrayList<String>> dataList, int verticeSize) {

		// Adjacency list for storing which vertices are connected
		ArrayList<ArrayList<Integer>> adjList =	new ArrayList<ArrayList<Integer>>(verticeSize);

		for (int i = 0; i < verticeSize; i++) {
			adjList.add(new ArrayList<Integer>());
		}

		for(int i=0; i<dataList.size(); i++) {
			for(int j=0; j<dataList.get(i).size(); j++) {
				if(j<dataList.get(i).size()-1) {
					//Creating graph, this function takes adjacency list, source,destination,vertex as argument and forms an edge between them.
					addEdge(adjList, getKey(mapofElements,dataList.get(i).get(j)),
							getKey(mapofElements,dataList.get(i).get(++j)));

					j--;
				}
			}
		}
		return adjList;
	}

	//Function to form edge between two vertices source and destination
	private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j)
	{
		adj.get(i).add(j);
		adj.get(j).add(i);
	}

	//Creating a distinct list for the purpose of creating a map from it
	//Here we are creating a map of all the destination and string present in configuration file
	private static HashMap<Integer, String> createMap(ArrayList<ArrayList<String>> dataList) {
		HashMap<Integer, String> map = new HashMap<>();
		int i = 0;
		
		ArrayList<String> listForMap = (ArrayList<String>) dataList.stream().flatMap(list -> list.stream()).distinct()
				.collect(Collectors.toList());
		
		for(String value: listForMap) {
			map.put(i, value); 
			i++;
		}
		return map;
	}


	//Function to read the file and convert it into an List of ArrayList
	//Each arrayList in list represent a path defined in the file.
	private static ArrayList<ArrayList<String>> arrangeData() throws IOException {
		ArrayList<ArrayList<String>> outterList =	new ArrayList<ArrayList<String>>();
		try {
			File currentDir = new File("");
			//Please change the path of the file according to the local system
			List<String> linesFromFile = Files
					.readAllLines(Path.of(currentDir.getAbsolutePath()+"\\src\\main\\java\\values.config"));

			for (String s : linesFromFile) { 
				ArrayList<String> innerlist=new ArrayList<String>();

				//Splitting the words with help of special characters
				for (String val: s.split("-|\\:")) {
					innerlist.add(val.toUpperCase().trim());
				}
				outterList.add(innerlist);
			}
		} catch (IOException e) {
			throw new IOException("Please enter the correct path of the file " + e);
		}
		return outterList;
	}

	// function to get key from map when value is supplied
	// This function is used to map Values to Key in hashMap
	private static <K, V> K getKey(Map<K, V> map, V value) {
		for (Entry<K, V> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	// function to print the shortest distance and path between source vertex and destination vertex
	private static void printShortestDistance(
			ArrayList<ArrayList<Integer>> adj,
			int source, int destination, int v, HashMap<Integer, String> map)
	{
		// predecessor[i] array stores predecessor of i and distance array stores distance of i from s
		int predecessor[] = new int[v];
		int distance[] = new int[v];

		if (bfs(adj, source, destination, v, predecessor, distance) == false) {
			System.out.println("Given source and destination are not connected");
			return;
		}

		// LinkedList to store path
		LinkedList<Integer> path = new LinkedList<Integer>();
		int covered = destination;
		path.add(covered);
		while (predecessor[covered] != -1) {
			path.add(predecessor[covered]);
			covered = predecessor[covered];
		}

		// Printing distance for test purpose
		// System.out.println("Shortest path length is: " + distance[destination]);

		// Print path
		for (int i = path.size() - 1; i >= 0; i--) {
			if(i == path.size()-1) {
				System.out.print("You are in the "+map.get(path.get(i)) + ". ");}
			else if(i < path.size()-1 && i>0) {
				System.out.print("Go to "+map.get(path.get(i)) + " ");}
			else {
				System.out.print("get "+map.get(path.get(i)) + ". ");
			}
		}
	}

	// Modified version of BFS that stores predecessor of each vertex in array predecessor
	// and its distance from source in array distance
	private static boolean bfs(ArrayList<ArrayList<Integer>> adj, int source,
			int destination, int v, int predecessor[], int distance[])
	{
		// a queue to maintain queue of vertices whose adjacency list is to be scanned as per normal
		// BFS algorithm using LinkedList of Integer type
		LinkedList<Integer> queue = new LinkedList<Integer>();

		// boolean array visited[] which stores the information whether ith vertex is reached
		// at least once in the Breadth first search
		boolean visited[] = new boolean[v];

		// initially all vertices are unvisited so v[i] for all i is false
		// and as no path is yet constructed dist[i] for all i set to infinity
		for (int i = 0; i < v; i++) {
			visited[i] = false;
			distance[i] = Integer.MAX_VALUE;
			predecessor[i] = -1;
		}

		// now source is first to be visited and distance from source to itself should be 0
		visited[source] = true;
		distance[source] = 0;
		queue.add(source);

		// BFS Algorithm
		while (!queue.isEmpty()) {
			int u = queue.remove();
			for (int i = 0; i < adj.get(u).size(); i++) {
				if (visited[adj.get(u).get(i)] == false) {
					visited[adj.get(u).get(i)] = true;
					distance[adj.get(u).get(i)] = distance[u] + 1;
					predecessor[adj.get(u).get(i)] = u;
					queue.add(adj.get(u).get(i));

					// stopping condition (when we find our destination)
					if (adj.get(u).get(i) == destination)
						return true;
				}
			}
		}
		return false;
	}
}
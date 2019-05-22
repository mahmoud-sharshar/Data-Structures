package eg.edu.alexu.csd.filestructure.graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import javafx.util.Pair;

public class ShortestPaths implements IGraph {

	private ArrayList<ArrayList<Pair<Integer, Integer>>> graph;
	private int numOfVertices;
	private int numOfEdges;
	private ArrayList<Integer> DijkstraProcessedOrder;

	public ShortestPaths() {
		this.graph = new ArrayList<>();
		this.numOfEdges = 0;
		this.numOfVertices = 0;
		DijkstraProcessedOrder = new ArrayList<>();
	}

	@Override
	public void readGraph(File file) {
		try {
			@SuppressWarnings("resource")
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			String line = fileReader.readLine();
			int iteration = -1;
			while (line != null) {
				String[] parts = line.split(" ");
				if (iteration == -1) {
					this.numOfVertices = Integer.parseInt(parts[0]);
					this.numOfEdges = Integer.parseInt(parts[1]);
					for (int i = 0; i < this.numOfVertices; i++)
						this.graph.add(new ArrayList<>());
				} else
					graph.get(Integer.parseInt(parts[0]))
							.add(new Pair<Integer, Integer>(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
				iteration++;
				line = fileReader.readLine();
			}
			if (iteration != this.numOfEdges)
				throw new RuntimeException();
		} catch (FileNotFoundException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public int size() {
		return this.numOfEdges;
	}

	@Override
	public ArrayList<Integer> getVertices() {
		ArrayList<Integer> vertices = new ArrayList<>();
		for (int i = 0; i < this.numOfVertices; i++) {
			vertices.add(i);
		}
		return vertices;
	}

	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		ArrayList<Integer> neighbors = new ArrayList<>();
		ArrayList<Pair<Integer, Integer>> n = this.graph.get(v);
		for (int i = 0; i < n.size(); i++) {
			neighbors.add(n.get(i).getKey());
		}
		return neighbors;
	}

	@Override
	public void runDijkstra(int src, int[] distances) {
		// initialize all distances to infinity
		int length = distances.length;
		for (int i = 0; i < length; i++)
			distances[i] = Integer.MAX_VALUE / 2;
		distances[src] = 0;
		// make custom comparator priority queue
		PriorityQueue<Integer> Q = new PriorityQueue<>(new Comparator<Integer>() {

			@Override
			public int compare(Integer node1, Integer node2) {
				if (distances[node1] > distances[node2])
					return 1;
				else if (distances[node1] < distances[node2])
					return -1;
				else
					return 0;
			}
		});
		// add vertices to priority queue
		Q.add(src);
		for (int i = 0; i < length; i++) {
			if (i != src) {
				Q.add(i);
			}
		}
		while (!Q.isEmpty()) {
			int min = Q.remove();
			DijkstraProcessedOrder.add(min);
			ArrayList<Pair<Integer, Integer>> adj = this.graph.get(min);
			for (int i = 0; i < adj.size(); i++) {
				Pair<Integer, Integer> child = adj.get(i);
				if (distances[child.getKey()] > (long) distances[min] + (long) child.getValue()) {
					distances[child.getKey()] = distances[min] + child.getValue();
					// change priority
					Q.remove(child.getKey());
					Q.add(child.getKey());
				}
			}
		}
	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {

		return DijkstraProcessedOrder;
	}

	@Override
	public boolean runBellmanFord(int src, int[] distances) {
		for (int i = 0; i < distances.length; i++) {
			distances[i] = Integer.MAX_VALUE / 2;
		}
		distances[src] = 0;
		boolean cycleFree = true;
		for (int i = 0; i < distances.length; i++) {
			boolean flag = false;
			for (int j = 0; j < distances.length; j++) {
				ArrayList<Pair<Integer, Integer>> neighbours = graph.get(j);
				for (int k = 0; k < graph.get(j).size(); k++) {
					Pair<Integer, Integer> child = neighbours.get(k);
					if (distances[child.getKey()] > (long) distances[j] + (long) child.getValue()) {
						if (i == distances.length - 1)// test negative cycle
							cycleFree = false;
						distances[child.getKey()] = distances[j] + child.getValue();
						flag = true;
					}
				}
			}
			if (!flag)
				break;
		}
		return cycleFree;
	}

}

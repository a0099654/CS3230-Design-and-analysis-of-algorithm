//Name:Stella Widyasari
//Student ID: A0099654L

import java.io.*;
import java.util.*;

public class graphTransformation {
    private HashMap<Vertex, TreeSet<Vertex>> myAdjList;
    private HashMap<String, Vertex> myVertices;
    private static final TreeSet<Vertex> EMPTY_SET = new TreeSet<Vertex>();
    private int myNumVertices;
    private int myNumEdges;

    /**
     * Construct empty Graph
     */
    public graphTransformation() {
        myAdjList = new HashMap<Vertex, TreeSet<Vertex>>();
        myVertices = new HashMap<String, Vertex>();
        myNumVertices = myNumEdges = 0;

    }

    /**
     * Add a new vertex name with no neighbors (if vertex does not yet exist)
     * 
     * @param name
     *            vertex to be added
     */
    public Vertex addVertex(String name) {
        Vertex v;
        v = myVertices.get(name);
        if (v == null) {
            v = new Vertex(name);
            myVertices.put(name, v);
            myAdjList.put(v, new TreeSet<Vertex>());
            myNumVertices += 1;
        }
        return v;
    }

    /**
     * Returns the Vertex matching v
     * @param name a String name of a Vertex that may be in
     * this Graph
     * @return the Vertex with a name that matches v or null
     * if no such Vertex exists in this Graph
     */
    public Vertex getVertex(String name) {
        return myVertices.get(name);
    }

    /**
     * Returns true iff v is in this Graph, false otherwise
     * @param name a String name of a Vertex that may be in
     * this Graph
     * @return true iff v is in this Graph
     */
    public boolean hasVertex(String name) {
        return myVertices.containsKey(name);
    }

    /**
     * Is from-to, an edge in this Graph. The graph is 
     * undirected so the order of from and to does not
     * matter.
     * @param from the name of the first Vertex
     * @param to the name of the second Vertex
     * @return true iff from-to exists in this Graph
     */
    public boolean hasEdge(String from, String to) {

        if (!hasVertex(from) || !hasVertex(to))
            return false;
        return myAdjList.get(myVertices.get(from)).contains(myVertices.get(to));
    }
    
    /**
     * Add to to from's set of neighbors, and add from to to's
     * set of neighbors. Does not add an edge if another edge
     * already exists
     * @param from the name of the first Vertex
     * @param to the name of the second Vertex
     */
    public void addEdge(String from, String to) {
        Vertex v, w;
        if (hasEdge(from, to))
            return;
        myNumEdges += 1;
        if ((v = getVertex(from)) == null)
            v = addVertex(from);
        if ((w = getVertex(to)) == null)
            w = addVertex(to);
        myAdjList.get(v).add(w);
        myAdjList.get(w).add(v);
    }


    
    /*
     * Returns adjacency-list representation of graph
     */
    public String toString() {
        String s = "";
        for (Vertex v : myVertices.values()) {
            s += v + ": ";
            for (Vertex w : myAdjList.get(v)) {
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }

   
    // the main method
	public static void main (String[] args){
	 	Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		// use this (a much faster output routine) instead of Java System.out.println (slow)

	 	int T,N, M;
	 	String u,v,p;

	 	graphTransformation Graph = new graphTransformation();
	 	String[] path = new String[30000];

	 	T = sc.nextInt();

	 	for(int i=0; i<T; i++){
	 		N = sc.nextInt();//number of vertices
	 		M = sc.nextInt();//number of edges

	 		// construct the graph
	 		for(int edge=0; edge < M; edge++){
	 			u = sc.next();
	 			v = sc.next();
	 			Graph.addEdge(u, v);
	 		}

	 		p = sc.next();
	 		path[0] = p;
	 		for(int j=1; j<=Integer.parseInt(p); j++){ //p is integer
	 			path[j] = sc.next();
	 		}

	 		String output = processInput(Graph, N, path);

	 		pw.write(output);
	 		pw.write("\n");

	 	}
	 	pw.close();
	 }

	 private static String processInput(graphTransformation Graph, int noOfVertices, String[] path){
	 	String output = "YES";
	 	int len = Integer.parseInt(path[0]);
	 	String[] temp = new String [len-1];

	 	//remove the last element on the path to check for duplicates
	 	for(int i=0; i<len-1; i++){
	 		temp[i] = path[i+1];
	 	}

	 	//check the length
	 	if((noOfVertices != (len-1)) || (checkDuplicate(temp))){
	 		return "NO";
	 	}

	 	//check whether the first one and the last element is the same
	 	if(!(path[1].equals(path[len]))){
	 		return "NO";
	 	}

	 	//check whether there exist an edge
	 	for(int i=1; i<len-1; i++){
	 		if(!Graph.hasEdge(path[i], path[i+1])){
	 			return "NO";
	 		}
	 	}	

	 	return output;
	 }

	 //to check whether there is Duplicate
	 private static boolean checkDuplicate(String[] path){
	 	
	 	Set<String> tempSet = new HashSet<String>();
	 	for(String str: path){
	 		if(!tempSet.add(str)){
	 			return true;
	 		}
	 	}
	 	return false;
	}
	 
}

/**
 * A C-style struct definition of a Vertex to be used with
 * the Graph class.
 * <p>
 * The distance field is designed to hold the length of the
 * shortest unweighted path from the source of the traversal
 * <p>
 * The predecessor field refers to the previous field on
 * the shortest path from the source (i.e. the vertex one edge
 * closer to the source).
 *
 */
class Vertex implements Comparable<Vertex> {
    /**
     * label for Vertex
     */
    public String name;  
    /**
     * length of shortest path from source
     */
    public int distance; 
    /**
     * previous vertex on path from sourxe
     */
    public Vertex predecessor; // previous vertex
    
    /**
     * Infinite distance indicates that there is no path
     * from the source to this vertex
     */
    public static final int INFINITY = Integer.MAX_VALUE;

    public Vertex(String v)
    {
        name = v;
        distance = INFINITY; // start as infinity away
        predecessor = null;
    }

    /**
     * The name of the Vertex is assumed to be unique, so it
     * is used as a HashCode
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return name.hashCode();
    }
    
    public String toString()
    { 
        return name;
    }
    /**
     * Compare on the basis of distance from source first and 
     * then lexicographically
     */
    public int compareTo(Vertex other)
    {
        int diff = distance - other.distance;
        if (diff != 0)
            return diff;
        else
            return name.compareTo(other.name);
    }
}
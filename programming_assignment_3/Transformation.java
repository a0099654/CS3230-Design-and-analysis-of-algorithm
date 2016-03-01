import java.util.*;
import java.io.*;

public class Transformation{
	static HashSet<String> vertices = new HashSet<String>();
	static SortedSet<String> edges = new TreeSet<String>();

	public static void main (String[] args){
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		// use this (a much faster output routine) instead of Java System.out.println (slow)

		int T,N,K;

		T = sc.nextInt();

		for(int i=0; i<T; i++){
			N = sc.nextInt();
			K = sc.nextInt();
			sc.nextLine();

		
			//process the clause
			for(int j=1; j<=K; j++){
				String v1 = sc.next();
				String v2 = sc.next();
				String v3 = sc.next();

				vertices.add("C" + j);
				processClause(N, j, v1);
				processClause(N, j, v2);
				processClause(N, j, v3);

			}

			//add all vertices and first set of edges
			processA(N, K);

			pw.write(vertices.size() + " " + edges.size());
			pw.write("\n");

			Iterator edge = edges.iterator();
			while(edge.hasNext()){
				pw.write(edge.next().toString());
				pw.write("\n");
			}

			edges.clear();
			vertices.clear();
		}

		pw.close();
	}

	private static void processClause(int N, int K, String v){
		if(v.charAt(0) == '-'){
			edges.add("C"+ K + " " + "X" + v.substring(1) + "." + K + "." + "L");
			edges.add("X" +v.substring(1) + "." + K +"." + "R" + " " + "C" + K);
		}else{
			edges.add("C"+ K +" " +"X" + v +"." + K +"." +"R");
			edges.add("X" + v +"." + K + "." +"L" +" " +"C"+K);
		}
	}

	public static void processA(int N,int K){
		String tempL="";
    	String tempR="";
		vertices.add(getS());
    	vertices.add(getT());
		
		for(int x=1;x<=N;x++){	

    		String left = getLeft(x);
    		String right = getRight(x);
    		String buffer1 = getfirstBuffer(x);
    		
    		vertices.add(left);
    		vertices.add(right);
    		vertices.add(buffer1);

    		if(x!=1){
    			edges.add(tempL +" " +left);      //tempL:Ln left:Ln+1
    			edges.add(tempL +" " +right);
    			edges.add(tempR +" " +right);
    			edges.add(tempR +" " +left);
    		}

    		edges.add(left+" " +buffer1);
    		edges.add(buffer1 +" " +left);

    		edges.add(buffer1+ " " +getILeft(x,1));
    		edges.add(getILeft(x,1)+ " " +buffer1);
    		String bufferTemp="";
    		//internal vertices
    		for(int y=1;y<=K;y++){
    			
    			String ILeft = getILeft(x,y);
    			String IRight = getIRight(x,y);
    			String IBuffer = getIBuffer(x,y);

    			//ADD VERTICES
    			vertices.add(ILeft);
    			vertices.add(IRight);
    			vertices.add(IBuffer);
    			
    			

    			//CREATE FIRST SET OF EDGES
    			edges.add(ILeft +" " +IRight);
    			edges.add(IRight +" " +ILeft);

    			edges.add(IRight +" " +IBuffer);
    			edges.add(IBuffer +" " +IRight);

    			
    			
				if(y!=1){
    				edges.add(ILeft +" " +bufferTemp); //bufferTemp: Bx.y-1 
    				edges.add(bufferTemp +" " +ILeft);
    			}	
    			bufferTemp = IBuffer;
    			
    		}
    		
    		

    		edges.add(getIBuffer(x,K) +" " +right);
    		edges.add(right +" " +getIBuffer(x,K));
    		tempL= left;
    		tempR = right;


    	}

    	edges.add(getS()+" " +getLeft(1));
    	edges.add(getS()+" " +getRight(1));
    	edges.add(getRight(N)+" " +getT());
    	edges.add(getLeft(N)+" " +getT());
    	edges.add(getT() +" " +getS());

	}

	public static String getS(){
		return "S";
	}

	public static String getT(){
		return "T";
	}

	public static String getLeft(int v){
		return "L" + v;
	}

	public static String getRight(int v){
		return "R" + v;
	}

	public static String getfirstBuffer(int v){
		return "B" + v + ".0";
	}

	public static String getILeft(int v1,int v2){
		return "X" + v1 +"." + v2 +"."+"L";
	}

	public static String getIRight(int v1,int v2){
		return "X" + v1 +"." +v2 +"."+"R";
	}

	public static String getIBuffer(int v1,int v2){

    	return "B" + v1 +"." + v2;
	}

}
//Name:Stella Widyasari
//Student ID: A0099654L

import java.util.*;
import java.io.*;

public class polytimeReduction{
	
	public static void main (String[] args){
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		// use this (a much faster output routine) instead of Java System.out.println (slow)

		int T,N,M;
		int no_vertices = 0;
		String u,v;
		String transformed_u, transformed_v;
		String linked_HnV_forU, linked_HnV_forV;
		String linked_TnV_forU, linked_TnV_forV;
		String replacedInput;

		// to store the result
		HashSet<String> arrList = new HashSet<String>();

		T = sc.nextInt();

		for(int i=0; i<T; i++){
			N = sc.nextInt();
			M = sc.nextInt();

			no_vertices = N;
			//processing the input	
			for(int edge=0; edge < M; edge++){
				u = sc.next();
				v = sc.next();

				transformed_u = totransformString(u);
				transformed_v = totransformString(v);
				
				linked_HnV_forU = linkedHandV(transformed_u);
				arrList.add(linked_HnV_forU);

				linked_HnV_forV = linkedHandV(transformed_v);
				arrList.add(linked_HnV_forV);

				linked_TnV_forU = linkedTandV(transformed_u);
				arrList.add(linked_TnV_forU);

				linked_TnV_forV = linkedTandV(transformed_v);
				arrList.add(linked_TnV_forV);

				replacedInput = toReplaceLetter(u, v);
				arrList.add(replacedInput);

			}

			arrList.add((no_vertices*3) + " " + (arrList.size()));

			String[] output = arrList.toArray(new String[arrList.size()]);
			Arrays.sort(output);

			for(int index=0; index<output.length; index++){
				pw.write(output[index]);
				pw.write("\n");
	      	}
	
			arrList.clear();
		}
		pw.close();
	}

	// to add H and T in the string
	private static String totransformString(String input){
		String head_vertex = "H" + input.substring(1);
		String tailed_vertex = "T" + input.substring(1);

		return head_vertex + " " + input + " " + tailed_vertex;
	}

	// to link the H and V in the string
	private static String linkedHandV(String input){
		return input.substring(0,5) + " " + input.substring(6,11);
	}

	// to link the T and V in the string
	private static String linkedTandV(String input){
		return input.substring(12) + " " + input.substring(6,11);
	}

	// to replace the input letter from V11 V22 to H11 and T22
	private static String toReplaceLetter(String u, String v){
		String replaced_u = u.replace('V', 'H');
		String replaced_v = v.replace('V', 'T');

		return replaced_u + " " + replaced_v;
	}
}
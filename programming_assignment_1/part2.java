/*
 * Name : Stella Widyasari
 * Student ID : A0099654L
 */

import java.io.*;
import java.util.*;

class part2{
	public static void main (String[] args){
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		// use this (a much faster output routine) instead of Java System.out.println (slow)

		int no_testCases, base;
		String V,M;
		no_testCases = sc.nextInt();
		int[] resultArr;


		for(int i=0; i<no_testCases; i++){
			base = sc.nextInt();
			sc.nextLine();
			V = sc.nextLine();
			M = sc.nextLine();

			int[] arrV = convertStringToArr(V);
			int[] arrM = convertStringToArr(M);

			resultArr = karatsuba(arrV,arrM, base);
			
			String output = convertArrToString(resultArr);

			pw.write(output);  
			pw.write("\n");
		}
		pw.close();
	}

	/**
	 * Convert digit to int (for reading)
	 */
	private static int parseDigit(char c) {
		if (c <= '9') {
			return c - '0';
		} 
		return c - 'A' + 10;
	}
	
	/**
	 * Convert int to digit. (for printing)
	 */
	private static char toDigit(int digit) {
		if (digit <= 9) {
			return (char)(digit + '0');
		} 
		return (char)(digit - 10 + 'A');
	}
	/**
	 * Convert a string to an integer array
	 */
	private static int[] convertStringToArr(String input){

		int len = input.length();
		int[] convertTo = new int[len+1];
		int index = 1;

		convertTo[0] = len;
		
		for(int i=len-1; i>=0; i--){
			char assignedVal = input.charAt(i);
			convertTo[index]=parseDigit(assignedVal);
			index++;
		}

		return convertTo;
	}

	/**
	 * Convert an int array to a string
	 */
	private static String convertArrToString(int[] arr){
		int len = arr[0];
		StringBuilder sb = new StringBuilder();

		for(int i=len; i>=1; i--){
			char assignedVal = toDigit(arr[i]);
			sb.append(assignedVal);
		}
		return sb.toString();
	}

    /**
     * A = A + B * base ^ offset
     */
    private static void addTwoArrayWithOffset(int[]A, int[]B, int base, int offset){
    	int lenA = A[0];
    	int lenB = B[0];

    	int carry =0;
    	int i =1 ;
    	offset++;
    	int b;
    	int a;

    	while(i<=lenB || carry>0){
    		if(offset > lenA){
    			a = 0;
    		}else{
    			a = A[offset];
    		}
    		b = i>lenB?0:B[i];

    		carry = a + b + carry;
    		if(carry>=base){
    			A[offset] = carry%base;
    			carry = 1;
    		}else{
    			A[offset] = carry;             
    			carry = 0;
    		}

    		i++;
    		offset++;
    	};

    	offset--;
    	while(offset>1 && A[offset]==0)
    		offset--;

    	if(offset>lenA){
    		A[0] = offset;
    	}
    }

    /*
     * To multiply two arrays
     */
    private static int[] mulTwoArray(int[] A, int[] B, int base){
    	int[] temp = new int[40400];
    	int[] res = new int[40400];

    	for(int i=0; i<A[0]+2; i++){
    		res[i] = temp[i] = 0;
    	}

    	int lenA = A[0];
    	int lenB = B[0];

    	for(int i=1; i<=lenA;i++){
    		for(int j=1; j<=lenB; j++){
    			temp[i+j-1] += A[i] * B[j];
    		}
    	}

    	int lenR = lenA + lenB + 1;
    	int carry = 0;

    	for(int i=1; i<=lenR; i++){
    		carry += temp[i];
    		res[i] = carry%base;
    		carry = carry/base;
    	}

    	while(lenR > 1 && res[lenR] == 0)
    		lenR--;

    	res[0] = lenR;

    	return res;
	}

    /**
     * Perform subtraction of the array
     */
    private static int[] subtraction(int[]A, int[]B){

    	int lenA = A[0];
		int lenB = B[0];

		
		int minLen = Math.min(lenA, lenB);
		int maxLen = Math.max(lenA, lenB);
		int[] resultArr = new int[maxLen + 1];
		resultArr[0] = maxLen;

		// A[i] - B[i];
		// karatsuba subtraction is never negative
		for(int i=1; i<=minLen; i++){
			//subtraction
			if(A[i] < B[i]){
				A[i] = A[i] + 10;
				A[i+1] = A[i+1] - 1;     
				
			}
			resultArr[i] = A[i] - B[i];
		}
		
		if(A[0] < B[0]){
			for(int i=minLen+1; i<=B[0]; i++){
				resultArr[i] = B[i];
			}
		}else{
			for(int i=minLen+1; i<=A[0]; i++){
				resultArr[i] = A[i];

			}
		}
		return resultArr;
    }

	private static int[] splitLow(int[] A, int R){
		int[] arr = new int[40400];

		int len = A[0];

		if(len< R){
			if(len-R == 0){
				arr[0] = 1;
				arr[1] = 0;
			}else{
				for(int i=0; i<=len; i++){
					arr[i] = A[i];
				}
			}
		}else{
			arr[0] = R;
			for(int i=1; i<=R; i++){
				arr[i] = A[i];
			}
		}


		return arr;
	}

	private static int[] splitHigh(int[] A, int R){
		int[] arr = new int[40400];

		int len = A[0];
		int index = 1;

		if(len<R){
			arr[0] = 1;
			arr[1] = 0;
	
		}else{
			if(len%2 == 0){ //can remove lol
				arr[0] = R;
			}else{
				arr[0] = R -1;
			}
			
			for(int i=R+1; i<=len; i++){
				arr[index] = A[i];
				index++;
			}
			index = 1;

		}

		return arr;
	}

	/*
	 * to perform karatsuba multiplication
	 */
    private static int[] karatsuba(int[]A, int[]B, int base){
    	int len = A[0];
    	int cutOff = 4000;

    	if(len < B[0]){
    		len = B[0];
    	}

    	if(len <= cutOff){
    		return mulTwoArray(A, B, 10); 
    	}
    	else{
    		
    		int R = len/2+ len%2;
       		int[] highA = splitHigh(A,R);     
    		int[] lowA = splitLow(A,R);
    		int[] highB = splitHigh(B,R);

    		int[] lowB = splitLow(B,R);
    		int[] Z0 = karatsuba(lowA, lowB, base);
    		int[] Z2 = karatsuba (highA, highB, base);

    		addTwoArrayWithOffset(lowA, highA, base, 0);
    		addTwoArrayWithOffset(lowB, highB, base, 0);
    		int[] Z1 = karatsuba(lowA, lowB, base);


    		// subtracting and adding result
    		int[] firstSub = subtraction(Z1,Z2);
    		int[] finalResult = subtraction(firstSub,Z0);

    		addTwoArrayWithOffset(Z0, finalResult, base, R);
    		addTwoArrayWithOffset(Z0, Z2, base, 2*R);

    		return Z0;
    	}
    }

}
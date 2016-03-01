// Name : Stella Widyasari
// Matric No : A0099654L

import java.io.*;
import java.util.*;

class abc{
	public static void main (String[] args){
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		// use this (a much faster output routine) instead of Java System.out.println (slow)

		int no_testCases, base;
		String V, M;
		no_testCases = sc.nextInt();
		int[] resultArr;
		int no_DecimalPlace = 0;

		for(int i=0; i<no_testCases; i++){
			base = sc.nextInt();
			sc.nextLine();
			V = sc.nextLine();
			M = sc.nextLine();

			int[] arrV = convertStringToArr(V);
			int[] arrM = convertStringToArr(M);
			no_DecimalPlace = 0;  

			no_DecimalPlace = no_DecimalPlace + countDecPlace(V);
			no_DecimalPlace = no_DecimalPlace + countDecPlace(M);

			resultArr = mulTwoArray(arrV,arrM,base);
			
			String output = convertArrToString(resultArr, no_DecimalPlace);

			pw.write(trimZeros(output));  
			pw.write("\n");
			           //to reset no_DecimalPlace
		}
		pw.close();
	}

	/**
	 * Use to trim leading and trailing zeros on a result string.
	 */
	private static String trimZeros(String input) {
		int left = 0;
		int right = input.length()-1;
		int fp = input.indexOf('.');
		if (fp == -1) {
			fp = input.length();
		}
		
		while(left < fp-1) {
			if (input.charAt(left) != '0')
				break;
			left++;
		}
		
		while (right >= fp) {
			if (input.charAt(right) != '0') {
				if (input.charAt(right) == '.')
					right--;
				break;
			}
			right--;
		}
		
		if (left >= fp)
			return "0" + input.substring(left,right+1);
		return input.substring(left,right+1);
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
	 * Convert a string to int
	 */
	private static int[] convertStringToArr(String input){

		int len = input.length();
		int[] convertTo = new int[len+1];
		int index = 1;

		convertTo[0] = len;
		
		for(int i=len-1; i>=0; i--){
			char assignedVal = input.charAt(i);
			if(assignedVal!='.'){
				convertTo[index]=parseDigit(assignedVal);
				index++;
			}
			
		}

		return convertTo;
	}

	/**
	 * Count the number of decimal place
	 */
	private static int countDecPlace(String input){
		int len = input.length();
		int no_decPlace = 0;
		
		for(int i=len-1; i>=0; i--){
			char assignedVal = input.charAt(i);
			

			if(assignedVal == '.'){
				no_decPlace = no_decPlace + (len-1-i);
			}
		}
		return no_decPlace;
	}

	/**
	 * Convert an int array to char array
	 */
	private static String convertArrToString(int[] arr, int decimalPlace){
		int len = arr[0];
		StringBuilder sb = new StringBuilder();

		for(int i=len; i>=1; i--){
			char assignedVal = toDigit(arr[i]);
			sb.append(assignedVal);
		}

		if(decimalPlace > 0){
			if(decimalPlace < sb.length()){
				sb.insert(sb.length()-decimalPlace, ".");
			}else{
				for(int j=0; j<decimalPlace; j++){
					sb.insert(0, "0");
				}
				sb.insert(sb.length()-decimalPlace,".");
			}
		}
		return sb.toString();
	}

	/**
	 * Multiply a digit "d"(integer value) with an array "arr"
	 */
    private static void mulDigit(int[]arr, int d, int base){
    	int arrLen = arr[0];
    	int carry = 0;
    	int product = 0;

    	for(int i = 1; i<=arrLen; i++){
    		product = arr[i] * d+ carry;
    		arr[i] = product%base;
    		carry = product/base;
    	}

    	if(carry > 0){
    		arrLen++;
    		arr[arrLen] = carry;
    		
    	}

    	arr[0] = arrLen;   //update the length of the array
    }

    /**
     * Summing two arrays with offset (the amount of shifts require)
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

    /**
     * Multiplying two arrays
     */
    private static int[] mulTwoArray(int[] A, int[] B, int base){

    	int lenA = A[0];
    	int[] C = Arrays.copyOf(A, 5005+1);    //temporal array to store intermediate result
    	int[] D = new int[10010];   				   // array to store final answers

    	int lenB = B[0];
    	for(int i=1; i<=lenB; i++){
    		int d = B[i];
    		C = Arrays.copyOf(A, 5005+1);
    		mulDigit(C, d, base);
    		addTwoArrayWithOffset(D, C, base, i-1);
    	}

    	return D;
    }

}
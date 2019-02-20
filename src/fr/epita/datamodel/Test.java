package fr.epita.datamodel;

import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;

public class Test {
	
	public static void main (String [] args) {
		
		ArrayList <ArrayList<String>> test = new ArrayList<ArrayList<String>>();
		ArrayList <String> element = new ArrayList<String>();
		test.add(element);
		//System.out.println(test);
		element.add("Russel");
		test.add(element);
		//System.out.println(test);
		element.clear();
		element.add("Russel is");
		element.add("the  best");
		test.add(element);
		//System.out.println(test);
		element.add("guy");
		test.add(element);
		//System.out.println(test);
		
		ArrayList <ArrayList<Integer>> temp = new ArrayList <ArrayList<Integer>>();
		
		for (int a = 1; a < 4 ; a++) {
			//System.out.println(a);
			//array.add(a);
			ArrayList <Integer> array = new ArrayList <Integer>();
			for (int i = 0; i < 8; i++) {
				//System.out.println(i);
				array.add(i*a);
				
			}
			//System.out.println(array.iterator());
			temp.add(array);
			//System.out.println(array);
					
		}
		System.out.println(temp);
		System.out.println(temp.toString());
		if (temp.toString().equals(temp.toString())) {
			System.out.println("equals");
		}else {
			System.out.println("not equals");
		}
		for (ArrayList<Integer> array : temp) {
			System.out.println(array);
			System.out.println(array.get(5));
			//for (int j = 0; j < array.size(); j++) {
				
			//}
			
		}
		//System.out.println(temp.listIterator());
		
		System.out.println(0/1);
		

		Boolean inut = false;
		Boolean inot = null;
		Boolean inat = true;
		System.out.println(inat);
		System.out.println(inot);
		System.out.println(inut);
		if (inat) {
			System.out.println("adsadasd");
		}
		if (inot == null) {
			System.out.println("mother");
		}
		for (char a = 'a' ; a > 'd' ; a++) {
			System.out.println(a);
		}
	}
	
	

}

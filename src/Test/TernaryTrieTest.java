package Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


import View.TernaryTrie;

public class TernaryTrieTest {
	TernaryTrie trie = new TernaryTrie("data.trie.test");
	String[] keys = {
			"Fuglevej##Lille Skensved;1", 
			"Lindingvadvej##Tjæreborg;2", 
			"Kirkevang##Stenløse;3", 
			"Kindertofte Skolevej##Slagelse;4", 
			"Mågevej#6950#Ringkøbing;5", 
			"Kalkbrænderiløbskaj#2100#København Ø;6", 
			"Over Isen Vej#7430#Ikast;7", 
			"Næstved#4700#Ahornvej;8", 
			"Randers C#8900#Lindholtsvej;9", 
			"Vedbæk#2950#Ved Vejen;10", 
			"Otterup##Franketoftegyden;11", 
			"Kongens Lyngby##Om Kæret;12", 
			"Kibæk##Troldhedevej;13",
			"Køge##;14"
			};

	@Test
	public void testGet(){
		for (String s : keys) {
			String[] spltd = s.split(";");
			assertEquals(spltd[1], trie.get(spltd[0].toLowerCase()));			
		}
	}
	
	@Test
	public void testFalseGet() {
		assertEquals(null, trie.get("lars")); //invalid string
	}
	
	@Test
	//Check if the strings returned by the startsWith(String) method are compliant with the strings recognized by the get(String) method  	
	public void testPrefix_GetComnpliance() {
		Map<String, String> pre = new HashMap<String, String>();
		//Fill the map with entries like this: [first char in location] -> [node id]
		for (String s : keys) pre.put(s.substring(0, 1), s.split(";")[1]);
		
		//check that each string returned by prefix searches are valid as parameters for the get method, and check that the  
		for (String s : pre.keySet()) {
			for (String k : trie.startsWith(s)) {
				assertEquals(pre.get(s), trie.get(s));
			}
		}
	}
	
	@Test
	public void testNumberOfPrefixMatches(){
		int i=0;
		for (String s : trie.startsWith("k")){
			i++;
		}
		assertEquals(6, i);
	}
	
	@Test
	public void testFalsePrefix() {
		int i=0;
		for (String s : trie.startsWith("lars")) { //an invalid prefix
			i++;
		}
		assertEquals(0, i);
	}
}

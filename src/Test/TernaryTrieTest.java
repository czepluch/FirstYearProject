package Test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

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
	
//	@Test
//	//Check if the strings returned by the startsWith(String) method are compliant with the strings recognized by the get(String) method  	
//	public void testPrefix_GetCompliance() {
//		//check that each string returned by prefix searches are valid as parameters for the get method.
//		for (String s : ids.keySet()) {
//			String pre = s.substring(0, 1).toLowerCase();
//			for (String k : trie.startsWith(pre)) {
//				System.out.println("\t" + pre + "\t" + k + "\t" + trie.get(k));
//				assertEquals(ids.get(k), trie.get(k));
//				
//			}
//		}
//	}
	
	@Test
	//Check if the strings returned by the startsWith(String) method are compliant with the strings recognized by the get(String) method  	
	public void testPrefix_GetCompliance() {
		Set<String> prefixes = new HashSet<String>();
		for (String p : keys) prefixes.add(p.substring(0, 1).toLowerCase());
		//check that each string returned by prefix searches are "valid" as parameters for the get method.
		for (String s : prefixes) {
			for (String k : trie.startsWith(s)) {
				System.out.println("\t" + s + " & " + k + " & $\\surd$");
				assertNotNull(trie.get(k));
			}
		}
	}
	
	@Test
	public void testNumberOfPrefixMatches(){
		int i=0;
		for (String s : trie.startsWith("k")){
			i++;
		}
		assertEquals(6, i); //hardcoded :(
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

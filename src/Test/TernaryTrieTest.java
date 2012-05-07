package Test;

import java.util.Iterator;

import View.TernaryTrie;

public class TernaryTrieTest {
	TernaryTrie tt;
	public TernaryTrieTest() {
		System.out.println("Loading trie");
		long start = System.currentTimeMillis();
		tt = new TernaryTrie();
		long stop = System.currentTimeMillis();
		System.out.println("Done loading trie "+ (stop-start)/1000 + " seconds");
//		for (String s : tt.startsWith("københavn")) {
//			System.out.println(s);
//		}
	} 
	
	public long accumPrefSearch(String pref) {
		System.out.println("Running accumulative pref-search on " + pref);
		long start = System.currentTimeMillis(); 
		for(int i=1; i<=pref.length(); i++){
			String nPref=pref.substring(0, i);
			System.out.println("Doing pref-search for " + nPref);
			printPS(nPref);
		}
		return System.currentTimeMillis()-start;
	}
	
	private void printPS(String pref) {
		tt.startsWith(pref);
//		for (String s : tt.startsWith(pref)) {
//			System.out.println(s);
//		}
	}
	
	public static void main(String[] args) {
		TernaryTrieTest ttt = new TernaryTrieTest();
		if (args.length > 0) {
			//det tager ~6 gange så lang tid hvis den også skal printe
			System.out.println("Accumalative time for pref-searches of " + args[0] + " in milliseconds: " + ttt.accumPrefSearch(args[0]));
		}
	}
}

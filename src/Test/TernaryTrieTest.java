package Test;

import View.TernaryTrie;

public class TernaryTrieTest {
	TernaryTrie tt;
	public TernaryTrieTest() {
		System.out.println("Loading trie");
		long start = System.currentTimeMillis();
		tt = new TernaryTrie();
		long stop = System.currentTimeMillis();
		System.out.println("Done loading trie");
		System.out.println(tt.get("ryryryryry"));
	} 
	
	public static void main(String[] args) {
		new TernaryTrieTest();
	}
}

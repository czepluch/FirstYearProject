package View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressParser {
	private final static String CITY_STREET_REGEX_PART = 	"[-a-zæøåäöüA-ZÆØÅÄÖÜ.\\s\']";
	private final static String CRAP_REGEX_PART = 			"[^a-zæøåäöüA-ZÆØÅÄÖÜ0-9]";
	private final static String ZIP_REGEX_PART = 			"[0-9]{3,5}";
	private final static String END_REGEX =					CRAP_REGEX_PART + "*[.]*";
	
	private final static String ZIP_LAST_REGEX = CRAP_REGEX_PART + "*" +
													"(" + CITY_STREET_REGEX_PART + "+)" +
												 CRAP_REGEX_PART + "*" + 
												 	"(" + CITY_STREET_REGEX_PART + "*)" +
												 CRAP_REGEX_PART + "*" +
												 	"([" + ZIP_REGEX_PART + "]*)" + 
												 END_REGEX;
	
	private final static String ZIP_FIRST_REGEX = CRAP_REGEX_PART + "*" +
														"(" + ZIP_REGEX_PART + ")" +
												  CRAP_REGEX_PART + "*" + 
												  		"(" + CITY_STREET_REGEX_PART + "*)" +
												  CRAP_REGEX_PART + "*" +
												  		"(" + CITY_STREET_REGEX_PART + "*)" + 
												  END_REGEX;
	
	private final static String ZIP_MIDDLE_REGEX = CRAP_REGEX_PART + "*" +
														"(" + CITY_STREET_REGEX_PART + "+)" +
												   CRAP_REGEX_PART + "*" + 
												   		"(" + ZIP_REGEX_PART + ")" +
												   CRAP_REGEX_PART + "*" +
												   		"(" + CITY_STREET_REGEX_PART + "*)" +
												   END_REGEX;
	
	private static final Pattern ZIP_LAST_PATTERN = Pattern.compile(ZIP_LAST_REGEX);
	private static final Pattern ZIP_MIDDLE_PATTERN = Pattern.compile(ZIP_MIDDLE_REGEX);
	private static final Pattern ZIP_FIRST_PATTERN = Pattern.compile(ZIP_FIRST_REGEX);
	
	/**
	 * Parses the given input String to an address
	 * @param s the input String
	 * @return	the parse address
	 */
	public static String parseAddress(String s) {
		String city = "";
		String street = "";
		String zip = "";
		
		// Clean the input for numbers that do not represent a zip code
		s = s.replaceAll("[0-9]{6,}", "");
		s = s.replaceAll("(?<!([0-9]))[0-9]{2}(?!([0-9]))", "");
		s = s.replaceAll("(?<!([0-9]))[0-9]{1}(?!([0-9]))", "");
		
		Matcher zipLastMatcher = ZIP_LAST_PATTERN.matcher(s);
		Matcher zipMiddleMatcher = ZIP_MIDDLE_PATTERN.matcher(s);
		Matcher zipFirstMatcher = ZIP_FIRST_PATTERN.matcher(s);
		
		if (zipLastMatcher.matches()) {
			city = zipLastMatcher.group(1).toLowerCase();
			street = zipLastMatcher.group(2).toLowerCase();
			zip = zipLastMatcher.group(3).toLowerCase();
		} else if (zipMiddleMatcher.matches()) {
			city = zipMiddleMatcher.group(1).toLowerCase();
			zip = zipMiddleMatcher.group(2).toLowerCase();
			street = zipMiddleMatcher.group(3).toLowerCase();
		} else if (zipFirstMatcher.matches()) {
			zip = zipFirstMatcher.group(1).toLowerCase();
			city = zipFirstMatcher.group(2).toLowerCase();
			street = zipFirstMatcher.group(3).toLowerCase();
		} else {
			return null;
		}
		
		// Make sure no values display null and trim the address parts
		if (city == null) 	city = "";
		else				city = city.trim();
		if (street == null) street = "";
		else				street = street.trim();
		if (zip == null) 	zip = "";
		else				zip = zip.trim();
		
		return String.format("%s#%s#%s", city, zip, street);
	}
	
	/**
	 * Parses the address using the parseAddress method and then cleans
	 * up the address for it to be used with the trie
	 * @param s	The input String to be interpreted as an address
	 * @return	The parsed and cleaned up address String
	 */
	public static String parseAddressLive(String s) {
		String address = parseAddress(s);
		if (address != null) {
			String[] parts = address.split("#");
			int max = 0;
			int min = 0;
			
			// Find the last part that is "in use"
			for (max = parts.length - 1; max >= 0; max--)
			{
				if (parts[max].length() > 0) break;
			}
			
			// Find the first part that is "in use"
			for (min = 0; min <= max; min ++) {
				if (parts[min].length() > 0) break;
			}
			
			// Put together the new address
			address = parts[min];
			for (min++; min <= max; min++) {
				address += "#" + parts[min];
			}
		}
		return address;
	}
	
	/**
	 * Checks if the given string argument can be interpreted
	 * as an ascii binary
	 * @param s	The String to be interpreted
	 * @return	Whether or not the given String can be interpreted
	 */
	public static boolean isBinary(String s) {
		Pattern binaryPattern = Pattern.compile("[01]+");
		Matcher m = binaryPattern.matcher(s);
		if ((s.length() % 8 == 0) && (m.matches())) return true;
		return false;
	}
	
	/**
	 * Assumes the given string can be interpreted as a binary number
	 * Converts the binary number to a String
	 * @param s	The String to be converted
	 * @return	The converted String
	 */
	public static String convertBinaryToString(String s) {
		String[] chars = new String[s.length() / 8];
		int lo;
		int hi;
		int i;
		for (lo = 0, hi = 8, i = 0; hi <= s.length(); lo += 8, hi += 8, i++) {
			chars[i] = s.substring(lo, hi);
		}
		StringBuilder sb = new StringBuilder();
		for (String charString : chars) {
			int charInt = Integer.parseInt(charString, 2);
			sb.append((char) charInt);
		}
		return sb + "";
	}
	
	/**
	 * Simple test client
	 * @param args - Unused
	 */
	public static void main(String[] args) {
		String s = "Køge 5123 la";
		System.out.println("Input:\t" + s);
		System.out.println(AddressParser.parseAddress(s));
	}
}

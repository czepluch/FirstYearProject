package View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewAddressParser {
	private final static String CITY_STREET_REGEX_PART = 	"[-a-zæøåA-ZÆØÅ.\\s\']";
	private final static String CRAP_REGEX_PART = 			"[-,\\.\\s]";
	private final static String ZIP_REGEX_PART = 			"[0-9]{3,5}";
	
	private final static String ZIP_LAST_REGEX = CRAP_REGEX_PART + "*" +
													"(" + CITY_STREET_REGEX_PART + "+)" +
												 CRAP_REGEX_PART + "*" + 
												 	"(" + CITY_STREET_REGEX_PART + "*)" +
												 CRAP_REGEX_PART + "*" +
												 	"([" + ZIP_REGEX_PART + "]*)";
	
	private final static String ZIP_FIRST_REGEX = CRAP_REGEX_PART + "*" +
														"(" + ZIP_REGEX_PART + ")" +
												  CRAP_REGEX_PART + "*" + 
												  		"(" + CITY_STREET_REGEX_PART + "*)" +
												  CRAP_REGEX_PART + "*" +
												  		"(" + CITY_STREET_REGEX_PART + "*)";
	
	private final static String ZIP_MIDDLE_REGEX = CRAP_REGEX_PART + "*" +
														"(" + CITY_STREET_REGEX_PART + "+)" +
												   CRAP_REGEX_PART + "+" + 
												   		"(" + ZIP_REGEX_PART + ")" +
												   CRAP_REGEX_PART + "*" +
												   		"(" + CITY_STREET_REGEX_PART + "*)";
	
	private static final Pattern ZIP_LAST_PATTERN = Pattern.compile(ZIP_LAST_REGEX);
	private static final Pattern ZIP_MIDDLE_PATTERN = Pattern.compile(ZIP_MIDDLE_REGEX);
	private static final Pattern ZIP_FIRST_PATTERN = Pattern.compile(ZIP_FIRST_REGEX);
	

	public static String parseAddress(String s) {
		String city = "";
		String street = "";
		String zip = "";
		
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
		
		if (city == null) 	city = "";
		if (street == null) street = "";
		if (zip == null) 	zip = "";
		
		return String.format("%s#%s#%s", city, zip, street);
	}
	
	/**
	 * Test client
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "Køge";
		System.out.println("Input:\t" + s);
		System.out.println(NewAddressParser.parseAddress(s));
	}
}

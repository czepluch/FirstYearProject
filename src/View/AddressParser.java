package View;

import java.util.regex.*;

public class AddressParser {
	
	public static String[] parseAddress(String s) throws IllegalArgumentException
	{
		String street, number, letter, floor, zip, city;
		street = number = letter = floor = zip = city = "";
		String regex = "([-a-zæøåA-ZÆØÅ.\\s\']+)[,\\s]*([0-9]{0,3})[,\\s]*([a-zæøåA-ZÆØÅ]?)" +
						"[.[,]]*\\s*(\\s[0-9]{1,3}[.]?\\s*(sal[\\s[,]]*)?([tT][[hH][vV]]\\s*[.]?,*)?)?" +
						"\\s*([,\\s][0-9]{4})?[,\\s]*(\\s[-a-zæøåA-ZÆØÅ.\\s\']+)?";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(s);

		if (m.matches())
		{
			if (m.group(1) != null)
			{
				street = m.group(1);
				street = street.replaceAll("[-.']", "").replaceAll("\\s+", " ").trim();
				if (street.length() < 2) throw new IllegalArgumentException("Invalid street name length");
			}
			else throw new IllegalArgumentException();
			if (m.group(2) != null) number = m.group(2);
			if (m.group(3) != null) letter = m.group(3);
			if (m.group(4) != null)
			{
				floor = m.group(4);
				floor = floor.replaceAll("[^0-9]", "");
				if (m.group(6) != null) floor = floor + " " + m.group(6);
				floor = floor.replaceAll("[.[,]]", "").trim();
			}
			if (m.group(7) != null) zip = m.group(7).replaceAll(",","").trim();
			if (m.group(8) != null)
			{
				city = m.group(8);
				city = city.replaceAll("[-.']", "").replaceAll("\\s+", " ").trim();
				if (city.length() < 2 || city.charAt(1) == ' ') throw new IllegalArgumentException("Invalid city name length");
			}
			if (zip.isEmpty() && city.isEmpty()) throw new IllegalArgumentException("Must contain zip and/or city");			
		}
		else throw new IllegalArgumentException();
		
		String[] addr = {street, number, letter, floor, zip, city};
		return addr;
	}
	

	public static void main(String args[])
	{
		AddressParser p = new AddressParser();
		String[] addr;
		try {
			addr = p.parseAddress("Vestergade 5, 5. sal Tv København");
			for (String s : addr)
				System.out.println(s);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


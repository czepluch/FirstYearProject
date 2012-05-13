package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import View.AddressParser;

public class AddressParserTest {

	// Tests for category A
	// _______________________________________________________
	
	@Test
	public void testParseAddressA1() {
		String input = "Odense";
		String expectedOutput = "odense##";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressA2() {
		String input = "! Odense";
		String expectedOutput = "odense##";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressA3() {
		String input = "+Odense 9/?";
		String expectedOutput = "odense##";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressA4() {
		String input = "Ødense æå by üäÖ";
		String expectedOutput = "ødense æå by üäö##";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressA5() {
		String input = "Odense-By";
		String expectedOutput = "odense-by##";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressA6() {
		String input = "Odense' den fine by";
		String expectedOutput = "odense' den fine by##";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category B
	// _______________________________________________________
	
	@Test
	public void testParseAddressB1() {
		String input = "1234";
		String expectedOutput = "#1234#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressB2() {
		String input = ",!1234";
		String expectedOutput = "#1234#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressB3() {
		String input = "??1234  )";
		String expectedOutput = "#1234#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressB4() {
		String input = "-1234";
		String expectedOutput = "-#1234#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressB5() {
		String input = "12";
		String expectedOutput = "null";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressB6() {
		String input = "123456";
		String expectedOutput = "null";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category C
	// _______________________________________________________
	
	@Test
	public void testParseAddressC1() {
		String input = "8765 kØGe";
		String expectedOutput = "køge#8765#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressC2() {
		String input = "=?8765 kØGe";
		String expectedOutput = "køge#8765#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressC3() {
		String input = "8765 kØGe=?";
		String expectedOutput = "køge#8765#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressC4() {
		String input = "køge 8765";
		String expectedOutput = "køge#8765#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressC5() {
		String input = "køge,?=!8765";
		String expectedOutput = "køge#8765#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category D
	// _______________________________________________________
	
	@Test
	public void testParseAddressD1() {
		String input = "Skive, vejen, 4567";
		String expectedOutput = "skive#4567#vejen";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressD2() {
		String input = "Skive, vejen4567";
		String expectedOutput = "skive#4567#vejen";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressD3() {
		String input = "Skive,vejen 4567";
		String expectedOutput = "skive#4567#vejen";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category E
	// _______________________________________________________
	
	@Test
	public void testParseAddressE1() {
		String input = "Rådby9876allegade";
		String expectedOutput = "rådby#9876#allegade";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressE2() {
		String input = "Rådby 9876 allegade";
		String expectedOutput = "rådby#9876#allegade";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressE3() {
		String input = "Rådby?=+\" +?3?9876 allegade";
		String expectedOutput = "rådby#9876#allegade";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category F
	// _______________________________________________________
	
	@Test
	public void testParseAddressF1() {
		String input = "malmö Vegen 95667";
		String expectedOutput = "malmö vegen#95667#";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressF2() {
		String input = "malmö,Vegen,95667";
		String expectedOutput = "malmö#95667#vegen";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category G
	// _______________________________________________________
	
	@Test
	public void testParseAddressG1() {
		String input = "københavn, roskilde, ålbæk";
		String expectedOutput = "null";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category H
	// _______________________________________________________
	
	@Test
	public void testParseAddressH1() {
		String input = "brøndby-øster +12+3 03+1 9,!?Nygade";
		String expectedOutput = "brøndby-øster##nygade";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category I
	// _______________________________________________________
	
	@Test
	public void testParseAddressI1() {
		String input = "1234 56789";
		String expectedOutput = "null";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressI2() {
		String input = "1234,56789";
		String expectedOutput = "null";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	
	// Tests for category J
	// _______________________________________________________
	
	@Test
	public void testParseAddressJ1() {
		String input = "!?23++";
		String expectedOutput = "null";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testParseAddressJ2() {
		String input = "";
		String expectedOutput = "null";
		String output = AddressParser.parseAddress(input);
		if (output == null) output = "null";
		assertEquals(expectedOutput, output);
	}
}

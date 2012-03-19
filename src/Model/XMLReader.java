package Model;

import java.util.ArrayList;

import nu.xom.*;

/*
 * Has the responsibility of reading XML-files of the KRAX-format,
 * so the data can be stored
 */
public class XMLReader extends ArrayList<Data> 
{
	public XMLReader(String file) throws Exception
	{
		Document doc = new Builder().build(file);
	    Elements elements = doc.getRootElement().getChildElements();
	    for(int i = 0; i < elements.size(); i++) add(new Data(elements.get(i)));
	}
}

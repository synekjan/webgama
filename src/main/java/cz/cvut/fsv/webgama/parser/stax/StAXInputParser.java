/**
 * Main input parser using Streaming API for XML
 */
package cz.cvut.fsv.webgama.parser.stax;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.parser.InputParser;

/**
 * @author Jan Synek
 * 
 */
public class StAXInputParser implements InputParser {
	
	private static final Logger logger = LoggerFactory.getLogger(StAXInputParser.class);

	@Override
	public Input parseInput(InputStream stream) {
		
		Input input = new Input();
		
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(stream);
			
			while (reader.hasNext()) {
			
				input.setVersion("2.0");
				
				
				
				
			}
			
		
		
		
		
		
		} catch (XMLStreamException e) {
			
			logger.warn("unexpected parse error");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void composeInput() {
		// TODO Auto-generated method stub

	}

}

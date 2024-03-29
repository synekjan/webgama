package cz.cvut.fsv.webgama.parser;

import java.io.InputStream;
import java.io.OutputStream;

import cz.cvut.fsv.webgama.domain.Input;

/**
 * @author Jan Synek
 * 
 */
public interface InputParser {

	public Input parseInput(InputStream stream);

	public void composeInput(OutputStream stream, Input input);

}

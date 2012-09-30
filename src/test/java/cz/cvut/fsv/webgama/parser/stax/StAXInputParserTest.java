package cz.cvut.fsv.webgama.parser.stax;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Timed;

import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.parser.InputParser;

public class StAXInputParserTest {

	private InputStream inputStream = null;
	private InputStream inputStream2 = null;
	private InputParser inputParser = null;

	@Before
	public void setUp() throws Exception {
		inputParser = new StAXInputParser();
		inputStream = this.getClass()
				.getResourceAsStream("dve-osnovy-uhel.xml");
		inputStream2 = this.getClass().getResourceAsStream("seq-dsuloha-d.xml");

	}

	@Test
	@Timed(millis = 1000)
	public final void testParseInput() {
		Input input = inputParser.parseInput(inputStream);
		assertEquals("left-handed", input.getNetwork().getAngles());
		assertEquals(Double.valueOf(10.0), input.getNetwork().getSigmaApr(),
				0.0000001);
		assertEquals(5, input.getNetwork().getPoints().size());
		assertEquals(5, input.getNetwork().getObservations().size());

		Input input2 = inputParser.parseInput(inputStream2);
		assertEquals(Double.valueOf(0.95), input2.getNetwork().getConfPr(),
				0.0000001);
	}

	@Test
	public final void testComposeInput() {

		Input input = inputParser.parseInput(inputStream);
		inputParser.composeInput(System.out, input);
	}

}

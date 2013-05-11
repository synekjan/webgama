package cz.cvut.fsv.webgama.parser.stax;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Timed;

import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.parser.InputParser;

public class StAXInputParserTest {

	private InputStream inputStream = null;
	private InputStream inputStream2 = null;
	private InputStream inputStream3 = null;
	private InputParser inputParser = null;

	@Before
	public void setUp() throws Exception {
		inputParser = new StAXInputParser();
		inputStream = this.getClass().getResourceAsStream("dve-osnovy-uhel.xml");
		inputStream2 = this.getClass().getResourceAsStream("seq-dsuloha-d.xml");
		inputStream3 = this.getClass().getResourceAsStream("tst-tetrahedron-1.gkf");
	}

	@Test
	@Timed(millis = 1000)
	public final void testParseInput() {
		Input input = inputParser.parseInput(inputStream);
		assertEquals("left-handed", input.getNetwork().getAngles());
		assertEquals(Double.valueOf(10.0), input.getNetwork().getSigmaApr(), 0.0000001);
		Input input2 = inputParser.parseInput(inputStream2);
		assertEquals(Double.valueOf(0.95), input2.getNetwork().getConfPr(), 0.0000001);
	}

	@Test
	@Repeat(value = 100)
	public final void testComposeInput() {

		Input input = inputParser.parseInput(inputStream2);
		Input input3 = inputParser.parseInput(inputStream3);
		assertNotNull(input);

		OutputStream baos = new ByteArrayOutputStream(10000);

		inputParser.composeInput(baos, input3);
		String str = baos.toString();
		assertTrue(str.contains("<dh from=\"1\" to=\"2\" val=\"0.061\" stdev=\"3.9\""));
		assertTrue(str.contains("val=\"266.666543\""));
		assertTrue(str.contains("to=\"3\" val=\"160.814358\""));
		/* System.out.println(str); */
	}

}

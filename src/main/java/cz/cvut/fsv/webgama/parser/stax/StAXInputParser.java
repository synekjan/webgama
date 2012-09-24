/**
 * Main input parser using Streaming API for XML
 */
package cz.cvut.fsv.webgama.parser.stax;

import java.io.InputStream;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cvut.fsv.webgama.domain.Angle;
import cz.cvut.fsv.webgama.domain.Direction;
import cz.cvut.fsv.webgama.domain.Distance;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.Point;
import cz.cvut.fsv.webgama.domain.SlopeDistance;
import cz.cvut.fsv.webgama.domain.ZenithAngle;
import cz.cvut.fsv.webgama.parser.InputParser;

/**
 * @author Jan Synek
 * 
 */
public class StAXInputParser implements InputParser {

	private static final Logger logger = LoggerFactory
			.getLogger(StAXInputParser.class);

	@Override
	public Input parseInput(InputStream stream) {

		Input input = new Input();

		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader reader = factory.createXMLEventReader(stream);

			Network network = null;
			Point point = null;
			Observation observation = null;
			Direction direction = null;
			Distance distance = null;
			Angle angle = null;
			SlopeDistance slopeDistance = null;
			ZenithAngle zenithAngle = null;

			while (reader.hasNext()) {

				XMLEvent event = reader.nextEvent();

				switch (event.getEventType()) {

				// Parses all start elements and their attributes
				case XMLStreamConstants.START_ELEMENT:

					StartElement startElement = event.asStartElement();

					if ("direction".equals(startElement.getName()
							.getLocalPart())) {
						direction = new Direction();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("to".equals(attribute.getName().getLocalPart())) {
								direction.setTo(attribute.getValue());
								continue;
							}
							if ("val"
									.equals(attribute.getName().getLocalPart())) {
								direction.setVal(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("stdev".equals(attribute.getName()
									.getLocalPart())) {
								direction.setStdev(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("from_dh".equals(attribute.getName()
									.getLocalPart())) {
								direction.setFromDh(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("to_dh".equals(attribute.getName()
									.getLocalPart())) {
								direction.setToDh(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
						}
						continue;
					}

					if ("distance"
							.equals(startElement.getName().getLocalPart())) {
						distance = new Distance();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("from".equals(attribute.getName()
									.getLocalPart())) {
								distance.setFrom(attribute.getValue());
								continue;
							}
							if ("to".equals(attribute.getName().getLocalPart())) {
								distance.setTo(attribute.getValue());
								continue;
							}
							if ("val"
									.equals(attribute.getName().getLocalPart())) {
								distance.setVal(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("stdev".equals(attribute.getName()
									.getLocalPart())) {
								distance.setStdev(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("from_dh".equals(attribute.getName()
									.getLocalPart())) {
								distance.setFromDh(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("to_dh".equals(attribute.getName()
									.getLocalPart())) {
								distance.setToDh(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
						}

						continue;
					}

					if ("angle".equals(startElement.getName().getLocalPart())) {
						angle = new Angle();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("from".equals(attribute.getName()
									.getLocalPart())) {
								angle.setFrom(attribute.getValue());
								continue;
							}
							if ("bs".equals(attribute.getName().getLocalPart())) {
								angle.setBs(attribute.getValue());
								continue;
							}
							if ("fs".equals(attribute.getName().getLocalPart())) {
								angle.setFs(attribute.getValue());
								continue;
							}
							if ("val"
									.equals(attribute.getName().getLocalPart())) {
								angle.setVal(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("stdev".equals(attribute.getName()
									.getLocalPart())) {
								angle.setStdev(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("from_dh".equals(attribute.getName()
									.getLocalPart())) {
								angle.setFromDh(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("bs_dh".equals(attribute.getName()
									.getLocalPart())) {
								angle.setBsDh(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("fs_dh".equals(attribute.getName()
									.getLocalPart())) {
								angle.setFsDh(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
						}
						continue;
					}

					if ("s-distance".equals(startElement.getName()
							.getLocalPart())) {
						slopeDistance = new SlopeDistance();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("from".equals(attribute.getName()
									.getLocalPart())) {
								slopeDistance.setFrom(attribute.getValue());
								continue;
							}
							if ("to".equals(attribute.getName().getLocalPart())) {
								slopeDistance.setTo(attribute.getValue());
								continue;
							}
							if ("val"
									.equals(attribute.getName().getLocalPart())) {
								slopeDistance.setVal(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("stdev".equals(attribute.getName()
									.getLocalPart())) {
								slopeDistance.setStdev(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("from_dh".equals(attribute.getName()
									.getLocalPart())) {
								slopeDistance.setFromDh(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("to_dh".equals(attribute.getName()
									.getLocalPart())) {
								slopeDistance.setToDh(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
						}

						continue;
					}

					if ("z-angle".equals(startElement.getName().getLocalPart())) {
						zenithAngle = new ZenithAngle();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("from".equals(attribute.getName()
									.getLocalPart())) {
								zenithAngle.setFrom(attribute.getValue());
								continue;
							}
							if ("to".equals(attribute.getName().getLocalPart())) {
								zenithAngle.setTo(attribute.getValue());
								continue;
							}
							if ("val"
									.equals(attribute.getName().getLocalPart())) {
								zenithAngle.setVal(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("stdev".equals(attribute.getName()
									.getLocalPart())) {
								zenithAngle.setStdev(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("from_dh".equals(attribute.getName()
									.getLocalPart())) {
								zenithAngle.setFromDh(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("to_dh".equals(attribute.getName()
									.getLocalPart())) {
								zenithAngle.setToDh(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
						}

						continue;
					}

					if ("obs".equals(startElement.getName().getLocalPart())) {
						observation = new Observation();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("from".equals(attribute.getName()
									.getLocalPart())) {
								observation.setFrom(attribute.getValue());
								continue;
							}
							if ("orientation".equals(attribute.getName()
									.getLocalPart())) {
								observation
										.setOrientation(attribute.getValue());
								continue;
							}
							if ("from_dh".equals(attribute.getName()
									.getLocalPart())) {
								observation.setFromDh(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
						}

						continue;
					}

					if ("point".equals(startElement.getName().getLocalPart())) {
						point = new Point();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("id".equals(attribute.getName().getLocalPart())) {
								point.setName(attribute.getValue());
								continue;
							}
							if ("x".equals(attribute.getName().getLocalPart())) {
								point.setX(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("y".equals(attribute.getName().getLocalPart())) {
								point.setY(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("z".equals(attribute.getName().getLocalPart())) {
								point.setZ(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("fix"
									.equals(attribute.getName().getLocalPart())) {
								point.setFix(attribute.getValue());
								continue;
							}
							if ("adj"
									.equals(attribute.getName().getLocalPart())) {
								point.setAdj(attribute.getValue());
								continue;
							}
						}

						continue;
					}

					if ("gama-local".equals(startElement.getName()
							.getLocalPart())) {

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("version".equals(attribute.getName()
									.getLocalPart())) {
								input.setVersion(attribute.getValue());
								continue;
							}
						}
						continue;
					}

					if ("network".equals(startElement.getName().getLocalPart())) {
						network = new Network();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("axes-xy".equals(attribute.getName()
									.getLocalPart())) {
								network.setAxesXY(attribute.getValue());
								continue;
							}
							if ("angles".equals(attribute.getName()
									.getLocalPart())) {
								network.setAngles(attribute.getValue());
								continue;
							}
							if ("epoch".equals(attribute.getName()
									.getLocalPart())) {
								network.setEpoch(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
						}
						continue;
					}

					if ("description".equals(startElement.getName()
							.getLocalPart())) {

						continue;
					}

					if ("parameters".equals(startElement.getName()
							.getLocalPart())) {

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("sigma-apr".equals(attribute.getName()
									.getLocalPart())) {
								network.setSigmaApr(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("conf-pr".equals(attribute.getName()
									.getLocalPart())) {
								network.setConfPr(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("tol-abs".equals(attribute.getName()
									.getLocalPart())) {
								network.setTolAbs(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("sigma-act".equals(attribute.getName()
									.getLocalPart())) {
								network.setSigmaAct(attribute.getValue());
								continue;
							}
							if ("update-constrained-coordinates"
									.equals(attribute.getName().getLocalPart())) {
								network.setUpdateCC(attribute.getValue());
								continue;
							}
						}

						continue;
					}

					if ("points-observations".equals(startElement.getName()
							.getLocalPart())) {

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("distance-stdev".equals(attribute.getName()
									.getLocalPart())) {
								network.setDistanceStdev(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("direction-stdev".equals(attribute.getName()
									.getLocalPart())) {
								network.setDirectionStdev(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("angle-stdev".equals(attribute.getName()
									.getLocalPart())) {
								network.setAngleStdev(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("zenith-angle-stdev".equals(attribute.getName()
									.getLocalPart())) {
								network.setZenithAngleStdev(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
						}
						continue;
					}

					// TODO
					if ("cov-mat".equals(startElement.getName().getLocalPart())) {

						continue;
					}
					// TODO
					if ("height-differences".equals(startElement.getName()
							.getLocalPart())) {

						continue;
					}
					// TODO
					if ("dh".equals(startElement.getName().getLocalPart())) {

						continue;
					}
					// TODO
					if ("coordinates".equals(startElement.getName()
							.getLocalPart())) {

						continue;
					}
					// TODO
					if ("vectors".equals(startElement.getName().getLocalPart())) {

						continue;
					}
					// TODO
					if ("vec".equals(startElement.getName().getLocalPart())) {

						continue;
					}

					logger.debug("Unrecognized START_ELEMENT:"
							+ startElement.getName());

					break;

				// Parses all end elements and insert particular object to his
				// parent object
				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = event.asEndElement();

					if ("direction".equals(endElement.getName().getLocalPart())) {
						observation.getDirections().add(direction);
						continue;
					}

					if ("distance".equals(endElement.getName().getLocalPart())) {
						observation.getDistances().add(distance);
						continue;
					}
					if ("angle".equals(endElement.getName().getLocalPart())) {
						observation.getAngles().add(angle);
						continue;
					}

					if ("s-distance"
							.equals(endElement.getName().getLocalPart())) {
						observation.getSlopeDistances().add(slopeDistance);
						continue;
					}

					if ("z-angle".equals(endElement.getName().getLocalPart())) {
						observation.getZenithAngles().add(zenithAngle);
						continue;
					}

					if ("point".equals(endElement.getName().getLocalPart())) {
						network.getPoints().add(point);
						continue;
					}

					if ("obs".equals(endElement.getName().getLocalPart())) {
						network.getObservations().add(observation);
						continue;
					}

					if ("gama-local"
							.equals(endElement.getName().getLocalPart())) {

						continue;
					}

					if ("network".equals(endElement.getName().getLocalPart())) {
						input.setNetwork(network);
						continue;
					}

					if ("description".equals(endElement.getName()
							.getLocalPart())) {

						continue;
					}

					if ("parameters"
							.equals(endElement.getName().getLocalPart())) {

						continue;
					}

					if ("points-observations".equals(endElement.getName()
							.getLocalPart())) {

						continue;
					}

					// TODO
					if ("cov-mat".equals(endElement.getName().getLocalPart())) {

						continue;
					}
					// TODO
					if ("height-differences".equals(endElement.getName()
							.getLocalPart())) {

						continue;
					}
					// TODO
					if ("dh".equals(endElement.getName().getLocalPart())) {

						continue;
					}
					// TODO
					if ("coordinates".equals(endElement.getName()
							.getLocalPart())) {

						continue;
					}
					// TODO
					if ("vectors".equals(endElement.getName().getLocalPart())) {

						continue;
					}
					// TODO
					if ("vec".equals(endElement.getName().getLocalPart())) {

						continue;
					}

					logger.debug("Unrecognized END_ELEMENT:"
							+ endElement.getName());

					break;

				case XMLStreamConstants.PROCESSING_INSTRUCTION:
					break;

				case XMLStreamConstants.CHARACTERS:
					break;

				case XMLStreamConstants.COMMENT:
					break;

				case XMLStreamConstants.START_DOCUMENT:
					break;

				case XMLStreamConstants.END_DOCUMENT:
					break;

				case XMLStreamConstants.ENTITY_REFERENCE:
					break;

				case XMLStreamConstants.ATTRIBUTE:
					break;

				case XMLStreamConstants.DTD:
					break;

				case XMLStreamConstants.CDATA:
					break;

				case XMLStreamConstants.SPACE:
					break;

				default:

					logger.debug("Unrecognized event");

					break;
				}

			}

		} catch (XMLStreamException e) {

			logger.warn("unexpected parse error");
			e.printStackTrace();
		}

		return input;
	}

	@Override
	public void composeInput() {
		// TODO Auto-generated method stub

	}

}

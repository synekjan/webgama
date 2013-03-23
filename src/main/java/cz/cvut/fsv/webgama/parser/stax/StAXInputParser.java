/**
 * Main input parser using Streaming API for XML
 */
package cz.cvut.fsv.webgama.parser.stax;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cvut.fsv.webgama.domain.Angle;
import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Coordinate;
import cz.cvut.fsv.webgama.domain.CovMat;
import cz.cvut.fsv.webgama.domain.Direction;
import cz.cvut.fsv.webgama.domain.Distance;
import cz.cvut.fsv.webgama.domain.HeightDifference;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.Point;
import cz.cvut.fsv.webgama.domain.SlopeDistance;
import cz.cvut.fsv.webgama.domain.Vector;
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

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		try {

			XMLEventReader eventReader = inputFactory
					.createXMLEventReader(stream);

			Network network = null;
			Point point = null;
			Cluster cluster = null;
			Observation observation = null;
			Direction direction = null;
			Distance distance = null;
			Angle angle = null;
			SlopeDistance slopeDistance = null;
			ZenithAngle zenithAngle = null;
			Coordinate coordinate = null;
			Vector vector = null;
			HeightDifference heightDifference = null;
			CovMat covMat = null;

			// current entered element
			String currentTagName = null;
			// description builder
			StringBuilder descriptionBuilder = new StringBuilder();

			while (eventReader.hasNext()) {

				XMLEvent event = eventReader.nextEvent();

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
						cluster = new Cluster();
						cluster.setTagname("obs");
						currentTagName = "obs";
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

						if (!("coordinates".equals(currentTagName))) {
							point = new Point();

							@SuppressWarnings("unchecked")
							Iterator<Attribute> attributes = startElement
									.getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();

								if ("id".equals(attribute.getName()
										.getLocalPart())) {
									point.setName(attribute.getValue());
									continue;
								}
								if ("x".equals(attribute.getName()
										.getLocalPart())) {
									point.setX(Double.parseDouble(attribute
											.getValue()));
									continue;
								}
								if ("y".equals(attribute.getName()
										.getLocalPart())) {
									point.setY(Double.parseDouble(attribute
											.getValue()));
									continue;
								}
								if ("z".equals(attribute.getName()
										.getLocalPart())) {
									point.setZ(Double.parseDouble(attribute
											.getValue()));
									continue;
								}
								if ("fix".equals(attribute.getName()
										.getLocalPart())) {
									point.setFix(attribute.getValue());
									continue;
								}
								if ("adj".equals(attribute.getName()
										.getLocalPart())) {
									point.setAdj(attribute.getValue());
									continue;
								}
							}
						} else {
							coordinate = new Coordinate();

							@SuppressWarnings("unchecked")
							Iterator<Attribute> attributes = startElement
									.getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();

								if ("id".equals(attribute.getName()
										.getLocalPart())) {
									coordinate.setName(attribute.getValue());
									continue;
								}
								if ("x".equals(attribute.getName()
										.getLocalPart())) {
									coordinate.setX(Double
											.parseDouble(attribute.getValue()));
									continue;
								}
								if ("y".equals(attribute.getName()
										.getLocalPart())) {
									coordinate.setY(Double
											.parseDouble(attribute.getValue()));
									continue;
								}
								if ("z".equals(attribute.getName()
										.getLocalPart())) {
									coordinate.setZ(Double
											.parseDouble(attribute.getValue()));
									continue;
								}
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
						currentTagName = "description";

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
								network.setDistanceStdev(attribute.getValue());
								continue;
							}
							if ("direction-stdev".equals(attribute.getName()
									.getLocalPart())) {
								network.setDirectionStdev(attribute.getValue());
								continue;
							}
							if ("angle-stdev".equals(attribute.getName()
									.getLocalPart())) {
								network.setAngleStdev(attribute.getValue());
								continue;
							}
							if ("zenith-angle-stdev".equals(attribute.getName()
									.getLocalPart())) {
								network.setZenithAngleStdev(attribute
										.getValue());
								continue;
							}
						}
						continue;
					}

					// TODO -- DOPLNIT COVMAT PARSOVANI
					if ("cov-mat".equals(startElement.getName().getLocalPart())) {

						covMat = new CovMat();

						if (currentTagName.equals("obs")) {

						} else if (currentTagName.equals("height-differences")) {

						} else if (currentTagName.equals("coordinates")) {

						} else if (currentTagName.equals("vectors")) {

						} else {
							logger.debug("Unrecognized CovMat parent node");
						}

						continue;
					}

					if ("height-differences".equals(startElement.getName()
							.getLocalPart())) {
						cluster = new Cluster();
						cluster.setTagname("height-differences");
						currentTagName = "height-differences";

						continue;
					}

					if ("dh".equals(startElement.getName().getLocalPart())) {
						heightDifference = new HeightDifference();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("from".equals(attribute.getName()
									.getLocalPart())) {
								heightDifference.setFrom(attribute.getValue());
								continue;
							}
							if ("to".equals(attribute.getName().getLocalPart())) {
								heightDifference.setTo(attribute.getValue());
								continue;
							}
							if ("val"
									.equals(attribute.getName().getLocalPart())) {
								heightDifference.setVal(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("stdev".equals(attribute.getName()
									.getLocalPart())) {
								heightDifference.setStdev(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
							if ("dist".equals(attribute.getName()
									.getLocalPart())) {
								heightDifference.setDist(Double
										.parseDouble(attribute.getValue()));
								continue;
							}
						}

						continue;
					}

					if ("coordinates".equals(startElement.getName()
							.getLocalPart())) {
						cluster = new Cluster();
						cluster.setTagname("coordinates");
						currentTagName = "coordinates";

						continue;
					}

					if ("vectors".equals(startElement.getName().getLocalPart())) {
						cluster = new Cluster();
						cluster.setTagname("vectors");
						currentTagName = "vectors";

						continue;
					}

					if ("vec".equals(startElement.getName().getLocalPart())) {
						vector = new Vector();

						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();

							if ("from".equals(attribute.getName()
									.getLocalPart())) {
								vector.setFrom(attribute.getValue());
								continue;
							}
							if ("to".equals(attribute.getName().getLocalPart())) {
								vector.setTo(attribute.getValue());
								continue;
							}
							if ("dx".equals(attribute.getName().getLocalPart())) {
								vector.setDx(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("dy".equals(attribute.getName().getLocalPart())) {
								vector.setDy(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("dz".equals(attribute.getName().getLocalPart())) {
								vector.setDz(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("from_dh".equals(attribute.getName()
									.getLocalPart())) {
								vector.setFromDh(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
							if ("to_dh".equals(attribute.getName()
									.getLocalPart())) {
								vector.setToDh(Double.parseDouble(attribute
										.getValue()));
								continue;
							}
						}
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

						if (!("coordinates".equals(currentTagName))) {
							network.getPoints().add(point);
						} else {
							cluster.getCoordinates().add(coordinate);
						}

						continue;
					}

					if ("obs".equals(endElement.getName().getLocalPart())) {
						cluster.getObservations().add(observation);
						network.getClusters().add(cluster);
						currentTagName = null;
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

						network.setDescription(descriptionBuilder.toString()
								.trim());

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

					// TODO -MATICE
					if ("cov-mat".equals(endElement.getName().getLocalPart())) {

						if (currentTagName.equals("obs")) {
							cluster.setCovMat(covMat);
						} else if (currentTagName.equals("height-differences")) {
							cluster.setCovMat(covMat);
						} else if (currentTagName.equals("coordinates")) {
							cluster.setCovMat(covMat);
						} else if (currentTagName.equals("vectors")) {
							cluster.setCovMat(covMat);
						} else {
							logger.debug("Unrecognized CovMat parent node");
						}
						continue;
					}

					if ("height-differences".equals(endElement.getName()
							.getLocalPart())) {
						network.getClusters().add(cluster);
						currentTagName = null;
						continue;
					}

					if ("dh".equals(endElement.getName().getLocalPart())) {
						cluster.getHeightDifferences().add(heightDifference);
						continue;
					}

					if ("coordinates".equals(endElement.getName()
							.getLocalPart())) {
						network.getClusters().add(cluster);
						currentTagName = null;
						continue;
					}

					if ("vectors".equals(endElement.getName().getLocalPart())) {
						network.getClusters().add(cluster);
						currentTagName = null;
						continue;
					}

					if ("vec".equals(endElement.getName().getLocalPart())) {
						cluster.getVectors().add(vector);
						continue;
					}

					logger.debug("Unrecognized END_ELEMENT:"
							+ endElement.getName());

					break;

				case XMLStreamConstants.CHARACTERS:

					Characters characters = event.asCharacters();

					if ("description".equals(currentTagName)) {
						descriptionBuilder.append(characters.getData());
					}

					break;

				case XMLStreamConstants.PROCESSING_INSTRUCTION:
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
	public void composeInput(OutputStream stream, Input input) {

		// creates output and event factories
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		XMLEventFactory eventFactory = XMLEventFactory.newFactory();

		try {
			XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(
					stream, "UTF-8");

			// linebreak event for proper formatting
			XMLEvent endLine = eventFactory.createDTD("\n");
			// XMLEvent tab = eventFactory.createDTD("\t");

			// own composing part
			XMLEvent event = eventFactory.createStartDocument("UTF-8", "1.0");
			eventWriter.add(event);
			eventWriter.add(endLine);

			// <gama-local>
			eventWriter.add(eventFactory.createStartElement("", "",
					"gama-local"));
			if (input.getVersion() != null)
				eventWriter.add(eventFactory.createAttribute("version",
						input.getVersion()));
			eventWriter.add(endLine);

			// <network>
			eventWriter.add(endLine);
			eventWriter.add(eventFactory.createStartElement("", "", "network"));
			if (input.getNetwork().getAxesXY() != null ) {
				eventWriter.add(eventFactory.createAttribute("axes-xy", input
						.getNetwork().getAxesXY()));
			}
			if (input.getNetwork().getAngles() != null) {
				eventWriter.add(eventFactory.createAttribute("angles", input
						.getNetwork().getAngles()));
			}
			if (input.getNetwork().getEpoch() != null) {
				eventWriter.add(eventFactory.createAttribute("epoch", input
						.getNetwork().getEpoch().toString()));
			}
			eventWriter.add(endLine);

			// <description> </description>
			if (input.getNetwork().getDescription() != null) {
				eventWriter.add(endLine);
				eventWriter.add(eventFactory.createStartElement("", "",
						"description"));
				eventWriter.add(endLine);
				eventWriter.add(eventFactory.createCharacters(input
						.getNetwork().getDescription()));
				eventWriter.add(endLine);
				eventWriter.add(eventFactory.createEndElement("", "",
						"description"));
				eventWriter.add(endLine);
			}

			// <parameters> </parameters>
			eventWriter.add(endLine);
			eventWriter.add(eventFactory.createStartElement("", "",
					"parameters"));
			if (input.getNetwork().getSigmaApr() != null) {
				eventWriter.add(eventFactory.createAttribute("sigma-apr", input
						.getNetwork().getSigmaApr().toString()));
			}
			if (input.getNetwork().getConfPr() != null) {
				eventWriter.add(eventFactory.createAttribute("conf-pr", input
						.getNetwork().getConfPr().toString()));
			}
			if (input.getNetwork().getTolAbs() != null) {
				eventWriter.add(eventFactory.createAttribute("tol-abs", input
						.getNetwork().getTolAbs().toString()));
			}
			if (input.getNetwork().getSigmaAct() != null) {
				eventWriter.add(eventFactory.createAttribute("sigma-act", input
						.getNetwork().getSigmaAct()));
			}
			if (input.getNetwork().getUpdateCC() != null) {
				eventWriter.add(eventFactory.createAttribute(
						"update-constrained-coordinates", input.getNetwork()
								.getUpdateCC()));
			}
			// eventWriter.add(endLine);
			eventWriter
					.add(eventFactory.createEndElement("", "", "parameters"));
			eventWriter.add(endLine);

			// <points-observations>
			eventWriter.add(endLine);
			eventWriter.add(eventFactory.createStartElement("", "",
					"points-observations"));
			if (input.getNetwork().getDistanceStdev() != null) {
				eventWriter.add(eventFactory.createAttribute("distance-stdev",
						input.getNetwork().getDistanceStdev().toString()));
			}
			if (input.getNetwork().getDirectionStdev() != null) {
				eventWriter.add(eventFactory.createAttribute("direction-stdev",
						input.getNetwork().getDirectionStdev().toString()));
			}
			if (input.getNetwork().getAngleStdev() != null) {
				eventWriter.add(eventFactory.createAttribute("angle-stdev",
						input.getNetwork().getAngleStdev().toString()));
			}
			if (input.getNetwork().getZenithAngleStdev() != null) {
				eventWriter.add(eventFactory.createAttribute(
						"zenith-angle-stdev", input.getNetwork()
								.getZenithAngleStdev().toString()));
			}
			eventWriter.add(endLine);
			eventWriter.add(endLine);

			// POINTS
			for (Point point : input.getNetwork().getPoints()) {
				// <point>
				eventWriter.add(eventFactory
						.createStartElement("", "", "point"));
				eventWriter.add(eventFactory.createAttribute("id",
						point.getName()));
				if (point.getX() != null) {
					eventWriter.add(eventFactory.createAttribute("x", point
							.getX().toString()));
				}
				if (point.getY() != null) {
					eventWriter.add(eventFactory.createAttribute("y", point
							.getY().toString()));
				}
				if (point.getZ() != null) {
					eventWriter.add(eventFactory.createAttribute("z", point
							.getZ().toString()));
				}
				if (point.getFix() != null) {
					eventWriter.add(eventFactory.createAttribute("fix",
							point.getFix()));
				}
				if (point.getAdj() != null) {
					eventWriter.add(eventFactory.createAttribute("adj",
							point.getAdj()));
				}
				// </point>
				eventWriter.add(eventFactory.createEndElement("", "", "point"));
				eventWriter.add(endLine);
			}
			eventWriter.add(endLine);

			// CLUSTERS
			for (Cluster cluster : input.getNetwork().getClusters()) {

				switch (cluster.getTagname()) {
				case "obs":
					for (Observation obs : cluster.getObservations()) {
						// <obs>
						eventWriter.add(eventFactory.createStartElement("", "",
								"obs"));
						if (obs.getFrom() != null) {
							eventWriter.add(eventFactory.createAttribute(
									"from", obs.getFrom()));
						}
						if (obs.getOrientation() != null) {
							eventWriter.add(eventFactory.createAttribute(
									"orientation", obs.getOrientation()));
						}
						if (obs.getFromDh() != null) {
							eventWriter.add(eventFactory.createAttribute(
									"from_dh", obs.getFromDh().toString()));
						}
						eventWriter.add(endLine);

						// DIRECTIONS
						for (Direction direction : obs.getDirections()) {
							// <direction>
							eventWriter.add(eventFactory.createStartElement("",
									"", "direction"));
							eventWriter.add(eventFactory.createAttribute("to",
									direction.getTo()));
							eventWriter.add(eventFactory.createAttribute("val",
									direction.getVal().toString()));
							if (direction.getStdev() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"stdev", direction.getStdev()
												.toString()));
							}
							if (direction.getFromDh() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"from_dh", direction.getFromDh()
												.toString()));
							}
							if (direction.getToDh() != null) {
								eventWriter.add(eventFactory
										.createAttribute("to_dh", direction
												.getToDh().toString()));
							}
							// </direction>
							eventWriter.add(eventFactory.createEndElement("",
									"", "direction"));
							eventWriter.add(endLine);
						}

						// DISTANCES
						for (Distance distance : obs.getDistances()) {
							// <distance>
							eventWriter.add(eventFactory.createStartElement("",
									"", "distance"));
							if (distance.getFrom() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"from", distance.getFrom()));
							}
							eventWriter.add(eventFactory.createAttribute("to",
									distance.getTo()));
							eventWriter.add(eventFactory.createAttribute("val",
									distance.getVal().toString()));
							if (distance.getStdev() != null) {
								eventWriter.add(eventFactory
										.createAttribute("stdev", distance
												.getStdev().toString()));
							}
							if (distance.getFromDh() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"from_dh", distance.getFromDh()
												.toString()));
							}
							if (distance.getToDh() != null) {
								eventWriter
										.add(eventFactory.createAttribute(
												"to_dh", distance.getToDh()
														.toString()));
							}
							// </distance>
							eventWriter.add(eventFactory.createEndElement("",
									"", "distance"));
							eventWriter.add(endLine);
						}

						// ANGLES
						for (Angle angle : obs.getAngles()) {
							// <angle>
							eventWriter.add(eventFactory.createStartElement("",
									"", "angle"));
							if (angle.getFrom() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"from", angle.getFrom()));
							}
							eventWriter.add(eventFactory.createAttribute("bs",
									angle.getBs()));
							eventWriter.add(eventFactory.createAttribute("fs",
									angle.getFs()));
							eventWriter.add(eventFactory.createAttribute("val",
									angle.getVal().toString()));
							if (angle.getStdev() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"stdev", angle.getStdev().toString()));
							}
							if (angle.getFromDh() != null) {
								eventWriter.add(eventFactory
										.createAttribute("from_dh", angle
												.getFromDh().toString()));
							}
							if (angle.getBsDh() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"bs_dh", angle.getBsDh().toString()));
							}
							if (angle.getFsDh() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"fs_dh", angle.getFsDh().toString()));
							}
							// </angle>
							eventWriter.add(eventFactory.createEndElement("",
									"", "angle"));
							eventWriter.add(endLine);
						}

						// SLOPE DISTANCES
						for (SlopeDistance slopeDistance : obs
								.getSlopeDistances()) {
							// <s-distance>
							eventWriter.add(eventFactory.createStartElement("",
									"", "s-distance"));
							if (slopeDistance.getFrom() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"from", slopeDistance.getFrom()));
							}
							eventWriter.add(eventFactory.createAttribute("to",
									slopeDistance.getTo()));
							eventWriter.add(eventFactory.createAttribute("val",
									slopeDistance.getVal().toString()));
							if (slopeDistance.getStdev() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"stdev", slopeDistance.getStdev()
												.toString()));
							}
							if (slopeDistance.getFromDh() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"from_dh", slopeDistance.getFromDh()
												.toString()));
							}
							if (slopeDistance.getToDh() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"to_dh", slopeDistance.getToDh()
												.toString()));
							}
							// </s-distance>
							eventWriter.add(eventFactory.createEndElement("",
									"", "s-distance"));
							eventWriter.add(endLine);
						}

						// ZENITH ANGLES
						for (ZenithAngle zenithAngle : obs.getZenithAngles()) {
							// <z-angle>
							eventWriter.add(eventFactory.createStartElement("",
									"", "z-angle"));
							if (zenithAngle.getFrom() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"from", zenithAngle.getFrom()));
							}
							eventWriter.add(eventFactory.createAttribute("to",
									zenithAngle.getTo()));
							eventWriter.add(eventFactory.createAttribute("val",
									zenithAngle.getVal().toString()));
							if (zenithAngle.getStdev() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"stdev", zenithAngle.getStdev()
												.toString()));
							}
							if (zenithAngle.getFromDh() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"from_dh", zenithAngle.getFromDh()
												.toString()));
							}
							if (zenithAngle.getToDh() != null) {
								eventWriter.add(eventFactory.createAttribute(
										"to_dh", zenithAngle.getToDh()
												.toString()));
							}
							// </z-angle>
							eventWriter.add(eventFactory.createEndElement("",
									"", "z-angle"));
							eventWriter.add(endLine);
						}

						// </obs>
						eventWriter.add(eventFactory.createEndElement("", "",
								"obs"));
						eventWriter.add(endLine);
					}
					break;

				// COORDINATES
				case "coordinates":

					// <coordinates>
					eventWriter.add(endLine);
					eventWriter.add(eventFactory.createStartElement("", "",
							"coordinates"));
					eventWriter.add(endLine);
					for (Coordinate coordinate : cluster.getCoordinates()) {

						// <point>
						eventWriter.add(eventFactory.createStartElement("", "",
								"point"));
						eventWriter.add(eventFactory.createAttribute("id",
								coordinate.getName()));
						if (coordinate.getX() != null) {
							eventWriter.add(eventFactory.createAttribute("x",
									coordinate.getX().toString()));
						}
						if (coordinate.getY() != null) {
							eventWriter.add(eventFactory.createAttribute("y",
									coordinate.getY().toString()));
						}
						if (coordinate.getZ() != null) {
							eventWriter.add(eventFactory.createAttribute("z",
									coordinate.getZ().toString()));
						}
						// </point>
						eventWriter.add(eventFactory.createEndElement("", "",
								"point"));
						eventWriter.add(endLine);
					}

					// TODO - tady doplnit MATICI
					// </coordinates>
					eventWriter.add(eventFactory.createEndElement("", "",
							"coordinates"));
					eventWriter.add(endLine);

					break;

				// HEIGHT-DIFFERENCES
				case "height-differences":

					// <height-differences>
					eventWriter.add(endLine);
					eventWriter.add(eventFactory.createStartElement("", "",
							"height-differences"));
					eventWriter.add(endLine);

					for (HeightDifference heightDifference : cluster
							.getHeightDifferences()) {

						// <dh>
						eventWriter.add(eventFactory.createStartElement("", "",
								"dh"));
						eventWriter.add(eventFactory.createAttribute("from",
								heightDifference.getFrom()));
						eventWriter.add(eventFactory.createAttribute("to",
								heightDifference.getTo()));
						eventWriter.add(eventFactory.createAttribute("val",
								heightDifference.getVal().toString()));

						if (heightDifference.getStdev() != null) {
							eventWriter.add(eventFactory.createAttribute(
									"stdev", heightDifference.getStdev()
											.toString()));
						}
						if (heightDifference.getDist() != null) {
							eventWriter.add(eventFactory.createAttribute(
									"dist", heightDifference.getDist()
											.toString()));
						}

						// </dh>
						eventWriter.add(eventFactory.createEndElement("", "",
								"dh"));
						eventWriter.add(endLine);

					}

					// TODO - tady doplnit MATICI
					// </height-differences>
					eventWriter.add(eventFactory.createEndElement("", "",
							"height-differences"));
					eventWriter.add(endLine);

					break;

				// VECTORS
				case "vectors":

					// <vectors>
					eventWriter.add(endLine);
					eventWriter.add(eventFactory.createStartElement("", "",
							"vectors"));
					eventWriter.add(endLine);

					for (Vector vector : cluster.getVectors()) {

						// <vec>
						eventWriter.add(eventFactory.createStartElement("", "",
								"vec"));
						eventWriter.add(eventFactory.createAttribute("from",
								vector.getFrom()));
						eventWriter.add(eventFactory.createAttribute("to",
								vector.getTo()));
						eventWriter.add(eventFactory.createAttribute("dx",
								vector.getDx().toString()));
						eventWriter.add(eventFactory.createAttribute("dy",
								vector.getDy().toString()));
						eventWriter.add(eventFactory.createAttribute("dz",
								vector.getDz().toString()));

						if (vector.getFromDh() != null) {
							eventWriter.add(eventFactory.createAttribute(
									"from_dh", vector.getFromDh().toString()));
						}
						if (vector.getToDh() != null) {
							eventWriter.add(eventFactory.createAttribute(
									"to_dh", vector.getToDh().toString()));
						}

						// </vec>
						eventWriter.add(eventFactory.createEndElement("", "",
								"vec"));
						eventWriter.add(endLine);

					}

					// TODO - tady doplnit MATICI
					// </vectors>
					eventWriter.add(eventFactory.createEndElement("", "",
							"vectors"));
					eventWriter.add(endLine);

					break;
				default:
					logger.error("Unrecognized alternative observation tag name");
					break;
				}

			}

			// </points-observations>
			eventWriter.add(eventFactory.createEndElement("", "",
					"points-observations"));
			eventWriter.add(endLine);

			// </network>
			eventWriter.add(eventFactory.createEndElement("", "", "network"));
			eventWriter.add(endLine);

			// </gama-local>
			eventWriter
					.add(eventFactory.createEndElement("", "", "gama-local"));
			eventWriter.add(eventFactory.createEndDocument());
			eventWriter.flush();
			eventWriter.close();
			stream.close();

		} catch (XMLStreamException e) {
			logger.warn("unexpected composing error");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

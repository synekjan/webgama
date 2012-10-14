package cz.cvut.fsv.webgama.domain;

import java.util.List;

public class Network {

	private Integer id;

	private String axesXY;

	private String angles;

	private Double epoch;

	private String description;

	private Double sigmaApr;

	private Double confPr;

	private Double tolAbs;

	private String sigmaAct;

	private String updateCC;

	private Double directionStdev;

	private Double angleStdev;

	private Double zenithAngleStdev;

	private Double distanceStdev;

	private List<Point> points;

	private List<Observation> observations;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAxesXY() {
		return axesXY;
	}

	public void setAxesXY(String axesXY) {
		this.axesXY = axesXY;
	}

	public String getAngles() {
		return angles;
	}

	public void setAngles(String angles) {
		this.angles = angles;
	}

	public Double getEpoch() {
		return epoch;
	}

	public void setEpoch(Double epoch) {
		this.epoch = epoch;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getSigmaApr() {
		return sigmaApr;
	}

	public void setSigmaApr(Double sigmaApr) {
		this.sigmaApr = sigmaApr;
	}

	public Double getConfPr() {
		return confPr;
	}

	public void setConfPr(Double confPr) {
		this.confPr = confPr;
	}

	public Double getTolAbs() {
		return tolAbs;
	}

	public void setTolAbs(Double tolAbs) {
		this.tolAbs = tolAbs;
	}

	public String getSigmaAct() {
		return sigmaAct;
	}

	public void setSigmaAct(String sigmaAct) {
		this.sigmaAct = sigmaAct;
	}

	public String getUpdateCC() {
		return updateCC;
	}

	public void setUpdateCC(String updateCC) {
		this.updateCC = updateCC;
	}

	public Double getDirectionStdev() {
		return directionStdev;
	}

	public void setDirectionStdev(Double directionStdev) {
		this.directionStdev = directionStdev;
	}

	public Double getAngleStdev() {
		return angleStdev;
	}

	public void setAngleStdev(Double angleStdev) {
		this.angleStdev = angleStdev;
	}

	public Double getZenithAngleStdev() {
		return zenithAngleStdev;
	}

	public void setZenithAngleStdev(Double zenithAngleStdev) {
		this.zenithAngleStdev = zenithAngleStdev;
	}

	public Double getDistanceStdev() {
		return distanceStdev;
	}

	public void setDistanceStdev(Double distanceStdev) {
		this.distanceStdev = distanceStdev;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public List<Observation> getObservations() {
		return observations;
	}

	public void setObservations(List<Observation> observations) {
		this.observations = observations;
	}

	@Override
	public String toString() {
		return "Network [id=" + id + ", axesXY=" + axesXY + ", angles="
				+ angles + ", epoch=" + epoch + ", description=" + description
				+ ", sigmaApr=" + sigmaApr + ", confPr=" + confPr + ", tolAbs="
				+ tolAbs + ", sigmaAct=" + sigmaAct + ", updateCC=" + updateCC
				+ ", directionStdev=" + directionStdev + ", angleStdev="
				+ angleStdev + ", zenithAngleStdev=" + zenithAngleStdev
				+ ", distanceStdev=" + distanceStdev + ", points=" + points
				+ ", observations=" + observations + "]";
	}

}

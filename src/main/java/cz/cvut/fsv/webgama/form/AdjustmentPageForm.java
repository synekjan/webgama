package cz.cvut.fsv.webgama.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Point;

public class AdjustmentPageForm {

	private String name;
	
	private String axesXY;

	private String angles;

	private Double epoch;

	private String description;

	private Double sigmaApr;

	private Double confPr;

	private Double tolAbs;

	private String sigmaAct;

	private String updateCC;

	private String directionStdev;

	private String angleStdev;

	private String zenithAngleStdev;

	private String distanceStdev;

	@Valid
	private List<Point> points = new ArrayList<>();

	@Valid
	private List<Cluster> clusters = new ArrayList<>();

	public AdjustmentPageForm() {

	}

	public AdjustmentPageForm(Input input) {
		name = input.getName();
		axesXY = input.getNetwork().getAxesXY();
		angles = input.getNetwork().getAngles();
		epoch = input.getNetwork().getEpoch();
		description = input.getNetwork().getDescription();
		sigmaApr = input.getNetwork().getSigmaApr();
		confPr = input.getNetwork().getConfPr();
		tolAbs = input.getNetwork().getTolAbs();
		sigmaAct = input.getNetwork().getSigmaAct();
		updateCC = input.getNetwork().getUpdateCC();
		directionStdev = input.getNetwork().getDirectionStdev();
		angleStdev = input.getNetwork().getAngleStdev();
		zenithAngleStdev = input.getNetwork().getZenithAngleStdev();
		distanceStdev = input.getNetwork().getDistanceStdev();
		points = input.getNetwork().getPoints();
		clusters = input.getNetwork().getClusters();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDirectionStdev() {
		return directionStdev;
	}

	public void setDirectionStdev(String directionStdev) {
		this.directionStdev = directionStdev;
	}

	public String getAngleStdev() {
		return angleStdev;
	}

	public void setAngleStdev(String angleStdev) {
		this.angleStdev = angleStdev;
	}

	public String getZenithAngleStdev() {
		return zenithAngleStdev;
	}

	public void setZenithAngleStdev(String zenithAngleStdev) {
		this.zenithAngleStdev = zenithAngleStdev;
	}

	public String getDistanceStdev() {
		return distanceStdev;
	}

	public void setDistanceStdev(String distanceStdev) {
		this.distanceStdev = distanceStdev;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

}

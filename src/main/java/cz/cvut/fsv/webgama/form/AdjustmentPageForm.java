package cz.cvut.fsv.webgama.form;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import cz.cvut.fsv.webgama.domain.Point;

public class AdjustmentPageForm {
	
	private String axesXY;
	
	@Length(max = 5)
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

	private List<Point> points = new ArrayList<Point>();

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

}

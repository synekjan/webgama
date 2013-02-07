package cz.cvut.fsv.webgama.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import cz.cvut.fsv.webgama.domain.Input;

public class AdjustmentPageForm {
	
	private String axesXY;
	
	@Length(max = 5)
	@NotBlank
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
	private List<PointForm> points = new ArrayList<>();
	
	@Valid
	private List<ObservationForm> observations = new ArrayList<>();

	public AdjustmentPageForm() {
		
	}
	//TODO
	public AdjustmentPageForm(Input input) {
		axesXY = input.getNetwork().getAxesXY();
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

	public List<PointForm> getPoints() {
		return points;
	}

	public void setPoints(List<PointForm> points) {
		this.points = points;
	}

	public List<ObservationForm> getObservations() {
		return observations;
	}

	public void setObservations(List<ObservationForm> observations) {
		this.observations = observations;
	}

}

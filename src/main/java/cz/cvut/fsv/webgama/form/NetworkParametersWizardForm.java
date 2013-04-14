package cz.cvut.fsv.webgama.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import cz.cvut.fsv.webgama.domain.Input;

public class NetworkParametersWizardForm {

	@Max(100)
	@Min(0)
	private Double sigmaApr;

	private Double confPr;

	private Double tolAbs;

	private String sigmaAct;

	private String updateCC;

	private String directionStdev;

	private String angleStdev;

	private String zenithAngleStdev;

	private String distanceStdev;

	public NetworkParametersWizardForm() {

	}

	public NetworkParametersWizardForm(Input input) {
		this.sigmaApr = input.getNetwork().getSigmaApr();
		this.confPr = input.getNetwork().getConfPr();
		this.tolAbs = input.getNetwork().getTolAbs();
		this.sigmaAct = input.getNetwork().getSigmaAct();
		this.updateCC = input.getNetwork().getUpdateCC();
		this.directionStdev = input.getNetwork().getDirectionStdev();
		this.angleStdev = input.getNetwork().getAngleStdev();
		this.zenithAngleStdev = input.getNetwork().getZenithAngleStdev();
		this.distanceStdev = input.getNetwork().getDistanceStdev();
	}

	public void enrichInput(Input input) {
		input.getNetwork().setSigmaApr(this.sigmaApr);
		input.getNetwork().setConfPr(this.confPr);
		input.getNetwork().setTolAbs(this.tolAbs);
		input.getNetwork().setSigmaAct(this.sigmaAct);
		input.getNetwork().setUpdateCC(this.updateCC);
		input.getNetwork().setDirectionStdev(this.directionStdev);
		input.getNetwork().setAngleStdev(this.angleStdev);
		input.getNetwork().setZenithAngleStdev(this.zenithAngleStdev);
		input.getNetwork().setDistanceStdev(this.distanceStdev);
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

}

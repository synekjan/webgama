package cz.cvut.fsv.webgama.form;

import cz.cvut.fsv.webgama.domain.Input;

public class NetworkDefinitionWizardForm {

	private String axesXY;

	private String angles;

	private Double epoch;

	private String description;

	public NetworkDefinitionWizardForm() {

	}

	public NetworkDefinitionWizardForm(Input input) {
		this.axesXY = input.getNetwork().getAxesXY();
		this.angles = input.getNetwork().getAngles();
		this.epoch = input.getNetwork().getEpoch();
		this.description = input.getNetwork().getDescription();
	}
	
	public void enrichInput(Input input) {
		input.getNetwork().setAxesXY(this.axesXY);
		input.getNetwork().setAngles(this.angles);
		input.getNetwork().setEpoch(this.epoch);
		input.getNetwork().setDescription(this.description);
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

}

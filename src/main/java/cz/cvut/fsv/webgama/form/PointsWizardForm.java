package cz.cvut.fsv.webgama.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Point;

public class PointsWizardForm {

	@Valid
	private List<Point> points = new ArrayList<>();

	public PointsWizardForm() {

	}

	public PointsWizardForm(Input input) {
		this.points = input.getNetwork().getPoints();
	}

	public void enrichInput(Input input) {
		input.getNetwork().setPoints(this.points);
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

}

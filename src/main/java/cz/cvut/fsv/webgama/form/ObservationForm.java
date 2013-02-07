package cz.cvut.fsv.webgama.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import cz.cvut.fsv.webgama.domain.CovMat;

public class ObservationForm {

	@NotBlank
	private String from;

	@NotBlank
	private String orientation;

	@NotNull
	private Double fromDh;

	private CovMat covMat;

	@Valid
	private List<DirectionForm> directions = new ArrayList<>();

	@Valid
	private List<DistanceForm> distances = new ArrayList<>();

	@Valid
	private List<AngleForm> angles = new ArrayList<>();

	@Valid
	private List<SlopeDistanceForm> slopeDistances = new ArrayList<>();

	@Valid
	private List<ZenithAngleForm> zenithAngles = new ArrayList<>();

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Double getFromDh() {
		return fromDh;
	}

	public void setFromDh(Double fromDh) {
		this.fromDh = fromDh;
	}

	public CovMat getCovMat() {
		return covMat;
	}

	public void setCovMat(CovMat covMat) {
		this.covMat = covMat;
	}

	public List<DirectionForm> getDirections() {
		return directions;
	}

	public void setDirections(List<DirectionForm> directions) {
		this.directions = directions;
	}

	public List<DistanceForm> getDistances() {
		return distances;
	}

	public void setDistances(List<DistanceForm> distances) {
		this.distances = distances;
	}

	public List<AngleForm> getAngles() {
		return angles;
	}

	public void setAngles(List<AngleForm> angles) {
		this.angles = angles;
	}

	public List<SlopeDistanceForm> getSlopeDistances() {
		return slopeDistances;
	}

	public void setSlopeDistances(List<SlopeDistanceForm> slopeDistances) {
		this.slopeDistances = slopeDistances;
	}

	public List<ZenithAngleForm> getZenithAngles() {
		return zenithAngles;
	}

	public void setZenithAngles(List<ZenithAngleForm> zenithAngles) {
		this.zenithAngles = zenithAngles;
	}

}

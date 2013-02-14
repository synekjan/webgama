package cz.cvut.fsv.webgama.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

public class Observation {

	private Long id;

	@NotBlank
	private String from;

	private String orientation;

	private Double fromDh;

	private CovMat covMat;

	@Valid
	private List<Direction> directions = new ArrayList<>();

	@Valid
	private List<Distance> distances = new ArrayList<>();

	@Valid
	private List<Angle> angles = new ArrayList<>();

	@Valid
	private List<SlopeDistance> slopeDistances = new ArrayList<>();

	@Valid
	private List<ZenithAngle> zenithAngles = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<Direction> getDirections() {
		return directions;
	}

	public void setDirections(List<Direction> directions) {
		this.directions = directions;
	}

	public List<Distance> getDistances() {
		return distances;
	}

	public void setDistances(List<Distance> distances) {
		this.distances = distances;
	}

	public List<Angle> getAngles() {
		return angles;
	}

	public void setAngles(List<Angle> angles) {
		this.angles = angles;
	}

	public List<SlopeDistance> getSlopeDistances() {
		return slopeDistances;
	}

	public void setSlopeDistances(List<SlopeDistance> slopeDistances) {
		this.slopeDistances = slopeDistances;
	}

	public List<ZenithAngle> getZenithAngles() {
		return zenithAngles;
	}

	public void setZenithAngles(List<ZenithAngle> zenithAngles) {
		this.zenithAngles = zenithAngles;
	}

}

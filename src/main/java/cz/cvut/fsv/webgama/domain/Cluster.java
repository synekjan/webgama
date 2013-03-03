package cz.cvut.fsv.webgama.domain;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

	private Long id;

	private String tagname;

	private CovMat covMat;

	private List<Observation> observations = new ArrayList<>();

	private List<Coordinate> coordinates = new ArrayList<>();

	private List<HeightDifference> heightDifferences = new ArrayList<>();

	private List<Vector> vectors = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public CovMat getCovMat() {
		return covMat;
	}

	public void setCovMat(CovMat covMat) {
		this.covMat = covMat;
	}

	public List<Observation> getObservations() {
		return observations;
	}

	public void setObservations(List<Observation> observations) {
		this.observations = observations;
	}

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public List<HeightDifference> getHeightDifferences() {
		return heightDifferences;
	}

	public void setHeightDifferences(List<HeightDifference> heightDifferences) {
		this.heightDifferences = heightDifferences;
	}

	public List<Vector> getVectors() {
		return vectors;
	}

	public void setVectors(List<Vector> vectors) {
		this.vectors = vectors;
	}
}

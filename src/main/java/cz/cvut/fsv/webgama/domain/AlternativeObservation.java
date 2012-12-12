package cz.cvut.fsv.webgama.domain;

import java.util.ArrayList;
import java.util.List;

public class AlternativeObservation {

	private Integer id;
	
	private String tagname;
	
	private CovMat covMat;

	private List<HeightDifference> heightDifferences = new ArrayList<HeightDifference>();

	private List<Vector> vectors = new ArrayList<Vector>();

	private List<Coordinate> coordinates = new ArrayList<Coordinate>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

}

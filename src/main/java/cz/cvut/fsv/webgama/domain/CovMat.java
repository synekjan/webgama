package cz.cvut.fsv.webgama.domain;

public class CovMat {

	private Long id;

	private Integer dim;

	private Integer band;

	// private Double[] val;

	public Integer getDim() {
		return dim;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDim(Integer dim) {
		this.dim = dim;
	}

	public Integer getBand() {
		return band;
	}

	public void setBand(Integer band) {
		this.band = band;
	}

	/*
	 * public Double[][] getVal() { return val; }
	 * 
	 * public void setVal(Double[][] val) { this.val = val; }
	 */

}

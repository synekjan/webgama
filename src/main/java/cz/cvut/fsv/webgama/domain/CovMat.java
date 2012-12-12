package cz.cvut.fsv.webgama.domain;

public class CovMat {
	
	private Integer id;
	
	private Integer dim;
	
	private Integer band;
	
	private Double[][] val;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDim() {
		return dim;
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

	public Double[][] getVal() {
		return val;
	}

	public void setVal(Double[][] val) {
		this.val = val;
	}

}

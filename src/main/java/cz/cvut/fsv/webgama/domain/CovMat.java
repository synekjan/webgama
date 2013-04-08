package cz.cvut.fsv.webgama.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CovMat {

	private Long id;

	@Min(0)
	@NotNull
	private Integer dim;

	@Min(0)
	@NotNull
	private Integer band;

	private String values;

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

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}
}

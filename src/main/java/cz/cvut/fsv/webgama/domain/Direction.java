package cz.cvut.fsv.webgama.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Direction implements Serializable {

	private static final long serialVersionUID = 7589773890531853403L;

	private Long id;

	@NotBlank
	private String to;

	@NotNull
	@Length(max = 30)
	private String val;

	private Double stdev;

	private Double fromDh;

	private Double toDh;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Double getStdev() {
		return stdev;
	}

	public void setStdev(Double stdev) {
		this.stdev = stdev;
	}

	public Double getFromDh() {
		return fromDh;
	}

	public void setFromDh(Double fromDh) {
		this.fromDh = fromDh;
	}

	public Double getToDh() {
		return toDh;
	}

	public void setToDh(Double toDh) {
		this.toDh = toDh;
	}

}

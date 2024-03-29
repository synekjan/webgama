package cz.cvut.fsv.webgama.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Vector implements Serializable {

	private static final long serialVersionUID = -4578626510871706109L;

	private Long id;

	@NotBlank
	private String from;

	@NotBlank
	private String to;

	@NotNull
	private Double dx;

	@NotNull
	private Double dy;

	@NotNull
	private Double dz;

	private Double fromDh;

	private Double toDh;

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

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Double getDx() {
		return dx;
	}

	public void setDx(Double dx) {
		this.dx = dx;
	}

	public Double getDy() {
		return dy;
	}

	public void setDy(Double dy) {
		this.dy = dy;
	}

	public Double getDz() {
		return dz;
	}

	public void setDz(Double dz) {
		this.dz = dz;
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

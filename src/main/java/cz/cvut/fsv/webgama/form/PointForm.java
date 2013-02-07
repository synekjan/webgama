package cz.cvut.fsv.webgama.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import cz.cvut.fsv.webgama.domain.Point;

public class PointForm {

	@NotBlank
	private String name;

	@NotNull
	private Double x;

	@NotNull
	private Double y;

	@NotNull
	private Double z;

	private String fix;

	private String adj;

	public PointForm() {

	}

	public PointForm(Point point) {
		this.name = point.getName();
		this.x = point.getX();
		this.y = point.getY();
		this.z = point.getZ();
		this.fix = point.getFix();
		this.adj = point.getAdj();
	}

	public Point toPoint() {
		Point point = new Point();
		point.setName(name);
		point.setX(x);
		point.setY(y);
		point.setZ(z);
		point.setFix(fix);
		point.setAdj(adj);
		return point;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getZ() {
		return z;
	}

	public void setZ(Double z) {
		this.z = z;
	}

	public String getFix() {
		return fix;
	}

	public void setFix(String fix) {
		this.fix = fix;
	}

	public String getAdj() {
		return adj;
	}

	public void setAdj(String adj) {
		this.adj = adj;
	}

}

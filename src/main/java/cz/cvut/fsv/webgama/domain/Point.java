package cz.cvut.fsv.webgama.domain;

public class Point {

	private Integer id;

	private String name;

	private Double x;

	private Double y;

	private Double z;

	private String fix;

	private String adj;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

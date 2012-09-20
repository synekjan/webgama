package cz.cvut.fsv.webgama.domain;

public class Point {
	
	private Integer id;
	
	private Network network;
	
	private String name;
	
	private Double x;
	
	private Double y;
	
	private Double z;
	
	private String adjType;
	
	private String coordType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
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

	public String getAdjType() {
		return adjType;
	}

	public void setAdjType(String adjType) {
		this.adjType = adjType;
	}

	public String getCoordType() {
		return coordType;
	}

	public void setCoordType(String coordType) {
		this.coordType = coordType;
	}

}

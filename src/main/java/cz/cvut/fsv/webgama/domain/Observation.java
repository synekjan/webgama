package cz.cvut.fsv.webgama.domain; 


public class Observation {

	private Integer id;
	
	private Network network;
	
	private String from;
	
	private String orientation;
	
	private Double fromDh;

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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Double getFromDh() {
		return fromDh;
	}

	public void setFromDh(Double fromDh) {
		this.fromDh = fromDh;
	}
	
}

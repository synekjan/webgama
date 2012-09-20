package cz.cvut.fsv.webgama.domain;

import org.joda.time.DateTime;

public class Input {
	
	private Integer id;
	
	private User user;
	
	private String filename;
	
	private String fileContent;
	
	private String algorithm;
	
	private Integer angUnits;
	
	private Double latitude;
	
	private String ellipsoid;
	
	private String version;
	
	private DateTime time;
	
	private Network network;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public Integer getAngUnits() {
		return angUnits;
	}

	public void setAngUnits(Integer angUnits) {
		this.angUnits = angUnits;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getEllipsoid() {
		return ellipsoid;
	}

	public void setEllipsoid(String ellipsoid) {
		this.ellipsoid = ellipsoid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

}

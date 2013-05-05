package cz.cvut.fsv.webgama.domain;

import java.util.List;

import org.joda.time.DateTime;

public class Calculation {

	private Long id;

	private User user;

	private String name;

	private String progress;

	private String language;

	private String algorithm;

	private Integer angUnits;

	private Double latitude;

	private String ellipsoid;

	private CalculationStatistic calculationStatistic;

	private List<CalculationPrivilege> calculationPrivileges;

	private DateTime time;

	private Input input;

	private Output output;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public CalculationStatistic getCalculationStatistic() {
		return calculationStatistic;
	}

	public void setCalculationStatistic(CalculationStatistic calculationStatistic) {
		this.calculationStatistic = calculationStatistic;
	}

	public List<CalculationPrivilege> getCalculationPrivileges() {
		return calculationPrivileges;
	}

	public void setCalculationPrivileges(List<CalculationPrivilege> calculationPrivileges) {
		this.calculationPrivileges = calculationPrivileges;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}
}

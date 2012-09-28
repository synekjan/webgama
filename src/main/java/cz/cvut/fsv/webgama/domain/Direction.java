package cz.cvut.fsv.webgama.domain;

public class Direction {

    private Integer id;

    private String to;

    private Double val;

    private Double stdev;

    private Double fromDh;

    private Double toDh;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getTo() {
	return to;
    }

    public void setTo(String to) {
	this.to = to;
    }

    public Double getVal() {
	return val;
    }

    public void setVal(Double val) {
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

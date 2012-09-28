package cz.cvut.fsv.webgama.domain;

public class Angle {

    private Integer id;

    private String from;

    private String bs;

    private String fs;

    private Double val;

    private Double stdev;

    private Double fromDh;

    private Double bsDh;

    private Double fsDh;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getFrom() {
	return from;
    }

    public void setFrom(String from) {
	this.from = from;
    }

    public String getBs() {
	return bs;
    }

    public void setBs(String bs) {
	this.bs = bs;
    }

    public String getFs() {
	return fs;
    }

    public void setFs(String fs) {
	this.fs = fs;
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

    public Double getBsDh() {
	return bsDh;
    }

    public void setBsDh(Double bsDh) {
	this.bsDh = bsDh;
    }

    public Double getFsDh() {
	return fsDh;
    }

    public void setFsDh(Double fsDh) {
	this.fsDh = fsDh;
    }

}

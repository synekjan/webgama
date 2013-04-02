package cz.cvut.fsv.webgama.domain;

import org.joda.time.DateTime;

public class Output {

	private Long id;

	private String xmlContent;

	private String textContent;

	private String htmlContent;

	private String svgContent;

	private Double runningTime;

	private DateTime time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getSvgContent() {
		return svgContent;
	}

	public void setSvgContent(String svgContent) {
		this.svgContent = svgContent;
	}

	public Double getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Double runningTime) {
		this.runningTime = runningTime;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

}

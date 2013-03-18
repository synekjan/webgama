package cz.cvut.fsv.webgama.domain;

public class ProcessOutput {

	private int exitValue;

	private String xmlResult;

	private String textResult;

	private String htmlResult;

	private String svgResult;

	private String errorMessage;

	public Integer getExitValue() {
		return exitValue;
	}

	public void setExitValue(Integer exitValue) {
		this.exitValue = exitValue;
	}

	public String getXmlResult() {
		return xmlResult;
	}

	public void setXmlResult(String xmlResult) {
		this.xmlResult = xmlResult;
	}

	public String getTextResult() {
		return textResult;
	}

	public void setTextResult(String textResult) {
		this.textResult = textResult;
	}

	public String getHtmlResult() {
		return htmlResult;
	}

	public void setHtmlResult(String htmlResult) {
		this.htmlResult = htmlResult;
	}

	public String getSvgResult() {
		return svgResult;
	}

	public void setSvgResult(String svgResult) {
		this.svgResult = svgResult;
	}

	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}

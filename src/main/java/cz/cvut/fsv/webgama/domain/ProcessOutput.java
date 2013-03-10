package cz.cvut.fsv.webgama.domain;

public class ProcessOutput {
	
	private int exitValue;
	
	private String result;
	
	private String errorMessage;

	public Integer getExitValue() {
		return exitValue;
	}

	public void setExitValue(Integer exitValue) {
		this.exitValue = exitValue;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}

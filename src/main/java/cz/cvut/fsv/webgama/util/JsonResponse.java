package cz.cvut.fsv.webgama.util;

public class JsonResponse {

	private boolean error = false;

	private String message;

	private Double runningTime;

	public Double getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Double runningTime) {
		this.runningTime = runningTime;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

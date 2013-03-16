package cz.cvut.fsv.webgama.domain;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Input implements Serializable {

	private static final long serialVersionUID = -5648997304219980815L;

	private Long id;

	private String xmlContent;

	private String version;

	private DateTime time;

	private Network network;

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

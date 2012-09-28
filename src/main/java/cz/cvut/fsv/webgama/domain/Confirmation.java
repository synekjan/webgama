package cz.cvut.fsv.webgama.domain;

import org.joda.time.DateTime;

public class Confirmation {

    private Integer id;
    private User user;
    private String uuid;
    private DateTime time;

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

    public String getUuid() {
	return uuid;
    }

    public void setUuid(String uuid) {
	this.uuid = uuid;
    }

    public DateTime getTime() {
	return time;
    }

    public void setTime(DateTime time) {
	this.time = time;
    }

}

package cz.cvut.fsv.webgama.service;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Activity;

public interface ActivityManager {
	
	public void recordActivity(String username, String type, String message);
	
	public List<Activity> getRecentActivitiesByUsername(String username);
	
	public List<Activity> getAllActivitiesByUsername(String username);

}

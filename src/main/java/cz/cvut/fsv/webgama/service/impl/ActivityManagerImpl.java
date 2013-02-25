package cz.cvut.fsv.webgama.service.impl;

import java.util.List;

import cz.cvut.fsv.webgama.dao.ActivityDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Activity;
import cz.cvut.fsv.webgama.service.ActivityManager;

public class ActivityManagerImpl implements ActivityManager {

	private ActivityDao activityDao;

	private UserDao userDao;

	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void recordActivity(String username, String message) {

		Activity activity = new Activity();
		activity.setUser(userDao.findUserByUsername(username));
		activity.setMessage(message);

		activityDao.insert(activity);

	}

	@Override
	public List<Activity> getRecentActivitiesByUsername(String username) {

		List<Activity> activities = activityDao
				.findRecentActivitiesByUser(userDao
						.findUserByUsername(username));

		return activities;
	}

	@Override
	public List<Activity> getAllActivitiesByUsername(String username) {
		
		List<Activity> activities = activityDao.findAllActivitiesByUser(userDao.findUserByUsername(username));
		
		return activities;
	}

}

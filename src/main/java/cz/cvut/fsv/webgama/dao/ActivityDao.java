package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Activity;
import cz.cvut.fsv.webgama.domain.User;

public interface ActivityDao {
	
	public void insert(Activity activity);
	
	public void delete(Activity activity);
	
	public void update(Activity activity);
	
	public List<Activity> findAllActivitiesByUser(User user);
	
	public List<Activity> findRecentActivitiesByUser(User user);

}

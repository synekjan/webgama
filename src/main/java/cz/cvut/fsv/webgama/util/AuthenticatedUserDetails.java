package cz.cvut.fsv.webgama.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import cz.cvut.fsv.webgama.service.UserManager;

public class AuthenticatedUserDetails {

	@Autowired
	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	// username exposed in header
	public String getUsername() {
		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		if (obj instanceof UserDetails) {
			return ((UserDetails) obj).getUsername();
		} else {
			return null;
		}
	}

	// Admin Section in header
	public Boolean getAdminRights() {

		return userManager.hasUserAdminRights(SecurityContextHolder
				.getContext().getAuthentication().getName());
	}
	
	// Last Login link in header
	public String getLastLogin() {
		
		return new Date().toString();
	}

}

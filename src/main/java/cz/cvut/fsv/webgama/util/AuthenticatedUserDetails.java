package cz.cvut.fsv.webgama.util;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import cz.cvut.fsv.webgama.domain.Login;
import cz.cvut.fsv.webgama.service.LoginManager;
import cz.cvut.fsv.webgama.service.UserManager;

public class AuthenticatedUserDetails {

	@Inject
	private UserManager userManager;

	@Inject
	private LoginManager loginManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
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

		List<Login> list = loginManager.getLoginList(SecurityContextHolder
				.getContext().getAuthentication().getName());

		if (list.isEmpty()) {
			return null;
		}

		return list.get(0).getTime().toString();
	}

}

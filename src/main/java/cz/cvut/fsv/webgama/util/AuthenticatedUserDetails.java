package cz.cvut.fsv.webgama.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUserDetails {

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

}

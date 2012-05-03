package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Authority;
import cz.cvut.fsv.webgama.domain.User;

public interface AuthorityDao {
	
	public List<Authority> getUserAuthorities(User user);

}

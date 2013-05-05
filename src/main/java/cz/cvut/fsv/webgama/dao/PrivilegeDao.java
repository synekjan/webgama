package cz.cvut.fsv.webgama.dao;

import cz.cvut.fsv.webgama.domain.Privilege;

public interface PrivilegeDao {

	public Privilege findPrivilegeById(Long id);
}

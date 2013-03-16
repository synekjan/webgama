package cz.cvut.fsv.webgama.dao.jdbc;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.OutputDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Output;

public class JdbcOutputDao extends JdbcDaoSupport implements OutputDao {

	@Override
	public Output findOutputInCalculation(Calculation calculation) {
		// TODO Auto-generated method stub
		return null;
	}

}

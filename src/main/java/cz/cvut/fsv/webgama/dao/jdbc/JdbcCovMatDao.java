package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.CovMatDao;
import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.CovMat;

public class JdbcCovMatDao extends JdbcDaoSupport implements CovMatDao {

	@Override
	public void insert(CovMat covMat, Long clusterId) {

		String sql = "INSERT INTO covmats (cluster_id, dim, band, values) VALUES (?, ?, ?, ?)";

		getJdbcTemplate()
				.update(sql, new Object[] { clusterId, covMat.getDim(), covMat.getBand(), covMat.getValues() });

	}

	@Override
	public void delete(CovMat covMat) {

		String sql = "DELETE FROM covmats WHERE covmat_id = ?";

		getJdbcTemplate().update(sql, new Object[] { covMat.getId() });

	}

	@Override
	public void update(CovMat covMat) {

		

	}

	@Override
	public CovMat findCovMatForCluster(Cluster cluster) {

		String sql = "SELECT * FROM covmats WHERE cluster_id = ?";

		CovMat covMat = null;
		try {
			covMat = getJdbcTemplate().queryForObject(sql, new Object[] { cluster.getId() }, new CovMatMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		return covMat;
	}

	private class CovMatMapper implements RowMapper<CovMat> {

		@Override
		public CovMat mapRow(ResultSet rs, int rowNum) throws SQLException {

			CovMat covMat = new CovMat();

			covMat.setId(rs.getLong("covmat_id"));
			covMat.setDim(rs.getInt("dim"));
			covMat.setBand(rs.getInt("band"));
			covMat.setValues(rs.getString("values"));

			return covMat;
		}
	}
}

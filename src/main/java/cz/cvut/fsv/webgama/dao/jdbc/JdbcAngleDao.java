package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.AngleDao;
import cz.cvut.fsv.webgama.domain.Angle;
import cz.cvut.fsv.webgama.domain.Observation;

public class JdbcAngleDao extends JdbcDaoSupport implements AngleDao {

	@Override
	public void insert(Angle angle, Integer observationId) {

		String sql = "INSERT INTO angles (observation_id, from_id, bs, fs, val, stdev, from_dh, bs_dh, fs_dh VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { observationId, angle.getFrom(), angle.getBs(),
						angle.getFs(), angle.getVal(), angle.getStdev(),
						angle.getFromDh(), angle.getBsDh(), angle.getFsDh() });

	}

	@Override
	public void delete(Angle angle) {

		String sql = "DELETE FROM angles WHERE angle_id = ?";

		getJdbcTemplate().update(sql, new Object[] { angle.getId() });
	}

	@Override
	public void update(Angle angle) {

		String sql = "UPDATE angles SET from_id=?, bs=?, fs=?, val=?, stdev=?, from_dh=?, bs_dh=?, fs_dh=? WHERE angle_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { angle.getFrom(), angle.getBs(), angle.getFs(),
						angle.getVal(), angle.getStdev(), angle.getFromDh(),
						angle.getBsDh(), angle.getFsDh(), angle.getId() });
	}

	@Override
	public List<Angle> findAnglesInObservation(Observation observation) {

		String sql = "SELECT * FROM angles WHERE observation_id = ?";

		List<Angle> angles = getJdbcTemplate().query(sql,
				new Object[] { observation.getId() }, new AngleMapper());

		return angles;
	}

	private class AngleMapper implements RowMapper<Angle> {

		@Override
		public Angle mapRow(ResultSet rs, int rowNum) throws SQLException {

			Angle angle = new Angle();

			angle.setId(rs.getInt("angle_id"));
			angle.setFrom(rs.getString("from_id"));
			angle.setBs(rs.getString("bs"));
			angle.setFs(rs.getString("fs"));
			angle.setVal(rs.getDouble("val"));
			angle.setStdev(rs.getDouble("stdev"));
			angle.setFromDh(rs.getDouble("from_dh"));
			angle.setBsDh(rs.getDouble("bs_dh"));
			angle.setFsDh(rs.getDouble("fs_dh"));

			return angle;
		}
	}
}

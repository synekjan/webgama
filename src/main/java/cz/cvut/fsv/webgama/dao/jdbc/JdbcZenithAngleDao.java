package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.ZenithAngleDao;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.ZenithAngle;

public class JdbcZenithAngleDao extends JdbcDaoSupport implements
		ZenithAngleDao {

	@Override
	public void insert(ZenithAngle zenithAngle, Integer observationId) {

		String sql = "INSERT INTO zenith_angles (observation_id, from_id, to_id, val, stdev, from_dh, to_dh) VALUES (?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { observationId, zenithAngle.getFrom(),
						zenithAngle.getTo(), zenithAngle.getVal(),
						zenithAngle.getStdev(), zenithAngle.getFromDh(),
						zenithAngle.getToDh() });
	}

	@Override
	public void delete(ZenithAngle zenithAngle) {

		String sql = "DELETE FROM zenith_angles WHERE zenith_angle_id = ?";

		getJdbcTemplate().update(sql, new Object[] { zenithAngle.getId() });
	}

	@Override
	public void update(ZenithAngle zenithAngle) {

		String sql = "UPDATE zenith_angles SET from_id=?, to_id=?, val=?, stdev=?, from_dh=?, to_dh=? WHERE zenith_angle_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { zenithAngle.getFrom(), zenithAngle.getTo(),
						zenithAngle.getVal(), zenithAngle.getStdev(),
						zenithAngle.getFromDh(), zenithAngle.getToDh(),
						zenithAngle.getId() });
	}

	@Override
	public List<ZenithAngle> findZenithAnglesInObservation(
			Observation observation) {

		String sql = "SELECT * FROM zenith_angles WHERE observation_id = ?";

		List<ZenithAngle> zenithAngles = getJdbcTemplate().query(sql,
				new Object[] { observation.getId() }, new ZenithAngleMapper());

		return zenithAngles;
	}

	private static class ZenithAngleMapper implements RowMapper<ZenithAngle> {

		@Override
		public ZenithAngle mapRow(ResultSet rs, int rowNum) throws SQLException {

			ZenithAngle zenithAngle = new ZenithAngle();

			zenithAngle.setId(rs.getInt("zenith_angle_id"));
			zenithAngle.setFrom(rs.getString("from_id"));
			zenithAngle.setTo(rs.getString("to_id"));
			zenithAngle.setVal(rs.getDouble("val"));
			zenithAngle.setStdev(rs.getDouble("stdev"));
			zenithAngle.setFromDh(rs.getDouble("from_dh"));
			zenithAngle.setToDh(rs.getDouble("to_dh"));

			return zenithAngle;
		}
	}

}

package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.CalculationStatisticDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.CalculationStatistic;

public class JdbcCalculationStatisticDao extends JdbcDaoSupport implements CalculationStatisticDao {

	@Override
	public void insert(CalculationStatistic calculationStatistic, Long calculationId) {

		String sql = "INSERT INTO calculation_statistics (calculation_id, points, clusters) VALUES (?, ?, ?)";

		getJdbcTemplate().update(sql, calculationId, calculationStatistic.getPoints(),
				calculationStatistic.getClusters());

	}

	@Override
	public void delete(CalculationStatistic calculationStatistic) {

		String sql = "DELETE FROM calculation_statistics WHERE calculation_statistic_id = ?";

		getJdbcTemplate().update(sql, calculationStatistic.getId());

	}

	@Override
	public void update(CalculationStatistic calculationStatistic) {

		String sql = "UPDATE calculation_statistics SET points=?, clusters=? WHERE calculation_statistic_id =?";

		getJdbcTemplate().update(sql, calculationStatistic.getPoints(), calculationStatistic.getClusters(),
				calculationStatistic.getId());

	}

	@Override
	public CalculationStatistic findCalculationStatisticInCalculation(Calculation calculation) {

		String sql = "SELECT * FROM calculation_statistics WHERE calculation_id =?";

		CalculationStatistic statistic = getJdbcTemplate().queryForObject(sql, new Object[] { calculation.getId() },
				new CalculationStatisticMapper());

		return statistic;
	}

	private class CalculationStatisticMapper implements RowMapper<CalculationStatistic> {

		@Override
		public CalculationStatistic mapRow(ResultSet rs, int rowNum) throws SQLException {

			CalculationStatistic statistic = new CalculationStatistic();

			statistic.setId(rs.getLong("calculation_statistic_id"));
			statistic.setPoints(rs.getInt("points"));
			statistic.setClusters(rs.getInt("clusters"));

			return statistic;
		}

	}

}

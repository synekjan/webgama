package cz.cvut.fsv.webgama.dao;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.CalculationStatistic;

public interface CalculationStatisticDao {

	public void insert(CalculationStatistic calculationStatistic, Long calculationId);

	public void delete(CalculationStatistic calculationStatistic);

	public void update(CalculationStatistic calculationStatistic);

	public CalculationStatistic findCalculationStatisticInCalculation(Calculation calculation);

}

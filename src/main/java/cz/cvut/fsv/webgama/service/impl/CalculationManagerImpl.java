package cz.cvut.fsv.webgama.service.impl;

import org.springframework.scheduling.annotation.Async;

import cz.cvut.fsv.webgama.service.CalculationManager;

public class CalculationManagerImpl implements CalculationManager {

	@Override
	@Async
	public void calculate() {
		
		System.out.println("Start --- " + Thread.currentThread().getName());
		
		long i = 0;
		while (i < 100000000L) {
			i++;
		}
		
		System.out.println("Konec --- " + Thread.currentThread().getName());
		

	}

}

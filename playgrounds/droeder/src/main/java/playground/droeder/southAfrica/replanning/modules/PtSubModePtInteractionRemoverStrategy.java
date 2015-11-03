/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2012 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package playground.droeder.southAfrica.replanning.modules;

import org.matsim.core.controler.Controler;
import org.matsim.core.replanning.modules.AbstractMultithreadedModule;
import org.matsim.population.algorithms.PlanAlgorithm;

/**
 * @author droeder
 *
 */
public class PtSubModePtInteractionRemoverStrategy extends AbstractMultithreadedModule {
	
	/**
	 * This class provides a strategy to remove pt-interactions from a plan, but changes the 
	 * legmode of the "real" pt-leg not to <code>TransportMode.pt</code>. Instead it keeps the 
	 * original mode
	 * 
	 * @param c
	 */
	public PtSubModePtInteractionRemoverStrategy(Controler c){
		super(c.getConfig().global());
	}
//
	@Override
	public PlanAlgorithm getPlanAlgoInstance() {
		return new PtSubModePtInteractionRemover();
	}

//	@Override
//	public void prepareReplanning() {
//		
//	}
//
//	@Override
//	public void handlePlan(Plan plan) {
//		new FixedPtSubModePtInteractionRemover().run(plan);
//		
//	}
//
//	@Override
//	public void finishReplanning() {
//		
//	}

}
/* *********************************************************************** *
 * project: org.matsim.*
 * FhEmissions.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009 by the members listed in the COPYING,        *
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
 *                                                                         
 * *********************************************************************** */
package playground.benjamin.emissions.dataTypes;

public class VisumRoadTypes {

	private final int VISUM_RT_NR;
	private String VISUM_RT_NAME;
	private final int HBEFA_RT_NR;
	private String HBEFA_RT_NAME;
	
	public VisumRoadTypes(int visumRtNr, int hbefaRtNr) {
		this.VISUM_RT_NR = visumRtNr;
		this.HBEFA_RT_NR = hbefaRtNr;
	}

	public int getVISUM_RT_NR() {
		return VISUM_RT_NR;
	}
	public String getVISUM_RT_NAME() {
		return VISUM_RT_NAME;
	}
	public void setVISUM_RT_NAME(String visumRtName) {
		VISUM_RT_NAME = visumRtName;
	}
	public int getHBEFA_RT_NR() {
		return HBEFA_RT_NR;
	}
	public String getHBEFA_RT_NAME() {
		return HBEFA_RT_NAME;
	}
	public void setHBEFA_RT_NAME(String hbefaRtName) {
		HBEFA_RT_NAME = hbefaRtName;
	}
}

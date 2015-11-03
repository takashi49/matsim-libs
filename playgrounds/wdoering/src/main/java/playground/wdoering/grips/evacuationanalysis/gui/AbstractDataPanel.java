/* *********************************************************************** *
 * project: org.matsim.*
 * RoadClosuresEditor.java
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

package playground.wdoering.grips.evacuationanalysis.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JPanel;

import org.matsim.core.utils.collections.QuadTree;

import playground.wdoering.grips.evacuationanalysis.data.EventData;

public abstract class AbstractDataPanel extends JPanel implements DataPanelInterface {
	

	protected EventData data;
	protected int width;
	protected int height;
	
	public AbstractDataPanel()
	{
		//reset all values
		resetData();
	}
	
	@Override
	public void resetData()
	{
		data = null;
	}

	@Override
	public void updateData(EventData data)
	{
		this.data = data;
		drawDataPanel();
	}
	
	public void setPanelSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public abstract void drawDataPanel();

}
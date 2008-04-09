/* *********************************************************************** *
 * project: org.matsim.*
 * MyControler5.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007 by the members listed in the COPYING,        *
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

package playground.yu.analysis;

import java.util.HashMap;
import java.util.Map;

import org.matsim.analysis.VolumesAnalyzer;
import org.matsim.config.Config;
import org.matsim.events.Events;
import org.matsim.events.MatsimEventsReader;
import org.matsim.gbl.Gbl;
import org.matsim.network.Link;
import org.matsim.network.MatsimNetworkReader;
import org.matsim.network.NetworkLayer;
import org.matsim.world.World;

public class VolumefromEventsTest {

	@SuppressWarnings("unchecked")
	public static void main(final String[] args) {
		final String netFilename = "./test/yu/test/input/network.xml";
		// final String plansFilename = "./examples/equil/plans100.xml";
		final String eventsFilename = "./test/yu/test/input/miv_zrh30km_10pct100.events.txt.gz";
		final String volumeTestFilename = "./test/yu/test/output/miv_zrh30km_10pct100.eventsVolumeTest.txt.gz";
		@SuppressWarnings("unused")
		Config config = Gbl.createConfig(null
		// new String[] { "./test/yu/test/configTest.xml" }
				);

		World world = Gbl.getWorld();

		NetworkLayer network = new NetworkLayer();
		new MatsimNetworkReader(network).readFile(netFilename);
		world.setNetworkLayer(network);

		Events events = new Events();
		VolumesAnalyzer volumes = new VolumesAnalyzer(3600, 24 * 3600 - 1,
				network);

		events.addHandler(volumes);

		new MatsimEventsReader(events).readFile(eventsFilename);

		Map<String, Double> vol7s = new HashMap<String, Double>();
		for (Link ql : network.getLinks().values()) {
			int[] v = volumes.getVolumesForLink(ql.getId().toString());
			vol7s.put(ql.getId().toString(), (double) ((v != null) ? v[7] : 0));
		}
		System.out.println("-> Done!");
		System.exit(0);
	}
}

package playground.mzilske.teach;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.config.groups.StrategyConfigGroup.StrategySettings;
import org.matsim.core.controler.Controler;
import org.matsim.core.population.ActivityImpl;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.scenario.ScenarioImpl;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.misc.RouteUtils;
import org.matsim.pt.transitSchedule.api.Departure;
import org.matsim.pt.transitSchedule.api.TransitLine;
import org.matsim.pt.transitSchedule.api.TransitRoute;
import org.matsim.pt.transitSchedule.api.TransitRouteStop;
import org.matsim.pt.transitSchedule.api.TransitSchedule;
import org.matsim.pt.transitSchedule.api.TransitScheduleFactory;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;
import org.matsim.vehicles.Vehicle;
import org.matsim.vehicles.VehicleCapacity;
import org.matsim.vehicles.VehicleType;
import org.matsim.vehicles.VehiclesFactory;


public class CrashExample
{
	public static void main(String [] args)
	{
		Config config = ConfigUtils.createConfig();
		config.scenario().setUseTransit(true);
		config.scenario().setUseVehicles(true);
		
		HashSet<String> transitModes = new HashSet<String>();
		transitModes.add("pt");
		config.transit().setTransitModes(transitModes);
		config.addQSimConfigGroup(new QSimConfigGroup());
		ScenarioImpl scen = (ScenarioImpl) ScenarioUtils.createScenario(config);
		
		/* ************** *
		 * Create Network *
		 * ************** */
		
		Network net = scen.getNetwork();
		NetworkFactory nw = net.getFactory();
		
		Node a = nw.createNode(scen.createId("NodeA"), scen.createCoord(100, 100));
		Node b = nw.createNode(scen.createId("NodeB"), scen.createCoord(500100, 500100));
		Node u = nw.createNode(scen.createId("NodeU"), scen.createCoord(95, 95));
		Node v = nw.createNode(scen.createId("NodeV"), scen.createCoord(500105, 500105));
		Link ab = nw.createLink(scen.createId("LinkAB"),a,b);
		Link ua = nw.createLink(scen.createId("LinkUA"),u,a);
		Link bv = nw.createLink(scen.createId("LinkBV"),b,v);
		Link ba = nw.createLink(scen.createId("LinkBA"),b,a);
		Link au = nw.createLink(scen.createId("LinkAU"),a,u);
		Link vb = nw.createLink(scen.createId("LinkVB"),v,b);
		
		net.addNode(a);
		net.addNode(b);
		net.addNode(u);
		net.addNode(v);
		net.addLink(ab);
		net.addLink(ua);
		net.addLink(bv);
		net.addLink(ba);
		net.addLink(au);
		net.addLink(vb);
			
		HashSet<String> modes = new HashSet<String>();
		modes.add("car");
		modes.add("walk");
		for (Link l : net.getLinks().values())
		{
			l.setAllowedModes(modes);
		}
		
		/* *************** *
		 * Create Schedule *
		 * *************** */
		
		boolean blockLane = false;
		
		TransitSchedule schedule = scen.getTransitSchedule();
		TransitScheduleFactory sFac = schedule.getFactory();
		
		TransitStopFacility aStop = sFac.createTransitStopFacility(scen.createId("AStop"), ua.getCoord(), blockLane);
		aStop.setLinkId(ua.getId());
		TransitStopFacility iStop = sFac.createTransitStopFacility(scen.createId("IStop"), ab.getCoord(), blockLane);
		iStop.setLinkId(ab.getId());
		TransitStopFacility bStop = sFac.createTransitStopFacility(scen.createId("BStop"), bv.getCoord(), blockLane);
		bStop.setLinkId(bv.getId());
		
		
		schedule.addStopFacility(aStop);
		schedule.addStopFacility(bStop);
		schedule.addStopFacility(iStop);
		
		TransitLine line1 = sFac.createTransitLine(scen.createId("Line1"));
		
		List<Id> routeIds1 = new LinkedList<Id>();
		routeIds1.add(ua.getId());
		routeIds1.add(ab.getId());
		routeIds1.add(bv.getId());
		
		List<TransitRouteStop> stops1 = new LinkedList<TransitRouteStop>();
		stops1.add(sFac.createTransitRouteStop(aStop, 0, 0));
		stops1.add(sFac.createTransitRouteStop(iStop, 2.1 * 3600, 2.1 * 3600));
		stops1.add(sFac.createTransitRouteStop(bStop, 4.2 * 3600, 4.2 * 3600));
		
		VehiclesFactory vFac = scen.getVehicles().getFactory();
		
		NetworkRoute route1 = RouteUtils.createNetworkRoute(routeIds1, net);
		TransitRoute transitRoute1 = sFac.createTransitRoute(scen.createId("l1r1"), route1, stops1, "pt");
		for (int t=1; t <= 20; t++)
		{
			Departure dep = sFac.createDeparture(scen.createId("r1dep"+t), 3600 * t);
			
			VehicleCapacity cap = vFac.createVehicleCapacity();
			cap.setSeats(10);
			cap.setStandingRoom(20);
			VehicleType type = vFac.createVehicleType(scen.createId("r1dep"+t+"type"));
			type.setCapacity(cap);
			type.setMaximumVelocity(50);
			scen.getVehicles().getVehicleTypes().put(type.getId(), type);
			Vehicle veh = vFac.createVehicle(scen.createId("r1dep"+t+"veh"), type);
			scen.getVehicles().getVehicles().put(veh.getId(), veh);
			dep.setVehicleId(veh.getId());
			
			
			transitRoute1.addDeparture(dep);
		}
		line1.addRoute(transitRoute1);
		
		schedule.addTransitLine(line1);		
		
		/* ***************** *
		 * Create Population *
		 * ***************** */
		
		Random ran = new Random(config.global().getRandomSeed());
		
		Population pop = scen.getPopulation();
		PopulationFactory popFac = pop.getFactory();
		
		ActivityImpl homeBegin = (ActivityImpl) popFac.createActivityFromLinkId("home", ua.getId());
		// Uncommenting this solves the problem!
		//homeBegin.setCoord(ua.getCoord());
		homeBegin.setEndTime(7 * 3600);
		ActivityImpl homeEnd = (ActivityImpl) popFac.createActivityFromLinkId("home", bv.getId());
		// Uncommenting this solves the problem!
		//homeEnd.setCoord(bv.getCoord());
		
		
		for (int t=0; t < 300; t++)
		{
			Person p = popFac.createPerson(scen.createId("Agent"+t));
			Plan plan = popFac.createPlan();
			plan.addActivity(homeBegin);
			plan.addLeg(popFac.createLeg("pt"));
			plan.addActivity(homeEnd);
			p.addPlan(plan);
			pop.addPerson(p);
		}
		
		
		/* ****************** *
		 * Initialize Scoring *
		 * ****************** */
		
		ActivityParams home = new ActivityParams("home");
		home.setTypicalDuration(3600 * 12);	
		config.planCalcScore().addActivityParams(home);
		
		/* ******************* *
		 * Initialize Strategy *
		 * ******************* */
		
		StrategySettings stratSets = new StrategySettings(scen.createId("1"));
		//stratSets.setModuleName("BestScore");
		stratSets.setModuleName("SelectExpBeta");
		stratSets.setProbability(0.9);
		config.strategy().addStrategySettings(stratSets);
		stratSets = new StrategySettings(scen.createId("2"));
		stratSets.setModuleName("TransitTimeAllocationMutator");
		stratSets.setProbability(0.1);
		config.strategy().addStrategySettings(stratSets);
		

		/* ****************** *
		 * Run the simulation *
		 * ****************** */
		
		Controler c = new Controler(scen);
		c.getConfig().controler().setWriteEventsInterval(0);
		c.setOverwriteFiles(true);
		
		c.run();
	}
}
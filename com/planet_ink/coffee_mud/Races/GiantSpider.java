package com.planet_ink.coffee_mud.Races;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class GiantSpider extends StdRace
{
	protected static Vector resources=new Vector();
	public GiantSpider()
	{
		super();
		myID=this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.')+1);
		name="Giant Spider";
		// inches
		shortestMale=42;
		shortestFemale=42;
		heightVariance=8;
		// pounds
		lightestWeight=180;
		weightVariance=40;
		forbiddenWornBits=Integer.MAX_VALUE;
	}
	public boolean playerSelectable(){return false;}

	public void affectEnvStats(Environmental affected, EnvStats affectableStats)
	{
		super.affectEnvStats(affected,affectableStats);
		affectableStats.setDisposition(affectableStats.disposition()|EnvStats.IS_SNEAKING);
	}
	public void affectCharStats(MOB affectedMOB, CharStats affectableStats)
	{
		super.affectCharStats(affectedMOB, affectableStats);
		affectableStats.setStat(CharStats.SAVE_POISON,affectableStats.getStat(CharStats.SAVE_POISON)+100);
	}
	public String arriveStr()
	{
		return "creeps in";
	}
	public String leaveStr()
	{
		return "creeps";
	}
	public Weapon myNaturalWeapon()
	{
		if(naturalWeapon==null)
		{
			naturalWeapon=CMClass.getWeapon("StdWeapon");
			naturalWeapon.setName("a nasty maw");
			naturalWeapon.setWeaponType(Weapon.TYPE_NATURAL);
		}
		return naturalWeapon;
	}
	public Vector myResources()
	{
		synchronized(resources)
		{
			if(resources.size()==0)
			{
				resources.addElement(makeResource
				("some "+name.toLowerCase()+" legs",EnvResource.RESOURCE_MEAT));
			}
		}
		return resources;
	}
}
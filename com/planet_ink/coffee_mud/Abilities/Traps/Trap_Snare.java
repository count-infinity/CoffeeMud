package com.planet_ink.coffee_mud.Abilities.Traps;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

/* 
   Copyright 2000-2012 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
@SuppressWarnings("unchecked")
public class Trap_Snare extends StdTrap
{
	public String ID() { return "Trap_Snare"; }
	public String name(){ return "snare trap";}
	protected int canAffectCode(){return Ability.CAN_ROOMS;}
	protected int canTargetCode(){return 0;}
	protected int trapLevel(){return 5;}
	public String requiresToSet(){return "5 pounds of cloth";}

	public Trap setTrap(MOB mob, Physical P, int trapBonus, int qualifyingClassLevel, boolean perm)
	{
		if(P==null) return null;
		if(mob!=null)
		{
			Item I=findMostOfMaterial(mob.location(),RawMaterial.MATERIAL_CLOTH);
			if(I!=null)
				super.destroyResources(mob.location(),I.material(),5);
		}
		return super.setTrap(mob,P,trapBonus,qualifyingClassLevel,perm);
	}

    public List<Item> getTrapComponents() {
        Vector V=new Vector();
        for(int i=0;i<5;i++)
            V.addElement(CMLib.materials().makeItemResource(RawMaterial.RESOURCE_COTTON));
        return V;
    }
	public boolean canSetTrapOn(MOB mob, Physical P)
	{
		if(!super.canSetTrapOn(mob,P)) return false;
		if(mob!=null)
		{
			Item I=findMostOfMaterial(mob.location(),RawMaterial.MATERIAL_CLOTH);
			if((I==null)
			||(findNumberOfResource(mob.location(),I.material())<5))
			{
				mob.tell("You'll need to set down at least 5 pounds of cloth first.");
				return false;
			}
		}
		return true;
	}

	public void spring(MOB target)
	{
		if((target!=invoker())&&(target.location()!=null))
		{
			if((!invoker().mayIFight(target))
			||(isLocalExempt(target))
			||(invoker().getGroupMembers(new HashSet<MOB>()).contains(target))
			||(target==invoker())
			||(doesSaveVsTraps(target)))
				target.location().show(target,null,null,CMMsg.MASK_ALWAYS|CMMsg.MSG_NOISE,"<S-NAME> avoid(s) tripping a snare trap!");
			else
			if(target.location().show(target,target,this,CMMsg.MASK_ALWAYS|CMMsg.MSG_NOISE,"<S-NAME> trip(s) a snare trap and get(s) all tangled up!"))
			{
				super.spring(target);
				target.basePhyStats().setDisposition(target.basePhyStats().disposition()|PhyStats.IS_SITTING);
				target.recoverPhyStats();
				Ability A=CMClass.getAbility("Thief_Bind");
				Item I=CMClass.getItem("StdItem");
				I.setName("the snare");
				A.setAffectedOne(I);
				A.invoke(invoker(),target,true,0);
			}
		}
	}
}

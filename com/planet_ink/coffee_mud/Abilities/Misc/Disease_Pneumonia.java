package com.planet_ink.coffee_mud.Abilities.Misc;
import com.planet_ink.coffee_mud.Abilities.StdAbility;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class Disease_Pneumonia extends Disease
{
	public String ID() { return "Disease_Pneumonia"; }
	public String name(){ return "Pneumonia";}
	public String displayText(){ return "(Pneumonia)";}
	protected int canAffectCode(){return CAN_MOBS;}
	protected int canTargetCode(){return CAN_MOBS;}
	public int quality(){return Ability.MALICIOUS;}
	public boolean putInCommandlist(){return false;}
	public Environmental newInstance(){	return new Disease_Pneumonia();}
	public int classificationCode(){return Ability.SKILL;}

	protected int DISEASE_TICKS(){return 38;}
	protected int DISEASE_DELAY(){return 3;}
	protected String DISEASE_DONE(){return "Your pneumonia clears up.";}
	protected String DISEASE_START(){return "^G<S-NAME> come(s) down with pneumonia.^?";}
	protected String DISEASE_AFFECT(){return "<S-NAME> shake(s) feverishly.";}
	
	public boolean tick(int tickID)
	{
		if(!super.tick(tickID))	return false;
		if((affected==null)||(invoker==null)) return false;

		MOB mob=(MOB)affected;
		if((--diseaseTick)<=0)
		{
			diseaseTick=DISEASE_DELAY();
			mob.location().show(mob,null,Affect.MSG_OK_VISUAL,DISEASE_AFFECT());
			int damage=Dice.roll(4,invoker.envStats().level()+1,1);
			ExternalPlay.postDamage(invoker,mob,this,damage,Affect.ACT_GENERAL|Affect.TYP_DISEASE,-1,null);
			catchIt(mob);
			return true;
		}
		return false;
	}

	public void affectCharStats(MOB affected, CharStats affectableStats)
	{
		if(affected==null) return;
		affectableStats.setStat(CharStats.CONSTITUTION,affectableStats.getStat(CharStats.CONSTITUTION)-8);
		affectableStats.setStat(CharStats.STRENGTH,affectableStats.getStat(CharStats.STRENGTH)-10);
		if(affectableStats.getStat(CharStats.CONSTITUTION)<=0)
			affectableStats.setStat(CharStats.CONSTITUTION,1);
		if(affectableStats.getStat(CharStats.STRENGTH)<=0)
			affectableStats.setStat(CharStats.STRENGTH,1);
	}
	
	public void affectCharState(MOB affected, CharState affectableState)
	{
		if(affected==null) return;
		affectableState.setMovement(10);
		affectableState.setMana(affectableState.getMana()-(affectableState.getMana()/2));
		affectableState.setHitPoints(affectableState.getHitPoints()-(affected.envStats().level()*2));
	}
}

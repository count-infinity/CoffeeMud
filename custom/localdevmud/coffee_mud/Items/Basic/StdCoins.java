package custom.localdevmud.coffee_mud.Items.Basic;


import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Items.interfaces.Coins;

import java.util.Enumeration;

public class StdCoins extends com.planet_ink.coffee_mud.Items.Basic.StdCoins implements Coins
{	

	@Override
	public void recoverPhyStats()
	{		
		basePhyStats.setWeight(0);		
		basePhyStats.copyInto(phyStats);
		// import not to sup this, otherwise 'ability' makes it magical!
		for(final Enumeration<Ability> a=effects();a.hasMoreElements();)
		{
			final Ability A=a.nextElement();
			if(A!=null)
				A.affectPhyStats(this,phyStats);
		}		
	}
}

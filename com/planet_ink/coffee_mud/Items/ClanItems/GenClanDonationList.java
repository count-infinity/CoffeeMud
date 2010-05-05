package com.planet_ink.coffee_mud.Items.ClanItems;
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
import com.planet_ink.coffee_mud.Libraries.interfaces.*;

/* 
   Copyright 2000-2010 Bo Zimmerman

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
public class GenClanDonationList extends StdClanDonationList
{
    public String ID(){ return "GenClanDonationList";}
    protected String readableText="";
    public GenClanDonationList()
    {
        super();
        setName("a generic clan donation list");
        setDisplayText("a generic clan donation list sits here.");
        recoverPhyStats();
    }


    public boolean isGeneric(){return true;}

    public String text()
    {
        return CMLib.coffeeMaker().getPropertiesStr(this,false);
    }

    public String readableText(){return readableText;}
    public void setReadableText(String text){readableText=text;}
    public void setMiscText(String newText)
    {
        miscText="";
        CMLib.coffeeMaker().setPropertiesStr(this,newText,false);
        recoverPhyStats();
    }
    private final static String[] MYCODES={"CLANID","CITYPE"};
    public String getStat(String code)
    {
        if(CMLib.coffeeMaker().getGenItemCodeNum(code)>=0)
            return CMLib.coffeeMaker().getGenItemStat(this,code);
        switch(getCodeNum(code))
        {
        case 0: return clanID();
        case 1: return ""+ciType();
        default:
            return CMProps.getStatCodeExtensionValue(getStatCodes(), xtraValues, code);
        }
    }
    public void setStat(String code, String val)
    {
        if(CMLib.coffeeMaker().getGenItemCodeNum(code)>=0)
            CMLib.coffeeMaker().setGenItemStat(this,code,val);
        else
        switch(getCodeNum(code))
        {
        case 0: setClanID(val); break;
        case 1: setCIType(CMath.s_parseListIntExpression(ClanItem.CI_DESC,val)); break;
        default:
            CMProps.setStatCodeExtensionValue(getStatCodes(), xtraValues, code, val);
            break;
        }
    }
    protected int getCodeNum(String code){
        for(int i=0;i<MYCODES.length;i++)
            if(code.equalsIgnoreCase(MYCODES[i])) return i;
        return -1;
    }
    private static String[] codes=null;
    public String[] getStatCodes()
    {
        if(codes!=null) return codes;
        String[] MYCODES=CMProps.getStatCodesList(GenClanDonationList.MYCODES,this);
        String[] superCodes=GenericBuilder.GENITEMCODES;
        codes=new String[superCodes.length+MYCODES.length];
        int i=0;
        for(;i<superCodes.length;i++)
            codes[i]=superCodes[i];
        for(int x=0;x<MYCODES.length;i++,x++)
            codes[i]=MYCODES[x];
        return codes;
    }
    public boolean sameAs(Environmental E)
    {
        if(!(E instanceof GenClanDonationList)) return false;
        String[] codes=getStatCodes();
        for(int i=0;i<codes.length;i++)
            if(!E.getStat(codes[i]).equals(getStat(codes[i])))
                return false;
        return true;
    }
}

/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

import java.util.ArrayList;

/**
 *
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class ResearchInfo
{
    public static final int BUILD_RESEARCH_CENTER = 0;
    public static final int BUILD_BOT_FACTORY = 1;
    public static final int BUILD_CONVERTER = 2;
    public static final int BUILD_POWER_STATION = 3;
    public static final int BUILD_RADAR_STATION = 4;
    public static final int BUILD_REPAIR_CENTER = 5;
    public static final int BUILD_DEFENSE_TOWER = 6;
    public static final int BUILD_POWER_PLANT = 7;
    public static final int BUILD_DERRICK = 8;
    public static final int BUILD_NUCLEAR_PLANT = 9;
    public static final int BUILD_AUTO_LAB = 10;
    public static final int BUILD_POWER_CAPTOR = 11;
    public static final int BUILD_EXCHANGE_POST = 12;
    public static final int BUILD_FLAT_GROUND = 13;
    public static final int BUILD_FLAG = 14;
    
    public static final int RESEARCH_TRACKER = 0;
    public static final int RESEARCH_WINGER = 1;
    public static final int RESEARCH_THUMPER = 2;
    public static final int RESEARCH_SHOOTER = 3;
    public static final int RESEARCH_TOWER = 4;
    public static final int RESEARCH_PHAZER = 5;
    public static final int RESEARCH_SHIELDER = 6;
    public static final int RESEARCH_ATOMIC = 7;
    public static final int RESEARCH_I_PAW = 8;
    public static final int RESEARCH_I_GUN = 9;
    
    
    
    private int enabledBuildings = 0;
    private int enabledResearch = 0;
    private int doneResearch = 0;
    
    ResearchInfo()
    {
        enableBuilding(BUILD_FLAT_GROUND);
        enableBuilding(BUILD_FLAG);
    }
    
    public boolean isBuildingEnabled(int type)
    {
        return (enabledBuildings & (1 << type)) != 0;
    }
    
    public void enableBuilding(int type)
    {
        enabledBuildings |= 1 << type;
    }
    
    public void disableBuilding(int type)
    {
        enabledBuildings &= ~(1 << type);
    }
    
    public void setEnabledBuilding(int type, boolean enabled)
    {
        if(enabled) enableBuilding(type);
        else disableBuilding(type);
    }
    
    public boolean isResearchEnabled(int type)
    {
        return (enabledResearch & (1 << type)) != 0;
    }
    
    public boolean isResearchDone(int type)
    {
        if(!isResearchEnabled(type)) return false;
        
        return (doneResearch & (1 << type)) != 0;
    }
    
    public void enableResearch(int type, boolean done)
    {
        enabledResearch |= 1 << type;
        
        if(done) doneResearch |= 1 << type;
    }
    
    public void disableResearch(int type)
    {
        enabledResearch &= ~(1 << type);
        doneResearch &= ~(1 << type);
    }
    
    public void setResearchEnabled(int type, boolean enabled, boolean done)
    {
        if(enabled) enableResearch(type, done);
        else disableResearch(type);
    }
}

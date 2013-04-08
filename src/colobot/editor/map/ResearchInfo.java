/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

/**
 * This class contains enabled buildings and research on map.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class ResearchInfo
{
    public enum Building
    {
        ResearchCenter,
        BotFactory,
        Converter,
        PowerStation,
        RadarStation,
        RepairCenter,
        DefenseTower,
        PowerPlant,
        Derrick,
        NuclearPlant,
        AutoLab,
        PowerCaptor,
        ExchangePost,
        FlatGround,
        Flag;
    }
    
    public enum Research
    {
        TRACKER,
        WINGER,
        THUMPER,
        SHOOTER,
        TOWER,
        PHAZER,
        SHIELDER,
        ATOMIC,
        iPAW,
        iGUN,
        RECYCLER,
        SUBBER,
        SNIFFER;
    }
    
    private int enabledBuildings = 0;
    private int enabledResearch = 0;
    private int doneResearch = 0;
    
    
    ResearchInfo()
    {
        // NOP
    }
    
    public void clear()
    {
        enabledBuildings = 0;
        enabledResearch = 0;
        doneResearch = 0;
    }
    
    public boolean isBuildingEnabled(Building b)
    {
        return (enabledBuildings & (1 << b.ordinal())) != 0;
    }
    
    public void enableBuilding(Building b)
    {
        enabledBuildings |= 1 << b.ordinal();
    }
    
    public void disableBuilding(Building b)
    {
        enabledBuildings &= ~(1 << b.ordinal());
    }
    
    public void setEnabledBuilding(Building b, boolean enabled)
    {
        if(enabled) enableBuilding(b);
        else disableBuilding(b);
    }
    
    public boolean isResearchEnabled(Research r)
    {
        return (enabledResearch & (1 << r.ordinal())) != 0;
    }
    
    public boolean isResearchDone(Research r)
    {
        return (doneResearch & (1 << r.ordinal())) != 0;
    }
    
    public void setResearchEnabled(Research r, boolean enabled)
    {
        if(enabled) enabledResearch |= 1 << r.ordinal();
        else enabledResearch &= ~(1 << r.ordinal());
    }
    
    public void setResearchDone(Research r, boolean enabled)
    {
        if(enabled) doneResearch |= 1 << r.ordinal();
        else doneResearch &= ~(1 << r.ordinal());
    }
    
    public void setResearch(Research r, boolean enabled, boolean done)
    {
        setResearchEnabled(r, enabled);
        setResearchDone(r, done);
    }
}

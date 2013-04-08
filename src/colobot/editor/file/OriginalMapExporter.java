/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.file;

import colobot.editor.map.ColobotObject;
import colobot.editor.map.GeneralInfo;
import colobot.editor.map.GeneralInfo.Language;
import colobot.editor.map.Map;
import colobot.editor.map.Objects;
import colobot.editor.map.ResearchInfo;
import colobot.editor.map.ResearchInfo.Building;
import colobot.editor.map.ResearchInfo.Research;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 * This class exports original Colobot maps to {@code Writer}.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class OriginalMapExporter extends MapExporter
{
    @Override
    public void exportMap(Writer writer, Map map) throws IOException
    {
        // write editor name and current date and time
        writer.write("// Colobot Map Editor, " + new Date(System.currentTimeMillis()).toString() + "\n");
        
        // TODO: writing other map parameters
        
        exportGeneralInfo(writer, map.getGeneralInfo());
        
        // writing objects
        exportObjects(writer, map.getObjects());
        
        // end of export
        writer.write("// end of file\n");
        
        // buildings and research
        exportResearchInfo(writer, map.getResearchInfo());
    }
    
    @Override
    public void exportObjects(Writer writer, Objects objects) throws IOException
    {
        // objects begin
        writer.write("// Colobot objects\n");
        writer.write("BeginObjects\n");
        
        for(ColobotObject object : objects)
        {
            writer.write(object.toString());
            writer.write('\n');
        }
    }
    
    private void exportGeneralInfo(Writer writer, GeneralInfo info) throws IOException
    {
        // exporting language info
        for(int i=0; i<4; i++)
        {
            Language lang = info.getLanguage(i);
            
            char letter = lang.getLetter();
            if(letter == '\0') continue;
            
            writer.write("Title." + letter + " text=\"" + lang.getTitle() + "\"\n");
            writer.write("Resume." + letter + " text=\"" + lang.getDescription() + "\"\n");
        }
        
        // general info
        writer.write("Instructions name=\"" + info.getInstructions() + "\"\n");
        writer.write("Satellite name=\"" + info.getSatellite() + "\"\n");
        writer.write("Loading name=\"" + info.getLoadingFile() + "\"\n");
        writer.write("SoluceFile name=\"" + info.getSolutionFile() + "\"\n");
        writer.write("HelpFile name=\"" + info.getHelpFile() + "\"\n");
        writer.write("EndingFile win=" + info.getEndingFileWin()
                + " lost=" + info.getEndingFileLost() + "\n");
        writer.write('\n');
    }
    
    private void exportResearchInfo(Writer writer, ResearchInfo info) throws IOException
    {
        // export available buildings
        for(Building b : Building.values())
        {
            if(!info.isBuildingEnabled(b)) continue;
            
            writer.write("EnableBuild type=");
            writer.write(b.toString());
            writer.write('\n');
        }
        
        writer.write('\n');
        
        Research[] research = Research.values();
        
        // export enabled research
        for(int i=0; i<research.length-3; i++)
        {
            if(!info.isResearchEnabled(research[i])) continue;
            
            writer.write("EnableResearch type=");
            writer.write(research[i].toString());
            writer.write('\n');
        }
        
        writer.write('\n');
        
        // export done research
        for(int i=0; i<research.length; i++)
        {
            if(!info.isResearchEnabled(research[i])) continue;
            
            writer.write("DoneResearch type=");
            writer.write(research[i].toString());
            writer.write('\n');
        }
    }
}

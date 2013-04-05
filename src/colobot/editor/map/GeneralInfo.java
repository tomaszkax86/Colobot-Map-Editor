/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

/**
 * Objects of this class represent general info of Colobot map.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class GeneralInfo
{
    public static final int AUDIO_MOON = 0;
    public static final int AUDIO_EARTH = 2;
    public static final int AUDIO_TROPIC = 3;
    public static final int AUDIO_CRYSTALIUM = 4;
    public static final int AUDIO_SAARI = 5;
    public static final int AUDIO_VOLCANO = 6;
    public static final int AUDIO_CENTAURY = 7;
    public static final int AUDIO_ORPHEON = 8;
    public static final int AUDIO_TERRANOVA = 9;
    
    private final Language[] lang = new Language[4];    // language data
    private String instructions = null;     // instructions file name
    private String satellite = null;        // satellite info file name
    private String loading = null;          // Houston programs file name
    private String solutionFile = null;     // solution file name
    private String helpFile = "cbot.txt";   // CBot help file
    private double messageDelay = 5.0;      // time of message display
    private int win = -1, lost = 0;         // win/lost type
    private int audioTrack = AUDIO_MOON;    // audio track
    
    
    GeneralInfo()
    {
        for(int i=0; i<lang.length; i++)
            lang[i] = new Language();
        
        lang[0].set('E', "Template title", "Template description");
    }
    
    public Language getLanguage(int index)
    {
        return lang[index];
    }
    
    public String getInstructions()
    {
        return instructions;
    }
    
    public GeneralInfo setInstructions(String file)
    {
        this.instructions = file;
        return this;
    }
    
    public String getSatellite()
    {
        return satellite;
    }
    
    public GeneralInfo setSatellite(String file)
    {
        this.satellite = file;
        return this;
    }
    
    public String getLoadingFile()
    {
        return loading;
    }
    
    public GeneralInfo setLoadingFile(String file)
    {
        this.loading = file;
        return this;
    }
    
    public String getSolutionFile()
    {
        return solutionFile;
    }
    
    public GeneralInfo setSolutionFile(String file)
    {
        this.solutionFile = file;
        return this;
    }
    
    public String getHelpFile()
    {
        return helpFile;
    }
    
    public GeneralInfo setHelpFile(String file)
    {
        this.helpFile = file;
        return this;
    }
    
    public double getMessageDelay()
    {
        return messageDelay;
    }
    
    public GeneralInfo setMessageDelay(double messageDelay)
    {
        this.messageDelay = messageDelay;
        return this;
    }
    
    public int getAudioTrack()
    {
        return audioTrack;
    }
    
    public GeneralInfo setAudioTrack(int audioTrack)
    {
        this.audioTrack = audioTrack;
        return this;
    }
    
    public int getEndingFileWin()
    {
        return win;
    }
    
    public int getEndingFileLost()
    {
        return lost;
    }
    
    public static final class Language
    {
        private char letter;
        private String title;
        private String description;
        
        public Language()
        {
            this((char) 0, null, null);
        }
        
        public Language(char letter, String title, String description)
        {
            setLetter(letter);
            setTitle(title);
            setDescription(description);
        }
        
        public char getLetter()
        {
            return letter;
        }
        
        public void setLetter(char letter)
        {
            this.letter = letter;
        }
        
        public String getTitle()
        {
            return title;
        }
        
        public void setTitle(String title)
        {
            this.title = title;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        public void setDescription(String description)
        {
            this.description = description;
        }
        
        public void set(char letter, String title, String description)
        {
            setLetter(letter);
            setTitle(title);
            setDescription(description);
        }
    }
}

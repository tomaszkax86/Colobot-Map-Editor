/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
final class Images
{
    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private static BufferedImage noImage = null;
    
    private Images() {}
    
    static void loadImages(File dir)
    {
        // TODO: loading noImage

        // general
        loadImage("me", dir, "human.png");
        
        loadImage("wheeled-grabber", dir, "botgr.png");
        loadImage("wheeled-sniffer", dir, "botsr.png");
        loadImage("wheeled-shooter", dir, "botfr.png");
        loadImage("wheeled-orga", dir, "botor.png");
        
        loadImage("tracked-grabber", dir, "botgc.png");
        loadImage("tracked-sniffer", dir, "botsc.png");
        loadImage("tracked-shooter", dir, "botfc.png");
        loadImage("tracked-orga", dir, "botoc.png");
        
        loadImage("winged-grabber", dir, "botgj.png");
        loadImage("winged-sniffer", dir, "botsj.png");
        loadImage("winged-shooter", dir, "botfj.png");
        loadImage("winged-orga", dir, "botoj.png");
        
        loadImage("legged-grabber", dir, "botgs.png");
        loadImage("legged-sniffer", dir, "botss.png");
        loadImage("legged-shooter", dir, "botfs.png");
        loadImage("legged-orga", dir, "botos.png");
        
        // insects
        loadImage("ant", dir, "ant.png");
        loadImage("spider", dir, "spider.png");
        loadImage("wasp", dir, "wasp.png");
        loadImage("worm", dir, "worm.png");
        loadImage("queen", dir, "mother.png");
        //loadImage("egg", dir, "egg.png");
    }
    
    private static void loadImage(String name, File dir, String filename)
    {
        try
        {
            File file = new File(dir, filename);
            BufferedImage image = ImageIO.read(file);

            images.put(name, image);
        }
        catch(Exception e)
        {
            // NOP
        }
    }
    
    public static BufferedImage getImage(String name)
    {
        BufferedImage image = images.get(name);
        
        return image != null ? image : noImage;
    }
}

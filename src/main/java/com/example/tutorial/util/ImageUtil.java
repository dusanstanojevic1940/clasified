package com.example.tutorial.util;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.example.tutorial.dao.SettingsDAO;

public class ImageUtil {
	public static void deleteImage(SettingsDAO settingsDAO, String url) {
		try {
			File f = new File(settingsDAO.getSettings().imageURL+"/"+url);
			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean generateAndSaveWatermark(File file, String text) {
		try {
			BufferedImage bufferedImage = generateWatermark(file, text);
			OutputStream out = new FileOutputStream(file);
	        String name = file.getName();
	        String format = name.substring(name.length()-3, name.length());
	        ImageIO.write(bufferedImage, format, out);
	        out.close();
	        return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static BufferedImage generateWatermark(File file, String text) {
		ImageIcon photo = new ImageIcon(file.getAbsolutePath());

		int w = 0, h = 0;
		if (photo.getIconHeight()<photo.getIconWidth()) {
			h = (int)(photo.getIconHeight()/(photo.getIconWidth()/640.0));
			w = 640;
		} else {
			w = (int)(photo.getIconWidth()/(photo.getIconHeight()/480.0));
			h = 480;
		}
		
		//Create an image 200 x 200
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

    	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
    	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
    	RenderingHints.VALUE_RENDER_QUALITY);
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	RenderingHints.VALUE_ANTIALIAS_ON);
    	
        g2d.drawImage(photo.getImage(),0, 0,w,h, null);

        //Create an alpha composite of 50%
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,                                                               0.5f);
        g2d.setComposite(alpha);

        g2d.setColor(Color.white);
        
        
        g2d.setFont(new Font("Arial", Font.BOLD, bufferedImage.getWidth()/20));

        String watermark = text;

        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(watermark, g2d);

        g2d.drawString(watermark, (w - (int) rect.getWidth()) / 2, (h - (int) rect.getHeight()));

        g2d.dispose();
        
        return bufferedImage;
	}
}

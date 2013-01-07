package com.codlex.jsms.client;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

public class ImagePinger implements Runnable {

	@Override
	public void run() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension dimenzijeEkrana = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle ekran = new Rectangle(dimenzijeEkrana.width, dimenzijeEkrana.height);
		
        while(true) {
        	System.out.println("Started sending my screen!");
        	BufferedImage screen = robot.createScreenCapture(ekran);
        	Message m = CentralizedServerNIC.getNICService().pingScreen();
        	OutputStream out = (OutputStream) m.getMsgObject();
        	try {
				ImageIO.write(screen, "PNG", out);
	        	out.close();
	        	Thread.sleep(200);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
		

	}

	            

}

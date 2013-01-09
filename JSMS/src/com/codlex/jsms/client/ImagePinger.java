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
import javax.swing.JOptionPane;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;
import com.codlex.jsms.utils.CompressionImageWriter;

public class ImagePinger implements Runnable {
	private boolean stop;
	public void stop() {
		stop = true;
	}
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
		
        while(!stop) {
        	
        	System.out.println("Started sending my screen!");
        	BufferedImage screen = robot.createScreenCapture(ekran);
        	Message m = CentralizedServerNIC.getNICService().pingScreen();
        	OutputStream out = (OutputStream) m.getMsgObject();
        	try {
        		CompressionImageWriter.jpgLowWrite(screen, out);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Lost connection with the server!");
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        	try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
		

	}

	            

}

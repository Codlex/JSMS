package com.codlex.jsms.client.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.codlex.jsms.client.model.FriendListModelImpl;
import com.codlex.jsms.client.model.TabbedPaneFriend;
import com.codlex.jsms.client.model.FriendListModelImpl.PodrazumevaniPanel;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

public class Osvezivac implements Runnable {

	@Override
	public void run() {
		while(true) {
			if( !(FriendListModelImpl.getPane().getSelectedComponent() instanceof TabbedPaneFriend) ) {
				System.out.println("Panel without picture");
				continue;
			}
			TabbedPaneFriend friend = (TabbedPaneFriend) FriendListModelImpl.getPane().getSelectedComponent();
			String username = friend.getUsername();
			Message m = CentralizedServerNIC.getNICService().getScreen(username);
			BufferedImage img = null;
			if(m.getMsgCode().equals(MSGCode.SUCCESS)) {
				InputStream input = (InputStream) m.getMsgObject();
				try {
					img = ImageIO.read(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(img != null) {
				System.out.println("Image updated");
				friend.setEkran(img);	
				friend.osvezi();
			}
			else {
				System.out.println("Image is null");
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

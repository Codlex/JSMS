package com.codlex.jsms.client.gui.paneli;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class PanelZaSliku extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image slika;

	public PanelZaSliku(Image slika) {
		super();
		this.slika = slika;
		// podesavanje velicine panela na velicinu slike
		this.setPreferredSize(new Dimension(slika.getWidth(this), slika
				.getHeight(this)));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// iscrtavanje slike
		g.drawImage(slika, 0, 0, this.getWidth(), slika.getHeight(this),
				this);

	}
}
package com.codlex.jsms.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class PisacKompresovaneSlike {
	
	public static synchronized void jpgIspisiUSlabomKvalitetu(BufferedImage slika, OutputStream izlaz)  {
		// Uzimanje javinog podrazumevanog imagewriter-a
		ImageOutputStream imageOutputStream = null;
		ImageWriter pisac = null;
		try {
			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
			pisac = (ImageWriter)iter.next();
			// podesavanje parametara kompresije
			ImageWriteParam parametriPisacaSlika = pisac.getDefaultWriteParam();
			parametriPisacaSlika.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			parametriPisacaSlika.setCompressionQuality(0.3f);  
			// podesavanje izlaza za izabrani pisac
			imageOutputStream = ImageIO.createImageOutputStream(izlaz);
			pisac.setOutput(imageOutputStream);
			// ispisujemo sliku koja je prosledjena
			IIOImage IIOSLika = new IIOImage(slika, null, null);
			pisac.write(null, IIOSLika, parametriPisacaSlika);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				imageOutputStream.close();
				izlaz.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pisac.dispose(); 
		}
	}


}

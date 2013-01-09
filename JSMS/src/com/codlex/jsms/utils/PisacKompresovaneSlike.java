package com.codlex.jsms.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class PisacKompresovaneSlike {
	
	public static void jpgIspisiUSlabomKvalitetu(BufferedImage slika, OutputStream izlaz) throws IOException {
		// Uzimanje javinog podrazumevanog imagewriter-a
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter pisac = (ImageWriter)iter.next();
		// podesavanje parametara kompresije
		ImageWriteParam parametriPisacaSlika = pisac.getDefaultWriteParam();
		parametriPisacaSlika.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		parametriPisacaSlika.setCompressionQuality(0.3f);  
		// podesavanje izlaza za izabrani pisac
		pisac.setOutput(ImageIO.createImageOutputStream(izlaz));
		// ispisujemo sliku koja je prosledjena
		IIOImage IIOSLika = new IIOImage(slika, null, null);
		pisac.write(null, IIOSLika, parametriPisacaSlika);
		pisac.dispose();
	}


}

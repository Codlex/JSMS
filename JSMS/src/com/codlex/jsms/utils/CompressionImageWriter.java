package com.codlex.jsms.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class CompressionImageWriter {
	
	public static void jpgLowWrite(BufferedImage screen, OutputStream out) throws IOException {
		Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = (ImageWriter)iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(0.3f);  
		writer.setOutput(ImageIO.createImageOutputStream(out));
		IIOImage image = new IIOImage(screen, null, null);
		writer.write(null, image, iwp);
		writer.dispose();
		
	}


}

/**
 * @package com.doteplay.editor.util
 * @file GraphicUtil.java
 */
package com.doteplay.editor.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import com.doteplay.editor.EditorConfig;

/**
 */
public class GUtil {

	public static File[] getPngFileCounts(File dir) {
		if (!dir.exists()) {
			return null;
		}
		File[] fs = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".png")) {
					return true;
				}
				return false;
			}
		});
		return fs;
	}

	public static int getDigit(int in) {
		int sum = 0, number = 0;
		in = Math.abs(in);// ȡ����ֵ
		if (in == 0) {
			return 1;
		}
		while (in != 0) {
			sum = sum + in % 10;// ȡ���ұߵ�һλ��
			in = in / 10;
			number++;
		}
		return number;
	}

	public static String numToAdd0String(int maxDigit, int in) {
		String s = "";
		int digit = getDigit(in);

		int adds = maxDigit - digit;
		for (int i = 0; i < adds; i++) {
			s += "0";
		}
		return s + in;
	}

	/**
	 * ��ȡͼ�����ʺϵĴ�С
	 * 
	 * @param src Դͼ��
	 * @param maskRGB �ɰ���ɫ
	 * @return
	 */
	@Deprecated
	public static Rectangle getImageFitSize__(BufferedImage src) {
		if (src == null) {
			return null;
		}

		int mask_rgb = src.getRGB(0, 0);

		System.out.println("getImageFitSize  mask_rgb:0x" + Integer.toHexString(mask_rgb) + "  " + src);

		int srcWidth = src.getWidth(); // Դͼ���
		int srcHeight = src.getHeight(); // Դͼ�߶�

		int[] rgbArray = new int[srcWidth * srcHeight];
		src.getRGB(0, 0, srcWidth, srcHeight, rgbArray, 0, srcWidth);

		int min_x = 0, min_y = 0, max_x = 0, max_y = 0;
		boolean loop;

		// ������ɨ�裬����Сxֵ
		loop = true;
		for (int x = 0; x < srcWidth && loop; x++) {
			for (int y = 0; y < srcHeight; y++) {
				int rgb = src.getRGB(x, y);
				if (rgb != mask_rgb) {
					min_x = x;
					loop = false;
				}
			}
		}

		// ���ϵ���ɨ�裬����Сyֵ
		loop = true;
		for (int y = 0; y < srcHeight && loop; y++) {
			for (int x = 0; x < srcWidth; x++) {
				int rgb = src.getRGB(x, y);
				if (rgb != mask_rgb) {
					min_y = y;
					loop = false;
				}
			}
		}

		// ���ҵ���ɨ�裬�����xֵ
		loop = true;
		for (int x = srcWidth - 1; x > 0 && loop; x--) {
			for (int y = srcHeight - 1; y > 0; y--) {
				int rgb = src.getRGB(x, y);
				if (rgb != mask_rgb) {
					max_x = x;
					loop = false;
				}
			}
		}

		// ���µ���ɨ�裬�����yֵ
		loop = true;
		for (int y = srcHeight - 1; y > 0 && loop; y--) {
			for (int x = srcWidth - 1; x > 0; x--) {
				int rgb = src.getRGB(x, y);
				if (rgb != mask_rgb) {
					max_y = y;
					loop = false;
				}
			}
		}

		if (min_x >= srcWidth) {
			min_x = 0;
			max_x = 0;
		}
		if (min_y >= srcHeight) {
			min_y = 0;
			max_y = 0;
		}
		// System.out.println(min_x + "," + min_y + "|" + max_x + "," + max_y);
		return new Rectangle(min_x, min_y, max_x - min_x + 1, max_y - min_y + 1);

	}

	public static Rectangle getImageFitSize(BufferedImage src) {
		if (src == null) {
			return null;
		}

		int srcWidth = src.getWidth(); // Դͼ���
		int srcHeight = src.getHeight(); // Դͼ�߶�

		int[] rgbArray = new int[srcWidth * srcHeight];
		src.getRGB(0, 0, srcWidth, srcHeight, rgbArray, 0, srcWidth);

		int min_x = 0, min_y = 0, max_x = 0, max_y = 0;
		boolean loop;

		// ������ɨ�裬����Сxֵ
		loop = true;
		for (int x = 0; x < srcWidth && loop; x++) {
			for (int y = 0; y < srcHeight; y++) {
				int rgb = src.getRGB(x, y);
//				if (rgb != 0xffffff && rgb != 0) 
				if ((rgb >> 24) != 0) 
				{
					min_x = x;
					loop = false;
				}
			}
		}

		// ���ϵ���ɨ�裬����Сyֵ
		loop = true;
		for (int y = 0; y < srcHeight && loop; y++) {
			for (int x = 0; x < srcWidth; x++) {
				int rgb = src.getRGB(x, y);
//				if (rgb != 0xffffff && rgb != 0) 
				if ((rgb >> 24) != 0) 
				{
					min_y = y;
					loop = false;
				}
			}
		}

		// ���ҵ���ɨ�裬�����xֵ
		loop = true;
		for (int x = srcWidth - 1; x > 0 && loop; x--) {
			for (int y = srcHeight - 1; y > 0; y--) {
				int rgb = src.getRGB(x, y);
//				if (rgb != 0xffffff && rgb != 0) 
				if ((rgb >> 24) != 0) 
				{
					max_x = x;
					loop = false;
				}
			}
		}

		// ���µ���ɨ�裬�����yֵ
		loop = true;
		for (int y = srcHeight - 1; y > 0 && loop; y--) {
			for (int x = srcWidth - 1; x > 0; x--) {
				int rgb = src.getRGB(x, y);
//				if (rgb != 0xffffff && rgb != 0) 

				if ((rgb >> 24) != 0) 
				{
					max_y = y;
					loop = false;
				}
			}
		}

		if (min_x >= srcWidth) {
			min_x = 0;
			max_x = 0;
		}
		if (min_y >= srcHeight) {
			min_y = 0;
			max_y = 0;
		}
		// System.out.println(min_x + "," + min_y + "|" + max_x + "," + max_y);
		return new Rectangle(min_x, min_y, max_x - min_x + 1, max_y - min_y + 1);

	}

	public static final void copyImage(BufferedImage dst, int dx, int dy, BufferedImage src, int sx, int sy, int w,
			int h) {
		try {
			int[] rgbArray = new int[w * h];
			src.getRGB(sx, sy, w, h, rgbArray, 0, w);
			dst.setRGB(dx, dy, w, h, rgbArray, 0, w);
		} catch (Exception e) {
			System.out.println(src + "====(" + dx + "," + dy + "),(" + sx + "," + sy + "),(" + w + "," + h + ")");
			// e.printStackTrace();
		}

	}

	/**
	 * �Զ�����ͼ�����ʺϵĴ�С
	 * 
	 * @param src
	 * @return
	 */
	public static BufferedImage autoCutImage(BufferedImage src) {
		Rectangle rect = getImageFitSize(src);
		return cutImage(src, rect);
	}

	/**
	 * ͼ�����
	 * 
	 * @param src Դͼ��
	 * @param rect ��ʼ����
	 * @return
	 */
	public static BufferedImage cutImage(BufferedImage src, Rectangle rect) {
		return cutImage(src, rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * ͼ�����
	 * 
	 * @param src Դͼ��
	 * @param x ��ʼ����x
	 * @param y ��ʼ����y
	 * @param w ��
	 * @param h ��
	 * @return ���ú�ͼ
	 */
	public static BufferedImage cutImage(BufferedImage src, int x, int y, int w, int h) {
		if (src == null) {
			return null;
		}

		int srcWidth = src.getWidth(); // Դͼ���
		int srcHeight = src.getHeight(); // Դͼ�߶�

		if (w <= 0 || h <= 0 || x < 0 || x + w > srcWidth || y < 0 || y + h > srcHeight) {
			return null;
		}

		int[] rgbArray = new int[w * h];
		src.getRGB(x, y, w, h, rgbArray, 0, w);

		BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		temp.setRGB(0, 0, w, h, rgbArray, 0, w);

		return temp;
	}

	/**
	 * ����ͼƬ
	 * 
	 * @param zoomRatio
	 */
	public static BufferedImage zoomImage(BufferedImage src, float zoomRatio) {
		int width = new Double(src.getWidth(null) * zoomRatio).intValue();
		int height = new Double(src.getHeight(null) * zoomRatio).intValue();
		// ����ͼ��
		Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = newImg.getGraphics();
		g.drawImage(image, 0, 0, null); // ������С���ͼ
		return newImg;
	}
	/**
	 * ָ���������ͼƬ
	 * 
	 * @param src Ҫ���ŵ�ͼƬ
	 * @param width ���ź�Ŀ��
	 * @param height ���ź�ĸ߶�
	 * @return BufferedImage ���ź��ͼƬ
	 */
	public static BufferedImage zoomImage(BufferedImage src, int width ,int height) {
		// ����ͼ��
		Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = newImg.getGraphics();
		g.drawImage(image, 0, 0, null); // ������С���ͼ
		return newImg;
	}
	/**
	 * ����ͼ�񻭲���С
	 * 
	 * @param src
	 * @param w
	 * @param h
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage src, int w, int h) {
		BufferedImage newImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = newImg.getGraphics();
		g.drawImage(src, 0, 0, null); // ���Ƹ��Ĵ�С���ͼ
		return newImg;
	}

	/**
	 * ��䷽��
	 * 
	 * @param g
	 * @param r
	 * @param c
	 */
	public static void fillRect(Graphics2D g, Rectangle r, Color c) {
		g.setColor(c);
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	/**
	 * ��䷽��
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param c
	 */
	public static void fillRect(Graphics2D g, int x, int y, int width, int height, Color c) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
	}
	
	public static void drawRect(Graphics2D g, Rectangle r, Color c) {
		g.setColor(c);
		g.drawRect(r.x, r.y, r.width, r.height);
	}
	
	public static void drawRect(Graphics2D g, int x, int y, int width, int height, Color c) {
		g.setColor(c);
		g.drawRect(x, y, width, height);
	}

	/**
	 * ��ȡͼƬ��Maskͼ
	 * 
	 * @param src
	 * @param maskColor
	 * @return
	 */
	public static BufferedImage getMaskImage(BufferedImage src) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();

		int collorNum = 128;
		int aa = 2;
		int[] palette = new int[collorNum];
		for(int i = 0;i<collorNum;i++)
		{
//			palette[i] = (0xff000000 | (i*aa));
			palette[i] = ((i*aa)<<24);
		}
        IndexColorModel cm = new IndexColorModel(8, palette.length, palette, 0, true, 0, DataBuffer.TYPE_BYTE);

//		BufferedImage maskImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		BufferedImage maskImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED, cm);
		int alphaCounts = 0;
		// boolean hasTransparency = false;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int argb = src.getRGB(x, y);
				int alpha = ((argb >> 24) & 0xff);
				// int rgb = argb & 0x00ffffff;
				// if (rgb == 0x0 && !hasTransparency) {
				// hasTransparency = true;
				// System.out.println("x="+x+","+"y="+y);
				// }
				if (alpha >= 0 && alpha < 0xff) {
					alphaCounts++;
				}

//				int c = ((argb) & 0xff000000);
				
//				System.out.println(alpha);
//				int c = (0xff000000 | alpha);
				int c = (alpha<<24);
				maskImg.setRGB(x, y, c);

			}
		}

		// �������Alpha
		if (alphaCounts == 0) {
			return null;
		}
		return maskImg;
	}

	public static void saveMaskImage(BufferedImage mask, DataOutputStream out) throws Exception {
		saveBufferedImage2Png8(mask, out);
	}

	public static byte[] getMaskImageData(BufferedImage img) {
		BufferedImage mask = getMaskImage(img);

		if (mask == null) {
			return null;
		}

//		byte[] pngbytes;
//		PngEncoderB png =  new PngEncoderB( mask,
//			PngEncoder.NO_ALPHA, 0, 9);
//
//		pngbytes = png.pngEncode();
//
//		return pngbytes;
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bout);
//			saveMaskImage(mask, out);
			ImageIO.write(mask, "png", out);
			out.flush();

			out.close();
			return bout.toByteArray();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return null;
	}

	public static byte[] getJPGImageData(BufferedImage img, float quality) {

		if (img == null) {
			return null;
		}

		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bout);
			GUtil.saveBufferedImage2Jpg(img, quality, out);
			out.close();

			byte[] data = bout.toByteArray();
			bout.close();
			return data;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static byte[] getAlphaData(BufferedImage src) {
		int w = src.getWidth();
		int h = src.getHeight();
		byte[] data = new byte[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int argb = src.getRGB(x, y);
				byte alpha = (byte) ((argb >> 24) & 0xff);
				data[y * w + x] = alpha;
			}
		}
		return data;
	}

	/**
	 * �����JPGͼƬ
	 * 
	 * @param src
	 * @param quality
	 * @param out
	 * @throws Exception
	 */
	public static void saveBufferedImage2Jpg(BufferedImage src, float quality, OutputStream out) throws Exception {

		int w = src.getWidth();
		int h = src.getHeight();
		int[] rgbArray = new int[w * h];
		src.getRGB(0, 0, w, h, rgbArray, 0, w);
		//ȥ��alpha
		int pos;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				pos = y*w+x;
				int argb = rgbArray[pos];
				byte alpha = (byte) ((argb >> 24) & 0xff);
				if(alpha == 0)
					argb = 0xff000000;
				rgbArray[pos] = argb | 0xff000000;
			}
		}

		// ������Ҫ��ͼƬת����TYPE_INT_RGB��ʽ
		BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		temp.setRGB(0, 0, w, h, rgbArray, 0, w);
//		temp.getGraphics().drawImage(src, 0, 0, null);

//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//		JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(temp);
//		jep.setQuality(quality, true);
//		encoder.setJPEGEncodeParam(jep);
//		encoder.encode(temp);
//
//		out.flush();
		
	    ImageWriter writer = null;
	    Iterator iter      = ImageIO.getImageWritersByFormatName("jpg");
	    if (iter.hasNext()) {
	      writer = (ImageWriter) iter.next();
	    }
	    ImageOutputStream ios = ImageIO.createImageOutputStream(out);
	    writer.setOutput(ios);
	    ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	    iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    iwparam.setCompressionQuality(quality);
	    writer.write(null, new IIOImage(temp, null, null), iwparam);
	    ios.flush();
	    writer.dispose();
	    ios.close();

	}

	/**
	 * ��ȡͼƬ��JPG����
	 * @param image
	 * @return
	 */
	public static final byte[] getJpgImageData(BufferedImage image) {
		return GUtil.getJPGImageData(image, EditorConfig.JPG_QUALITY);
	}

	/**
	 * ��ȡͼƬ��GIF����
	 * @param image
	 * @return
	 */
	public static final byte[] getGifImageData(BufferedImage image) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bout);
			GUtil.saveBufferedImage2Gif(image, out);
			out.close();
			byte[] data = bout.toByteArray();
			bout.close();
			return data;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return new byte[0];
	}

	public static void saveBufferedImage2Gif(BufferedImage src, OutputStream out) throws Exception {
		ImageIO.write(src, "gif", out);
		out.flush();
	}

	
	public static final byte[] getPng8ImageData(BufferedImage image) {
		
//		int w = image.getWidth();
//		int h = image.getHeight();
//		
//		BufferedImage target = new BufferedImage(w,h, BufferedImage.TYPE_BYTE_INDEXED);
//		Graphics g = target.getGraphics();
//		g.drawImage(image, 0,0, w, h, null);
//		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		try {
//			saveBufferedImage2Png8(target,out);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		ShowImageDialog.showDialog(null, target);
//		return out.toByteArray();
		
		return Util.getIndexImageData(image);
	}

	/**
	 * �����PNGͼƬ
	 * 
	 * @param src
	 * @param out
	 * @throws Exception
	 */
	public static void saveBufferedImage2Png8(BufferedImage src, OutputStream out) throws Exception {
		// BufferedImage img = Util.getIndexImage(src,256);
		BufferedImage img = Util.getIndexImage(src);
		ImageIO.write(img, "png", out);
		out.flush();
	}

	public static void saveBufferedImage2Png24(BufferedImage src, OutputStream out) throws Exception {
		ImageIO.write(src, "png", out);
		out.flush();
	}

	
	public static byte[] getBufferedImageAlpha(BufferedImage src) {
		int w = src.getWidth();
		int h = src.getHeight();
		byte[] bytes = new byte[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int c = src.getRGB(x, y);
				byte alpha = (byte) ((c >> 24) & 0xff);
				bytes[y * w + x] = alpha;
			}
		}
		return bytes;
	}

	public static void saveBufferedImage2Bytes(BufferedImage src, OutputStream out) throws Exception {
		byte[] bytes = getBufferedImageAlpha(src);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gzout = new GZIPOutputStream(bout);
		gzout.write(bytes);
		// InflaterOutputStream zos = new InflaterOutputStream(bout);
		// zos.write(bytes);
		out.write(bout.toByteArray());
	}

	/**
	 * ͼ��ƥ���ж�
	 */
	private static final boolean equalsBufferedImage(BufferedImage img_1, BufferedImage img_2) {
		if (img_1 == img_2) // ��ΪnullʱҲ����
			return true;

		if (img_1 == null || img_2 == null)
			return false;

		{// TODO �Զ����㷨�����Ż�
			int[] rgb_1 = getRGB(img_1);
			int[] rgb_2 = getRGB(img_2);

			for (int i = 0; i < rgb_1.length; i++) {
				if (rgb_1[i] != rgb_2[i])
					return false;
			}
		}
		// System.out.println("------ͼ��ƥ��");
		return true;
	}

	private static final int[] getRGB(BufferedImage img) {
		if (img == null) {
			return null;
		}

		final int w = img.getWidth();
		final int h = img.getHeight();
		int[] rgb = new int[w * h];

		return img.getRGB(0, 0, w, h, rgb, 0, w);
	}
	
	public static final String getImageFormatType(byte[] data) {
		if (data == null || data.length <= 0) {
			return "null";
		}
		
		if(data[0] == 0xff && data[1] == 0xD8){
			return "jpg";
		}
		
		if(data[0] == 0x89 && data[1] == 0x50){
			return "png";
		}
		
		return "unknow";
	}
}
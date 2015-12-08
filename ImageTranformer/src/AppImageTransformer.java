import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AppImageTransformer {

	/**
	 * 
	 * @param argv : list of commands :
	 * a : anonymization of the image
	 * r : reverse image
	 * mH : mirror horizontal
	 * mV : mirror vertical
	 * dV : divide the image by two in the middle of width
	 */
	public static void main(String[] argv) {
		File outputRepertory = new File("output");
		for(File f : outputRepertory.listFiles()) {
			f.delete();
		}

		File inputRepertory = new File("input");
		int cpt=0;

		for(File f : inputRepertory.listFiles()) {
			System.out.println("=== "+f.getName()+" ("+cpt+"e image) ===");
			tranform(f, argv,cpt);
			cpt++;
		}
	}

	/**
	 * 
	 * @param f : file to tranforrm
	 * @param argv : list of commands
	 * @param cpt : id of the original image
	 */
	private static void tranform(File f, String[] argv, int cpt) {
		try {
			for(String command : argv) {
				System.out.println("command : " + command);
				if(command.equals("a"))
					save(""+cpt,f.getName(),
							ImageIO.read(f)
							);
				else if(command.equals("r"))
					save(f.getName().split("\\.")[0]+"%reverse",f.getName(),
							reverse(ImageIO.read(f))
							);
				else if(command.equals("mH"))
					save(f.getName().split("\\.")[0]+"%mirrorH",f.getName(),
							mirrorH(ImageIO.read(f))
							);
				else if(command.equals("mV"))
					save(f.getName().split("\\.")[0]+"%mirrorV",f.getName(),
							mirrorV(ImageIO.read(f))
							);
				else if(command.equals("same"))
					save(f.getName().split("\\.")[0],f.getName(),
							ImageIO.read(f)
							);
				else if(command.equals("l")) {
					save(f.getName().split("\\.")[0]+"%lower",f.getName(),
							lower(ImageIO.read(f))
							);
				}
				else if(command.equals("lDiff")) {
					save(f.getName().split("\\.")[0]+"%lowerDiff",f.getName(),
							lowerDiff(ImageIO.read(f))
							);
				}

				else if(command.equals("All")) {
					save(f.getName().split("\\.")[0]+"%reverse",f.getName(),
							reverse(ImageIO.read(f))
							);
					save(f.getName().split("\\.")[0]+"%reverse%mirrorV",f.getName(),
							reverse(mirrorV(ImageIO.read(f)))
							);
					save(f.getName().split("\\.")[0]+"%reverse%mirrorH",f.getName(),
							reverse(mirrorH(ImageIO.read(f)))
							);

					save(f.getName().split("\\.")[0]+"%mirrorV",f.getName(),
							mirrorV(ImageIO.read(f))
							);
					save(f.getName().split("\\.")[0]+"%mirrorH",f.getName(),
							mirrorH(ImageIO.read(f))
							);
					save(f.getName().split("\\.")[0]+"%mirrorHV",f.getName(),
							mirrorH(mirrorV(ImageIO.read(f)))
							);

					save(f.getName().split("\\.")[0]+"%red",f.getName(),
							red(ImageIO.read(f))
							);
					save(f.getName().split("\\.")[0]+"%green",f.getName(),
							green(ImageIO.read(f))
							);
					save(f.getName().split("\\.")[0]+"%blue",f.getName(),
							blue(ImageIO.read(f))
							);
					save(f.getName().split("\\.")[0]+"%gray",f.getName(),
							gray(ImageIO.read(f))
							);
					save(f.getName().split("\\.")[0]+"%lower",f.getName(),
							lower(ImageIO.read(f))
							);
					save(f.getName().split("\\.")[0]+"%lower",f.getName(),
							lower(ImageIO.read(f))
							);

				}

				else if(command.equals("dV")) {
					BufferedImage[] tab = divideV(ImageIO.read(f));
					save(f.getName().split("\\.")[0]+"%divideV_before",f.getName(), tab[0]);
					save(f.getName().split("\\.")[0]+"%divideV_after",f.getName(), tab[1]);
				}
			}
		} catch (Exception e) {
			System.out.println("Error : "+e.getMessage());
			//f.delete();
		}
	}

	private static BufferedImage red(BufferedImage img) {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				Color c2= new Color(c1.getRed(),0,0);
				img.setRGB(i, j, c2.getRGB());
			}
		}
		return img;
	}

	private static BufferedImage blue(BufferedImage img) {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				Color c2= new Color(0,0,c1.getBlue());
				img.setRGB(i, j, c2.getRGB());
			}
		}
		return img;
	}

	private static BufferedImage green(BufferedImage img) {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				Color c2= new Color(0,c1.getGreen(),0);
				img.setRGB(i, j, c2.getRGB());
			}
		}
		return img;
	}

	private static BufferedImage gray(BufferedImage img) {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				int gray = (c1.getBlue()+c1.getGreen()+c1.getRed())/3;
				Color c2= new Color(gray,gray,gray);
				img.setRGB(i, j, c2.getRGB());
			}
		}
		return img;
	}

	private static BufferedImage lower(BufferedImage img) {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				int lower = Math.min(c1.getBlue(),Math.min(c1.getGreen(),c1.getRed()));
				Color c2= new Color(
						(lower==c1.getRed()?c1.getRed():0),
						(lower==c1.getGreen()?c1.getGreen():0),
						(lower==c1.getBlue()?c1.getBlue():0)
						);
				img.setRGB(i, j, c2.getRGB());
			}
		}
		return img;	
	}

	private static BufferedImage lowerDiff(BufferedImage img) {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				int lowerDiff = Math.min(Math.abs(c1.getBlue()-c1.getGreen()),Math.min(Math.abs(c1.getBlue()-c1.getRed()),Math.abs(c1.getRed()-c1.getGreen())));
				Color c2= new Color(lowerDiff,lowerDiff,lowerDiff);
				img.setRGB(i, j, c2.getRGB());
			}
		}
		return img;	
	}

	private static void save(String keyword, String fileName, BufferedImage img) throws IOException {
		ImageIO.write(img, "png",new File("output/"+keyword+"."+(fileName.split("\\.")[1])));		
	}

	private static BufferedImage[] divideV(BufferedImage img) throws IOException {
		BufferedImage[] tab = new BufferedImage[2];
		tab[0] = new BufferedImage(img.getWidth()/2, img.getHeight(), img.getType());
		tab[1] = new BufferedImage(img.getWidth()-(img.getWidth()/2), img.getHeight(), img.getType());

		for (int i = 0; i < img.getWidth()/2; i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				tab[0].setRGB(i, j, c1.getRGB());
			}
		}
		for (int i =  img.getWidth()/2; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				tab[1].setRGB(i-img.getWidth()/2, j, c1.getRGB());
			}
		}
		return tab;
	}

	private static BufferedImage mirrorV(BufferedImage img) throws IOException {	
		for (int i = 0; i < img.getWidth()/2; i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1= new Color(img.getRGB(i, j));
				Color c2= new Color(img.getRGB(img.getWidth()-i-1, j));
				img.setRGB(i, j, c2.getRGB());
				img.setRGB(img.getWidth()-i-1, j, c1.getRGB());
			}
		}
		return img;
	}

	private static BufferedImage mirrorH(BufferedImage img) throws IOException {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight()/2; j++) {
				Color c1= new Color(img.getRGB(i, j));
				Color c2= new Color(img.getRGB(i, img.getHeight()-j-1));
				img.setRGB(i, j, c2.getRGB());
				img.setRGB(i, img.getHeight()-j-1, c1.getRGB());
			}
		}
		return img;
	}

	private static BufferedImage reverse(BufferedImage img) throws IOException {
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {

				Color pixelcolor= new Color(img.getRGB(i, j));

				int r=pixelcolor.getRed();
				int g=pixelcolor.getGreen();
				int b=pixelcolor.getBlue();

				r=Math.abs(r-255);
				g=Math.abs(g-255);
				b=Math.abs(b-255);

				int rgb=new Color(r,g,b).getRGB();

				img.setRGB(i, j, rgb);
			}
		}
		return img;
	}

	private static BufferedImage deleteBorder(BufferedImage img) throws IOException {
		System.out.println("W="+img.getWidth()+" / H="+img.getHeight());
		int xMin=0, xMax=img.getWidth()-1, yMin=0, yMax=img.getHeight()-1;

		while(xMin<xMax && verticalBorder(img,xMin))
			xMin++;
		System.out.println("===");
		while(xMin<xMax && verticalBorder(img,xMax))
			xMax--;
		System.out.println("xMin="+xMin+" / xMax="+xMax);

		while(yMin<yMax && horizontalBorder(img,yMin))
			yMin++;
		System.out.println("===");
		while(yMin<yMax && horizontalBorder(img,yMax))
			yMax--;
		System.out.println("yMin="+yMin+" / yMax="+yMax);

		BufferedImage img2 = new BufferedImage(xMax-xMin+1,yMax-yMin+1,img.getType());

		for (int i=0; i<img2.getWidth(); i++) {
			for (int j=0; j<img2.getHeight(); j++) {
				img2.setRGB(i,j, img.getRGB(i+xMin, j+yMin));
			}
		}
		return img2;
	}

	private static boolean horizontalBorder(BufferedImage img, int y) {
		int color = img.getRGB(0, y);
		int x=1;
		while(x<img.getWidth() && color==img.getRGB(x, y))
			x++;
		if(x<img.getWidth()) {
			Color c1 = new Color(img.getRGB(0, y));
			Color c2 = new Color(img.getRGB(x, y));
			System.out.println("\t\t "+c1);			
			System.out.println("\t\t "+c2);			
		}
		System.out.println("\t "+(x==img.getWidth()?"Yes":"No"));
		return x==img.getWidth();
	}

	private static boolean verticalBorder(BufferedImage img, int x) {
		int color = img.getRGB(x, 0);
		int y=1;
		while(y<img.getHeight() && color==img.getRGB(x, y))
			y++;
		System.out.println("\t "+(y==img.getHeight()?"Yes":"No"));
		return y==img.getHeight();
	}

}

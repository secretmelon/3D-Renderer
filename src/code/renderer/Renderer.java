package code.renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Renderer extends GUI {

	public ArrayList<Scene.Polygon> polygons = new ArrayList<>();
	public Vector3D lightSource;

	@Override
	protected void onLoad(File file) {
		/*
		 * This method should parse the given file into a Scene object, which
		 * you store and use to render an image.
		 */

		try {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			String[] currentLine;
			String lineJustFetched;

			//DECLARING VARIABLES FOR STORING INFO
			int R, G, B;                                //Colour
			float x1, y1, z1, x2, y2, z2, x3, y3, z3;    //Points
			float a, b, c;                                //LightSource

			//SKIP FIRST LINE
			buf.readLine();

			//LOAD INFO
			for (lineJustFetched = buf.readLine(); lineJustFetched != null; lineJustFetched = buf.readLine()) {

				currentLine = lineJustFetched.split(",");

				if (currentLine.length > 4) {
					List<String> colours = new ArrayList<>();
					List<String> points = new ArrayList<>();

					//COLOR VALUES
					R = Integer.parseInt(currentLine[0]);
					G = Integer.parseInt(currentLine[1]);
					B = Integer.parseInt(currentLine[2]);
					Color c = new Color(R, G, B);


					//FIRST CO-ORD SET
					x1 = Float.parseFloat(currentLine[3]);
					y1 = Float.parseFloat(currentLine[4]);
					z1 = Float.parseFloat(currentLine[5]);
					Vector3D v1 = new Vector3D(x1, y1, z1);

					//SECOND CO-ORD SET
					x2 = Float.parseFloat(currentLine[6]);
					y2 = Float.parseFloat(currentLine[7]);
					z2 = Float.parseFloat(currentLine[8]);
					Vector3D v2 = new Vector3D(x2, y2, z2);

					//THIRD CO-ORD SET
					x3 = Float.parseFloat(currentLine[9]);
					y3 = Float.parseFloat(currentLine[10]);
					z3 = Float.parseFloat(currentLine[11]);
					Vector3D v3 = new Vector3D(x3, y3, z3);

					//CREATE POLYGON & ADD TO LIST
					Scene.Polygon p = new Scene.Polygon(v1, v2, v3, c);
					polygons.add(p);
				} else if (currentLine.length == 3) {
					a = Float.parseFloat(currentLine[0]);
					b = Float.parseFloat(currentLine[1]);
					c = Float.parseFloat(currentLine[2]);
					lightSource = new Vector3D(a, b, c);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


		@Override
	protected void onKeyPress(KeyEvent ev) {
		// TODO fill this in.

		/*
		 * This method should be used to rotate the user's viewpoint.
		 */
	}

	@Override
	protected BufferedImage render() {
		// TODO fill this in.

		/*
		 * This method should put together the pieces of your renderer, as
		 * described in the lecture. This will involve calling each of the
		 * static method stubs in the Pipeline class, which you also need to
		 * fill in.
		 */
		return null;
	}

	/**
	 * Converts a 2D array of Colors to a BufferedImage. Assumes that bitmap is
	 * indexed by column then row and has imageHeight rows and imageWidth
	 * columns. Note that image.setRGB requires x (col) and y (row) are given in
	 * that order.
	 */
	private BufferedImage convertBitmapToImage(Color[][] bitmap) {
		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}

	public static void main(String[] args) {
		new Renderer();
	}
}

// code for comp261 assignments

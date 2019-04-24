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
	public ArrayList<Vector3D> vectors = new ArrayList<>();

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
			String firstLine;

			//DECLARING VARIABLES TO STORE INFO
			int R, G ,B;
			float x,y,z;
			int numLines;

			//SKIP FIRST LINE
			firstLine = buf.readLine();
			numLines = Integer.parseInt(firstLine);

			//LOAD INFO
			for (lineJustFetched = buf.readLine(); lineJustFetched != null; lineJustFetched = buf.readLine()) {

				currentLine = lineJustFetched.split(",");

				List<String> colours = new ArrayList<>();
				List<String> points = new ArrayList<>();

				//COLOUR VALUES
				R = Integer.parseInt(currentLine[0]);
				G = Integer.parseInt(currentLine[1]);
				B = Integer.parseInt(currentLine[2]);
				Color c = new Color(R,G,B);


				for (int i = 3; i != currentLine.length; i++) {
					points.add(currentLine[i]);
				}

				for(int i = 0; i < points.size(); i += 3 ){
					x = Float.parseFloat(currentLine[i]);
					y = Float.parseFloat(currentLine[i+1]);
					z = Float.parseFloat(currentLine[i+2]);
					Vector3D v = new Vector3D(x,y,z);
					vectors.add(v);
				}
			}

				//CREATE POLYGON & ADD TO LIST


				Scene.Polygon p = new Scene.Polygon(v1,v2,v3,c);
				polygons.add(p);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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

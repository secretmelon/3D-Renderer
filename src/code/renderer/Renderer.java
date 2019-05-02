package code.renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Renderer extends GUI {

	private Vector3D lightDir;
	private Scene scene;
	private Color ILColour = new Color(239, 215, 160);
	List<Scene.Polygon> polygons = new ArrayList<>();

	@Override
	protected void onLoad(File file){

		try {
			lightDir = null;
			polygons.clear();
			BufferedReader buf = new BufferedReader(new FileReader(file));
			String[] currentLine;
			String lineJustFetched;

			int totalPolygons = 0;
			boolean firstLine = true;

			//LOAD INFO
			for (lineJustFetched = buf.readLine(); lineJustFetched != null; lineJustFetched = buf.readLine()) {
				currentLine = lineJustFetched.split(",");

				//GET NUMBER OF POLYGONS IN FILE
				if (firstLine){
					totalPolygons = Integer.parseInt(currentLine[0]);
					firstLine = false;
				}

				//GET LIGHT DIRECTION AT END OF FILE
				else if (polygons.size() == totalPolygons) { // last //if (currentLine.length == 3) {
					float a = Float.parseFloat(currentLine[0]);
					float b = Float.parseFloat(currentLine[1]);
					float c = Float.parseFloat(currentLine[2]);
					lightDir = new Vector3D(a, b, c);
				}

				else {
					//COLOR VALUES
					int R = Integer.parseInt(currentLine[0]);
					int G = Integer.parseInt(currentLine[1]);
					int B = Integer.parseInt(currentLine[2]);
					Color col = new Color(R, G, B);

					//FIRST CO-ORD SET
					float x1 = Float.parseFloat(currentLine[3]);
					float y1 = Float.parseFloat(currentLine[4]);
					float z1 = Float.parseFloat(currentLine[5]);
					Vector3D v1 = new Vector3D(x1, y1, z1);

					//SECOND CO-ORD SET
					float x2 = Float.parseFloat(currentLine[6]);
					float y2 = Float.parseFloat(currentLine[7]);
					float z2 = Float.parseFloat(currentLine[8]);
					Vector3D v2 = new Vector3D(x2, y2, z2);

					//THIRD CO-ORD SET
					float x3 = Float.parseFloat(currentLine[9]);
					float y3 = Float.parseFloat(currentLine[10]);
					float z3 = Float.parseFloat(currentLine[11]);
					Vector3D v3 = new Vector3D(x3, y3, z3);

					//CREATE POLYGON & ADD TO LIST
					Scene.Polygon p = new Scene.Polygon(v1, v2, v3, col);
					polygons.add(p);
				}

			}
			//CREATE SCENE
			scene = new Scene(polygons, lightDir);
			buf.close();
		}

		catch (IOException e) {e.printStackTrace();}

		//BUGS AND SHIT
		System.out.println(scene.getPolygons().size());

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
		if(scene != null) {

			//scene = Pipeline.rotateScene(scene);
			//scene = Pipeline.scaleScene(scene);
			//scene = Pipeline.translateScene(scene);

			Color[][] renderedImg = new Color[CANVAS_WIDTH][CANVAS_HEIGHT];
			float[][] zDepth = new float[CANVAS_WIDTH][CANVAS_HEIGHT];


			for (int x = 0; x < CANVAS_WIDTH; x++) {
				for (int y = 0; y < CANVAS_HEIGHT; y++) {
					renderedImg[x][y] = Color.WHITE;
					zDepth[x][y] = Float.MAX_VALUE;
				}
			}

			EdgeList polygonEdges;

			for (Scene.Polygon p : scene.getPolygons()) {
				if (!Pipeline.isHidden(p)) {
					int[] amb = getAmbientLight();
					Color ambientLight = new Color(amb[0], amb[1], amb[2]);
					Color polygonShade = Pipeline.getShading(p, scene.getLight(), ILColour, ambientLight);
					polygonEdges = Pipeline.computeEdgeList(p);
					Pipeline.computeZBuffer(renderedImg, zDepth, polygonEdges, polygonShade);
				}
			}



			/*
			 * This method should put together the pieces of your renderer, as
			 * described in the lecture. This will involve calling each of the
			 * static method stubs in the Pipeline class, which you also need to
			 * fill in.
			 */
			return convertBitmapToImage(renderedImg);
		}
		System.out.println("nully");
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

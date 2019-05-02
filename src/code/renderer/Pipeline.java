package code.renderer;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * The Pipeline class has method stubs for all the major components of the
 * rendering pipeline, for you to fill in.
 *
 * Some of these methods can get quite long, in which case you should strongly
 * consider moving them out into their own file. You'll need to update the
 * imports in the test suite if you do.
 */
public class Pipeline {

	/**
	 * Returns true if the given polygon is facing away from the camera (and so
	 * should be hidden), and false otherwise.
	 */
	public static boolean isHidden(Scene.Polygon poly) {
		Vector3D v1 = poly.getVertices()[0];
		Vector3D v2 = poly.getVertices()[1];
		Vector3D v3 = poly.getVertices()[2];
		Vector3D normal = v2.minus(v1).crossProduct(v3.minus(v2)).unitVector();

		float zValue = normal.z;
		return zValue > 0;
	}

	/**
	 * Computes the colour of a polygon on the screen, once the lights, their
	 * angles relative to the polygon's face, and the reflectance of the polygon
	 * have been accounted for.
	 *
	 * @param lightDirection
	 *            The Vector3D pointing to the directional light read in from
	 *            the file.
	 * @param lightColor
	 *            The color of that directional light.
	 * @param ambientLight
	 *            The ambient light in the scene, i.e. light that doesn't depend
	 *            on the direction.
	 */
	public static Color getShading(Scene.Polygon poly, Vector3D lightDirection, Color lightColor, Color ambientLight) {

		//FIND NORMAL
		Vector3D v1 = poly.getVertices()[0];
		Vector3D v2 = poly.getVertices()[1];
		Vector3D v3 = poly.getVertices()[2];
		Vector3D 	normal 		= v2.minus(v1).crossProduct(v3.minus(v2)).unitVector();

		Color 		reflectance = poly.getReflectance();
		float 		cosTheta 	= normal.cosTheta(lightDirection);

		//CREATE ARRAYS TO MANIPULATE SPECIFIC COLOUR VALUES
		int[] ALColour 	= {ambientLight.getRed(), ambientLight.getGreen(),ambientLight.getBlue()};
		int[] ILColour	= {lightColor.getRed(),lightColor.getGreen(), lightColor.getBlue()};
		int[] RColour	= {reflectance.getRed(),reflectance.getGreen(),reflectance.getBlue()};

		//INITIALISE SHADE COLOUR ARRAY
		int[] shading = new int[3];

		for(int i = 0; i < 3; i++){

			//FIND INTENSITIES
			float ambientLI  = ALColour[i] / 255.0f;
			float incidentLI = ILColour[i] / 255.0f;

			//SHADING = (AMB.LIGHT INTENSITY x REFLECTANCE) + (INCI.LIGHT INTENSITY x REFLECTANCE x ANGLE
			shading[i] = (int) ((ambientLI * RColour[i]) + (incidentLI * RColour[i]) * cosTheta);

			//NO NEGATIVE VALUES
			if (shading[i] < 0){
				shading[i] += 255;
			}
		}
		//TROUBLE-SHOOTING
		System.out.println("Color: " + shading[0] +" "+  shading[1] +" " + shading[2]);

		return new Color(shading[0], shading[1], shading[2]);
	}

	/**
	 * This method should rotate the polygons and light such that the viewer is
	 * looking down the Z-axis. The idea is that it returns an entirely new
	 * Scene object, filled with new Polygons, that have been rotated.
	 *
	 * @param scene
	 *            The original Scene.
	 * @param xRot
	 *            An angle describing the viewer's rotation in the YZ-plane (i.e
	 *            around the X-axis).
	 * @param yRot
	 *            An angle describing the viewer's rotation in the XZ-plane (i.e
	 *            around the Y-axis).
	 * @return A new Scene where all the polygons and the light source have been
	 *         rotated accordingly.
	 */
	public static Scene rotateScene(Scene scene, float xRot, float yRot) {
		//DIDN'T REALISE I WASN'T SUPPOSED TO IMPLEMENT THIS YET... WAS TOO FOCUSED ON PASSING THE TESTS FIRST
		//DUMBASS

		List<Scene.Polygon> preRotation  = scene.getPolygons();
		List<Scene.Polygon> postRotation = new ArrayList<>(preRotation);

		//CREATE TRANSFORM OBJECTS FOR EACH AXIS
		Transform xAxis = Transform.newXRotation(xRot);
		Transform yAxis = Transform.newYRotation(yRot);

		//ROTATE EACH POLYGON IN SCENE
		for(Scene.Polygon p : postRotation){
			Vector3D[] vList = p.getVertices();

			//ROTATE EVERY VERTEX IF THERE IS A ROTATION VALUE
			for(int v = 0; v < vList.length; v++){
				if(xRot != 0.0f){vList[v] = xAxis.multiply(vList[v]);}
				if(yRot != 0.0f){vList[v] = yAxis.multiply(vList[v]);}
			}
		}

		//ROTATE THE DIRECTION OF LIGHT
		Vector3D light  = scene.getLight();
		if(xRot != 0.0f){light = xAxis.multiply(light);}
		if(yRot != 0.0f){light = yAxis.multiply(light);}

		//RETURN ROTATED SCENE
		return new Scene(postRotation, light);
	}

	/**
	 * This should translate the scene by the appropriate amount.
	 *
	 * @param scene
	 * @return
	 */
	public static Scene translateScene(Scene scene) {
		// TODO fill this in.
		return null;
	}

	/**
	 * This should scale the scene.
	 *
	 * @param scene
	 * @return
	 */
	public static Scene scaleScene(Scene scene) {
		// TODO fill this in.
		return null;
	}

	/**
	 * Computes the edgelist of a single provided polygon, as per the lecture
	 * slides.
	 */
	public static EdgeList computeEdgeList(Scene.Polygon poly){
		Vector3D[] vertices = poly.getVertices();
		int ymin = (int) (Double.POSITIVE_INFINITY);
		int ymax = (int) (Double.NEGATIVE_INFINITY);

		//GO THROUGH EVERY VERTEX AND SET MIN AND MAX Y VALUES
		for(Vector3D vertex : vertices){
			if(vertex.y < ymin){
				ymin = Math.round(vertex.y);
			}
			else if(vertex.y > ymax){
				ymax = Math.round(vertex.y);
			}


		}

		EdgeList edges = new EdgeList(ymin,ymax);
		Vector3D a, b;

		for(int v = 0; v < 3; v++){
			//ANTI-CLOCKWISE ORDER
			if(v != 2){
				a = vertices[v];
				b = vertices[v+1];
			}
			//IF AT V3, THEN B GOES BACK TO V1
			else{
				a = vertices[v];
				b = vertices[0];
			}

			//SLOPES
			float mX = (b.x - a.x) / (b.y - a.y);
			float mZ = (b.z - a.z) / (b.y - a.y);

			// X Y Z VALUES
			float x = a.x;
			int y = Math.round(a.y);
			float z = a.z;

			//GOING DOWN
			if(a.y < b.y){
				while(y <= Math.round(b.y)){
					edges.setLeftX(y,x);
					edges.setLeftZ(y,z);
					x = x + mX;
					z = z + mZ;
					y++;

				}

			}

			//GOING UP
			else if (a.y > b.y){
				while(y >= Math.round(b.y)){
					edges.setRightX(y,x);
					edges.setRightZ(y,z);
					x = x - mX;
					z = z - mZ;
					y--;
				}
			}

		}
		return edges;
	}

	/**
	 * Fills a zbuffer with the contents of a single edge list according to the
	 * lecture slides.
	 *
	 * The idea here is to make zbuffer and zdepth arrays in your main loop, and
	 * pass them into the method to be modified.
	 *
	 * @param zbuffer
	 *            A double array of colours representing the Color at each pixel
	 *            so far.
	 * @param zdepth
	 *            A double array of floats storing the z-value of each pixel
	 *            that has been coloured in so far.
	 * @param edges
	 *            The edgelist of the polygon to add into the zbuffer.
	 * @param polyColor
	 *            The colour of the polygon to add into the zbuffer.
	 */
	public static void computeZBuffer(Color[][] zbuffer, float[][] zdepth, EdgeList edges, Color polyColor) {
		//DONT KNOW WTF IS GOING ON HERE, LITERALLY COPIED THE LECTURE SLIDES

		for(int y = edges.getStartY(); y < edges.getEndY(); y++) {
			float slope = (edges.getRightZ(y) - edges.getLeftZ(y)) / (edges.getRightX(y) - edges.getLeftX(y));
			int x = Math.round(edges.getLeftX(y));
			float z = edges.getLeftZ(y) + slope * (x - edges.getLeftX(y));

			while (x < Math.round(edges.getRightX(y))) {
				//MAKING SURE WE DON'T GO OUT OF THE CANVAS SIZE
				if ((y >= 0 && y < GUI.CANVAS_HEIGHT) && (x >= 0 && x < GUI.CANVAS_WIDTH) && (z < zdepth[x][y])){
					zbuffer[x][y] = polyColor;
					zdepth[x][y] = z;
				}
				z = z + slope;
				x++;
			}
		}
	}
}

// code for comp261 assignments

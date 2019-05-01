package code.renderer;

import java.awt.Color;


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
		float zValue = computeUnitNormal(poly).z;
		return zValue < 0;
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

		Vector3D 	normal 		= computeUnitNormal(poly);
		Color 		reflectance = poly.getReflectance();

		double cosTheta	= (Math.acos(normal.cosTheta(lightDirection)));
		//double cosTheta = (normal.cosTheta(lightDirection));


		int[] ambientLightARR 	= {ambientLight.getRed(), ambientLight.getGreen(),ambientLight.getBlue()};
		int[] indicentLightARR	= {lightColor.getRed(),lightColor.getGreen(), lightColor.getBlue()};
		int[] reflectanceARR	= {reflectance.getRed(),reflectance.getGreen(),reflectance.getBlue()};
		int[] shadingARR 		= new int[3];

		for(int i = 0; i < 3; i++){
			int ambientLI  = ambientLightARR[i] / 255; //   /reflectanceARR[[i]
			int indicentLI = (int) (indicentLightARR[i] / (reflectanceARR[i] * cosTheta));

			int diffuseColor = (int) (indicentLightARR[i] * indicentLI * ambientLightARR[i] * cosTheta);
			shadingARR[i] = (int) (ambientLightARR[i] + diffuseColor);
		}

		System.out.println("Color: " + shadingARR[0] +" "+  shadingARR[1] +" " + shadingARR[2]);

		Color shading = new Color(shadingARR[0], shadingARR[1], shadingARR[2]);

		return shading;
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
		// TODO fill this in.
		return null;
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
	public static EdgeList computeEdgeList(Scene.Polygon poly) {
		// TODO fill this in.
		return null;
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
	 * @param polyEdgeList
	 *            The edgelist of the polygon to add into the zbuffer.
	 * @param polyColor
	 *            The colour of the polygon to add into the zbuffer.
	 */
	public static void computeZBuffer(Color[][] zbuffer, float[][] zdepth, EdgeList polyEdgeList, Color polyColor) {
		// TODO fill this in.
	}

	private static Vector3D computeUnitNormal(Scene.Polygon poly) {
		Vector3D v1 = poly.getVertices()[0];
		Vector3D v2 = poly.getVertices()[1];
		Vector3D v3 = poly.getVertices()[2];
		Vector3D normal = v2.minus(v1).crossProduct(v3.minus(v2)).unitVector();
		return normal;
	}
}

// code for comp261 assignments

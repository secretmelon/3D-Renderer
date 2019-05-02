package code.renderer;

/**
 * EdgeList should store the data for the edge list of a single polygon in your
 * scene. A few method stubs have been provided so that it can be tested, but
 * you'll need to fill in all the details.
 *
 * You'll probably want to add some setters as well as getters or, for example,
 * an addRow(y, xLeft, xRight, zLeft, zRight) method.
 */
public class EdgeList {

	private int startY;
	private int endY;
	private float[][] table;

	public EdgeList(int startY, int endY) {
		this.startY = startY;
		this.endY = endY;
		this.table = new float[4][endY - startY + 1];
	}

	public int getStartY() {
		return startY;
	}
	public int getEndY() {
		return endY;
	}

	public float getLeftX(int y) {
		return table[0][y-startY];
	}

	public void setLeftX(int y, float x) {
		table[0][y - startY] = x;
	}

	public float getRightX(int y) {
		return table[2][y-startY];
	}

	public void setRightX(int y, float x) {
		table[2][y-startY] = x;
	}

	public float getLeftZ(int y) {
		return table[1][y-startY];
	}

	public void setLeftZ(int y, float z) {
		table[1][y-startY] = z;
	}

	public float getRightZ(int y) {
		return table[3][y-startY];
	}

	public void setRightZ(int y, float z){
		table[3][y-startY] = z;
	}

	public float[][] getTable() {
		return table;
	}
}

// code for comp261 assignments

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

	public EdgeList(int startY, int endY) {
		this.startY = startY;
		this.endY = endY;
	}

	public int getStartY() {
		return startY;

	}

	public int getEndY() {
		return endY;
	}

	public float getLeftX(int y) {
		// TODO fill this in.
		return 0;
	}

	public float getRightX(int y) {
		// TODO fill this in.
		return 0;
	}

	public float getLeftZ(int y) {
		// TODO fill this in.
		return 0;
	}

	public float getRightZ(int y) {
		// TODO fill this in.
		return 0;
	}
}

// code for comp261 assignments

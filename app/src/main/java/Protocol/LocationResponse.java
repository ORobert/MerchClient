package Protocol;

/**
 * Created by Sergiu on 03-Feb-17.
 */
public class LocationResponse implements Response {
	private Double[] coord;

	public LocationResponse(Double[] coord) {
		this.coord = coord;
	}

	public Double[] getCoord() {
		return coord;
	}

	public void setCoord(Double[] coord) {
		this.coord = coord;
	}
}

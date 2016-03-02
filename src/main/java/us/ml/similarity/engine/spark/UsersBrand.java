package us.ml.similarity.engine.spark;

import java.io.Serializable;

public class UsersBrand implements Serializable{

	private static final long serialVersionUID = 1264310929036391853L;
	
	private final long userId;
	private final long brandId;
	private final long dummyRating;

	public UsersBrand(long userId, long brandId) {
		super();
		this.userId = userId;
		this.brandId = brandId;
		this.dummyRating = 5;
	}

	public long getUserId() {
		return userId;
	}

	public long getBrandId() {
		return brandId;
	}

	public long getDummyRating() {
		return dummyRating;
	}

}

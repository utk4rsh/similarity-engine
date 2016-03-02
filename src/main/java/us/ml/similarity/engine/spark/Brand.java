package us.ml.similarity.engine.spark;

import java.io.Serializable;

public class Brand implements Serializable {

	private static final long serialVersionUID = 2171590226896420860L;

	private final Long id;
	private final String name;

	public Brand(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + "]";
	}

}

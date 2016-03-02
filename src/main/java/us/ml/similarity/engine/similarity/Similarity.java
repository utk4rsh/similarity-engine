package us.ml.similarity.engine.similarity;

public abstract class Similarity {

	/**
	 * Computes Similarity between two vectors
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public abstract double compute(int[] a, int[] b);

}

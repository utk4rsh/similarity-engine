package us.ml.similarity.engine.similarity;

public class EuclideanSimilarity extends Similarity {

	@Override
	public double compute(int[] a, int[] b) {
		if (a.length != b.length)
			throw new IllegalArgumentException("Cosine Distance on Vectors of different Dimension");
		long squaredSum = 0;
		for (int i = 0; i < a.length; i++) {
			squaredSum += (a[i] - b[i]) * (a[i] - b[i]);
		}
		return 1/(1+ Math.sqrt(squaredSum));
	}

}

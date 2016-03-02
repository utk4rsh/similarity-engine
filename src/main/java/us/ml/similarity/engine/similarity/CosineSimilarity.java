package us.ml.similarity.engine.similarity;

public class CosineSimilarity extends Similarity {

	/**
	 * Simple Cosine similarity between two vectors. The result is the cosine of
	 * the angle formed between the two preference vectors.
	 */
	@Override
	public double compute(int[] a, int[] b) {
		if (a.length != b.length)
			throw new IllegalArgumentException("Cosine Distance on Vectors of different Dimension");
		long dotSum = 0;
		long squaredSumA = 0;
		long squaredSumB = 0;
		for (int i = 0; i < a.length; i++) {
			dotSum += a[i] * b[i];
			squaredSumA += (a[i] * a[i]);
			squaredSumB += (b[i] * b[i]);
		}
		if (dotSum == 0)
			return 0;
		if (squaredSumA == 0 || squaredSumB == 0)
			return 1;
		double cosineDistance = dotSum / (Math.sqrt(squaredSumA) * Math.sqrt(squaredSumB));
		return cosineDistance;
	}

}

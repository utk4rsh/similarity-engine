package us.ml.similarity.engine.similarity;

public class PearsonCorrelationSimilarity extends Similarity {

	@Override
	public double compute(int[] a, int[] b) {
		if (a.length != b.length)
			throw new IllegalArgumentException("Cosine Distance on Vectors of different Dimension");
		long squaredSumA = 0;
		long squaredSumB = 0;
		long sumAB = 0;
		for (int i = 0; i < a.length; i++) {
			squaredSumA += (a[i] * a[i]);
			squaredSumB += (b[i] * b[i]);
			sumAB += (a[i] * b[i]);
		}
		double xTerm = Math.sqrt(squaredSumA);
		double yTerm = Math.sqrt(squaredSumB);
		double denominator = xTerm * yTerm;
		if (denominator == 0.0) {
			return 0;
		}
		return sumAB / denominator;
	}
}

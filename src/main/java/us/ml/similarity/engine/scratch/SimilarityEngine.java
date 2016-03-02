package us.ml.similarity.engine.scratch;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import us.ml.similarity.engine.similarity.Similarity;

import java.util.Set;

public class SimilarityEngine {

	private final Class<? extends Similarity> similarityClass;
	private final int limitingUserSize;
	private final int limitingBrandSize;
	private final int searchId;

	public SimilarityEngine(String similarityClassName, int limitingUserSize, int limitingBrandSize, int searchId) {
		super();
		this.similarityClass = getSimilarityClass(similarityClassName);
		this.limitingUserSize = limitingUserSize;
		this.limitingBrandSize = limitingBrandSize;
		this.searchId = searchId;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Similarity> getSimilarityClass(String similarityClassName) {
		String className = null;
		try {
			className = "us.ml.similarity.engine.similarity." + similarityClassName;
			return (Class<? extends Similarity>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Invalid Similarity class name " + className);
		}
	}

	public void getSimilarItems() {
		UserItemMatrix matrix = new UserItemMatrix(this.limitingUserSize, this.limitingBrandSize);
		matrix.contructUserItemMatrix();
		Set<Integer> items = matrix.getItems();
		int[][] itemUserMatrix = matrix.getItemUserMatrix();
		Map<Integer, Double> cosineDistanceMap = new HashMap<>();
		if (!items.contains(this.searchId)) {
			System.out.println("No item present with item Id " + this.searchId);
			System.out.println("Try Another item to get similar items");
			System.exit(1);
		}
		try {
			Constructor<?> ctor = this.similarityClass.getConstructor();
			Similarity similarity = (Similarity) ctor.newInstance();
			for (int i = 0; i < this.limitingBrandSize; i++) {
				if (items.contains(i) && this.searchId != i) {
					double cosineDistance = similarity.compute(itemUserMatrix[searchId], itemUserMatrix[i]);
					cosineDistanceMap.put(i, cosineDistance);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid Similarity class name");
		}
		printSimilarItems(matrix, cosineDistanceMap);
	}

	private void printSimilarItems(UserItemMatrix matrix, Map<Integer, Double> cosineDistanceMap) {
		Map<Integer, String> itemBrand = matrix.getItemBrand();
		Map<Integer, Double> sortedCosineMap = MapUtils.sortByValue(cosineDistanceMap);
		int results = 0;
		System.out.println("Top 10 results for : " + itemBrand.get(searchId));
		System.out.println();
		for (Entry<Integer, Double> entry : sortedCosineMap.entrySet()) {
			System.out.println(itemBrand.get(entry.getKey()) + " (" + entry.getValue() + ")");
			if (results == 15)
				break;
			results++;
		}
		System.out.println();
	}

}

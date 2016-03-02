package us.ml.similarity.engine.scratch;

public class ItemSimilarityApp {

	/**
	 * Main program to get Similar products for a given product
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String className = "CosineSimilarity";
		int limitUser = 100000;
		int limitBrand = 5000;
		int searchId = 1668;
		if (args.length != 4) {
			System.out.println("No Arguments provided going to use default parameters UserSize " + limitUser
					+ " BrandSize : " + limitBrand + " and random search id : " + searchId);
		} else if (args.length == 4) {
			className = args[0];
			limitUser = Integer.parseInt(args[1]);
			limitBrand = Integer.parseInt(args[2]);
			searchId = Integer.parseInt(args[3]);
		}
		SimilarityEngine engine = new SimilarityEngine(className, limitUser, limitBrand, searchId);
		engine.getSimilarItems();
	}
}

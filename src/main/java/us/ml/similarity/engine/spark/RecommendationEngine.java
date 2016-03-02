package us.ml.similarity.engine.spark;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.rdd.RDD;

import scala.Tuple2;

public class RecommendationEngine {

	private static final String DATA_PATH = "/home/utkarsh/work/personal/gitclones/recommendataion-engine/data/brands_filtered.txt";

	private static JavaRDD<Brand> loadBrandDictionary(JavaSparkContext jsc) {
		JavaRDD<String> data = jsc.textFile(DATA_PATH);
		JavaRDD<Brand> brandRDD = data.map(new Function<String, Brand>() {
			private static final long serialVersionUID = 1L;

			public Brand call(String s) {
				String[] sarray = s.split("\t");
				return new Brand(Long.parseLong(sarray[1]), sarray[2]);
			}
		});
		return brandRDD;
	}

	private static JavaRDD<Rating> loadUserBrands(JavaSparkContext jsc) {
		JavaRDD<String> data = jsc.textFile(DATA_PATH);
		JavaRDD<Rating> usersBrandRDD = data.map(new Function<String, Rating>() {
			private static final long serialVersionUID = 7420973465063983578L;

			public Rating call(String s) {
				String[] sarray = s.split("\t");
				return new Rating(Integer.parseInt(sarray[1]), Integer.parseInt(sarray[0]), 5);
			}
		});
		return usersBrandRDD;
	}

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Java Collaborative Filtering Example");
		conf.setMaster("local[3]");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		List<Brand> brands = loadBrandDictionary(jsc).take(5);
		brands.forEach(sample -> System.out.println(sample));
		RDD<Rating> userBrandsRDD = JavaRDD.toRDD(loadUserBrands(jsc));
		int rank = 10;
		int numIterations = 10;
		JavaRDD<Rating> ratings = loadUserBrands(jsc);
		MatrixFactorizationModel model = ALS.train(userBrandsRDD, rank, numIterations, 0.01);
		calculateRMSE(ratings, model);
		model.save(jsc.sc(), "target/tmp/myCollaborativeFilter");
		MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(jsc.sc(),
				"target/tmp/myCollaborativeFilter");
		double predictionsRes = sameModel.predict(1, 1);
		System.out.println(predictionsRes);
		Rating[] recommendations = sameModel.recommendProducts(1, 10);
		for (Rating recommendation : recommendations) {
			System.out.println(recommendation);
		}
	}

	private static void calculateRMSE(JavaRDD<Rating> ratings, MatrixFactorizationModel model) {
		JavaRDD<Tuple2<Object, Object>> userProducts = getUserProducts(ratings);
		JavaPairRDD<Tuple2<Integer, Integer>, Double> predictions = getPredictions(userProducts, model);
		JavaRDD<Tuple2<Double, Double>> ratesAndPreds = JavaPairRDD
				.fromJavaRDD(ratings.map(new Function<Rating, Tuple2<Tuple2<Integer, Integer>, Double>>() {
					private static final long serialVersionUID = 4271528827650792724L;

					public Tuple2<Tuple2<Integer, Integer>, Double> call(Rating r) {
						return new Tuple2<Tuple2<Integer, Integer>, Double>(
								new Tuple2<Integer, Integer>(r.user(), r.product()), r.rating());
					}
				})).join(predictions).values();

		double MSE = JavaDoubleRDD.fromRDD(ratesAndPreds.map(new Function<Tuple2<Double, Double>, Object>() {
			private static final long serialVersionUID = -3693552072904286883L;

			public Object call(Tuple2<Double, Double> pair) {
				Double err = pair._1() - pair._2();
				return err * err;
			}
		}).rdd()).mean();

		System.out.println("Mean Squared Error = " + MSE);
		// Save and load model
	}

	private static JavaRDD<Tuple2<Object, Object>> getUserProducts(JavaRDD<Rating> ratings) {
		JavaRDD<Tuple2<Object, Object>> userProducts = ratings.map(new Function<Rating, Tuple2<Object, Object>>() {
			private static final long serialVersionUID = -5227034414602561876L;

			public Tuple2<Object, Object> call(Rating r) {
				return new Tuple2<Object, Object>(r.user(), r.product());
			}
		});
		return userProducts;
	}

	private static JavaPairRDD<Tuple2<Integer, Integer>, Double> getPredictions(
			JavaRDD<Tuple2<Object, Object>> userProducts, MatrixFactorizationModel model) {
		JavaPairRDD<Tuple2<Integer, Integer>, Double> predictions = JavaPairRDD
				.fromJavaRDD(model.predict(JavaRDD.toRDD(userProducts)).toJavaRDD()
						.map(new Function<Rating, Tuple2<Tuple2<Integer, Integer>, Double>>() {
							private static final long serialVersionUID = -3455494845926944154L;

							public Tuple2<Tuple2<Integer, Integer>, Double> call(Rating r) {
								return new Tuple2<Tuple2<Integer, Integer>, Double>(
										new Tuple2<Integer, Integer>(r.user(), r.product()), r.rating());
							}
						}));
		return predictions;
	}

}

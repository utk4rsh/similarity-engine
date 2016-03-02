# Similarity-Engine

##Introduction

This is an attempt to build a simple recommendation engine which recommends similar brands based on provided data set of random shoppers and the brands that they have marked as favorites. The engine takes a brand as input, and recommends 15 similar brands. 

The dataset is fairly simple and looks like the following 

|   UserID   |   ItemID  |   ItemName        |
| ---------- |:---------:| -----------------:|
|     1      |    2      |    Newport News   |
|     1      |    12     |    Aldo           |
|     2      |    41     |    Derek Lam      |
|     2      |     4     |    Moschino       |

For example, the engine might recommend

1. ["Citizen", "Tag Heuer", "Maurice Lacroix", ...] for "Bulova"
2. ["American Eagle", "Aeropostale", "Wet Seal", ... ] for "Hollister"


##Implementation

If we look at the data, the data is fairly simple and features on which recommendation for similar brands can be made are should mostly be based on correlation of items with users.

Since we don't know have much information like category of brands, strenght of likeness of brand by user. Simple similarity computation on item vectors (whose dimensions are the userids) should give a sense of similarity.

There are various vector similarity alogrithms that can be used. The following have be used and implemented

1. CosineSimilarity
2. EuclideanSimilarity
3. PearsonCorellationSimilarity

Since there is no test data, it is hard to say which similarity algorithm is best in this scenario. However it is usually observed that CosineSimilarity performs better.

##Steps to Execute

This is maven project, simple mvn exec can be used to run the project and get brands similar to a known brand.

The command expects 4 paramters : 

1. Similarity Algorithm  to be used. Please check the supported similarity algorithms in description above.
2. Since Data Size is large and execution might consume much memory, similarity can be limited by number of users that should be considered.
3. Due to similar memory reasons, number of brands can also be limited by a certain number
4. Brand ID for which similar items need to recommended.

mvn exec:java -Dexec.mainClass="us.ml.similarity.engine.scratch.ItemSimilarityApp" -Dexec.cleanupDaemonThreads=false -Dexec.args="CosineSimilarity 100000 5000 168"

##Sample Output

Top 15 results for : David Kahn 

|   Item Name                  |   Similarity Wt        |
| -----------------------------|:----------------------:|
|     Derek Rose               |    0.5773502691896258  |
|     Imagine by Vince Camuto  |    12               |
|     Jhane Barnes             |    41     |    Derek Lam      |
|     Magic Suit               |     4     |    Moschino       |
|     Magic Suit               |     4     |    Moschino       |
|     Magic Suit               |     4     |    Moschino       |
|     Magic Suit               |     4     |    Moschino       |

# Similarity-Engine

##Introduction

This is an attempt to build a simple recommendation engine which recommends similar brands based on provided data set of random shoppers and the brands that they have marked as favorites. The engine takes a brand as input, and recommends 15 similar brands. 

For example, the engine might recommend:
["Citizen", "Tag Heuer", "Maurice Lacroix", ...] for "Bulova"
["American Eagle", "Aeropostale", "Wet Seal", ... ] for "Hollister"

##Steps to Execute

This is maven project, simple mvn exec can be used to run the project and get brands similar to a known brand.

The command expects 4 paramters : 

1. Similarity Algorithm  to be used. Please check the supported similarity algorithms in description above.
2. Since Data Size is large and execution might consume much memory, similarity can be limited by number of users that should be considered.
3. Due to similar memory reasons, number of brands can also be limited by a certain number
4. Brand ID for which similar items need to recommended.

mvn exec:java -Dexec.mainClass="us.ml.similarity.engine.scratch.ItemSimilarityApp" -Dexec.cleanupDaemonThreads=false -Dexec.args="CosineSimilarity 100000 5000 168"

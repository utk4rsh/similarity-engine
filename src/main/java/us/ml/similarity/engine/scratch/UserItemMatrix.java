package us.ml.similarity.engine.scratch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserItemMatrix {

	private static final String DATA_PATH = "data/brands_filtered.txt";

	private final int userSize;
	private final int itemSize;
	private Set<Integer> items;
	private Set<Integer> users;
	private Map<Integer, String> itemBrand;
	private Map<Integer, Set<Integer>> itemUserMap;
	private int[][] itemUserMatrix;

	public UserItemMatrix(int userSize, int itemSize) {
		this.items = new HashSet<>();
		this.users = new HashSet<>();
		this.itemBrand = new HashMap<>();
		this.itemUserMap = new HashMap<>();
		this.userSize = userSize;
		this.itemSize = itemSize;
	}

	public void contructUserItemMatrix() {
		constructLookupData(DATA_PATH);
		this.itemUserMatrix = new int[itemSize][userSize];
		for (int itemId : this.items) {
			if (itemId < itemSize)
				for (int userId : this.itemUserMap.get(itemId)) {
					if (userId < userSize)
						this.itemUserMatrix[itemId][userId] = 1;
				}
		}
	}

	private void constructLookupData(String filePath) {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e1) {
			throw new IllegalArgumentException("Data file not found at path : " + DATA_PATH);
		}
		String line;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
			while ((line = br.readLine()) != null) {
				String[] sarray = line.split("\t");
				int userKey = Integer.parseInt(sarray[0]);
				int itemKey = Integer.parseInt(sarray[1]);
				String itemName = sarray[2];
				if (itemUserMap.get(itemKey) == null) {
					Set<Integer> newUsers = new HashSet<>();
					newUsers.add(userKey);
					itemUserMap.put(itemKey, newUsers);
				} else {
					itemUserMap.get(itemKey).add(userKey);
				}
				this.itemBrand.put(itemKey, itemName);
				this.items.add(itemKey);
				this.users.add(userKey);
			}
		} catch (IOException e) {
			throw new RuntimeException("Caught Exception while reading data file: " + e);
		}
		System.out.println("Users Size : " + users.size());
		System.out.println("Items Size : " + items.size());
	}

	public Set<Integer> getItems() {
		return items;
	}

	public void setItems(Set<Integer> items) {
		this.items = items;
	}

	public Set<Integer> getUsers() {
		return users;
	}

	public void setUsers(Set<Integer> users) {
		this.users = users;
	}

	public Map<Integer, String> getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(Map<Integer, String> itemBrand) {
		this.itemBrand = itemBrand;
	}

	public Map<Integer, Set<Integer>> getItemUserMap() {
		return itemUserMap;
	}

	public void setItemUserMap(Map<Integer, Set<Integer>> itemUserMap) {
		this.itemUserMap = itemUserMap;
	}

	public int[][] getItemUserMatrix() {
		return itemUserMatrix;
	}

	public void setItemUserMatrix(int[][] itemUserMatrix) {
		this.itemUserMatrix = itemUserMatrix;
	}

	public int getUserSize() {
		return userSize;
	}

	public int getItemSize() {
		return itemSize;
	}
}
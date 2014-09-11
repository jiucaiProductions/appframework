package test.base;

import com.google.common.collect.ImmutableList;

public class TestGuava {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);

		
		System.out.println("list size: "  + list.size());

	}

}

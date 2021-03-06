package hashcool;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoarseHashMapTest {

	@Test
	public void test() {
		CoarseHashMap<Integer, Integer> h = new CoarseHashMap<Integer, Integer>(8);

		int minLimit = Integer.MIN_VALUE + 1;
		int maxLimit = Integer.MAX_VALUE - 1;

		assertFalse(h.contains(0));
		assertFalse(h.contains(minLimit));
		assertFalse(h.contains(maxLimit));

		for (int i = 0; i < 32; i++) {
			assertTrue(h.add(i, 32 - i));
		}

		for (int i = 0; i < 32; i += 5) {
			assertFalse(h.add(i, i));
		}

		for (int i = 0; i < 32; i++) {
			assertTrue(h.contains(i));
		}

		for (int i = 0; i < 32; i += 2) {
			assertTrue(h.remove(i));
		}

		for (int i = 0; i < 32; i += 4) {
			assertFalse(h.remove(i));
		}

		for (int i = 0; i < 32; i++) {
			assertEquals(h.contains(i), i % 2 != 0);
		}

		for (int i = 0; i < 32; i += 2) {
			assertTrue(h.add(i , 32 - i));
		}

		for (int i = 0; i < 32; i++) {
			assertTrue(h.contains(i));
		}
	}

}

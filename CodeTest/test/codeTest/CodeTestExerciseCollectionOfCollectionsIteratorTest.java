package codeTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

/**
 * Implement a set of tests to test your implementation of the collection of collections iterator
 */
public class CodeTestExerciseCollectionOfCollectionsIteratorTest extends TestCase {

	/**
	 * Sample test - test that an empty iterator is returned if the collection of collections 
	 * has no contents
	 */
	public void testEmptyIteratorReturnedIfCollectionOfCollectionsIsEmpty() {
		String[][] contents = {};
		Collection<Collection<Object>> collection = createCollections(contents);
		assertTrue(!new CodeTestExerciseCollectionOfCollectionsIterator(collection).hasNext());
	}
	
	/**
	 * hasNext() test - test that hasNext() returns false if the collection has no contents 
	 * otherwise returns true
	 */
	public void testIfCollectionHasMoreElements() {
		String[][] contents = {
		        { "A", "B", "C"},
		        { "D"},
		        { "E", "F"}};
		Collection<Collection<Object>> collection = createCollections(contents);
		assertTrue(new CodeTestExerciseCollectionOfCollectionsIterator(collection).hasNext());
	}
	
	/**
	 * next() test - test that next() returns the next collection in the collection (["A", "B", "C"]) 
	 * using an iterator. otherwise returns null
	 */
	public void testNextElementOfCollection() {
		String[][] contents = {
		        { "A", "B", "C"},
		        { "D"},
		        { "E", "F"}};
		Collection<Collection<Object>> collection = createCollections(contents);
		assertEquals(new CodeTestExerciseCollectionOfCollectionsIterator(collection).next().equals("[\"A\", \"B\", \"C\"]"), false);
	}

	/**
	 * Utility method that takes a 2-dimensional Object array and returns it as a collection
	 * of collections. 
	 * For example, if contents = {{"a", "b"}, {"c"}}, 
	 * then the returned result would be a Collection containing two Collections:
	 * index 0: Collection containing "a", "b"
	 * index 1: Collection containing "c"
	 * 
	 * @param contents 2-dimensional Object array
	 * @return collection of collections containing the contents of the array
	 */
	private Collection<Collection<Object>> createCollections(Object[][] contents) {
		Collection<Collection<Object>> result = new ArrayList<Collection<Object>>(contents.length);
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] == null) {
				result.add(null);
			} else {
				result.add(Arrays.asList(contents[i]));
			}
		}
		return result;
	}

}

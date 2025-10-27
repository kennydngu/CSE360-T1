package tests;

import entityClasses.Post;

/*-********************************************************************************************

The class for post testing 

**********************************************************************************************/

/**********
 * <p> Title: PostTest Class</p>
 * 
 * <p> Description: This public class supports the actions to test post's. This includes post creation,
 * author validation, title validator,content validator, and update post. In this case, there are 8
 * methods, all method calls start by calling main(String[] args). There is one constructor, and no
 * attributes for this class. </p>
 *
 */
public class PostTest {
	
	/**********
	 * <p> Method: public PostTest() </p>
	 * 
	 * <p> Description: This method is a default constructor of PostTest, but since it has no attributes,
	 * it does nothing.
	 * 	 
	 * 
	 */
	public PostTest() {
	}
	/*-********************************************************************************************

	The methods for post testing
	
	**********************************************************************************************/
	
	
	/**********
	 * <p> Method: public main(String[] args) </p>
	 * 
	 * <p> Description: This method is called when a developer wants to test posts, so it's usually called
	 * within guiStudent.
	 * 	 
	 * @param args is any arguments sent with main, which should usually be nothing.
	 * 
	 */
	public static void main(String[] args) {
		PostCreateTest();
		AuthorConstructorTest();
		ContentConstructorTest();
		UpdateAndReadTest();

	}
	
	/**********
	 * <p> Method: public PostCreateTest() </p>
	 * 
	 * <p> Description: This method is called when a developer wants to test post creation, so it's usually
	 * called within guiStudent. It is also called if a developer calls main(String[] args) from this class.
	 * 	 
	 * 
	 */
	public static void PostCreateTest() { // Create Post testing
		try {
			Post post = new Post(1, "Liam Dang", "How to do this in CSE360", "Content", "General", null, null);
			assertNotNull(post.getId(), "Post Id should not be null");
			assertString("Liam Dang", post.getAuthor());
			assertString("How to do this in CSE360", post.getTitle());
			assertString("Content", post.getContent());
			assertString("General", post.getThread());
			System.out.println("[Pass] Post Create is working properly!");
		} catch (AssertionError err) {
			System.out.println("[Fail] Post Create is not working properly" + err.getMessage());
		}
	}
	
	/**********
	 * <p> Method: public AuthorConstructorTest() </p>
	 * 
	 * <p> Description: This method is called when a developer wants to test author validation, so it's usually
	 * called within guiStudent. It is also called if a developer calls main(String[] args) from this class.
	 * 	 
	 * 
	 */
	public static void AuthorConstructorTest() { //author validator
		try {
			Post post = new Post(2, "", "How to do this in CSE360", "Content", "General", null, null);
			assertString("How to do this in CSE360", post.getTitle());
			assertString("Content", post.getContent());
			assertString("General", post.getThread());
			System.out.println("[Pass] Author Constructor rejects empty string!");
		} catch (IllegalArgumentException err) {
			System.out.println("[Fail] Author Constructor accepts empty string!" + err.getMessage());
		}
	}
	
	/**********
	 * <p> Method: public TitleConstructorTest() </p>
	 * 
	 * <p> Description: This method is called when a developer wants to test title validation, so it's usually
	 * called within guiStudent. It is also called if a developer calls main(String[] args) from this class.
	 * 	 
	 * 
	 */
	public static void TitleConstructorTest() { //title validator
		try {
			Post post = new Post(4, "", "How to do this in CSE360", "Content", "General", null, null);
			assertNotNull(post.getId(), "Post Id should not be null");
			assertString("Content", post.getContent());
			assertString("General", post.getThread());
			System.out.println("[Pass] Author Constructor rejects empty string!");
		} catch (IllegalArgumentException err) {
			System.out.println("[Fail] Author Constructor accepts empty string!" + err.getMessage());
		}
	}
	
	/**********
	 * <p> Method: public ContentConstructorTest() </p>
	 * 
	 * <p> Description: This method is called when a developer wants to test content validation, so it's usually
	 * called within guiStudent. It is also called if a developer calls main(String[] args) from this class.
	 * 	 
	 * 
	 */
	public static void ContentConstructorTest() { //content validator
		try {
			Post post = new Post(1, "Liam Dang", "How to do this in CSE360", "", "General", null, null);
			assertNotNull(post.getId(), "Post Id should be positive integer");
			assertString("Liam Dang", post.getAuthor());
			assertString("How to do this in CSE360", post.getTitle());
			assertString("", post.getContent());
			assertString("General", post.getThread());
			System.out.println("[Pass] Content Constructor rejects empty string!");
		} catch (AssertionError err) {
			System.out.println("[Fail] Content Constructor accepts empty string!" + err.getMessage());
		}
	}
	
	/**********
	 * <p> Method: public UpdateAndReadTest() </p>
	 * 
	 * <p> Description: This method is called when a developer wants to test updating a post, so it's usually
	 * called within guiStudent. It is also called if a developer calls main(String[] args) from this class.
	 * 	 
	 * @throws AssertionError - if the update and read test returns incorrectly.
	 * 
	 */
	public static void UpdateAndReadTest() { // Update and Read Testing at the same time
		try {
			Post post = new Post(1, "Liam Dang", "How to do this in CSE360", "Content", "General", null, null);
			post.setAuthor("Someone else");
			post.setTitle("How to be successful in CSE360");
			post.setContent("Updated Content");
			if (post.getAuthor().equals("Someone else") && post.getTitle().equals("How to be successful in CSE360")
					&& post.getContent().equals("Updated Content")) {
				System.out.println("[Pass] Update and Read Test return correctly!");
			} else {
				throw new AssertionError("[Fail] Update and Read Test return incorrectly!");
			}
		} catch (AssertionError err) {
			System.out.println("[Fail] Update and Read Test return incorrectly!");
		}
	}
	
	
	// helper functions
	/*-********************************************************************************************

	The following methods are helper functions for testing
	
	**********************************************************************************************/
	
	/**********
	 * <p> Method: public assertString(String expected, String actual) </p>
	 * 
	 * <p> Description: This method is called when a method in this class wants to compare two strings,
	 * if they are the same, nothing happens; otherwise, an error is thrown.
	 * 	 
	 * @param expected is the string that is expected to be the correct String for a method's test such
	 * as the expected title.
	 * 
	 * @param actual is the string that the actual String for a method's test such as the actual title
	 * related to a post object.
	 * 
	 * @throws AssertionError - if the actual and expected is not the same
	 * 
	 */
	public static void assertString(String expected, String actual) {
		if (!actual.equals(expected)) {
			throw new AssertionError("Actual: " + expected + ", Expected: " + actual);
		}
	}

	/**********
	 * <p> Method: public assertNotNull(Object object, String message) </p>
	 * 
	 * <p> Description: This method is called when a method in this class wants to confirm an object is
	 * not null. If it is null, an error is thrown.
	 * 	 
	 * @param object is the object to be checked.
	 * 
	 * @param message is the error message that should be thrown.
	 * 
	 * @throws AssertionError - if the object is null
	 * 
	 */
	public static void assertNotNull(Object object, String message) {
		if (object == null) {
			throw new AssertionError(message);
		}
	}
}

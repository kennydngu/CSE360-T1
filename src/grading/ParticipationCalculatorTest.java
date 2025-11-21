package grading;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entityClasses.Post;
import entityClasses.Reply;

/********************************************************************************
 * Title: ParticipationCalculatorBasicTest
 *
 * Description:
 *   This class provides a very simple test for the ParticipationCalculator. It uses hand-constructed Post and Reply objects to
 *   test the core TP3 participation grading rubric:
 *
 *       • counting distinct students answered
 *       • meeting the distinct answer threshold
 *
 *   It prints test results to the terminal and reports whether each test passed.
 *
 *
 * Author: Kenny Nguyen
 * Version: 1.0 (HW3 Test Documentation)
 *******************************************************************************/
public class ParticipationCalculatorTest {

    private static final ParticipationCalculator calc = new ParticipationCalculator();

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== ParticipationCalculator Basic Test Suite ===\n");

        testZeroActivity();
        testTwoDistinctStudents();
        testThreeDistinctStudents();
        testRepliesToOwnPostsAreIgnored();
        testMixedActivity();

        System.out.println("\n=== TESTING COMPLETE ===");
        System.out.println("PASSED: " + passed);
        System.out.println("FAILED: " + failed);
    }

    /******************************* TEST CASES ********************************/

    /** 
     * Test: no posts and no replies should yield zero distinct students answered.
     */
    private static void testZeroActivity() {
        List<Post> posts = new ArrayList<>();
        List<Reply> replies = new ArrayList<>();

        int count = calc.countDistinctStudentsAnswered("alice", posts, replies);
        boolean meets = calc.meetsDistinctAnswerThreshold("alice", posts, replies, 3);

        check("Zero Activity", count, 0, meets, false);
    }

    /**
     * Test: student answers exactly two distinct students.
     */
    private static void testTwoDistinctStudents() {
        List<Post> posts = new ArrayList<>();
        List<Reply> replies = new ArrayList<>();

        posts.add(newPost(1, "student1"));
        posts.add(newPost(2, "student2"));
        posts.add(newPost(3, "student3"));

        replies.add(newReply(10, "studen4", 1)); // answers student1
        replies.add(newReply(11, "student4", 2)); // answers student2

        int count = calc.countDistinctStudentsAnswered("alice", posts, replies);
        boolean meets = calc.meetsDistinctAnswerThreshold("alice", posts, replies, 3);

        check("Two Distinct Students", count, 2, meets, false);
    }

    /**
     * Test: student meets threshold by answering three distinct students.
     */
    private static void testThreeDistinctStudents() {
        List<Post> posts = new ArrayList<>();
        List<Reply> replies = new ArrayList<>();

        posts.add(newPost(1, "student1"));
        posts.add(newPost(2, "student2"));
        posts.add(newPost(3, "student3"));

        replies.add(newReply(10, "student4", 1));
        replies.add(newReply(11, "student4", 2));
        replies.add(newReply(12, "student4", 3));

        int count = calc.countDistinctStudentsAnswered("student4", posts, replies);
        boolean meets = calc.meetsDistinctAnswerThreshold("student4", posts, replies, 3);

        check("Three Distinct Students", count, 3, meets, true);
    }

    /**
     * Test: replies to own posts should not count as answering another student.
     */
    private static void testRepliesToOwnPostsAreIgnored() {
        List<Post> posts = new ArrayList<>();
        List<Reply> replies = new ArrayList<>();

        posts.add(newPost(1, "student4")); // own post
        posts.add(newPost(2, "student1"));

        replies.add(newReply(10, "student4", 1));  // should be ignored
        replies.add(newReply(11, "student4", 2));  // counts

        int count = calc.countDistinctStudentsAnswered("student4", posts, replies);

        check("Replies To Own Posts Ignored", count, 1, calc.meetsDistinctAnswerThreshold("student4", posts, replies, 1), true);
    }

    /**
     * Mixed test: multiple answers to the same student, only one counts.
     */
    private static void testMixedActivity() {
        List<Post> posts = new ArrayList<>();
        List<Reply> replies = new ArrayList<>();

        posts.add(newPost(1, "student1"));
        posts.add(newPost(2, "student1"));   // same student again
        posts.add(newPost(3, "student3"));

        replies.add(newReply(10, "student4", 1)); // student1
        replies.add(newReply(11, "student4", 2)); // student1 - should NOT add distinct
        replies.add(newReply(12, "student4", 3)); // carol

        int count = calc.countDistinctStudentsAnswered("student4", posts, replies);

        check("Mixed Activity Distinct Counting", count, 2, false, false);
    }

    /******************************* HELPERS **********************************/

    /** Makes a Post with minimal required fields. */
    private static Post newPost(int id, String author) {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        return new Post(id, author, "", "", "", t, t);
    }

    /** Makes a Reply with minimal required fields. */
    private static Reply newReply(int id, String author, int postId) {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        return new Reply(id, author, "", t, t, postId);
    }

    /**
     * Checks results for each test.
     */
    private static void check(String name, int actualCount, int expectedCount,
                              boolean actualFlag, boolean expectedFlag) {
        System.out.println("Test: " + name);
        System.out.println("  Expected Count:   " + expectedCount);
        System.out.println("  Actual Count:     " + actualCount);
        System.out.println("  Expected Meets?:  " + expectedFlag);
        System.out.println("  Actual Meets?:    " + actualFlag);

        boolean result = (actualCount == expectedCount) && (actualFlag == expectedFlag);

        if (result) {
            System.out.println("  --> PASS\n");
            passed++;
        } else {
            System.out.println("  --> FAIL\n");
            failed++;
        }
    }
}

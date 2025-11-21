package grading;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entityClasses.Post;
import entityClasses.Reply;

/**
 * <p>Title: ParticipationCalculator</p>
 *
 * <p>Description: Computes discussion-based participation metrics for a single student.
 * In particular, this class focuses on counting how many distinct other students a given
 * student has answered by replying to their posts.</p>
 *
 * <p>This class is designed so that it can be tested independently of the database or GUI.
 * Callers provide in-memory lists of {@link Post} and {@link Reply} objects.</p>
 *
 * @author Kenny Nguyen
 * @version 1.00 2025-11-13 Initial prototype for TP3 aspect: grade students based on parameters.
 */
public class ParticipationCalculator {

    /**
     * Count how many distinct other students the given student has answered.
     *
     * @param studentUserName the user name of the student whose participation is being measured
     * @param posts           a list of all discussion posts
     * @param replies         a list of all replies
     * @return the number of distinct other students that this student has answered
     */
    public int countDistinctStudentsAnswered(String studentUserName,
                                             List<Post> posts,
                                             List<Reply> replies) {

        if (studentUserName == null || posts == null || replies == null) {
            return 0;
        }

        Map<Integer, Post> postsById = new HashMap<>();
        for (Post post : posts) {
            postsById.put(post.getId(), post);
        }

        Set<String> distinctAuthorsAnswered = new HashSet<>();

        for (Reply reply : replies) {
            if (!studentUserName.equals(reply.getAuthor())) {
                continue;
            }

            Post originalPost = postsById.get(reply.getPostId());
            if (originalPost == null) {
                continue;
            }

            String originalAuthor = originalPost.getAuthor();
            if (originalAuthor == null || studentUserName.equals(originalAuthor)) {
                continue;
            }

            distinctAuthorsAnswered.add(originalAuthor);
        }

        return distinctAuthorsAnswered.size();
    }

    /**
     * Determine whether the given student meets the threshold for distinct students answered.
     *
     * @param studentUserName the user name of the student being evaluated
     * @param posts           a list of all discussion posts
     * @param replies         a list of all replies
     * @param threshold       the minimum number of distinct students the student must have
     * @return {@code true} if the student meets or exceeds the threshold, {@code false} otherwise
     */
    public boolean meetsDistinctAnswerThreshold(String studentUserName,
                                                List<Post> posts,
                                                List<Reply> replies,
                                                int threshold) {

        int normalizedThreshold = Math.max(threshold, 0);
        int distinctCount = countDistinctStudentsAnswered(studentUserName, posts, replies);
        return distinctCount >= normalizedThreshold;
    }
}

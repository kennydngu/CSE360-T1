package guiStaff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import applicationMain.FoundationsMain;
import database.Database;
import entityClasses.Post;
import entityClasses.Reply;
import entityClasses.User;
import grading.ParticipationCalculator;
import guiTools.BaseView;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*******
 * <p> Title: ViewStaffGrading Class. </p>
 *
 * <p> Description: Provides a JavaFX screen for staff to see a list of students,
 * whether they have met the "answered at least N different students" threshold,
 * how many posts and answers they have made, and the automatically computed
 * participation grade. Staff can also override the grade for an individual
 * student using an "Edit Grade" button on each row.</p>
 *
 * @author Kenny Nguyen
 *
 * @version 1.00 2025-11-13 Initial prototype
 */
public class ViewStaffGrading extends BaseView {

    private static final double width = FoundationsMain.WINDOW_WIDTH;
    private static final double height = FoundationsMain.WINDOW_HEIGHT;

    /** Threshold for "has answered enough different students". */
    private static final int DISTINCT_THRESHOLD = 3;

    protected static Stage theStage;
    protected static User theUser;
    protected static Database theDatabase = FoundationsMain.database;
    private static ViewStaffGrading theView;

    private ParticipationCalculator participationCalculator;

    private TableView<StudentParticipationRow> table;


    public static void displayStaffGrading(Stage stage, User user) {
        theStage = stage;
        theUser = user;

        if (theView == null) {
            theView = new ViewStaffGrading(stage);
        }
        theView.show();
    }

    private ViewStaffGrading(Stage stage) {
        super(stage, width, height);
    }

    @Override
    protected Node buildContent() {
        Label title = new Label("Staff Grading – Discussion Participation");
        title.getStyleClass().add("title");

        Label subtitle = new Label("User: " + (theUser != null ? theUser.getUserName() : ""));
        subtitle.getStyleClass().add("subtitle");

        VBox header = vbox(4, null, title, subtitle);

        table = new TableView<>();
        configureTableColumns();
        reloadTableData();

        VBox content = new VBox(16, header, table);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        return content;
    }

    private void configureTableColumns() {
        TableColumn<StudentParticipationRow, String> nameCol =
                new TableColumn<>("Student");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        nameCol.setPrefWidth(180);

        TableColumn<StudentParticipationRow, Boolean> thresholdCol =
                new TableColumn<>("Threshold Met?");
        thresholdCol.setCellValueFactory(new PropertyValueFactory<>("thresholdMet"));
        thresholdCol.setPrefWidth(120);

        TableColumn<StudentParticipationRow, Integer> postsCol =
                new TableColumn<>("Posts");
        postsCol.setCellValueFactory(new PropertyValueFactory<>("postCount"));
        postsCol.setPrefWidth(80);

        TableColumn<StudentParticipationRow, Integer> answersCol =
                new TableColumn<>("Answers");
        answersCol.setCellValueFactory(new PropertyValueFactory<>("answerCount"));
        answersCol.setPrefWidth(80);

        TableColumn<StudentParticipationRow, Integer> distinctCol =
                new TableColumn<>("Distinct Students Answered");
        distinctCol.setCellValueFactory(new PropertyValueFactory<>("distinctStudentsAnswered"));
        distinctCol.setPrefWidth(200);

        TableColumn<StudentParticipationRow, Double> gradeCol =
                new TableColumn<>("Auto Grade");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("autoGrade"));
        gradeCol.setPrefWidth(100);

        TableColumn<StudentParticipationRow, Void> editCol =
                new TableColumn<>("Edit Grade");
        editCol.setCellFactory(col -> new TableCell<StudentParticipationRow, Void>() {
            private final Button btn = new Button("Edit");

            {
                btn.getStyleClass().add("secondary-btn");
                btn.setOnAction(e -> {
                    StudentParticipationRow row = getTableView().getItems().get(getIndex());
                    showEditGradeDialog(row);
                    getTableView().refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        table.getColumns().setAll(
                nameCol,
                thresholdCol,
                postsCol,
                answersCol,
                distinctCol,
                gradeCol,
                editCol
        );
    }

    private void reloadTableData() {

        if (participationCalculator == null) {
            participationCalculator = new ParticipationCalculator();
        }

        List<Post> posts = new ArrayList<>();
        List<Reply> allReplies = new ArrayList<>();
        List<String> studentUserNames = new ArrayList<>();

        try {
            posts = theDatabase.listPosts();
            for (Post p : posts) {
                allReplies.addAll(theDatabase.listRepliesForPost(p.getId()));
            }
            studentUserNames = theDatabase.listStudentUserNames();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Error loading data for grading: " + ex.getMessage());
            alert.showAndWait();
        }

        Map<String, Integer> postCount = new HashMap<>();
        Map<String, Integer> answerCount = new HashMap<>();

        for (Post p : posts) {
            String author = p.getAuthor();
            if (author == null || author.isEmpty()) continue;
            postCount.put(author, postCount.getOrDefault(author, 0) + 1);
        }

        for (Reply r : allReplies) {
            String author = r.getAuthor();
            if (author == null || author.isEmpty()) continue;
            answerCount.put(author, answerCount.getOrDefault(author, 0) + 1);
        }

        List<StudentParticipationRow> rows = new ArrayList<>();

        for (String studentName : studentUserNames) {
            int postsForStudent = postCount.getOrDefault(studentName, 0);
            int answersForStudent = answerCount.getOrDefault(studentName, 0);

            int distinctAnswered =
                    participationCalculator.countDistinctStudentsAnswered(studentName, posts, allReplies);

            boolean thresholdMet =
                    participationCalculator.meetsDistinctAnswerThreshold(
                            studentName, posts, allReplies, DISTINCT_THRESHOLD);

            double autoGrade = computeAutoGrade(
                    postsForStudent,
                    answersForStudent,
                    distinctAnswered,
                    thresholdMet
            );

            rows.add(new StudentParticipationRow(
                    studentName,
                    postsForStudent,
                    answersForStudent,
                    distinctAnswered,
                    thresholdMet,
                    autoGrade
            ));
        }

        table.setItems(FXCollections.observableArrayList(rows));
    }


    private double computeAutoGrade(int postsForStudent,
                                    int answersForStudent,
                                    int distinctAnswered,
                                    boolean thresholdMet) {

        double grade = 50.0;

        int distinctContribution = Math.min(distinctAnswered, DISTINCT_THRESHOLD);
        grade += distinctContribution * 10.0;

        int cappedPosts = Math.min(postsForStudent + answersForStudent, 10);
        grade += cappedPosts * 2.0;

        if (grade < 0.0) grade = 0.0;
        if (grade > 100.0) grade = 100.0;

        return grade;
    }

    private void showEditGradeDialog(StudentParticipationRow row) {
        TextInputDialog dialog = new TextInputDialog(String.format("%.1f", row.getAutoGrade()));
        dialog.setTitle("Edit Grade");
        dialog.setHeaderText("Edit grade for " + row.getStudentName());
        dialog.setContentText("Enter new grade (0–100):");

        dialog.showAndWait().ifPresent(input -> {
            try {
                double newGrade = Double.parseDouble(input);
                if (newGrade < 0.0 || newGrade > 100.0) {
                    throw new NumberFormatException("Grade must be between 0 and 100.");
                }
                row.setAutoGrade(newGrade);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.ERROR,
                        "Invalid grade value. Please enter a number between 0 and 100.");
                alert.showAndWait();
            }
        });
    }

    @Override
    protected void onQuit() {
        ControllerStaffHome.performQuit();
    }

    @Override
    protected void onLogout() {
        ControllerStaffHome.performLogout();
    }

 
    public static class StudentParticipationRow {
        private final String studentName;
        private final int postCount;
        private final int answerCount;
        private final int distinctStudentsAnswered;
        private final boolean thresholdMet;
        private double autoGrade;

        public StudentParticipationRow(String studentName,
                                       int postCount,
                                       int answerCount,
                                       int distinctStudentsAnswered,
                                       boolean thresholdMet,
                                       double autoGrade) {
            this.studentName = studentName;
            this.postCount = postCount;
            this.answerCount = answerCount;
            this.distinctStudentsAnswered = distinctStudentsAnswered;
            this.thresholdMet = thresholdMet;
            this.autoGrade = autoGrade;
        }

        public String getStudentName() { return studentName; }

        public int getPostCount() { return postCount; }

        public int getAnswerCount() { return answerCount; }

        public int getDistinctStudentsAnswered() { return distinctStudentsAnswered; }

        // For PropertyValueFactory("thresholdMet")
        public boolean getThresholdMet() { return thresholdMet; }
        public boolean isThresholdMet() { return thresholdMet; }

        public double getAutoGrade() { return autoGrade; }

        public void setAutoGrade(double autoGrade) { this.autoGrade = autoGrade; }
    }
}

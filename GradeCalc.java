import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GradeCalc {
    private List<Homework> homeworks;
    private List<Quiz> quizzes;
    private int prelimScore;

    public GradeCalc() {
        homeworks = new ArrayList<>();
        quizzes = new ArrayList<>();
    }

    public void inputHomeworkScores(Scanner scanner) {
        for (int i = 1; i <= 5; i++) {
            Homework hw = new Homework();
            hw.inputHomeworkDetails(scanner, i);
            homeworks.add(hw);
        }
    }

    public void inputQuizScores(Scanner scanner) {
        for (int i = 1; i <= 6; i++) {
            Quiz quiz = new Quiz();
            quiz.inputQuizScore(scanner, i);
            quizzes.add(quiz);
        }
    }

    public void inputPrelimScore(Scanner scanner) {
        while (true) {
            System.out.print("Enter a grade for prelim: ");
            if (scanner.hasNextInt()) {
                prelimScore = scanner.nextInt();
                if (prelimScore < 0 || prelimScore > 100) {
                    System.out.println("Not a legal entry: Please enter an integer between 0 and 100.");
                } else {
                    break;
                }
            } else {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next(); // added errors to make sure code runs smoothly and is more realistic 
            }
        }
    }

    public double calculateFinalScore() {
        double totalScore = 0;

        // Calculate homework scores
        double homeworkScore = 0;
        for (Homework hw : homeworks) {
            homeworkScore += hw.getAdjustedScore();
        }
        totalScore += (homeworkScore / homeworks.size()) * 0.4; // 40% weight

        // Calculate quiz scores
        double quizScore = 0;
        int lowestQuizScore = Integer.MAX_VALUE;
        for (Quiz quiz : quizzes) {
            int score = quiz.getScore();
            quizScore += score;
            if (score < lowestQuizScore) {
                lowestQuizScore = score;
            }
        }
        // Drop the lowest quiz score
        quizScore -= lowestQuizScore;
        totalScore += (quizScore / (quizzes.size() - 1)) * 0.2; 

        
        totalScore += (prelimScore * 0.4); 

        
        totalScore /= 1.0; 

        return totalScore;
    }

    public String determineLetterGrade(double finalScore) {
        if (finalScore >= 90) return "A";
        if (finalScore >= 80) return "B";
        if (finalScore >= 70) return "C";
        if (finalScore >= 60) return "D";
        return "F";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GradeCalc gradeCalc = new GradeCalc();

        System.out.println("Enter your scores for homework:");
        gradeCalc.inputHomeworkScores(scanner);

        System.out.println("Enter your scores for quizzes:");
        gradeCalc.inputQuizScores(scanner);

        
        gradeCalc.inputPrelimScore(scanner);

        double finalScore = gradeCalc.calculateFinalScore();
        String letterGrade = gradeCalc.determineLetterGrade(finalScore);

        System.out.printf("-------------------------------------\n");
        System.out.printf("Your final course score: %.2f\n", finalScore);
        System.out.printf("Your letter grade will be at least: %s\n", letterGrade);

        scanner.close();
    }
}

class Homework {
    private int score;
    private boolean submittedOnTime;

    public void inputHomeworkDetails(Scanner scanner, int homeworkNumber) {
        while (true) {
            System.out.print("Enter a grade for homework #" + homeworkNumber + ": ");
            if (scanner.hasNextInt()) {
                score = scanner.nextInt();
                if (score < 0 || score > 100) {
                    System.out.println("Not a legal entry: Please enter an integer between 0 and 100.");
                } else {
                    break;
                }
            } else {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next(); 
            }
        }

        submittedOnTime = inputYesNo(scanner, "Was homework #" + homeworkNumber + " submitted on time? (Y/N): ");

        // Adjusts the score based on submission status
        if (!submittedOnTime) {
            boolean submittedLate = inputYesNo(scanner, "Was homework #" + homeworkNumber + " submitted within 24 hours of the deadline? (Y/N): ");
            if (submittedLate) {
                score *= 0.8; 
            } else {
                score *= 0; 
            }
        }
    }

    public int getAdjustedScore() {
        return score;
    }

    private boolean inputYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.next().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        }
    }
}

class Quiz {
    private int score;

    public void inputQuizScore(Scanner scanner, int quizNumber) {
        while (true) {
            System.out.print("Enter a grade for quiz #" + quizNumber + ": ");
            if (scanner.hasNextInt()) {
                score = scanner.nextInt();
                if (score < 0 || score > 100) {
                    System.out.println("Not a legal entry: Please enter an integer between 0 and 100.");
                } else {
                    break;
                }
            } else {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next(); 
            }
        }
    }

    public int getScore() {
        return score;
    }
}

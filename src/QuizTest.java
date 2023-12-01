import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class InvalidOperationException extends Exception{
    InvalidOperationException(String msg){
        super(msg);
    }
}
class Question implements Comparable<Question>{
    String text;
    int points;
    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    @Override
    public int compareTo(Question o) {
        return Integer.compare(o.points,points);
    }
    public double answerQuestion(String studentasnwer){
        return 0;
    }
}

class TFQuestion extends Question{
    boolean answer;

    public TFQuestion(String text, int points, boolean answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public String toString() {
        //True/False Question: Question3 Points: 2 Answer: false
        return "True/False Question: " + text + " Points: " + points + " Answer: " + answer;
    }
    @Override
    public double answerQuestion(String studentasnwer){
        return answer == Boolean.parseBoolean(studentasnwer) ? points : 0.0;
    }
}
class MCQuestion extends Question{
    String answer;

    public MCQuestion(String text, int points, String answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public String toString() {
        //Multiple Choice Question: Question2 Points 4 Answer: E
        return "Multiple Choice Question: " + text + " Points " + points + " Answer: " + answer;
    }
    @Override
    public double answerQuestion(String studentasnwer){
        return answer.equals(studentasnwer) ? points : points*(-0.2);
    }
}
class QuizFactory{
    static List<String> allowed = Arrays.asList("A","B","C","D","E");
    static Question create(String QD) throws InvalidOperationException {
        String [] parts = QD.split(";");
        if (parts[0].equals("MC")){
            if (!allowed.contains(parts[3])){
                throw new InvalidOperationException(String.format("%s is not allowed option for this question",parts[3]));
            }
            else {
                return new MCQuestion(parts[1],Integer.parseInt(parts[2]),parts[3]);
            }
        } else {
            return new TFQuestion(parts[1],Integer.parseInt(parts[2]),Boolean.parseBoolean(parts[3]));
        }
    }
}
class Quiz{
    List<Question> questions;
    public Quiz(){
        questions = new ArrayList<>();
    }
    public void addQuestion(String questionData) throws InvalidOperationException {
        questions.add(QuizFactory.create(questionData));
    }
    public void printQuiz(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        questions.stream().sorted().forEach(question -> pw.println(question));
        pw.flush();
        pw.close();
    }
    public void answerQuiz (List<String> answers, OutputStream os) throws InvalidOperationException {
        if (questions.size()!=answers.size()){
            throw new InvalidOperationException("Answers and questions must be of same length!");
        } else {
            double sum = 0;
            PrintWriter pw = new PrintWriter(os);
            for (int i = 0; i < answers.size(); i++) {
                double points = questions.get(i).answerQuestion(answers.get(i));
                pw.println(String.format("%d. %.2f", i+1,  points));
                sum+=points;
            }
            pw.println(String.format("Total points: %.2f",sum));
            pw.flush();
        }
    }
}

public class QuizTest {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            try {
                quiz.addQuestion(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

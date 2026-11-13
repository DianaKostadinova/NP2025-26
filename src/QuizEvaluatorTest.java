//package midterms.m1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class QuizResult{
    String id;
    int correct;
    QuizResult(String id, int correct) {
        this.id = id;
        this.correct = correct;
    }
    int getCorrect() {
        return correct;
    }
    String getId() {
        return id;
    }
}
class QuizEvaluator{
    List<String> correctAnswers;
    public QuizEvaluator(List<String> correctAnswers){
        this.correctAnswers = correctAnswers;
    }
    public void evaluateQuizAttempts(InputStream is, OutputStream os) {
        Scanner scanner = new Scanner(is);
        PrintWriter printWriter = new PrintWriter(os);

        List<QuizResult> results = new ArrayList<>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            if(line.isEmpty()){
                continue;
            }
            String [] parts = line.split(" ");
            String id = parts[0];
            int cnt = 0;
            int n = Math.min(parts.length -1, correctAnswers.size());
            for (int i = 0; i < n; i++) {
                if (parts[i+1].equals(correctAnswers.get(i).trim())) {
                    cnt++;
                }
            }
            results.add(new QuizResult(id,cnt));
        }

        results.sort(Comparator.comparingInt(QuizResult::getCorrect).reversed());

        for (QuizResult result : results) {
            printWriter.println(String.format("%s %d", result.id, result.getCorrect()));
        }
        printWriter.flush();
    }
}


public class QuizEvaluatorTest {

    public static void wtf (Scanner in) throws FileNotFoundException {
        /*
        Read all lines from scanner and write them to a file input.txt
        * */
        PrintWriter pw = new PrintWriter(new FileOutputStream("input.txt"));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            pw.println(line);
        }

        in.close();
        pw.flush();
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        List<String> correct = Arrays.stream(sc.nextLine().split(";")).collect(Collectors.toList());

        wtf(sc);

        QuizEvaluator qe = new QuizEvaluator(correct);

        qe.evaluateQuizAttempts(new FileInputStream("input.txt"), System.out);
    }
}

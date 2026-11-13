import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class SkillUser{
    String name;
    int numSkills;
    public SkillUser(String name, int numSkills){
        this.name = name;
        this.numSkills = numSkills;
    }
    String getName(){
        return this.name;
    }
    int getNumSkills(){
        return this.numSkills;
    }
}

class SkillsMatcher {
    List<String>requiredSkills;
    SkillsMatcher(List<String> requiredSkills){
        this.requiredSkills = requiredSkills;
    }
    public void evaluateCandidates(InputStream is, OutputStream os){
        Scanner sc = new Scanner(is);
        PrintWriter pw = new PrintWriter(os);
        List<SkillUser>results = new ArrayList<>();

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if (line.isEmpty())
            {
                continue;
            }

            String [] parts = line.split(" ");
            String user = parts[0];
            int cnt=0;
            for ( int i = 0; i < requiredSkills.size(); i++ ){
                for ( int j =1; j< parts.length; j++){
                    if(requiredSkills.get(i).equals(parts[j])){
                        cnt++;
                        break;
                    }
                }
            }
            results.add(new SkillUser(user,cnt));
        }

        results.sort(Comparator.comparing(SkillUser::getNumSkills).reversed());
        results.forEach(x -> pw.println(String.format("%s %d", x.getName(),x.getNumSkills())));
        pw.flush();
    }
}

public class SkillsMatcherTest {

    public static void wtf(Scanner in) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("input.txt"));
        while (in.hasNextLine()) {
            pw.println(in.nextLine());
        }
        pw.flush();
        pw.close();
        in.close();
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);


        List<String> required = Arrays.stream(sc.nextLine().split(";"))
                .collect(Collectors.toList());


        wtf(sc);

        SkillsMatcher matcher = new SkillsMatcher(required);


        matcher.evaluateCandidates(new FileInputStream("input.txt"), System.out);
    }
}

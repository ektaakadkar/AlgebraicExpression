import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


public class Main_Class {

    private  static String fileName = "";
    private static HashMap<String, Integer> storage = new HashMap<>();
    private static List<Node> parseTrees = new ArrayList<>();
    private static List<String> operators = new ArrayList<>();

    private static int operator(int left, int right, String operator ){
        int result = 0;
        switch (operator){
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "//":
                result = left / right;
                break;
        }
        return result;
    }

    private static int __evaluate(Node n){
        if (n.right == null){
            if (!operators.contains(n.value)){
                int ret = 0;
                if ((storage.containsKey(n.value))){
                    ret = storage.get(n.value);
                }
                else {
                    ret = Integer.parseInt(n.value);
                }
                return ret;
            }
        }
            int left = __evaluate(n.left);
            int right = __evaluate(n.right);
            return operator(left, right, n.value);
    }

    private static void  __assign(Node n){
        int put = __evaluate(n.right);
        storage.put(n.left.value, put);
    }

    private static void evaluate(){
        for (Node n:parseTrees) {
            int result;
            if (n.value.equals("=")){
                __assign(n);
                continue;
            }
            else{
            result = __evaluate(n);}
            System.out.println(result);
        }
    }

    private static void __emit(Node n){
        if (n == null)
            return;
        if (n.left != null && !n.value.equals("="))
            System.out.print("(");
        __emit(n.left);
        System.out.print(" "+n.value + " ");
        __emit(n.right);
        if (n.right != null && !n.value.equals("="))
            System.out.print(")");
    }

    private static void emit(){
        for (Node n:parseTrees) {
            __emit(n);
            System.out.println();
        }
    }

    private static Node __parseTree(String fileLine){
        String[] char_array = fileLine.split(" ");
        Stack<Node> stack = new Stack<>();
        int length = char_array.length - 1 ;
        int counter = 0;
        Node n1 = new Node(char_array[length],null,null);
        length--;
        counter ++ ;
        stack.add(n1);
        Node n2 = new Node(char_array[length], null, null);
        length--;
        counter ++ ;
        stack.add(n2);
        while (counter < char_array.length){
            String c = char_array[length];
            if (operators.contains(c)){
                n1 = stack.pop();
                n2 = stack.pop();
                Node n3 = new Node(char_array[length],n1,n2);
                length--;
                counter ++ ;
                stack.add(n3);
            }
            else {
                Node n4 = new Node(char_array[length], null, null);
                length--;
                counter ++ ;
                stack.add(n4);
            }
        }
        return stack.pop();
    }

    private static void parseTree(){
        try {
            String fileLine;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            while ((fileLine = bufferedReader.readLine()) != null) {
                if (!fileLine.equals("") && fileLine.charAt(0) != '#') {
                    Node parseTree = __parseTree(fileLine);
                    parseTrees.add(parseTree);
                }
            }
            System.out.println(parseTrees);
            bufferedReader.close();
            fileReader.close();
        }
        catch (Exception e){
            System.out.println("Error occured!");
        }
    }

    public static void main(String[] args) {
        fileName = args[0];
        operators.add("+");
        operators.add("-");
        operators.add("//");
        operators.add("*");
        operators.add("=");
        try {
            parseTree();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            emit();
        } catch (Exception e) {
            System.out.println("Error occured!");
        }
        for (Node n : parseTrees) {
            try {
                int result;
                if (n.value.equals("=")) {
                    __assign(n);
                    continue;
                } else {
                    result = __evaluate(n);
                }
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Error occured!");
            }
        }
    }
}

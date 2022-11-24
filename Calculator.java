import java.util.Scanner;

public class Calculator {

    //Field
    Object[] stack;
    int top;

    //Constructor
    public Calculator(){
        int init_cap = 50;
        stack = new Object[init_cap];
        top = -1;
    }

    //Method - Stack
    public boolean isEmpty(){
        return (top==-1);
    }

    public boolean isFull(){
        return (top==stack.length-1);
    }

    public Object peek(){
        if (isEmpty())
            throw new RuntimeException("Illegal Expression.");
        return stack[top];
    }

    public void push(Object obj){
        if (isFull())
            ensureCapacity();
        stack[++top] = obj;
    }
    public Object pop(){
        if (isEmpty())
            throw new RuntimeException("Illegal Expression.");
        Object top_element = stack[top];
        stack[top--] = null;
        return top_element;
    }

    private void ensureCapacity(){
        Object[] larger = new Object[stack.length*2+1];
        System.arraycopy(stack, 0, larger, 0, stack.length);
        stack = larger;
    }

    String infixToPostfix(String infix_exp){
        boolean end_of_number = false;
        String postfix_exp = "";
        Calculator stk = new Calculator();

        for (int i =0; i< infix_exp.length(); i++){
            switch (infix_exp.charAt(i)){
                //공백: pass
                case ' ': break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.':
                    postfix_exp = postfix_exp.concat(infix_exp.charAt(i)+"");
                    end_of_number = true;
                    break;
                case '(':
                    if (end_of_number){
                        postfix_exp = postfix_exp.concat(" ");
                        end_of_number = false;
                    }
                    stk.push('(');
                    break;
                case ')': //'(' 만날 때까지 pop 후 postfix_exp 설정
                    if (end_of_number){
                        postfix_exp = postfix_exp.concat(" ");
                        end_of_number = false;
                    }
                    while ((Character) stk.peek() != '(')
                        postfix_exp = postfix_exp.concat(((Character)stk.pop()).toString());
                    stk.pop();
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if (end_of_number){
                        postfix_exp = postfix_exp.concat(" ");
                        end_of_number = false;
                    }
                    //비었으면 push 전에 연산자 비교하고 낮으면 높아질 때까지 pop
                    while (!stk.isEmpty() && (Character)stk.peek()!='(' && getPre(infix_exp.charAt(i)) <= getPre((Character)stk.peek())) {
                        postfix_exp = postfix_exp.concat(((Character)stk.pop()).toString());
                    }
                    stk.push(infix_exp.charAt(i));
                    break;
                default:
                    throw new RuntimeException("Illegal Character.");
            }
        }

        if (end_of_number){
            postfix_exp = postfix_exp.concat(" ");
        }

        //스택에 남아있는 items 모두 pop 후 리턴
        while (!stk.isEmpty()){
            postfix_exp = postfix_exp.concat(((Character)stk.pop()).toString());
        }
        return postfix_exp;
    }

    double calculatePostfix(String postfix_exp){
        double value, buffer;
        String temp = "";
        Calculator stk = new Calculator();

        for (int i=0; i<postfix_exp.length(); i++){
            switch (postfix_exp.charAt(i)){
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.':
                    temp = temp.concat(postfix_exp.charAt(i)+"");
                case ' ':
                    stk.push(new Double(temp));
                    temp = "";
                    break;
                case '+':
                    value = (Double) stk.pop() + (Double) stk.pop();
                    stk.push(value);
                    break;
                case '-':
                    buffer = (Double) stk.pop();
                    value = (Double) stk.pop() - buffer;
                    stk.push(value);
                    break;
                case '*':
                    value = (Double) stk.pop() * (Double) stk.pop();
                    stk.push(value);
                    break;
                case '/':
                    buffer = (Double) stk.pop();
                    if (buffer == 0){
                        throw  new ArithmeticException("Can't Divide by 0.");
                    }
                    value = (Double) stk.pop() / buffer;
                    stk.push(value);
                    break;
                default:
                    throw  new RuntimeException("Illegal Character.");
            }
        }
        return (Double)stk.peek();
    }

    int getPre(char op){
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("[Calculator]");
        System.out.println("Expression in INFIX: ");
        String infix_exp = scanner.nextLine();
        Calculator c = new Calculator();
        String postfix_exp = c.infixToPostfix(infix_exp);
        double result = c.calculatePostfix(postfix_exp);
        System.out.println("Expression in POSTFIX: " + postfix_exp);
        System.out.println("Result: "+result);
    }
}

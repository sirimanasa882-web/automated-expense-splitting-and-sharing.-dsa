import java.util.*;

class Expense {
    String category;
    double amount;
    double gst;
    double total;
    String payer;
    double split;
    List<String> persons;

    Expense(String category,double amount,double gst,double total,String payer,double split,List<String> persons){
        this.category=category;
        this.amount=amount;
        this.gst=gst;
        this.total=total;
        this.payer=payer;
        this.split=split;
        this.persons=persons;
    }

    public String toString(){
        return "\nCategory: "+category+
               "\nAmount: "+amount+
               "\nGST: "+gst+
               "\nTotal: "+total+
               "\nPaid By: "+payer+
               "\nPer Person: "+split+
               "\nPersons: "+persons+"\n";
    }
}

public class ExpenseManager {

    static LinkedList<Expense> transactions = new LinkedList<>();
    static Stack<Expense> undoStack = new Stack<>();
    static Queue<Expense> processQueue = new LinkedList<>();

    static Scanner sc = new Scanner(System.in);

    static double calculateGST(double amount){
        return amount * 0.18;
    }

    static void addExpense(){

        System.out.print("Enter number of persons: ");
        int n = sc.nextInt();
        sc.nextLine();

        List<String> persons = new ArrayList<>();

        for(int i=0;i<n;i++){
            System.out.print("Enter name of person "+(i+1)+": ");
            persons.add(sc.nextLine());
        }

        System.out.print("Enter Category (Food/Travel/Rent/Shopping): ");
        String category = sc.nextLine();

        System.out.print("Enter Total Bill Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        double gst = calculateGST(amount);
        double total = amount + gst;

        double split = total / n;

        System.out.print("Who Paid?: ");
        String payer = sc.nextLine();

        Expense exp = new Expense(category,amount,gst,total,payer,split,persons);

        transactions.add(exp);
        undoStack.push(exp);
        processQueue.add(exp);

        System.out.println("\nExpense Added Successfully");
    }

    static void showTransactions(){

        if(transactions.isEmpty()){
            System.out.println("No transactions found");
            return;
        }

        for(Expense e : transactions){
            System.out.println(e);
        }
    }

    static void searchExpense(){

        System.out.print("Enter category to search: ");
        String key = sc.next();

        boolean found = false;

        for(Expense e : transactions){

            if(e.category.equalsIgnoreCase(key)){
                System.out.println("Transaction Found:");
                System.out.println(e);
                found = true;
            }

        }

        if(!found){
            System.out.println("No matching expense found");
        }

    }

    static void sortExpenses(){

        transactions.sort((a,b)->Double.compare(a.total,b.total));

        System.out.println("\nExpenses Sorted By Total Amount");

        for(Expense e : transactions){
            System.out.println(e);
        }

    }

    static void undoTransaction(){

        if(undoStack.isEmpty()){
            System.out.println("Nothing to undo");
            return;
        }

        Expense last = undoStack.pop();
        transactions.remove(last);

        System.out.println("Last transaction removed");
    }

    static void processQueue(){

        if(processQueue.isEmpty()){
            System.out.println("Queue Empty");
            return;
        }

        Expense e = processQueue.poll();

        System.out.println("Processing Transaction:");
        System.out.println(e);
    }

    public static void main(String[] args){

        while(true){

            System.out.println("\n===== Automated Expense Splitting System =====");
            System.out.println("1 Add Expense");
            System.out.println("2 Show Transactions");
            System.out.println("3 Search Expense");
            System.out.println("4 Sort Expenses");
            System.out.println("5 Undo Last Transaction");
            System.out.println("6 Process Queue");
            System.out.println("7 Exit");

            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch(ch){

                case 1:
                    addExpense();
                    break;

                case 2:
                    showTransactions();
                    break;

                case 3:
                    searchExpense();
                    break;

                case 4:
                    sortExpenses();
                    break;

                case 5:
                    undoTransaction();
                    break;

                case 6:
                    processQueue();
                    break;

                case 7:
                    System.out.println("Program Ended");
                    return;

                default:
                    System.out.println("Invalid Choice");

            }

        }

    }
}
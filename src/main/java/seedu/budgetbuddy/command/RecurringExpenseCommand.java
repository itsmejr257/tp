package seedu.budgetbuddy.command;

import seedu.budgetbuddy.RecurringExpensesList;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.ArrayList;
import java.util.Arrays;

public class RecurringExpenseCommand extends Command{
    public static ArrayList<String> commandTypes = new ArrayList<>(Arrays.asList("newlist",
            "removelist", "rename", "viewlists", "removelist"));
    private RecurringExpensesList expensesList;
    private String initialListName;
    private String newListName;
    private String commandType;
    private int listNumber;

    public RecurringExpenseCommand(String initialListName, String newListName, String commandType) {
        this.initialListName = initialListName;
        this.newListName = newListName;
        this.commandType = commandType;
    }

    public RecurringExpenseCommand(String commandType, RecurringExpensesList expensesList) {
        this.commandType = commandType;
        this.expensesList = expensesList;
    }

    public RecurringExpenseCommand(String initialListName, String commandType,
                                   RecurringExpensesList expensesList) {
        this.initialListName = initialListName;
        this.commandType = commandType;
        this.expensesList = expensesList;
    }

    public RecurringExpenseCommand(int listNumber, String commandType,
                                   RecurringExpensesList expensesList) {
        this.listNumber = listNumber;
        this.commandType = commandType;
        this.expensesList = expensesList;
    }

    public void addNewList(String listName) {
        expensesList.addNewRecurringList(listName);
    }

    public void removeList() {

        if (listNumber == 0 || listNumber > expensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + expensesList.getSize());
            return;
        }

        expensesList.removeList(listNumber);
    }

    public void printList() {
        expensesList.printAllRecurringLists();
    }
    public void execute(){

        switch(commandType) {
        case "newlist":
            addNewList(initialListName);
            break;

        case "viewlists":
            printList();
            break;

        case "removelist":
            removeList();
        default:
            break;
        }
    }

}

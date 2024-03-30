package seedu.budgetbuddy.command;

import seedu.budgetbuddy.Expense;
import seedu.budgetbuddy.ExpenseList;
import seedu.budgetbuddy.RecurringExpensesList;
import seedu.budgetbuddy.Ui;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represent a command to manage recurring expenses
 */
public class RecurringExpenseCommand extends Command{
    public static ArrayList<String> commandTypes = new ArrayList<>(Arrays.asList("newlist",
            "removelist", "rename", "viewlists", "newexpense", "addrec", "viewexpenses"));

    private RecurringExpensesList recurringExpensesList;

    private ExpenseList overallExpenses;
    private String initialListName;
    private String commandType;
    private int listNumber;

    private String category;
    private Double amount;
    private String description;

    private Ui ui = new Ui();


    /**
     * Constructs a RecurringExpenseCommand for operations that only require the recurringExpensesList.
     * This constructor is used when the commandType is `viewlists`
     *
     * @param recurringExpensesList The overall recurringExpensesList containing the list of ExpenseList
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(RecurringExpensesList recurringExpensesList, String commandType) {
        this.commandType = commandType;
        this.recurringExpensesList = recurringExpensesList;
    }

    /**
     * Constructs a RecurringExpenseCommand for operations that require the name of the ExpenseList
     * , the recurringExpensesList
     * This constructor is used when the commandType is `newlist`
     *
     * @param initialListName The name of the new RecurringExpenseList to create
     * @param recurringExpensesList The overall recurringExpensesList containing the list of ExpenseList
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(String initialListName,
                                   RecurringExpensesList recurringExpensesList, String commandType) {
        this.initialListName = initialListName;
        this.commandType = commandType;
        this.recurringExpensesList = recurringExpensesList;
    }

    /**
     * Constructs a RecurringExpenseCommand for operations that require listNumber, recurringExpensesList
     * This constructor is used when the commandType is either `viewexpenses` or `removelist`
     *
     * @param listNumber The listNumber associated to the listName printed during a `viewlists` command
     * @param recurringExpensesList The overall recurringExpensesList containing the list of ExpenseList
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(int listNumber,
                                   RecurringExpensesList recurringExpensesList, String commandType) {
        this.listNumber = listNumber;
        this.commandType = commandType;
        this.recurringExpensesList = recurringExpensesList;
    }

    /**
     * Constructs a RecurringExpenseCommand for operations that require listNumber, recurringExpensesList
     * , overallExpenses.
     * This constructor is used when the commandType is `addrec`
     *
     * @param listNumber The listNumber associated to the listName printed during a `viewlists` command
     * @param recurringExpensesList The overall recurringExpensesList containing the list of ExpenseList
     * @param overallExpenses The overall ExpenseList containing all the user's expenses
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(int listNumber, RecurringExpensesList recurringExpensesList,
                                   ExpenseList overallExpenses, String commandType) {

        this.recurringExpensesList = recurringExpensesList;
        this.overallExpenses = overallExpenses;
        this.listNumber = listNumber;
        this.commandType = commandType;
    }

    /**
     * Constructs a RecurringExpenseCommand for operations that require listNumber, recurringExpensesList,
     * category, amount, description.
     * This constructor is used when the commandType is `newexpense`
     *
     * @param listNumber The listNumber associated to the listName printed during a `viewlists` command
     * @param recurringExpensesList The overall recurringExpensesList containing the list of ExpenseList
     * @param category The category of the new expense user wishes to add
     * @param amount The amount of the new expense user wishes to add
     * @param description The description of the new expense user wishes to add
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(int listNumber, RecurringExpensesList recurringExpensesList, String category,
                                   Double amount, String description, String commandType) {

        this.recurringExpensesList = recurringExpensesList;
        this.listNumber = listNumber;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.commandType = commandType;
    }


    /**
     * Adds a new list with the name `listName` to the recurringExpensesList
     *
     * @param listName The name of the new list
     */
    public void addNewList(String listName) {
        recurringExpensesList.addNewRecurringList(listName);
    }

    /**
     * Removes the ExpenseList located at listNumber in the overall recurringExpensesList
     *
     * @param listNumber The list position of the ExpenseList to remove
     */
    public void removeList(int listNumber) {

        if (listNumber == 0 || listNumber > recurringExpensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpensesList.getSize());
            return;
        }

        recurringExpensesList.removeList(listNumber);
    }


    /**
     * Adds an Expense with the provided category, amount and description to the ExpenseList located
     * at the provided listNumber in the overall recurringExpensesList
     *
     * @param listNumber The list position of the ExpenseList to add the Expense in
     * @param category The category of the Expense to add
     * @param amount The amount of the Expense to add
     * @param description The description of the Expense to add
     */
    public void addExpenseToList(int listNumber, String category, Double amount, String description) {

        if (listNumber <= 0 || listNumber > recurringExpensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpensesList.getSize());
            return;
        }

        ExpenseList expenses = recurringExpensesList.getExpenseListAtListNumber(listNumber);

        try {
            expenses.addExpense(category, amount.toString(), description);

            ui.printDivider();
            System.out.println("Successfully Added Expense to " + expenses.getName());
            ui.printDivider();

        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Adds all Expenses in the ExpenseList located at the provided listNumber in the `recurringExpensesList`,
     * into the provided `overallExpenses`
     *
     * @param listNumber The list position of the ExpenseList in recurringExpensesList
     * @param recurringExpensesList The overall recurringExpensesList
     * @param overallExpenses The overall expenses
     */
    public void addRecurringExpensesToExpenses(int listNumber
            , RecurringExpensesList recurringExpensesList, ExpenseList overallExpenses) {

        if (listNumber <= 0 || listNumber > recurringExpensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpensesList.getSize());
            return;
        }

        ExpenseList expenseList = recurringExpensesList.getExpenseListAtListNumber(listNumber);
        ArrayList<Expense> expenses = expenseList.getExpenses();

        for (Expense expense : expenses) {
            String category = expense.getCategory();
            Double amount = expense.getAmount();
            String description = expense.getDescription();

            Command addExpenseCommand = new AddExpenseCommand(overallExpenses, category,
                    amount.toString(), description);

            addExpenseCommand.execute();
        }

        ui.printDivider();
        System.out.println("You Recurring Expenses in " + expenseList.getName() +
                "has been added to your overall Expenses");

        ui.printDivider();

    }

    /**
     * Prints all expenses in the ExpenseList located at the provided `listNumber` in the overall
     * `recurringExpensesList`
     *
     * @param listNumber The list position of the ExpenseList in recurringExpensesList
     * @param recurringExpensesList The recurringExpensesList to obtain ExpenseList from
     */
    public void printExpensesAtIndex(int listNumber, RecurringExpensesList recurringExpensesList) {

        if (listNumber <= 0 || listNumber > recurringExpensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpensesList.getSize());
            return;
        }

        ExpenseList expenseList = recurringExpensesList.getExpenseListAtListNumber(listNumber);

        expenseList.listExpenses(null);
    }

    /**
     * Prints the names of all ExpenseList in the recurringExpensesList
     */
    public void printList() {
        recurringExpensesList.printAllRecurringLists();
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
            removeList(this.listNumber);
            break;

        case "newexpense":
            addExpenseToList(listNumber, category, amount, description);
            break;
        case "addrec":
            addRecurringExpensesToExpenses(listNumber, recurringExpensesList, overallExpenses);
            break;

        case "viewexpenses":
            printExpensesAtIndex(listNumber, recurringExpensesList);
            break;

        default:
            break;
        }
    }

}

package seedu.budgetbuddy.command;

import seedu.budgetbuddy.Expense;
import seedu.budgetbuddy.ExpenseList;
import seedu.budgetbuddy.Ui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FindExpensesCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(FindExpensesCommand.class.getName());
    private ExpenseList expenses;
    private String description;
    private Double minAmount;
    private Double maxAmount;
    private Ui ui;

    /**
     * Constructs a FindExpensesCommand with the specified expenses, description, minAmount and maxAmount.
     * Also instantiates an Ui class to print dividers.
     *
     * @param expenses The ExpenseList to filter
     * @param description The description to filter
     * @param minAmount The minimum amount to filter
     * @param maxAmount The maximum amount to filter
     */
    public FindExpensesCommand(ExpenseList expenses, String description, Double minAmount, Double maxAmount) {
        if (minAmount != null && maxAmount != null) {
            assert minAmount <= maxAmount : "Minimum amount cannot be larger than Maximum Amount";
        }

        ui = new Ui();
        this.expenses = expenses;

        if(description == null) {
            this.description = "";
        } else {
            this.description = description;
        }
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    /**
     * Prints the initialization message detailing the search criteria.
     * This method outlines the parameters the search will use to find matching expenses
     *
     */
    public void printInitializationMessage() {
        ui.printDivider();
        System.out.println("Looking for Expenses with the following parameters : ");

        System.out.println("Description : ");
        if (description == null) {
            System.out.println("N.A");
        } else {
            System.out.println(description);
        }

        System.out.println("Minimum Amount : ");
        if (minAmount == null) {
            System.out.println("N.A");
        } else {
            System.out.println(minAmount);
        }

        System.out.println("Maximum Amount : ");
        if (maxAmount == null) {
            System.out.println("N.A");
        } else {
            System.out.println(maxAmount);
        }
    }

    /**
     * Executes the find expenses command. It obtains an ArrayList containing all the filtered expenses
     * and prints the obtained filtered expenses to the CLI. If the filtered expenses is empty, it prints a
     * message stating no matching expenses were found.
     *
     */
    @Override
    public void execute() {

        LOGGER.log(Level.INFO, "Start processing of Find Command");

        if (minAmount != null && maxAmount != null) {
            assert minAmount <= maxAmount : "Minimum amount cannot be larger than Maximum Amount";
        }

        LOGGER.log(Level.INFO, "Creating filteredExpenses");

        printInitializationMessage();
        ArrayList<Expense> filteredExpenses = expenses.filterExpenses(description, minAmount, maxAmount);
        ExpenseList filteredExpenseList = new ExpenseList(filteredExpenses);

        if (filteredExpenses.isEmpty()) {
            LOGGER.log(Level.INFO, "filtered expenses is empty, returning no expenses found");

            ui.printDivider();
            System.out.println("No matching expenses found.");
            ui.printDivider();
        } else {
            LOGGER.log(Level.INFO, "Filtered expenses contains items, returning matching expenses");

            ui.printDivider();
            System.out.println("Here are the matching expenses : ");
            filteredExpenseList.listExpenses(null);
            ui.printDivider();
        }
    }
}

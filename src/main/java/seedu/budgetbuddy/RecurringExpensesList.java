package seedu.budgetbuddy;

import java.util.ArrayList;

/**
 * Represents a list of ExpenseList. Each ExpenseList contains multiple expenses.
 * This class provides methods to add, remove and manage the list of ExpenseList
 *
 */
public class RecurringExpensesList {
    protected ArrayList<ExpenseList> recurringExpensesList;

    Ui ui = new Ui();

    /**
     * Constructs an RecurringExpensesList object with an empty ArrayList
     */
    public RecurringExpensesList() {
        this.recurringExpensesList = new ArrayList<>();
    }

    /**
     * Adds a new RecurringExpenseList with the provided listName to the list of recurring expenses
     *
     * @param listName Name of the RecurringExpenseList to be added
     */
    public void addNewRecurringList(String listName) {
        ExpenseList expenses = new RecurringExpenseList(listName, new ArrayList<>());

        recurringExpensesList.add(expenses);

    }

    /**
     * Removes the ExpenseList at the provided listNumber in the list of recurring expenses
     *
     * @param listNumber Position of list to delete according to the provided list of recurring expenses printed to user
     */
    public void removeList(int listNumber) {
        int listNumberAsArrayPosition = listNumber - 1;
        recurringExpensesList.remove(listNumberAsArrayPosition);

        ui.printDivider();
        System.out.println("List Successfully Removed");
        ui.printDivider();

    }

    /**
     * Prints the names of all ExpenseList within the main list of recurring expenses. If there are
     * no ExpenseList inside recurringExpensesList, a message indicating the absence of recurring expenses
     * is displayed
     */
    public void printAllRecurringLists() {

        int counter = 1;

        if (recurringExpensesList.isEmpty()) {
            ui.printDivider();
            System.out.println("You currently have no Recurring Expenses");
            ui.printDivider();
            return;
        }

        ui.printDivider();
        System.out.println("These are your lists of Recurring Expenses");

        for (ExpenseList expenses : recurringExpensesList) {
            String listName = expenses.getName();
            System.out.println(counter + ". " + listName);
            counter += 1;
        }

        ui.printDivider();
    }

    /**
     * Returns the number of ExpenseList present in recurringExpensesList
     *
     * @return The size of recurringExpensesList
     */
    public int getSize() {
        return recurringExpensesList.size();
    }

    /**
     * Returns the ExpenseList at the specified position.
     * Position of the ExpenseList is 1-based, so position is subtracted by 1 to convert to
     * ArrayList index
     *
     * @param listNumber Position of ExpenseList according to the printed list to the user
     * @return The ExpenseList at the listNumber provided
     */
    public ExpenseList getExpenseListAtListNumber(int listNumber) {

        int listNumberAsArrayPosition = listNumber - 1;
        return recurringExpensesList.get(listNumberAsArrayPosition);
    }

}

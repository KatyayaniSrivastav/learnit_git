package javacore.bakingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingSystem {

	public static void main(String[] args) {
		 Bank bank = new Bank();
	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            System.out.println("\n--- Banking System Menu ---");
	            System.out.println("1. Create Account");
	            System.out.println("2. Deposit Money");
	            System.out.println("3. Withdraw Money");
	            System.out.println("4. Transfer Money");
	            System.out.println("5. View Account Details");
	            System.out.println("6. Exit");
	            System.out.print("Enter your choice: ");

	            int choice = scanner.nextInt();
	            scanner.nextLine(); // Consume newline

	            switch (choice) {
	                case 1:
	                    System.out.print("Enter account holder's name: ");
	                    String name = scanner.nextLine();
	                    System.out.print("Enter initial deposit amount: ");
	                    double initialDeposit = scanner.nextDouble();
	                    Account newAccount = bank.createAccount(name, initialDeposit);
	                    System.out.println("Account created successfully! Account Number: " + newAccount.getAccountNumber());
	                    break;

	                case 2:
	                    System.out.print("Enter account number: ");
	                    int depositAccountNumber = scanner.nextInt();
	                    System.out.print("Enter deposit amount: ");
	                    double depositAmount = scanner.nextDouble();
	                    bank.depositMoney(depositAccountNumber, depositAmount);
	                    break;

	                case 3:
	                    System.out.print("Enter account number: ");
	                    int withdrawAccountNumber = scanner.nextInt();
	                    System.out.print("Enter withdrawal amount: ");
	                    double withdrawAmount = scanner.nextDouble();
	                    bank.withdrawMoney(withdrawAccountNumber, withdrawAmount);
	                    break;

	                case 4:
	                    System.out.print("Enter sender account number: ");
	                    int senderAccountNumber = scanner.nextInt();
	                    System.out.print("Enter receiver account number: ");
	                    int receiverAccountNumber = scanner.nextInt();
	                    System.out.print("Enter transfer amount: ");
	                    double transferAmount = scanner.nextDouble();
	                    bank.transferMoney(senderAccountNumber, receiverAccountNumber, transferAmount);
	                    break;

	                case 5:
	                    System.out.print("Enter account number: ");
	                    int viewAccountNumber = scanner.nextInt();
	                    Account account = bank.getAccount(viewAccountNumber);
	                    if (account != null) {
	                        System.out.println(account);
	                    } else {
	                        System.out.println("Account not found.");
	                    }
	                    break;

	                case 6:
	                    System.out.println("Exiting Banking System. Goodbye!");
	                    scanner.close();
	                    System.exit(0);
	                    break;

	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}

	// Account Class
	class Account {
	    private static int accountCounter = 1000;
	    private int accountNumber;
	    private String accountHolderName;
	    private double balance;

	    public Account(String accountHolderName, double initialDeposit) {
	        this.accountNumber = accountCounter++;
	        this.accountHolderName = accountHolderName;
	        this.balance = initialDeposit;
	    }

	    public int getAccountNumber() {
	        return accountNumber;
	    }

	    public String getAccountHolderName() {
	        return accountHolderName;
	    }

	    public double getBalance() {
	        return balance;
	    }

	    public void deposit(double amount) {
	        if (amount <= 0) {
	            throw new IllegalArgumentException("Deposit amount must be positive.");
	        }
	        balance += amount;
	    }

	    public void withdraw(double amount) {
	        if (amount > balance) {
	            throw new NotEnoughMoneyException("Insufficient balance.");
	        }
	        balance -= amount;
	    }

	    @Override
	    public String toString() {
	        return "Account Number: " + accountNumber + ", Account Holder: " + accountHolderName + ", Balance: " + balance;
	    }
	}

	// Bank Class
	class Bank {
	    private List<Account> accounts;

	    public Bank() {
	        accounts = new ArrayList<>();
	    }

	    public Account createAccount(String accountHolderName, double initialDeposit) {
	        Account account = new Account(accountHolderName, initialDeposit);
	        accounts.add(account);
	        return account;
	    }

	    public void depositMoney(int accountNumber, double amount) {
	        Account account = getAccount(accountNumber);
	        if (account != null) {
	            account.deposit(amount);
	            System.out.println("Deposit successful. New balance: " + account.getBalance());
	        } else {
	            System.out.println("Account not found.");
	        }
	    }

	    public void withdrawMoney(int accountNumber, double amount) {
	        Account account = getAccount(accountNumber);
	        if (account != null) {
	            try {
	                account.withdraw(amount);
	                System.out.println("Withdrawal successful. New balance: " + account.getBalance());
	            } catch (NotEnoughMoneyException e) {
	                System.out.println(e.getMessage());
	            }
	        } else {
	            System.out.println("Account not found.");
	        }
	    }

	    public void transferMoney(int fromAccountNumber, int toAccountNumber, double amount) {
	        Account fromAccount = getAccount(fromAccountNumber);
	        Account toAccount = getAccount(toAccountNumber);

	        if (fromAccount == null || toAccount == null) {
	            System.out.println("One or both accounts not found.");
	            return;
	        }

	        try {
	            fromAccount.withdraw(amount);
	            toAccount.deposit(amount);
	            System.out.println("Transfer successful. New balance for sender: " + fromAccount.getBalance());
	        } catch (NotEnoughMoneyException e) {
	            System.out.println(e.getMessage());
	        }
	    }

	    public Account getAccount(int accountNumber) {
	        for (Account account : accounts) {
	            if (account.getAccountNumber() == accountNumber) {
	                return account;
	            }
	        }
	        return null;
	    }
	}
	
	class NotEnoughMoneyException extends RuntimeException {
	    public NotEnoughMoneyException(String message) {
	        super(message);

	}

}

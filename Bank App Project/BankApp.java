import javax.swing.*;
import java.awt.*;

public class BankApp extends JFrame {

    JLabel titleLabel, sloganLabel, logoLabel;
    JButton loginBtn, exitBtn;
    JButton depositBtn, withdrawBtn, historyBtn, balanceBtn;
    JButton openAccountBtn, closeAccountBtn, loanRequestBtn, accountDetailsBtn;

    public BankApp() {
        super("Tara Bank Ltd");

        this.setSize(1000, 1250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center on screen

        // Load background image
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/tanjila anam/Desktop/Image/background.jpg");

        // Create a JLabel to hold the background image
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 4000, 3000);
        backgroundLabel.setLayout(null); // Important: to manually place components

        int frameWidth = 600;
        int centerX = frameWidth / 2;

        // Load and set the bank logo
        ImageIcon icon = new ImageIcon("C:/Users/tanjila anam/Desktop/Image/Tara_Bank_Logo_01.png");
        logoLabel = new JLabel(icon);
        logoLabel.setBounds(centerX - 50, 20, 100, 100);
        backgroundLabel.add(logoLabel);

        // Exit and Login buttons
        exitBtn = new JButton("Exit");
        exitBtn.setBounds(centerX - 110, 60, 100, 30);
        backgroundLabel.add(exitBtn);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(centerX + 10, 60, 100, 30);
        backgroundLabel.add(loginBtn);

        // Title
        titleLabel = new JLabel("Welcome to Tara Bank Ltd", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setBounds(centerX - 200, 130, 400, 30);
        titleLabel.setForeground(new Color(0, 102, 204));
        backgroundLabel.add(titleLabel);

        // Slogan
        sloganLabel = new JLabel("\"Your Trust, Our Future\"", JLabel.CENTER);
        sloganLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        sloganLabel.setBounds(centerX - 150, 160, 300, 25);
        sloganLabel.setForeground(new Color(0, 102, 102));
        backgroundLabel.add(sloganLabel);

        // Buttons
        depositBtn = new JButton("Deposit");
        depositBtn.setBounds(centerX - 170, 210, 160, 30);
        backgroundLabel.add(depositBtn);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(centerX + 10, 210, 160, 30);
        backgroundLabel.add(withdrawBtn);

        historyBtn = new JButton("Transaction History");
        historyBtn.setBounds(centerX - 130, 250, 260, 30);
        backgroundLabel.add(historyBtn);

        balanceBtn = new JButton("Balance");
        balanceBtn.setBounds(centerX - 170, 290, 160, 30);
        backgroundLabel.add(balanceBtn);

        openAccountBtn = new JButton("Open Account");
        openAccountBtn.setBounds(centerX + 10, 290, 160, 30);
        backgroundLabel.add(openAccountBtn);

        accountDetailsBtn = new JButton("Account Details");
        accountDetailsBtn.setBounds(centerX - 130, 330, 260, 30);
        backgroundLabel.add(accountDetailsBtn);

        closeAccountBtn = new JButton("Close Account");
        closeAccountBtn.setBounds(centerX - 170, 370, 160, 30);
        backgroundLabel.add(closeAccountBtn);

        loanRequestBtn = new JButton("Loan Request");
        loanRequestBtn.setBounds(centerX + 10, 370, 160, 30);
        backgroundLabel.add(loanRequestBtn);

        // Add actions
        exitBtn.addActionListener(e -> System.exit(0));

        depositBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    if (amount > 0) {
                        JOptionPane.showMessageDialog(this, "Deposited: $" + amount);
                    } else {
                        JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.");
                }
            }
        });

        withdrawBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    if (amount > 0) {
                        JOptionPane.showMessageDialog(this, "Withdrawn: $" + amount);
                    } else {
                        JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.");
                }
            }
        });

        historyBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Showing recent transaction history...");
        });

        balanceBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Current balance: $10,000");
        });

        openAccountBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Opening new account...");
        });

        closeAccountBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Closing account...");
        });

        loanRequestBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Processing loan request...");
        });

        accountDetailsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fetching account details...");
        });

        // Add the backgroundLabel (with all components on top) to the JFrame
        this.setContentPane(backgroundLabel);
    }

    public static void main(String[] args) {
        BankApp b = new BankApp();
        b.setVisible(true);
    }
}

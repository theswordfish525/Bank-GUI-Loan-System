import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Bank extends JFrame implements MouseListener {
    JLabel titleLabel, sloganLabel, logoLabel, welcomeUserLabel;
    JButton loginBtn, exitBtn, applyLoanBtn, viewLoanStatusBtn,
            repayLoanBtn, repaymentScheduleBtn, topUpBtn, calculateLoanBtn;
    JLabel backgroundLabel;
    JTextArea loanCalcBox, loanStatusBox, repaymentScheduleBox;
    JTextField loanTakenField, loanPaidField;

    Random rand = new Random();
    int totalLoanTaken = 0;
    int totalLoanPaid = 0;
    double interestRate = 0;
    LocalDate repaymentStartDate = LocalDate.of(2025, 6, 15);
    int numberOfMonths = 4;

    private FileWriter writer;
    private boolean fileOpened = false;

    public Bank() {
        super("CrownStone Bank");
        this.setSize(960, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        Image originalBackground = new ImageIcon("C:/Users/USER/OneDrive/Desktop/Images/BGLogo.jpg").getImage();
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel.setLayout(null);
        this.setContentPane(backgroundLabel);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = getWidth();
                int h = getHeight();
                Image scaledImage = originalBackground.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(scaledImage));
                backgroundLabel.setBounds(0, 0, w, h);
            }
        });

        setupUI();
        setupFileWriter();
    }

    private void setupUI() {
        int centerX = getWidth() / 2;
        int y = 30;

        welcomeUserLabel = new JLabel("Welcome Tahlil Al-Raiyan");
        welcomeUserLabel.setBounds(20, 30, 300, 25);
        welcomeUserLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        welcomeUserLabel.setForeground(Color.RED);
        backgroundLabel.add(welcomeUserLabel);

        ImageIcon originalIcon = new ImageIcon("C:/Users/USER/OneDrive/Desktop/Images/CrownStoneLogo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoLabel.setBounds(centerX - 40, y, 80, 80);
        backgroundLabel.add(logoLabel);

        titleLabel = new JLabel("Welcome to CrownStone Bank", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBounds(centerX - 200, y + 90, 400, 30);
        titleLabel.setForeground(new Color(0, 255, 255));
        backgroundLabel.add(titleLabel);

        sloganLabel = new JLabel("\"Your Trust, Our Future\"", JLabel.CENTER);
        sloganLabel.setFont(new Font("Serif", Font.BOLD, 16));
        sloganLabel.setBounds(centerX - 180, y + 120, 360, 25);
        sloganLabel.setForeground(new Color(0, 255, 255));
        backgroundLabel.add(sloganLabel);

        JLabel userTypeLabel = new JLabel("Enter your type:");
        userTypeLabel.setBounds(20, 200, 150, 20);
        userTypeLabel.setForeground(Color.RED);
        backgroundLabel.add(userTypeLabel);

        String[] roles = { "Employee", "New User", "User", "Manager", "Admin", "Other" };
        ButtonGroup group = new ButtonGroup();
        int radioY = 230;
        for (String role : roles) {
            JRadioButton rb = new JRadioButton(role);
            rb.setBounds(20, radioY, 150, 20);
            rb.setOpaque(false);
            rb.setForeground(Color.GREEN);
            group.add(rb);
            backgroundLabel.add(rb);
            radioY += 25;
        }

        loginBtn = createButton("LogOut", centerX - 160, 200, 120, 30);
        exitBtn = createButton("Exit", centerX + 40, 200, 120, 30);
        applyLoanBtn = createButton("Apply For Loan", centerX - 80, 240, 160, 30);

        viewLoanStatusBtn = createButton("View Loan Status", centerX - 160, 290, 330, 30);
        repayLoanBtn = createButton("Repay Loan", centerX - 160, 330, 160, 30);
        repaymentScheduleBtn = createButton("Repayment Schedule", centerX + 10, 330, 160, 30);
        topUpBtn = createButton("Request Loan Top-Up", centerX - 160, 370, 330, 30);

        loanTakenField = new JTextField();
        loanTakenField.setBounds(centerX - 160, 420, 160, 40);
        loanTakenField.setBorder(BorderFactory.createTitledBorder("Loan Taken"));
        backgroundLabel.add(loanTakenField);

        loanPaidField = new JTextField();
        loanPaidField.setBounds(centerX + 10, 420, 160, 40);
        loanPaidField.setBorder(BorderFactory.createTitledBorder("Loan Paid"));
        backgroundLabel.add(loanPaidField);

        calculateLoanBtn = createButton("Calculate Loan", centerX - 80, 470, 160, 30);
        calculateLoanBtn.addActionListener(e -> {
            String takenText = loanTakenField.getText().trim();
            String paidText = loanPaidField.getText().trim();
            if (takenText.isEmpty() || paidText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Loan Taken and Loan Paid values.");
                return;
            }
            try {
                totalLoanTaken = Integer.parseInt(takenText);
                totalLoanPaid = Integer.parseInt(paidText);
                interestRate = 5 + rand.nextDouble() * 5;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numeric values!");
                return;
            }
            updateLoanCalculatorBox();
        });

        loanCalcBox = createTextArea("   Loan calculator result will appear here.", 700, 130, 240, 100, new Color(255, 250, 205));
        loanStatusBox = createTextArea("   Loan status will be shown here.", 700, 240, 240, 90, new Color(204, 229, 255));
        repaymentScheduleBox = createTextArea("   Repayment schedule will appear here.", 700, 340, 240, 110, new Color(240, 255, 240));

        exitBtn.addActionListener(e -> System.exit(0));
        applyLoanBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Redirecting to loan application form..."));

        viewLoanStatusBtn.addActionListener(e -> {
            String takenText = loanTakenField.getText().trim();
            String paidText = loanPaidField.getText().trim();
            if (takenText.isEmpty() || paidText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Loan Taken and Loan Paid values.");
                return;
            }
            try {
                totalLoanTaken = Integer.parseInt(takenText);
                totalLoanPaid = Integer.parseInt(paidText);
                interestRate = 5 + rand.nextDouble() * 5;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numeric values!");
                return;
            }

            double interestAmount = totalLoanTaken * (interestRate / 100.0);
            double totalRepayment = totalLoanTaken + interestAmount;
            int monthlyInstallment = (int) Math.ceil(totalRepayment / numberOfMonths);

            String status;
            if (totalLoanPaid >= totalRepayment) {
                status = "Completed";
            } else if (totalLoanPaid == 0) {
                status = "Pending";
            } else {
                status = "Active";
            }

            int paidInstallments = totalLoanPaid / monthlyInstallment;
            LocalDate nextDueDate = repaymentStartDate.plusMonths(paidInstallments);

            loanStatusBox.setText(String.format(
                "Loan Status: %s\nTotal Loan Left: BDT %,d\nNext payment due: %s",
                status, (int)(totalRepayment - totalLoanPaid),
                nextDueDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
            ));

            updateLoanCalculatorBox();
        });

        repaymentScheduleBtn.addActionListener(e -> {
            if (totalLoanTaken == 0 || interestRate == 0) {
                JOptionPane.showMessageDialog(this, "Please calculate or view loan status first.");
                return;
            }

            double interestAmount = totalLoanTaken * (interestRate / 100.0);
            double totalRepayment = totalLoanTaken + interestAmount;

            int baseInstallment = (int) (totalRepayment / numberOfMonths);
            int remainder = (int)Math.round(totalRepayment - baseInstallment * numberOfMonths);

            StringBuilder schedule = new StringBuilder("Repayment Schedule:\n");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

            for (int i = 0; i < numberOfMonths; i++) {
                LocalDate dueDate = repaymentStartDate.plusMonths(i);
                int thisInstallment = baseInstallment;
                if (i == numberOfMonths - 1) {
                    thisInstallment += remainder;
                }

                schedule.append("Installment ").append(i + 1)
                        .append(": ").append(dueDate.format(formatter))
                        .append(" - BDT ").append(String.format("%,d", thisInstallment)).append("\n");
            }
            repaymentScheduleBox.setText(schedule.toString());
        });

        repayLoanBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Processing loan repayment..."));
        topUpBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Requesting loan top-up..."));
    }

    private void updateLoanCalculatorBox() {
        double interestAmount = totalLoanTaken * (interestRate / 100.0);
        double totalRepayment = totalLoanTaken + interestAmount;
        double totalLoanLeft = totalRepayment - totalLoanPaid;

        String result = String.format(
            "Loan Calculator:\n" +
            "Total Loan Taken: BDT %,d\n" +
            "Total Loan Paid: BDT %,d\n" +
            "Total Loan Left (with interest): BDT %,.2f\n" +
            "Interest Rate: %.2f%%",
            totalLoanTaken, totalLoanPaid, totalLoanLeft, interestRate
        );

        loanCalcBox.setText(result);

        String log = String.format(
            "Loan Calculation - %s\nTaken: BDT %,d, Paid: BDT %,d, Left: BDT %,.2f, Interest: %.2f%%",
            LocalDate.now(), totalLoanTaken, totalLoanPaid, totalLoanLeft, interestRate
        );
        writeToFile(log);
    }

    private void setupFileWriter() {
        try {
            File file = new File("calculation_history.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String content) {
        try {
            if (writer != null) {
                writer.write(content + "\n");
                writer.flush();

                if (!fileOpened) {
                    Desktop.getDesktop().open(new File("calculation_history.txt"));
                    fileOpened = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, width, height);
        btn.setBackground(Color.ORANGE);
        btn.addMouseListener(this);
        backgroundLabel.add(btn);
        return btn;
    }

    private JTextArea createTextArea(String text, int x, int y, int width, int height, Color bg) {
        JTextArea ta = new JTextArea(text);
        ta.setBounds(x, y, width, height);
        ta.setBackground(bg);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(new Font("SansSerif", Font.PLAIN, 12));
        ta.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        ta.setEditable(false);
        backgroundLabel.add(ta);
        return ta;
    }

    @Override public void mouseClicked(MouseEvent me) { if (me.getSource() instanceof JButton) ((JButton) me.getSource()).setBackground(Color.RED); }
    @Override public void mousePressed(MouseEvent me) { if (me.getSource() instanceof JButton) ((JButton) me.getSource()).setBackground(Color.GREEN); }
    @Override public void mouseReleased(MouseEvent me) { if (me.getSource() instanceof JButton) ((JButton) me.getSource()).setBackground(Color.BLUE); }
    @Override public void mouseEntered(MouseEvent me) { if (me.getSource() instanceof JButton) ((JButton) me.getSource()).setBackground(Color.CYAN); }
    @Override public void mouseExited(MouseEvent me) { if (me.getSource() instanceof JButton) ((JButton) me.getSource()).setBackground(Color.ORANGE); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bank bankFrame = new Bank();
            bankFrame.setVisible(true);
        });
    }
}

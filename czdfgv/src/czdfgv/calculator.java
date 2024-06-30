package czdfgv;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField display;
    private boolean isOn = false;
    private String lastOperator = "";
    private double lastValue = 0;

    public CalculatorApp() {
        // Set up the frame
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set up the display
        display = new JTextField();
        display.setBounds(30, 40, 340, 40);
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 20));
        add(display);

        // Set up radio buttons for ON and OFF
        JRadioButton onButton = new JRadioButton("on");
        JRadioButton offButton = new JRadioButton("off");
        onButton.setBounds(30, 90, 60, 40);
        offButton.setBounds(100, 90, 60, 40);
        ButtonGroup bg = new ButtonGroup();
        bg.add(onButton);
        bg.add(offButton);
        add(onButton);
        add(offButton);

        // Action listeners for ON and OFF buttons
        onButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOn = true;
                enableButtons(true);
            }
        });

        offButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOn = false;
                enableButtons(false);
                display.setText("");
            }
        });

        // Set up panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(30, 140, 340, 400);
        buttonPanel.setLayout(new GridLayout(6, 4, 10, 10)); // 6 rows, 4 columns, 10px padding
        add(buttonPanel);

        // Add buttons to the panel in the correct sequence
        buttonPanel.add(createButton("C", e -> {
            display.setText("");
            lastOperator = "";
            lastValue = 0;
        }));

        buttonPanel.add(createButton("DEL", e -> {
            String text = display.getText();
            if (!text.isEmpty()) {
                display.setText(text.substring(0, text.length() - 1));
            }
        }));

        buttonPanel.add(createButton("%", e -> applyOperator("%")));
        buttonPanel.add(createButton("/", e -> applyOperator("/")));

        buttonPanel.add(createButton("7", e -> appendToDisplay("7")));
        buttonPanel.add(createButton("8", e -> appendToDisplay("8")));
        buttonPanel.add(createButton("9", e -> appendToDisplay("9")));
        buttonPanel.add(createButton("X", e -> applyOperator("X")));

        buttonPanel.add(createButton("4", e -> appendToDisplay("4")));
        buttonPanel.add(createButton("5", e -> appendToDisplay("5")));
        buttonPanel.add(createButton("6", e -> appendToDisplay("6")));
        buttonPanel.add(createButton("-", e -> applyOperator("-")));

        buttonPanel.add(createButton("1", e -> appendToDisplay("1")));
        buttonPanel.add(createButton("2", e -> appendToDisplay("2")));
        buttonPanel.add(createButton("3", e -> appendToDisplay("3")));
        buttonPanel.add(createButton("+", e -> applyOperator("+")));

        buttonPanel.add(createButton("0", e -> appendToDisplay("0")));
        buttonPanel.add(createButton(".", e -> appendToDisplay(".")));
        buttonPanel.add(createButton("√", e -> calculateSquareRoot()));
        buttonPanel.add(createButton("=", e -> calculateResult()));

        // Set background color
        getContentPane().setBackground(Color.ORANGE);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setEnabled(false);
        button.addActionListener(listener);
        return button;
    }

    private void enableButtons(boolean enable) {
        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component button : panel.getComponents()) {
                    button.setEnabled(enable);
                }
            }
        }
    }

    private void appendToDisplay(String text) {
        display.setText(display.getText() + text);
    }

    private void applyOperator(String operator) {
        try {
            lastValue = Double.parseDouble(display.getText());
            lastOperator = operator;
            display.setText("");
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }
    }

    private void calculateResult() {
        try {
            double currentValue = Double.parseDouble(display.getText());
            double result = 0;

            switch (lastOperator) {
                case "/":
                    result = lastValue / currentValue;
                    break;
                case "-":
                    result = lastValue - currentValue;
                    break;
                case "+":
                    result = lastValue + currentValue;
                    break;
                case "X":
                    result = lastValue * currentValue;
                    break;
                case "%":
                    result = lastValue % currentValue;
                    break;
            }

            display.setText(String.valueOf(result));
            lastOperator = "";
            lastValue = 0;
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }
    }

    private void calculateSquareRoot() {
        try {
            double value = Double.parseDouble(display.getText());
            display.setText(String.valueOf(Math.sqrt(value)));
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }
    }

    private void calculateSquare() {
        try {
            double value = Double.parseDouble(display.getText());
            display.setText(String.valueOf(value * value));
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }
    }

    private void calculateInverse() {
        try {
            double value = Double.parseDouble(display.getText());
            display.setText(String.valueOf(1 / value));
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculatorApp().setVisible(true);
            }
        });
    }
}
import ui.FinanceTrackerUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FinanceTrackerUI::new);
    }
}

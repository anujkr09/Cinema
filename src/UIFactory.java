import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public final class UIFactory {
    public static final Color RED = new Color(204, 36, 48);
    public static final Color INK = new Color(26, 31, 39);
    public static final Color GOLD = new Color(232, 178, 80);
    public static final Color SURFACE = new Color(25, 29, 36);
    public static final Color PANEL = new Color(32, 37, 46);
    public static final Color LINE = new Color(62, 69, 82);
    private static final Map<String, ImageIcon> IMAGE_CACHE = new HashMap<>();

    private UIFactory() {
    }

    public static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(RED);
        button.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return button;
    }

    public static JButton secondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(INK);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 220)),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return button;
    }

    public static JLabel heading(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        return label;
    }

    public static JLabel imageLabel(String imagePath, int width, int height) {
        File file = new File(imagePath);
        if (file.exists()) {
            String key = imagePath + "|" + width + "x" + height;
            ImageIcon cached = IMAGE_CACHE.get(key);
            if (cached == null) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                cached = new ImageIcon(scaled);
                IMAGE_CACHE.put(key, cached);
            }
            return new JLabel(cached);
        }
        JLabel fallback = new JLabel("No Image", SwingConstants.CENTER);
        fallback.setOpaque(true);
        fallback.setBackground(new Color(40, 43, 50));
        fallback.setForeground(Color.WHITE);
        fallback.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        return fallback;
    }

    public static JPanel darkPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(SURFACE);
        return panel;
    }
}

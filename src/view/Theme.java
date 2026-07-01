package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Centralized theme constants and styling utilities for the cinema management app.
 * All visual properties should be defined here to ensure consistency.
 */
public final class Theme {

    // ──────────────────────────────────────────────
    // Palette
    // ──────────────────────────────────────────────

    public static final Color BG_PRIMARY    = Color.decode("#121212");
    public static final Color BG_PANEL      = Color.decode("#1E1E1E");
    public static final Color BG_INPUT      = Color.decode("#2A2A2A");
    public static final Color BG_ALT_ROW    = Color.decode("#252525");

    public static final Color PRIMARY       = Color.decode("#D32F2F");   // main red
    public static final Color SECONDARY     = Color.decode("#FFC107");   // amber accent
    public static final Color GREEN         = Color.decode("#2E7D32");   // confirm
    public static final Color RED_INTENSE   = Color.decode("#C62828");   // delete

    public static final Color TEXT_PRIMARY   = Color.decode("#FFFFFF");
    public static final Color TEXT_SECONDARY = Color.decode("#BDBDBD");
    public static final Color TEXT_DISABLED  = Color.decode("#666666");

    public static final Color BORDER         = Color.decode("#333333");
    public static final Color DIVIDER        = Color.decode("#2A2A2A");

    // Butaca map colors
    public static final Color BUTACA_FREE    = Color.decode("#2E7D32");
    public static final Color BUTACA_TAKEN   = Color.decode("#C62828");
    public static final Color BUTACA_SELECTED = Color.decode("#FFC107");

    // ──────────────────────────────────────────────
    // Typography – single family: Segoe UI
    // ──────────────────────────────────────────────

    public static final Font FONT_TITLE      = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SUBTITLE   = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY       = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD  = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_SMALL      = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON     = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_TABLE      = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 13);

    // ──────────────────────────────────────────────
    // Dimensions
    // ──────────────────────────────────────────────

    public static final int BUTTON_HEIGHT    = 42;
    public static final int BUTTON_WIDTH     = 200;
    public static final int INPUT_HEIGHT     = 36;
    public static final int TABLE_ROW_HEIGHT = 32;
    public static final int PANEL_PADDING    = 15;

    private Theme() {
        // utility class
    }

    // ──────────────────────────────────────────────
    // Nimbus UIDefaults installer
    // ──────────────────────────────────────────────

    /** Call ONCE after UIManager.setLookAndFeel("Nimbus") to apply dark palette. */
    public static void installNimbusOverrides() {
        // Base colors Nimbus uses to derive component gradients
        UIManager.put("nimbusBase",               BG_PRIMARY);
        UIManager.put("nimbusBlueGrey",           BG_PANEL);
        UIManager.put("control",                  BG_PANEL);
        UIManager.put("nimbusLightBackground",    BG_INPUT);
        UIManager.put("nimbusSelectionBackground", PRIMARY);
        UIManager.put("nimbusSelectedText",       TEXT_PRIMARY);
        UIManager.put("nimbusDisabledText",       TEXT_DISABLED);
        UIManager.put("nimbusFocus",              PRIMARY);
        UIManager.put("text",                     TEXT_PRIMARY);
        UIManager.put("textHighlight",            PRIMARY);
        UIManager.put("textHighlightText",        TEXT_PRIMARY);

        // Component-level overrides
        UIManager.put("Panel.background",          BG_PRIMARY);
        UIManager.put("Label.foreground",          TEXT_PRIMARY);

        UIManager.put("Button.background",         PRIMARY);
        UIManager.put("Button.foreground",         TEXT_PRIMARY);
        UIManager.put("Button.font",               FONT_BUTTON);
        UIManager.put("Button.contentAreaFilled",  Boolean.TRUE);

        UIManager.put("TextField.background",      BG_INPUT);
        UIManager.put("TextField.foreground",      TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground", TEXT_PRIMARY);
        UIManager.put("TextField.font",            FONT_BODY);

        UIManager.put("FormattedTextField.background", BG_INPUT);
        UIManager.put("FormattedTextField.foreground", TEXT_PRIMARY);

        UIManager.put("TextArea.background",       BG_INPUT);
        UIManager.put("TextArea.foreground",       TEXT_PRIMARY);

        UIManager.put("ComboBox.background",       BG_INPUT);
        UIManager.put("ComboBox.foreground",       TEXT_PRIMARY);
        UIManager.put("ComboBox.selectionBackground", PRIMARY);
        UIManager.put("ComboBox.selectionForeground", TEXT_PRIMARY);
        UIManager.put("ComboBox.font",             FONT_BODY);

        UIManager.put("Table.background",          BG_PANEL);
        UIManager.put("Table.foreground",          TEXT_PRIMARY);
        UIManager.put("Table.selectionBackground", PRIMARY);
        UIManager.put("Table.selectionForeground", TEXT_PRIMARY);
        UIManager.put("Table.font",                FONT_TABLE);
        UIManager.put("Table.rowHeight",           TABLE_ROW_HEIGHT);
        UIManager.put("Table.gridColor",           DIVIDER);
        UIManager.put("Table.alternateRowColor",   BG_ALT_ROW);

        UIManager.put("TableHeader.background",    BG_PANEL);
        UIManager.put("TableHeader.foreground",    PRIMARY);
        UIManager.put("TableHeader.font",          FONT_TABLE_HEADER);

        UIManager.put("ScrollPane.background",     BG_PRIMARY);
        UIManager.put("ScrollBar.background",      BG_PANEL);
        UIManager.put("ScrollBar.thumb",           BG_INPUT);

        UIManager.put("Spinner.background",        BG_INPUT);
        UIManager.put("Spinner.foreground",        TEXT_PRIMARY);
        UIManager.put("Spinner.font",              FONT_BODY);

        UIManager.put("List.background",           BG_PANEL);
        UIManager.put("List.foreground",           TEXT_PRIMARY);
        UIManager.put("List.selectionBackground",  PRIMARY);
        UIManager.put("List.selectionForeground",  TEXT_PRIMARY);

        UIManager.put("ToggleButton.background",   BUTACA_FREE);
        UIManager.put("ToggleButton.foreground",   TEXT_PRIMARY);
        UIManager.put("ToggleButton.font",         FONT_SMALL);

        UIManager.put("OptionPane.background",     BG_PRIMARY);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        UIManager.put("OptionPane.buttonBackground", PRIMARY);

        UIManager.put("Separator.background",      DIVIDER);

        UIManager.put("Viewport.background",       BG_PRIMARY);

        UIManager.put("Table.showGrid",            Boolean.FALSE);
        UIManager.put("Table.intercellSpacing",    new Dimension(0, 0));
    }

    // ──────────────────────────────────────────────
    // Button styling
    // ──────────────────────────────────────────────

    public static void styleButton(JButton btn, Color bg) {
        btn.setFont(FONT_BUTTON);
        btn.setBackground(bg);
        btn.setForeground(TEXT_PRIMARY);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(10, 24, 10, 24)));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }

    public static void styleButtonPrimary(JButton btn) {
        styleButton(btn, PRIMARY);
    }

    public static void styleButtonSecondary(JButton btn) {
        styleButton(btn, BG_PANEL);
    }

    public static void styleButtonConfirm(JButton btn) {
        styleButton(btn, GREEN);
    }

    public static void styleButtonDelete(JButton btn) {
        styleButton(btn, RED_INTENSE);
    }

    // ──────────────────────────────────────────────
    // Label styling
    // ──────────────────────────────────────────────

    public static void styleTitle(JLabel lbl) {
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void styleSubtitle(JLabel lbl) {
        lbl.setFont(FONT_SUBTITLE);
        lbl.setForeground(TEXT_PRIMARY);
    }

    public static void styleBodyLabel(JLabel lbl) {
        lbl.setFont(FONT_BODY);
        lbl.setForeground(TEXT_SECONDARY);
    }

    // ──────────────────────────────────────────────
    // Panel styling
    // ──────────────────────────────────────────────

    public static void stylePanel(JPanel panel) {
        panel.setBackground(BG_PRIMARY);
    }

    // ──────────────────────────────────────────────
    // Table styling
    // ──────────────────────────────────────────────

    public static void styleTable(JTable table) {
        // Most defaults are handled by Nimbus overrides,
        // but ensure alternate row coloring and no grid.
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(TABLE_ROW_HEIGHT);
        table.setFont(FONT_TABLE);
        table.getTableHeader().setFont(FONT_TABLE_HEADER);
    }

    // ──────────────────────────────────────────────
    // Dialog helpers — styled JOptionPane wrappers
    // ──────────────────────────────────────────────

    private static void styleDialogPane(JOptionPane pane, String title) {
        pane.setBackground(BG_PRIMARY);
        pane.setForeground(TEXT_PRIMARY);
        JDialog dialog = pane.createDialog(null, title);
        dialog.setBackground(BG_PRIMARY);
        dialog.getContentPane().setBackground(BG_PRIMARY);
        dialog.setVisible(true);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        styleDialogPane(pane, "Informacion");
    }

    public static void showInfo(Component parent, String message, String title) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        styleDialogPane(pane, title);
    }

    public static void showError(Component parent, String message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        styleDialogPane(pane, "Error");
    }

    public static void showError(Component parent, String message, String title) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        styleDialogPane(pane, title);
    }

    public static void showWarning(Component parent, String message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE);
        styleDialogPane(pane, "Advertencia");
    }

    public static void showWarning(Component parent, String message, String title) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE);
        styleDialogPane(pane, title);
    }

    public static boolean showConfirm(Component parent, String message) {
        return showConfirm(parent, message, "Confirmar");
    }

    public static boolean showConfirm(Component parent, String message, String title) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        pane.setBackground(BG_PRIMARY);
        pane.setForeground(TEXT_PRIMARY);
        JDialog dialog = pane.createDialog(parent, title);
        dialog.setBackground(BG_PRIMARY);
        dialog.getContentPane().setBackground(BG_PRIMARY);
        dialog.setVisible(true);
        Object selected = pane.getValue();
        return selected != null && selected instanceof Integer && (Integer) selected == JOptionPane.YES_OPTION;
    }
}

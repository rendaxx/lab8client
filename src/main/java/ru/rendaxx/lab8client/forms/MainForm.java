package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.DeleteCommandClient;
import ru.rendaxx.lab8client.client.UpdateHandler;
import ru.rendaxx.lab8client.frame.AddCommandFrame;
import ru.rendaxx.lab8client.frame.CanvasFrame;
import ru.rendaxx.lab8client.frame.UpdateCommandFrame;
import ru.rendaxx.lab8client.model.table.OrganizationTableModel;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UpdateListener;
import ru.rendaxx.lab8client.util.UserSession;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

@Log
@Component
@Scope("prototype")
public class MainForm implements SetTextListener, UpdateListener {
    @Getter
    private JPanel rootPanel;
    private JButton canvasButton;
    private JButton addButton;
    private JTable organizationTable;
    private JLabel usernameLabel;
    private JScrollPane scrollPane;
    private JTextField filterField;
    private JLabel filterLabel;
    private JSpinner columSpinner;

    private ApplicationContext applicationContext;

    public MainForm(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        addButton.addActionListener(e -> {
            EventQueue.invokeLater(() -> applicationContext.getBean(AddCommandFrame.class));
        });
        canvasButton.addActionListener(e -> {
            EventQueue.invokeLater(() -> applicationContext.getBean(CanvasFrame.class));
        });
    }

    private void createUIComponents() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);
        applicationContext.getBean(UpdateHandler.class).addListener(this);

        OrganizationTableModel model = applicationContext.getBean(OrganizationTableModel.class);
        organizationTable = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        organizationTable.setRowSorter(sorter);

        final JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e->{
            int row = organizationTable.getSelectedRow();
            applicationContext.getBean(DeleteCommandClient.class).delete(((OrganizationTableModel) organizationTable.getModel()).getData(row));
        });

        JMenuItem updateItem = new JMenuItem("Update");
        updateItem.addActionListener(e -> {
            int row = organizationTable.getSelectedRow();
            UpdateCommandFrame updateFrame = applicationContext.getBean(UpdateCommandFrame.class);
            updateFrame.setFields(((OrganizationTableModel) organizationTable.getModel()).getData(row));
        });

        popupMenu.add(deleteItem);
        popupMenu.add(updateItem);

        organizationTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int r = organizationTable.rowAtPoint(e.getPoint());
                if (r >= 0 && r < organizationTable.getRowCount()) {
                    organizationTable.setRowSelectionInterval(r, r);
                } else {
                    organizationTable.clearSelection();
                }

                int rowindex = organizationTable.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        organizationTable.setComponentPopupMenu(popupMenu);

        SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 11, 1);
        columSpinner = new JSpinner(model1);

        filterField = new JTextField();
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter(filterField.getText(), (int) columSpinner.getValue());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter(filterField.getText(), (int) columSpinner.getValue());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            private void filter(String text, int column) {
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, column));
                }
            }
        });

        columSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                filter(filterField.getText(), (int) columSpinner.getValue());
            }

            private void filter(String text, int column) {
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, column));
                }
            }
        });

        usernameLabel = new JLabel();
        setText();
    }

    @Override
    public void setText() {
        usernameLabel.setText(applicationContext.getBean(UserSession.class).getUsername());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/main");
        if (canvasButton == null || addButton == null) return;
        canvasButton.setText(resourceBundle.getString("form.canvas.button"));
        addButton.setText(resourceBundle.getString("form.add.button"));
        filterLabel.setText(resourceBundle.getString("form.filter.label"));
    }

    @Override
    public boolean isVisible() {
        return rootPanel.isVisible();
    }

    @Override
    public void update() {

    }
}

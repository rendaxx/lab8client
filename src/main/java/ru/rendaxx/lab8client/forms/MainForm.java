package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.DeleteCommandClient;
import ru.rendaxx.lab8client.client.UpdateCommandClient;
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
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        organizationTable = new JTable(applicationContext.getBean(OrganizationTableModel.class));

        final JPopupMenu popupMenu = new JPopupMenu();

        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                int rowAtPoint = organizationTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), organizationTable));
                if (rowAtPoint > -1) {
                    organizationTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

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
//            applicationContext.getBean(UpdateCommandClient.class).update()
        });

        popupMenu.add(deleteItem);
        popupMenu.add(updateItem);
        organizationTable.setComponentPopupMenu(popupMenu);

        usernameLabel = new JLabel();
        setText();
    }

    @Override
    public void setText() {
        usernameLabel.setText(applicationContext.getBean(UserSession.class).getUsername());
    }

    @Override
    public boolean isVisible() {
        return rootPanel.isVisible();
    }

    @Override
    public void update() {

    }
}

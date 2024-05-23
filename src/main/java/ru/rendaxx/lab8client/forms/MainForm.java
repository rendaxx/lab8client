package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.frame.AddCommandFrame;
import ru.rendaxx.lab8client.model.table.OrganizationTableModel;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UserSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

@Log
@Component
@Scope("prototype")
public class MainForm implements SetTextListener {
    @Getter
    private JPanel rootPanel;
    private JButton button1;
    private JButton button2;
    private JTable organizationTable;
    private JLabel usernameLabel;

    private ApplicationContext applicationContext;

    public MainForm(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        button2.addActionListener(e -> {
            applicationContext.getBean(AddCommandFrame.class);
        });
    }

    private void createUIComponents() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);
        organizationTable = new JTable(applicationContext.getBean(OrganizationTableModel.class));

        usernameLabel = new JLabel();
        setText();
    }

    @Override
    public void setText() {
        usernameLabel.setText(applicationContext.getBean(UserSession.class).getUsername());
//        organizationTable.getColumnModel().getColumns().asIterator().forEachRemaining();
        organizationTable.getTableHeader().resizeAndRepaint();

    }

    @Override
    public boolean isVisible() {
        return rootPanel.isVisible();
    }
}

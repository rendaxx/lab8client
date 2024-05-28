package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.AddCommandClient;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.model.object.OrganizationType;
import ru.rendaxx.lab8client.util.UserSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@Getter
@Component
@Scope("prototype")
public class AddCommandForm {
    private JPanel rootPanel;
    private JTextField nameField;
    private JButton addButton;
    private JTextField xField;
    private JTextField yField;
    private JTextField annualTurnoverField;
    private JTextField fullNameField;
    private JTextField employeesCountField;
    private JTextField streetField;
    private JTextField zipCodeField;
    private JComboBox<OrganizationType> comboBox1;

    private ApplicationContext applicationContext;
    private UserSession userSession;

    @Autowired
    public AddCommandForm(ApplicationContext applicationContext, UserSession userSession) {
        this.applicationContext = applicationContext;
        this.userSession = userSession;
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            Double x = Double.valueOf(xField.getText());
            Double y = Double.valueOf(yField.getText());
            Long annualTurnover = Long.valueOf(annualTurnoverField.getText());
            String fullName = fullNameField.getText();
            Long employeesCount = Long.valueOf(employeesCountField.getText());
            OrganizationType organizationType = (OrganizationType) comboBox1.getSelectedItem();
            String street = streetField.getText();
            String zipCode = zipCodeField.getText();
            applicationContext.getBean(AddCommandClient.class).add(
                    new OrganizationDto(name, x, y, annualTurnover, fullName, employeesCount, organizationType, street, zipCode, userSession.getUsername()));
        });
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox<>(OrganizationType.values());
    }
}

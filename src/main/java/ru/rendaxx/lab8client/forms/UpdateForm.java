package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.AddCommandClient;
import ru.rendaxx.lab8client.client.UpdateCommandClient;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.model.object.OrganizationType;
import ru.rendaxx.lab8client.util.UserSession;

import javax.swing.*;

@Getter
@Component
public class UpdateForm {
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

    @Setter
    private Long id;

    private JComboBox<OrganizationType> comboBox1;

    private ApplicationContext applicationContext;
    private UserSession userSession;

    @Autowired
    public UpdateForm(ApplicationContext applicationContext, UserSession userSession) {
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
            applicationContext.getBean(UpdateCommandClient.class).update(
                    new OrganizationDto(id, name, x, y, annualTurnover, fullName, employeesCount, organizationType, street, zipCode, userSession.getUsername()));
        });
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox<>(OrganizationType.values());
    }
}

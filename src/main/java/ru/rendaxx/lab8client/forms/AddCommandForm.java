package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.AddCommandClient;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.model.object.OrganizationType;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UserSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;


@Getter
@Component
@Scope("prototype")
public class AddCommandForm implements SetTextListener {
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
    private JLabel nameLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel annualTurnoverLabel;
    private JLabel fullnameLabel;
    private JLabel employeesLabel;
    private JLabel organizationTypeLabel;
    private JLabel streetLabel;
    private JLabel zipCodeLabel;

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

    @Override
    public void setText() {
        if (!xField.getText().isEmpty()) {
            xField.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.valueOf(xField.getText())));
            yField.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.valueOf(yField.getText())));
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/add");
        nameLabel.setText(resourceBundle.getString("form.name.label"));
        xLabel.setText(resourceBundle.getString("form.x.label"));
        yLabel.setText(resourceBundle.getString("form.y.label"));
        annualTurnoverLabel.setText(resourceBundle.getString("form.annualTurnover.label"));
        fullnameLabel.setText(resourceBundle.getString("form.fullName.label"));
        employeesCountField.setText(resourceBundle.getString("form.employeesCount.label"));
        organizationTypeLabel.setText(resourceBundle.getString("form.organizationType.label"));
        streetLabel.setText(resourceBundle.getString("form.street.label"));
        zipCodeLabel.setText(resourceBundle.getString("form.zipCode.label"));
    }

    @Override
    public boolean isVisible() {
        return rootPanel.isVisible();
    }
}

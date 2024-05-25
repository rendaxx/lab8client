package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.forms.AddCommandForm;
import ru.rendaxx.lab8client.forms.DefaultMenuBar;
import ru.rendaxx.lab8client.forms.UpdateForm;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class UpdateCommandFrame extends JFrame implements SetTextListener {

    private final ApplicationContext applicationContext;
    private UpdateForm updateForm;

    @Autowired
    public UpdateCommandFrame(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.updateForm = applicationContext.getBean(UpdateForm.class);
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(updateForm.getRootPanel());

        JMenuBar menuBar = applicationContext.getBean(DefaultMenuBar.class);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void setFields(OrganizationDto organization) {
        updateForm.getNameField().setText(organization.getName());
        updateForm.getXField().setText(organization.getX().toString());
        updateForm.getYField().setText(organization.getY().toString());
        updateForm.getAnnualTurnoverField().setText(organization.getAnnualTurnover().toString());
        updateForm.getFullNameField().setText(organization.getFullName());
        updateForm.getEmployeesCountField().setText(organization.getEmployeesCount().toString());
        updateForm.setId(organization.getId());
        updateForm.getComboBox1().setSelectedItem(organization.getOrganizationType());
        updateForm.getStreetField().setText(organization.getStreet());
        updateForm.getZipCodeField().setText(organization.getZipCode());
    }

    @Override
    public void setText() {

    }
}

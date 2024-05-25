package ru.rendaxx.lab8client.model.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.model.OrganizationService;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.model.object.OrganizationType;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@Scope("prototype")
public class OrganizationTableModel extends AbstractTableModel {

    private OrganizationService organizationService;
    private Set<TableModelListener> listeners = new HashSet<>();

    @Autowired
    public OrganizationTableModel(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    public int getRowCount() {
        return organizationService.getOrganizationList().size();
    }

    @Override
    public int getColumnCount() {
        return OrganizationDto.class.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/add");
        return switch (columnIndex) {
            case 0 -> resourceBundle.getString("form.id.label");
            case 1 -> resourceBundle.getString("form.name.label");
            case 2 -> resourceBundle.getString("form.x.label");
            case 3 -> resourceBundle.getString("form.y.label");
            case 4 -> resourceBundle.getString("form.localDate.label");
            case 5 -> resourceBundle.getString("form.annualTurnover.label");
            case 6 -> resourceBundle.getString("form.fullName.label");
            case 7 -> resourceBundle.getString("form.employeesCount.label");
            case 8 -> resourceBundle.getString("form.organizationType.label");
            case 9 -> resourceBundle.getString("form.street.label");
            case 10 -> resourceBundle.getString("form.zipCode.label");
            case 11 -> resourceBundle.getString("form.username.label");
            default -> "huh?";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Long.class;
            case 1 -> String.class;
            case 2 -> Double.class;
            case 3 -> Double.class;
            case 4 -> LocalDate.class;
            case 5 -> Long.class;
            case 6 -> String.class;
            case 7 -> Long.class;
            case 8 -> OrganizationType.class;
            case 9 -> String.class;
            case 10 -> String.class;
            case 11 -> String.class;
            default -> Void.class;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0 && columnIndex != getColumnCount() - 1;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrganizationDto org = (OrganizationDto) organizationService.getOrganizationList().toArray()[rowIndex]; // god forgive me
        return switch (columnIndex) {
            case 0 -> org.getId();
            case 1 -> org.getName();
            case 2 -> org.getX();
            case 3 -> org.getY();
            case 4 -> org.getLocalDate();
            case 5 -> org.getAnnualTurnover();
            case 6 -> org.getFullName();
            case 7 -> org.getEmployeesCount();
            case 8 -> org.getOrganizationType();
            case 9 -> org.getStreet();
            case 10 -> org.getZipCode();
            case 11 -> org.getCreatorName();
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        OrganizationDto org = (OrganizationDto) organizationService.getOrganizationList().toArray()[rowIndex]; // god forgive me
        switch (columnIndex) {
            case 0 -> org.setId((Long) aValue);
            case 1 -> org.setName((String) aValue);
            case 2 -> org.setX((Double) aValue);
            case 3 -> org.setY((Double) aValue);
            case 4 -> org.setLocalDate((LocalDate) aValue);
            case 5 -> org.setAnnualTurnover((Long) aValue);
            case 6 -> org.setFullName((String) aValue);
            case 7 -> org.setEmployeesCount((Long) aValue);
            case 8 -> org.setOrganizationType((OrganizationType) aValue);
            case 9 -> org.setStreet((String) aValue);
            case 10 -> org.setZipCode((String) aValue);
            case 11 -> org.setCreatorName((String) aValue);
            default -> {}
        }
        organizationService.update(org);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public OrganizationDto getData(int rowIndex) {
        return (OrganizationDto) organizationService.getOrganizationList().toArray()[rowIndex];
    }
}

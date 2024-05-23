package ru.rendaxx.lab8client.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.model.object.OrganizationDto;

import java.util.Collection;
import java.util.LinkedHashSet;


@Getter
@Component
public class OrganizationService implements CollectionService<OrganizationDto> {
    private LinkedHashSet<OrganizationDto> organizationList;

    @Autowired
    public OrganizationService(@Qualifier("myOrganizationCollection") LinkedHashSet<OrganizationDto> organizationList) {
        this.organizationList = organizationList;
    }

    @Override
    public void load(Collection<OrganizationDto> collection) {
        organizationList.clear();
        organizationList.addAll(collection);
    }

    @Override
    public void add(OrganizationDto organization) {
        organizationList.add(organization);
    }

    @Override
    public void delete(OrganizationDto organization) {
        organizationList.remove(organization);
    }

    @Override
    public void update(OrganizationDto organization) {
        var oldOrg = organizationList.stream().filter(o -> o.getId().equals(organization.getId())).findFirst().orElseThrow();
        delete(oldOrg);
        add(organization);
    }
}

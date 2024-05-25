package ru.rendaxx.lab8client.model.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrganizationDto implements Comparable<OrganizationDto> {
    private Long id;
    private String name;
    private Double x;
    private Double y;
    private LocalDate localDate;
    private Long annualTurnover;
    private String fullName;
    private Long employeesCount;
    private OrganizationType organizationType;
    private String street;
    private String zipCode;
    private String creatorName;

    public OrganizationDto(String name, Double x,  Double y, Long annualTurnover, String fullName, Long employeesCount, OrganizationType organizationType, String street, String zipCode, String creatorName) {
        this.id = null;
        this.localDate = LocalDate.now();
        this.y = y;
        this.x = x;
        this.name = name;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.organizationType = organizationType;
        this.street = street;
        this.zipCode = zipCode;
        this.creatorName = creatorName;
    }

    public OrganizationDto(Long id, String name, Double x,  Double y, Long annualTurnover, String fullName, Long employeesCount, OrganizationType organizationType, String street, String zipCode, String creatorName) {
        this.id = id;
        this.localDate = LocalDate.now();
        this.y = y;
        this.x = x;
        this.name = name;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.organizationType = organizationType;
        this.street = street;
        this.zipCode = zipCode;
        this.creatorName = creatorName;
    }

    @Override
    public int compareTo(OrganizationDto o) {
        return Long.compare(this.annualTurnover, o.annualTurnover);
    }
}

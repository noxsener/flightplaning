package com.senerk.casestudy.flightplanning.entity;

import com.senerk.casestudy.flightplanning.converter.DbBooleanConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "AIRPORTS", indexes = {@Index(columnList = "CODE", name = "ix_airport_code")})
public class AirPort implements EntityClass {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private String id;
    @Column(name = "DISPLAY_NAME")
    private String displayName;
    @Column(name = "CODE")
    private String code;
    @Column(name = "PASSIVE", length = 1, nullable = false)
    @Convert(converter = DbBooleanConverter.class)
    private Boolean passive = Boolean.FALSE;

    public AirPort() {
    }

    public AirPort(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
        this.passive = Boolean.FALSE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getPassive() {
        return passive;
    }

    public void setPassive(Boolean passive) {
        this.passive = passive;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirPort airPort = (AirPort) o;
        return new org.apache.commons.lang3.builder.EqualsBuilder().append(id, airPort.id).append(getCode(), airPort.getCode()).isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(id).append(getCode()).toHashCode();
    }
}

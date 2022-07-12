package com.senerk.casestudy.flightplanning.entitydto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AirPortDto implements EntityDtoClass {
    private String id;

    @NotNull(message = "Airport displayName cannot be null")
    @NotBlank(message = "Airport displayName cannot be blank")
    private String displayName;

    @NotNull(message = "Airport code cannot be null")
    @NotBlank(message = "Airport code cannot be blank")
    private String code;
    private String passive;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassive() {
        return passive;
    }

    public void setPassive(String passive) {
        this.passive = passive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirPortDto airPort = (AirPortDto) o;
        return new org.apache.commons.lang3.builder.EqualsBuilder().append(id, airPort.id).append(getCode(), airPort.getCode()).isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(id).append(getCode()).toHashCode();
    }
}

package com.senerk.casestudy.flightplanning.entitydto;

import com.senerk.casestudy.flightplanning.enums.FlightStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class FlightDto implements EntityDtoClass {
    private String id;

    @NotNull(message = "Flight displayName cannot be null")
    @NotBlank(message = "Flight displayName cannot be blank")
    private String displayText;

    @NotNull(message = "Flight flightCode cannot be null")
    @NotBlank(message = "Flight flightCode cannot be blank")
    private String flightCode;

    @NotNull(message = "Flight sourceAirPort cannot be null")
    private AirPortDto sourceAirPort;
    @NotNull(message = "Flight arrivalAirPort cannot be null")
    private AirPortDto arrivalAirPort;
    @NotNull(message = "Flight takeOffDate cannot be null")
    private LocalDateTime takeOffDate;

    @NotNull(message = "Flight status cannot be null")
    private FlightStatus status;

    private Boolean passive;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public LocalDateTime getTakeOffDate() {
        return takeOffDate;
    }

    public void setTakeOffDate(LocalDateTime takeOffDate) {
        this.takeOffDate = takeOffDate;
    }

    public Boolean getPassive() {
        return passive;
    }

    public void setPassive(Boolean passive) {
        this.passive = passive;
    }

    public AirPortDto getSourceAirPort() {
        return sourceAirPort;
    }

    public void setSourceAirPort(AirPortDto sourceAirPort) {
        this.sourceAirPort = sourceAirPort;
    }

    public AirPortDto getArrivalAirPort() {
        return arrivalAirPort;
    }

    public void setArrivalAirPort(AirPortDto arrivalAirPort) {
        this.arrivalAirPort = arrivalAirPort;
    }
}

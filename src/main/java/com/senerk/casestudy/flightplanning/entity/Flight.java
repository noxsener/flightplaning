package com.senerk.casestudy.flightplanning.entity;

import com.senerk.casestudy.flightplanning.converter.DbBooleanConverter;
import com.senerk.casestudy.flightplanning.enums.FlightStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FLIGHTS", indexes = {
        @Index(columnList = "CODE,STATUS", name = "ix_flight__code_status"),
        @Index(columnList = "SOURCE_AIRPORT_ID,ARRIVAL_AIRPORT_ID", name = "ix_flight__sourceAirPort_arrivalAirPort"),
})
public class Flight implements EntityClass {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private String id;
    @Column(name = "DISPLAY_TEXT")
    private String displayText;
    @Column(name = "CODE")
    private String flightCode;
    @Column(name = "STATUS", nullable = false)
    private FlightStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SOURCE_AIRPORT_ID", nullable = false)
    private AirPort sourceAirPort;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ARRIVAL_AIRPORT_ID", nullable = false)
    private AirPort arrivalAirPort;

    @Column(name = "TAKE_OFF_DATE", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime takeOffDate;

    @Column(name = "PASSIVE", length = 1, nullable = false)
    @Convert(converter = DbBooleanConverter.class)
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

    public Boolean getPassive() {
        return passive;
    }

    public void setPassive(Boolean passive) {
        this.passive = passive;
    }

    public AirPort getSourceAirPort() {
        return sourceAirPort;
    }

    public void setSourceAirPort(AirPort sourceAirPort) {
        this.sourceAirPort = sourceAirPort;
    }

    public AirPort getArrivalAirPort() {
        return arrivalAirPort;
    }

    public void setArrivalAirPort(AirPort arrivalAirPort) {
        this.arrivalAirPort = arrivalAirPort;
    }

    public LocalDateTime getTakeOffDate() {
        return takeOffDate;
    }

    public void setTakeOffDate(LocalDateTime takeOffDate) {
        this.takeOffDate = takeOffDate;
    }
}

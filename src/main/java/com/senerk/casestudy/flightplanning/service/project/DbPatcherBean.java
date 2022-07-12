package com.senerk.casestudy.flightplanning.service.project;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.enums.FlightStatus;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import com.senerk.casestudy.flightplanning.service.airport.AirPortService;
import com.senerk.casestudy.flightplanning.service.flight.FlightService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DbPatcherBean {

    private final AirPortService airPortService;
    private final FlightService flightService;

    @Value("${spring.jpa.hibernate.ddl-auto:validate}")
    private String ddlAuto;

    @Value("${flightplanning.generatedumpdata:false}")
    private Boolean generateDumpData;

    private static final String CREATE = "create";
    private static final String CREATE_DROP = "create-drop";

    public DbPatcherBean(AirPortService airPortService, FlightService flightService) {
        this.airPortService = airPortService;
        this.flightService = flightService;
    }

    private boolean isInitializingDB() {
        return CREATE.equalsIgnoreCase(ddlAuto) || CREATE_DROP.equalsIgnoreCase(ddlAuto);
    }

    /**
     * Saves AirPort records for just initialized DB
     */
    public void dbInitializer() {
        if (!isInitializingDB()) {
            return;
        }

        final List<AirPort> turkeyAirPortList = new ArrayList<>();
        turkeyAirPortList.add(new AirPort("Adana Havalimanı", "ADA"));
        turkeyAirPortList.add(new AirPort("Ankara Esenboğa Havalimanı", "ESB"));
        turkeyAirPortList.add(new AirPort("Alanya Gazipaşa Havalimanı", "GZP"));
        turkeyAirPortList.add(new AirPort("Antalya Havalimanı", "AYT"));
        turkeyAirPortList.add(new AirPort("Balıkesir Kocaseyit Havalimanı", "EDO"));
        turkeyAirPortList.add(new AirPort("Bursa Yenişehir Havalimanı", "YEI"));
        turkeyAirPortList.add(new AirPort("Çanakkale Havalimanı", "CKZ"));
        turkeyAirPortList.add(new AirPort("Denizli Çardak Havalimanı", "DNZ"));
        turkeyAirPortList.add(new AirPort("Diyarbakır Havalimanı", "DIY"));
        turkeyAirPortList.add(new AirPort("Elazığ Havalimanı", "EZS"));
        turkeyAirPortList.add(new AirPort("Erzurum Havalimanı", "ERZ"));
        turkeyAirPortList.add(new AirPort("Eskişehir Hasan Polatkan Havalimanı", "AOE"));
        turkeyAirPortList.add(new AirPort("Gaziantep Havalimanı", "GZT"));
        turkeyAirPortList.add(new AirPort("Hatay Havalimanı", "HTY"));
        turkeyAirPortList.add(new AirPort("Isparta Süleyman Demirel Havalimanı", "ISE"));
        turkeyAirPortList.add(new AirPort("İstanbul Havalimanı", "IST"));
        turkeyAirPortList.add(new AirPort("İstanbul Sabiha Gökçen Havalimanı", "SAW"));
        turkeyAirPortList.add(new AirPort("İzmir Adnan Menderes Havalimanı", "ADB"));
        turkeyAirPortList.add(new AirPort("Kars Harakani Havalimanı", "KSY"));
        turkeyAirPortList.add(new AirPort("Kayseri Havalimanı", "ASR"));
        turkeyAirPortList.add(new AirPort("Kocaeli Cengiz Topel Havalimanı", "KCO"));
        turkeyAirPortList.add(new AirPort("Konya Havalimanı", "KYA"));
        turkeyAirPortList.add(new AirPort("Kütahya Zafer Havalimanı", "KZR"));
        turkeyAirPortList.add(new AirPort("Malatya Havalimanı", "MLX"));
        turkeyAirPortList.add(new AirPort("Muğla Dalaman Havalimanı", "DLM"));
        turkeyAirPortList.add(new AirPort("Muğla Milas-Bodrum Havalimanı", "BJV"));
        turkeyAirPortList.add(new AirPort("Nevşehir Kapadokya Havalimanı", "NAV"));
        turkeyAirPortList.add(new AirPort("Ordu Giresun Havalimanı", "OGU"));
        turkeyAirPortList.add(new AirPort("Samsun Çarşamba Havalimanı", "SZF"));
        turkeyAirPortList.add(new AirPort("Sinop Havalimanı", "NOP"));
        turkeyAirPortList.add(new AirPort("Sivas Nuri Demirağ Havalimanı", "VAS"));
        turkeyAirPortList.add(new AirPort("Şanlıurfa GAP Havalimanı", "GNY"));
        turkeyAirPortList.add(new AirPort("Tekirdağ Çorlu Havalimanı", "TEQ"));
        turkeyAirPortList.add(new AirPort("Trabzon Havalimanı", "TZX"));
        turkeyAirPortList.add(new AirPort("Uşak Havalimanı", "USQ"));
        turkeyAirPortList.add(new AirPort("Van Ferit Melen Havalimanı", "VAN"));
        turkeyAirPortList.add(new AirPort("Zonguldak Çaycuma Havalimanı", "ONQ"));
        for (AirPort airPort : turkeyAirPortList) {
            airPort.setId(airPortService.save(airPort));
        }

        if(generateDumpData) {
            generateDumpFlights(turkeyAirPortList);
        }
    }

    /**
     * Saves a few random flights
     * @param turkeyAirPortList
     */
    private void generateDumpFlights(List<AirPort> turkeyAirPortList) {
        Random random = new SecureRandom();
        int airPortListSize = turkeyAirPortList.size();
        int flightStatusSize = FlightStatus.values().length;
        for (int i = 0; i < 100; i++) {
            Flight flight = new Flight();
            flight.setPassive(Boolean.FALSE);
            flight.setFlightCode("TK" + random.nextInt(10000));
            flight.setSourceAirPort(turkeyAirPortList.get(random.nextInt(airPortListSize - 1)));
            do {
                flight.setArrivalAirPort(turkeyAirPortList.get(random.nextInt(airPortListSize - 1)));
            } while (flight.getSourceAirPort().equals(flight.getArrivalAirPort()));
            flight.setDisplayText(flight.getSourceAirPort().getDisplayName() + "->" + flight.getArrivalAirPort().getDisplayName());
            flight.setStatus(FlightStatus.values()[random.nextInt(flightStatusSize - 1)]);
            LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
            localDateTime = localDateTime.plusDays(random.nextBoolean() ? 1 : -1 * random.nextInt(10));
            localDateTime = localDateTime.plusHours(random.nextBoolean() ? 1 : -1 * random.nextInt(10));
            flight.setTakeOffDate(localDateTime);
            try {
                flightService.save(flight);
            } catch (ValidatorException ignored) {
                // Who cares?
            }
        }
    }

    public void setDdlAuto(String ddlAuto) {
        this.ddlAuto = ddlAuto;
    }

    public void setGenerateDumpData(Boolean generateDumpData) {
        this.generateDumpData = generateDumpData;
    }
}

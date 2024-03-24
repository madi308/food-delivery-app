package com.example.fooddeliveryapp.observation;

import com.example.fooddeliveryapp.city.City;
import com.example.fooddeliveryapp.city.CityService;
import com.example.fooddeliveryapp.exception.NoObservationFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ObservationService {

    /**
     * Source: Estonian Environment Agency
     * URL: ilmateenistus.ee
     */
    private static final String OBSERVATIONS_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private final ObservationRepository observationRepository;
    private final CityService cityService;

    @Autowired
    public ObservationService(ObservationRepository observationRepository, CityService cityService) {
        this.observationRepository = observationRepository;
        this.cityService = cityService;
    }

    @Scheduled(cron = "0 15 * * * *")
    private void addNewObservations() {

        List<City> cities = cityService.getAllCities();
        if (cities.isEmpty())
            return;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection urlConnection = new URL(OBSERVATIONS_URL).openConnection();
            urlConnection.addRequestProperty("Accept", "application/xml");
            Document doc = db.parse(urlConnection.getInputStream());
            doc.getDocumentElement().normalize();

            long timestamp = Long.parseLong(doc.getDocumentElement().getAttribute("timestamp"));
            NodeList stations = doc.getElementsByTagName("station");
            int counter = 0;
            int citiesLength = cities.size();
            Set<String> stationNames = cities.stream().map(City::getStationName).collect(Collectors.toSet());

            for (int i = 0; i < stations.getLength(); i++) {
                if (counter == citiesLength)
                    break;
                Element station = (Element) stations.item(i);
                String name = station.getElementsByTagName("name").item(0).getTextContent();
                if (stationNames.contains(name)) {
                    int wmocode = Integer.parseInt(station.getElementsByTagName("wmocode").item(0).getTextContent());
                    float airtemperature = Float.parseFloat(station.getElementsByTagName("airtemperature").item(0).getTextContent());
                    float windspeed = Float.parseFloat(station.getElementsByTagName("windspeed").item(0).getTextContent());
                    String phenomenon = station.getElementsByTagName("phenomenon").item(0).getTextContent();
                    observationRepository.save(new Observation(
                            name, wmocode, airtemperature, windspeed, phenomenon, timestamp
                    ));
                    stationNames.remove(name);
                    counter++;
                }
            }
        } catch (Exception e) {
            throw new NoObservationFoundException("Something went wong while fetching new observations");
        }
    }

    public Observation getLatestObservation(String stationName) {
        Optional<Observation> optionalObservation = observationRepository.findLatestByName(stationName);
        if (optionalObservation.isEmpty()) throw new NoObservationFoundException("No observations with specified location found");
        return optionalObservation.get();
    }
}

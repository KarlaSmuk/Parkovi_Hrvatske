package or.labos.application.controller;


import or.labos.application.dto.ParkToFileDto;
import or.labos.application.service.JsonExporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;


@RestController
@RequestMapping("")
public class DownloadController {

    @Autowired
    private JsonExporterService jsonExporterService;

    private List<ParkToFileDto> parks; // Store data temporarily

    @CrossOrigin
    @PostMapping("/search/sendData")
    public ResponseEntity<List<ParkToFileDto>> json(@RequestBody List<Map<String, Object>> requestData) {

        List<ParkToFileDto> parkToFileDtos = new ArrayList<>();

        requestData.forEach(park -> {
            ParkToFileDto existingPark = parkToFileDtos.stream()
                    .filter(p -> p.getPark().equals( park.get("parkName")))
                    .findFirst()
                    .orElse(null);

            Set<String> counties = new HashSet<>();
            counties.add((String) park.get("county"));

            Set<Map<String, String>> animals = new HashSet<>();

            Map<String, String> animal = new LinkedHashMap<>();
            animal.put("naziv", (String) park.get("animal"));
            animal.put("vrsta", (String) park.get("species"));
            animals.add(animal);

            Map<String, Object> vrhMap = new LinkedHashMap<>();
            if (!Objects.isNull(park.get("peakName"))) {
                vrhMap.put("naziv", park.get("peakName"));
                vrhMap.put("visina", park.get("peakHeight"));

            } else {
                vrhMap.put("naziv", null);
                vrhMap.put("visina", null);

            }

            if (existingPark != null) {
                existingPark.getZupanije().addAll(counties);
                existingPark.getZivotinje().addAll(animals);
            } else {
                parkToFileDtos.add(new ParkToFileDto(
                        (String) park.get("parkName"),
                        (String) park.get("typeOfPark"),
                        (Integer) park.get("yearOfFoundation"),
                        park.get("area") instanceof Double ? (Double) park.get("area") : ((Integer) park.get("area")).doubleValue(),
                        vrhMap,
                        counties,
                        (String) park.get("atraction"),
                        (String) park.get("event"),
                        animals
                ));
            }

        });

        parks = new ArrayList<>(parkToFileDtos);


        return ResponseEntity.ok(parkToFileDtos);
    }

    @CrossOrigin
    @GetMapping("/search/downloadJson")
    public ResponseEntity<Resource> downloadJsonFile() {

        String parkJsonString = jsonExporterService.export(parks);

        byte[] parkJsonBytes = parkJsonString.getBytes(StandardCharsets.UTF_8);
        ByteArrayResource resource = new ByteArrayResource(parkJsonBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=parks.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(parkJsonBytes.length)
                .body(resource);
    }
}


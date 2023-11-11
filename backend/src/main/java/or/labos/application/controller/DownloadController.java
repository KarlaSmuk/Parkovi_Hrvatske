package or.labos.application.controller;


import or.labos.application.dto.ParkToFileDto;
import or.labos.application.service.JsonExporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


@RestController
@RequestMapping("")
public class DownloadController {

    @Autowired
    private JsonExporterService jsonExporterService;

    private List<ParkToFileDto> parksToJSON; // Store data temporarily
    private List<Map<String, Object>> parksToCSV; // Store data temporarily

    @CrossOrigin
    @PostMapping("/search/sendData")
    public ResponseEntity<List<ParkToFileDto>> json(@RequestBody List<Map<String, Object>> requestData) {

        List<ParkToFileDto> parkToFileDtos = new ArrayList<>();

        parksToCSV = new ArrayList<>(requestData);

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
                existingPark.getZupanija().addAll(counties);
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

        parksToJSON = new ArrayList<>(parkToFileDtos);


        return ResponseEntity.ok(parkToFileDtos);
    }

    @CrossOrigin
    @GetMapping("/search/downloadJson")
    public ResponseEntity<Resource> downloadJsonFile() throws IOException {

        String parkJsonString = jsonExporterService.export(parksToJSON);

        byte[] parkJsonBytes = parkJsonString.getBytes(StandardCharsets.UTF_8);
        ByteArrayResource resource = new ByteArrayResource(parkJsonBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=parks.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(parkJsonBytes.length)
                .body(resource);
    }

    @CrossOrigin
    @GetMapping("/search/downloadCSV")
    public ResponseEntity<byte[]> generateCsvFile() {

        String CSV_HEADER = "park,tip,godina_osnutka,povrsina,vrh,visina_vrh,zupanija,atrakcija,dogadjaj_godine,zivotinja,vrsta_zivotinja\n";

        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        parksToCSV.forEach(park -> {
            csvContent.append(park.get("parkName")).append(",")
                    .append(park.get("typeOfPark")).append(",")
                    .append(park.get("yearOfFoundation")).append(",")
                    .append(park.get("area")).append(",")
                    .append(park.get("peakName")).append(",")
                    .append(park.get("peakHeight")).append(",")
                    .append(park.get("county")).append(",")
                    .append(park.get("atraction")).append(",")
                    .append(park.get("event")).append(",")
                    .append(park.get("animal")).append(",")
                    .append(park.get("species")).append("\n");
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "parks.csv");

        byte[] csvBytes = csvContent.toString().getBytes(StandardCharsets.UTF_8);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

}


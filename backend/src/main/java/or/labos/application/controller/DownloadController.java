package or.labos.application.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import or.labos.application.dto.ParkToFileDto;
import or.labos.application.service.JsonExporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@RestController
@RequestMapping("")
public class DownloadController {

    @Autowired
    private JsonExporterService jsonExporterService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
                    .append(park.get("peakName") != null ? park.get("peakName") : "").append(",")
                    .append(park.get("peakHeight") != null ? park.get("peakHeight") : "").append(",")
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


    @CrossOrigin
    @GetMapping("/refreshData")
    public ResponseEntity<String> refreshData() {
        try {
            // json
            String sqlQuery = "SELECT json_agg(json_build_object(\n" +
                    "    'park', t.park,\n" +
                    "    'tip', t.tip_parka,\n" +
                    "    'godina_osnutka', t.godina_osnutka,\n" +
                    "    'povrsina', t.povrsina,\n" +
                    "    'vrh', json_build_object('naziv', t.nazvrh, 'visina', t.visinavrh),\n" +
                    "    'zupanija', t.zupanije,\n" +
                    "    'atrakcija', t.atrakcija,\n" +
                    "    'dogadjaj_godine', t.dogadjaj,\n" +
                    "    'zivotinje', t.zivotinje\n" +
                    ")) AS parkoviHrvatske\n" +
                    "FROM (\n" +
                    "    SELECT\n" +
                    "        nazpark AS park,\n" +
                    "        naztippark AS tip_parka,\n" +
                    "        godosnutka AS godina_osnutka,\n" +
                    "        povrsina,\n" +
                    "        COALESCE(nazvrh, null) AS nazvrh,\n" +
                    "        COALESCE(visina, null) AS visinavrh,\n" +
                    "        nazAtrakcija AS atrakcija,\n" +
                    "        nazdoggod AS dogadjaj,\n" +
                    "        COALESCE(\n" +
                    "            json_agg(DISTINCT nazzupanija),\n" +
                    "            '[]'\n" +
                    "        ) AS zupanije,\n" +
                    "        COALESCE(\n" +
                    "            json_agg(DISTINCT jsonb_build_object('naziv', nazzivotinja, 'vrsta', vrstazivotinja)),\n" +
                    "            '[]'\n" +
                    "        ) AS zivotinje\n" +
                    "    FROM parkovi\n" +
                    "    JOIN tipovi_parka ON parkovi.siftippark = tipovi_parka.siftippark\n" +
                    "    NATURAL JOIN nalazise \n" +
                    "    NATURAL JOIN zupanije\n" +
                    "    LEFT JOIN imazivotinju ON parkovi.sifpark = imazivotinju.sifpark\n" +
                    "    LEFT JOIN zivotinje ON imazivotinju.sifzivotinja = zivotinje.sifzivotinja\n" +
                    "    LEFT JOIN najvisi_vrhovi ON parkovi.sifvrh = najvisi_vrhovi.sifvrh\n" +
                    "    GROUP BY nazpark, godosnutka, povrsina, nazAtrakcija, naztippark, nazvrh, visinavrh, nazdoggod\n" +
                    ") t";

            List<Map<String, Object>> result = jdbcTemplate.query(sqlQuery, new ColumnMapRowMapper());

            // Convert the result to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(result);

            String valueField = result.get(0).get("parkoviHrvatske").toString();
            // Save the result to a file
            String filePath = "C:\\Users\\Karla\\Parkovi_Hrvatske\\frontend\\public\\data\\data.json";
            Files.writeString(Paths.get(filePath), valueField);

            //csv

            sqlQuery = "SELECT\n" +
                    "        nazpark AS park,\n" +
                    "        naztippark AS tip,\n" +
                    "        godosnutka AS godina_osnutka,\n" +
                    "        povrsina,\n" +
                    "        COALESCE(nazvrh, null) AS vrh,\n" +
                    "        COALESCE(visina, null) AS visina_vrh,\n" +
                    "\t\tnazzupanija AS zupanija,\n" +
                    "        nazAtrakcija AS atrakcija,\n" +
                    "        nazdoggod AS dogadjaj_godine,\n" +
                    "        nazzivotinja AS zivotinja,\n" +
                    "\t\tvrstazivotinja AS vrsta_zivotinja\n" +
                    "    FROM parkovi\n" +
                    "    JOIN tipovi_parka ON parkovi.siftippark = tipovi_parka.siftippark\n" +
                    "    NATURAL JOIN nalazise \n" +
                    "    NATURAL JOIN zupanije\n" +
                    "    LEFT JOIN imazivotinju ON parkovi.sifpark = imazivotinju.sifpark\n" +
                    "    LEFT JOIN zivotinje ON imazivotinju.sifzivotinja = zivotinje.sifzivotinja\n" +
                    "    LEFT JOIN najvisi_vrhovi ON parkovi.sifvrh = najvisi_vrhovi.sifvrh\n" +
                    "    GROUP BY nazpark, naztippark, godosnutka, povrsina, nazvrh, visina, nazzupanija, nazAtrakcija, nazdoggod,nazzivotinja, vrstazivotinja\n" +
                    "\tORDER BY nazpark";

            result = jdbcTemplate.query(sqlQuery, new ColumnMapRowMapper());


            StringBuilder csvData = new StringBuilder();
            if (!result.isEmpty()) {
                // Append CSV header
                csvData.append("park,tip,godina_osnutka,povrsina,vrh,visina_vrh,zupanija,atrakcija,dogadjaj_godine,zivotinja\n");

                // Append data rows
                for (Map<String, Object> park : result) {
                    csvData.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                            park.get("park"),
                            park.get("tip"),
                            park.get("godina_osnutka"),
                            park.get("povrsina"),
                            park.get("vrh"),
                            park.get("visina_vrh"),
                            park.get("zupanija"),
                            park.get("atrakcija"),
                            park.get("dogadjaj_godine"),
                            park.get("zivotinja")
                    ));
                }
            }

            filePath = "C:\\Users\\Karla\\Parkovi_Hrvatske\\frontend\\public\\data\\data.csv";
            Files.writeString(Paths.get(filePath), csvData.toString());

            return new ResponseEntity<>("Data generated and saved successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error generating data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


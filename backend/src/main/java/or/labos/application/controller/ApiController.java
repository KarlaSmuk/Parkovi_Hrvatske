package or.labos.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public ResponseEntity<String> getAPI() {
        try {
            String openApiFilePath = "C:/Users/Karla/Parkovi_Hrvatske/openapi.yml";
            String openapiContent = new String(Files.readAllBytes(Path.of(openApiFilePath)));
            return ResponseEntity.ok(openapiContent);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Error reading OpenAPI specification: " + e.getMessage());
        }
    }


}

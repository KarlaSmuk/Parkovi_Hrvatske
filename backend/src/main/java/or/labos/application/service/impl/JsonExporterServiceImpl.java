package or.labos.application.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import or.labos.application.dto.ParkToFileDto;
import or.labos.application.service.JsonExporterService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Service
public class JsonExporterServiceImpl implements JsonExporterService {

    @Override
    public String export(List<ParkToFileDto> parks) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parks);

        try {
            InputStream schemaStream = new FileInputStream("C:/Users/Karla/Parkovi_Hrvatske/schema.json");
            JsonNode jsonNode = mapper.readTree(json);

            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
            JsonSchema jsonSchema = factory.getSchema(schemaStream);

            Set<ValidationMessage> validationResult = jsonSchema.validate(jsonNode);

            if (!validationResult.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder("JSON not valid. Validation errors:\n");
                for (ValidationMessage message : validationResult) {
                    errorMessage.append(message).append("\n");
                }
                throw new IOException(errorMessage.toString());
            }

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Error while validating JSON against schema.", e);
        }

        return json;
    }
}

package or.labos.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public ResponseEntity<String> getAPI() {

        String openapi = "openapi: 3.1.0\n" +
                "info:\n" +
                "  title: Parkovi hrvatske\n" +
                "  description: API za parkove hrvatske\n" +
                "  contact:\n" +
                "    name: Karla Šmuk\n" +
                "  license:\n" +
                "    name: CC0-1.0 license\n" +
                "    url: https://creativecommons.org/publicdomain/zero/1.0/\n" +
                "  version: 1.0.0\n" +
                "paths:\n" +
                "  /api/parks:\n" +
                "    get:\n" +
                "      tags:\n" +
                "        - Park REST Controller\n" +
                "      summary: Fetch all parks\n" +
                "      description: Fetch all parks\n" +
                "      operationId: listAllParks\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: Fetched all parks\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                  $ref: '#/components/schemas/ParksResponse'\n" +
                "    post:\n" +
                "      tags:\n" +
                "          - Park REST Controller\n" +
                "      summary: Add a new park\n" +
                "      description: Add a new park\n" +
                "      operationId: addNewPark\n" +
                "      requestBody:\n" +
                "        description: Create a new park\n" +
                "        content:\n" +
                "          application/json:\n" +
                "            schema:\n" +
                "              $ref: '#/components/schemas/CreateParkRequest'\n" +
                "        required: true\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: New park created\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ParkResponse'    \n" +
                "        '409':\n" +
                "          description: Park already exists \n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ResponseError'\n" +
                "  /api/parks/{parkId}:\n" +
                "    get:\n" +
                "      tags:\n" +
                "        - Park REST Controller\n" +
                "      summary: Find park by id\n" +
                "      description: Returns a park\n" +
                "      operationId: getParkByID\n" +
                "      parameters:\n" +
                "        - name: parkId\n" +
                "          in: path\n" +
                "          description: ID of park to return\n" +
                "          required: true\n" +
                "          schema:\n" +
                "            type: integer\n" +
                "            format: int64\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: Fetched park with parkId\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                  $ref: '#/components/schemas/ParkResponse'          \n" +
                "        '404':\n" +
                "          description: Park not found\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ResponseError'  \n" +
                "    put:\n" +
                "      tags:\n" +
                "        - Park REST Controller\n" +
                "      summary: Update an existing park\n" +
                "      description: Update an existing park by Id\n" +
                "      operationId: updatePark\n" +
                "      parameters:\n" +
                "        - name: parkId\n" +
                "          in: path\n" +
                "          description: ID of park to return\n" +
                "          required: true\n" +
                "          schema:\n" +
                "            type: integer\n" +
                "            format: int64\n" +
                "      requestBody:\n" +
                "        description: Update an existing park\n" +
                "        content:\n" +
                "          application/json:\n" +
                "            schema:\n" +
                "              $ref: '#/components/schemas/CreateParkRequest'\n" +
                "        required: true\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: Park updated\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ParkResponse'\n" +
                "        '404':\n" +
                "          description: Park not found\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ResponseError'  \n" +
                "    delete:\n" +
                "      tags:\n" +
                "        - Park REST Controller\n" +
                "      summary: Delete a park\n" +
                "      description: Delete a park\n" +
                "      operationId: deletePark\n" +
                "      parameters:\n" +
                "        - name: parkId\n" +
                "          in: path\n" +
                "          description: ID of park to delete\n" +
                "          required: true\n" +
                "          schema:\n" +
                "            type: integer\n" +
                "            format: int64\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: Park deleted\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ParkDeletedResponse' \n" +
                "        '404':\n" +
                "          description: Park doesnt exist\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ResponseError'  \n" +
                "  /api/parks/{parkId}/animals:\n" +
                "    get:\n" +
                "      tags:\n" +
                "        - Park REST Controller\n" +
                "      summary: Find animals of park\n" +
                "      description: Returns animals of park\n" +
                "      operationId: getAnimalsByPark\n" +
                "      parameters:\n" +
                "        - name: parkId\n" +
                "          in: path\n" +
                "          description: ID of park\n" +
                "          required: true\n" +
                "          schema:\n" +
                "            type: integer\n" +
                "            format: int64\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: Fetched animals for park with parkId\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/AnimalsResponse'       \n" +
                "        '404':\n" +
                "          description: Park not found\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ResponseError'  \n" +
                "  /api/parks/{parkId}/counties:\n" +
                "    get:\n" +
                "      tags:\n" +
                "        - Park REST Controller\n" +
                "      summary: Find counties of park\n" +
                "      description: Returns counties of park\n" +
                "      operationId: getCountiesByPark\n" +
                "      parameters:\n" +
                "        - name: parkId\n" +
                "          in: path\n" +
                "          description: ID of park\n" +
                "          required: true\n" +
                "          schema:\n" +
                "            type: integer\n" +
                "            format: int64\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: Fetched counties for park with parkId\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/CountiesResponse'           \n" +
                "        '404':\n" +
                "          description: Park not found\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ResponseError'  \n" +
                "  /api/parks/{parkId}/peak:\n" +
                "    get:\n" +
                "      tags:\n" +
                "        - Park REST Controller\n" +
                "      summary: Find peak of park\n" +
                "      description: Returns peak of park\n" +
                "      operationId: getPeakByParkID\n" +
                "      parameters:\n" +
                "        - name: parkId\n" +
                "          in: path\n" +
                "          description: ID of park\n" +
                "          required: true\n" +
                "          schema:\n" +
                "            type: integer\n" +
                "            format: int64\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: Fetched peak for park with parkId\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/PeakResponse'\n" +
                "        '404':\n" +
                "          description: Peak not found\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/ResponseError'  \n" +
                "  /api:\n" +
                "    get:\n" +
                "      tags:\n" +
                "        - API Controller\n" +
                "      summary: API\n" +
                "      description: Returns openapi specification\n" +
                "      operationId: getAPI\n" +
                "      responses:\n" +
                "        '200':\n" +
                "          description: Successful response\n" +
                "components:\n" +
                "  schemas:\n" +
                "    ParksResponse:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        status:\n" +
                "          type: string\n" +
                "          example: OK\n" +
                "        message:\n" +
                "          type: string\n" +
                "          example: Fetched all parks\n" +
                "        response:\n" +
                "          type: array\n" +
                "          format: list\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/ParkResponseDto'\n" +
                "    ParkResponse:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        status:\n" +
                "          type: string\n" +
                "          example: OK\n" +
                "        message:\n" +
                "          type: string\n" +
                "          example: Fetched park with parkId\n" +
                "        response:\n" +
                "          $ref: '#/components/schemas/ParkResponseDto'\n" +
                "    ParkDeletedResponse:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        status:\n" +
                "          type: string\n" +
                "          example: OK\n" +
                "        message:\n" +
                "          type: string\n" +
                "          example: Park deleted\n" +
                "        response:\n" +
                "          example: null\n" +
                "    PeakResponse:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        status:\n" +
                "          type: string\n" +
                "          example: OK\n" +
                "        message:\n" +
                "          type: string\n" +
                "          example: Fetched peak for park with parkId\n" +
                "        response:\n" +
                "          $ref: '#/components/schemas/HighestPeakResponseDto'\n" +
                "    AnimalsResponse:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        status:\n" +
                "          type: string\n" +
                "          example: OK\n" +
                "        message:\n" +
                "          type: string\n" +
                "          example: Fetched animals for park with parkId\n" +
                "        response:\n" +
                "          type: array\n" +
                "          format: list\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/AnimalResponseDto'\n" +
                "    CountiesResponse:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        status:\n" +
                "          type: string\n" +
                "          example: OK\n" +
                "        message:\n" +
                "          type: string\n" +
                "          example: Fetched counties for park with parkId\n" +
                "        response:\n" +
                "          type: array\n" +
                "          format: list\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/CountyResponseDto'        \n" +
                "    ResponseError:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        status:\n" +
                "          type: string\n" +
                "        message:\n" +
                "          type: string\n" +
                "        response:\n" +
                "          type: string\n" +
                "    CreateParkRequest:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        parkName:\n" +
                "          type: string\n" +
                "        typeOfParkName:\n" +
                "          type: string\n" +
                "        yearOfFoundation:\n" +
                "          type: integer\n" +
                "        area:\n" +
                "          type: number\n" +
                "          format: double\n" +
                "        peak:\n" +
                "          type: object\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/HighestPeakDto'\n" +
                "        counties:\n" +
                "          type: array\n" +
                "          format: list\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/CountyDto'\n" +
                "        atraction:\n" +
                "          type: string\n" +
                "        event:\n" +
                "          type: string\n" +
                "        animals:\n" +
                "          type: array\n" +
                "          format: list\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/AnimalDto'\n" +
                "    ParkResponseDto:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        parkId:\n" +
                "          type: integer\n" +
                "        parkName:\n" +
                "          type: string\n" +
                "        typeOfParkName:\n" +
                "          type: string\n" +
                "        yearOfFoundation:\n" +
                "          type: integer\n" +
                "        area:\n" +
                "          type: number\n" +
                "          format: double\n" +
                "        peak:\n" +
                "          type: object\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/HighestPeakResponseDto'\n" +
                "        counties:\n" +
                "          type: array\n" +
                "          format: list\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/CountyResponseDto'\n" +
                "        atraction:\n" +
                "          type: string\n" +
                "        event:\n" +
                "          type: string\n" +
                "        animals:\n" +
                "          type: array\n" +
                "          format: list\n" +
                "          items:\n" +
                "            $ref: '#/components/schemas/AnimalResponseDto'\n" +
                "    HighestPeakResponseDto:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        peakID:\n" +
                "          type: integer\n" +
                "        peakName:\n" +
                "          type: string\n" +
                "        peakHeight:\n" +
                "          type: integer\n" +
                "    AnimalResponseDto:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        animalID:\n" +
                "          type: integer\n" +
                "        animalName:\n" +
                "          type: string\n" +
                "        speciesOfAnimal:\n" +
                "          type: string\n" +
                "    CountyResponseDto:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        countyID:\n" +
                "          type: integer\n" +
                "        countyName:\n" +
                "          type: string\n" +
                "    HighestPeakDto:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        peakName:\n" +
                "          type: string\n" +
                "        peakHeight:\n" +
                "          type: integer\n" +
                "    AnimalDto:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        animalName:\n" +
                "          type: string\n" +
                "        speciesOfAnimal:\n" +
                "          type: string\n" +
                "    CountyDto:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        countyName:\n" +
                "          type: string\n" +
                "  requestBodies:\n" +
                "    Park:\n" +
                "      description: Park object that needs to be created or updated\n" +
                "      content:\n" +
                "        application/json:\n" +
                "          schema:\n" +
                "            $ref: '#/components/schemas/CreateParkRequest'";

        return ResponseEntity.ok(openapi);
    }


}

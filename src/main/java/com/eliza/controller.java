package com.eliza;

import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;



@RestController
public class controller {

    private service service;

    /**
     * @param service
     */
    public controller(service service) {
        this.service = service;
    }

    /**
     * @param xml
     * @return
     */
    @PostMapping(
            value = "/convert",
            consumes = "text/xml",
            produces = "text/x-yaml"
    )
    public ResponseEntity<String> convert(
            @RequestBody String xml) {
        try {
            //convert xml to map using service
            Map<String, Object> data = service.xmlToMap(xml);
            //convert map to yaml using service
            String yaml = service.mapToYaml(data);
            return new ResponseEntity<>(yaml, HttpStatus.OK);
        } catch (JsonParseException | XMLStreamException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

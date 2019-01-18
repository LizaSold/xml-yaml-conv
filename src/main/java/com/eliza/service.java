package com.eliza;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.xmldeser.ArrayInferringUntypedObjectDeserializer;
import com.google.xmldeser.RootSniffingXMLStreamReader;


@Service
public class service {

    private XmlMapper xmlMapper;

    private YAMLMapper yamlMapper;

    public service() {
        xmlMapper = new XmlMapper();
        Module module = new SimpleModule().addDeserializer(
                Object.class, new ArrayInferringUntypedObjectDeserializer()
        );
        xmlMapper.registerModule(module);
        yamlMapper = new YAMLMapper();
    }

    //convert xml to map

    /**
     * @param xml
     * @return
     * @throws IOException
     * @throws XMLStreamException
     */
    public Map<String, Object> xmlToMap(String xml) throws IOException, XMLStreamException {
        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        // create XML deserializer using DinoCheisa
        RootSniffingXMLStreamReader reader = new RootSniffingXMLStreamReader(
                XMLInputFactory.newFactory().createXMLStreamReader(stream)
        );
        Map<String, Object> data = xmlMapper.readValue(reader, Map.class);
        Map<String, Object> dataWithRoot = new HashMap<>();
        dataWithRoot.put(reader.getLocalNameForRootElement(), data);
        return dataWithRoot;
    }

    //convert map to yaml

    /**
     * @param data
     * @return
     * @throws IOException
     */
    public String mapToYaml(Map<String, Object> data) throws IOException {
        StringWriter stringWriter = new StringWriter();
        yamlMapper.writeValue(stringWriter, data);
        String yaml = stringWriter.toString();
        // remove dashes
        yaml = yaml.substring(yaml.indexOf("---") + 4);
        return yaml;
    }

}

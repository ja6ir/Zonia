package com.zonia.zonia.service;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Service
public class TikaService {

    public String extractTextFromFile(String filePath) throws IOException, TikaException, SAXException {
        Path path = Paths.get(filePath);
        try (InputStream inputStream = Files.newInputStream(path)) {
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            ParseContext parseContext = new ParseContext();
            parser.parse(inputStream, handler, metadata, parseContext);
            System.out.println("hand" +handler.toString());
            return handler.toString();
        }
    }
}
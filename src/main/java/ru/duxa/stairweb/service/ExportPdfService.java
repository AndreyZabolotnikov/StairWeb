package ru.duxa.stairweb.service;

import java.io.ByteArrayInputStream;
import java.util.Map;

public interface ExportPdfService {
    ByteArrayInputStream exportPdf(String templateName, Map<String, Object> data);
}
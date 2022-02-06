package com.testforcentralbank.printerdispatcher.service;

import com.testforcentralbank.printerdispatcher.documents_base.Document;
import com.testforcentralbank.printerdispatcher.documents_base.enums_property_document.EnumSortDocument;

import java.util.List;

public interface ManagerService {
    String sendDocument(Document document);

    void sendStop();

    String sendStart();

    String cancelDocument(String name);

    List<Document> stopPrintingAndGetCanceledDocuments();

    List<Document> getPrintedDocuments(EnumSortDocument enumSortDocument, String order);

    List<Document> getUnprintedDocuments(EnumSortDocument enumSortDocument, String order);

    List<Document> getSortedDocuments(List<Document> documents, EnumSortDocument enumSortDocument, String order);

    long getAverageDurationPrintedDocuments();

    Document getDocumentByType(String type);

    void clear();
}


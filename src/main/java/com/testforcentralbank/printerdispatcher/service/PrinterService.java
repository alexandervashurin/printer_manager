package com.testforcentralbank.printerdispatcher.service;

import com.testforcentralbank.printerdispatcher.documents_base.Document;

import java.util.List;

public interface PrinterService {
    void processDocument(Document document);

    List<Document> getRemovedDocuments();

    long getAverageDuration();
}

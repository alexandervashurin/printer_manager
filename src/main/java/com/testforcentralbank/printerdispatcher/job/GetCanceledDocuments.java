package com.testforcentralbank.printerdispatcher.job;
import com.testforcentralbank.printerdispatcher.documents_base.Document;
import com.testforcentralbank.printerdispatcher.service.impl.PrinterServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
public class GetCanceledDocuments implements Callable<List<Document>> {

    private final PrinterServiceImpl manager;

    @Override
    public List<Document> call() {
        return manager.getCanceledDocuments();
    }
}

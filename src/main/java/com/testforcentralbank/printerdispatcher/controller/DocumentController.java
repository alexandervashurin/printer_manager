package com.testforcentralbank.printerdispatcher.controller;

import com.testforcentralbank.printerdispatcher.documents_base.Document;
import com.testforcentralbank.printerdispatcher.documents_base.enums_property_document.EnumSortDocument;
import com.testforcentralbank.printerdispatcher.service.impl.ManagerServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {
    private final ManagerServiceImpl service;

  @ApiOperation(value = "Остановить диспетчер и получить список ненапечатанных документов")
    @GetMapping(path = "stop-manager")
    public Collection<Document> stopManager() {
        return service.stopPrintingAndGetCanceledDocuments();
    }

 @ApiOperation(value = "Запустить диспетчер")
    @GetMapping(path = "start-manager")
    public String startManager() {
        return service.sendStart();
    }

  @ApiOperation(value = "Принять документ на печать")
    @PostMapping(consumes = {"application/json"}, path = "print")
    public String printFile(@Validated @RequestBody @NotNull Document document) {
        log.info("Document accepted for printing" + document.getName());
        return service.sendDocument(document);
    }

  @ApiOperation(value = "Отменить печать принятого документа, если он еще не был напечатан")
    @GetMapping(path = "cancel-document-by-name")
    public String cancelPrintingDocument(@RequestParam String nameDocument) {
        return service.cancelDocument(nameDocument);
    }

 @ApiOperation(value = "Получить отсортированный список напечатанных документов")
    @GetMapping(path = "get-printed-list")
    public Collection<Document> printedList(@RequestParam(required = false, defaultValue = "DEFAULT") EnumSortDocument enumSortDocument,
                                      @RequestParam(required = false, defaultValue = "") String order) {
        return service.getPrintedDocuments(enumSortDocument, order);
    }

  @ApiOperation(value = "Получить отсортированный список ненапечатанных документов")
    @GetMapping(path = "get-canceled-list")
    public Collection<Document> canceledList(@RequestParam(required = false, defaultValue = "DEFAULT") EnumSortDocument enumSortDocument,
                                       @RequestParam(required = false, defaultValue = "") String order) {
        return service.getUnprintedDocuments(enumSortDocument, order);
    }

  @ApiOperation(value = "Рассчитать среднюю продолжительность печати напечатанных документов")
    @GetMapping(path = "get-average-duration")
    public String getAverageDurationPrinting() {
        return "Средняя продолжительность печатания напечатанных документов " +
                service.getAverageDurationPrintedDocuments() + " (мс)";
    }

 @ApiOperation(value = "Получить документ по типу")
    @GetMapping(path = "get-document-by-type")
    public Document getDocumentByType(@RequestParam(required = false, defaultValue = "") String type) {
        return service.getDocumentByType(type);
    }

   @ApiOperation(value = "Очистить списки документов")
    @GetMapping(path = "clear")
    public void clear() {
        service.clear();
    }
}

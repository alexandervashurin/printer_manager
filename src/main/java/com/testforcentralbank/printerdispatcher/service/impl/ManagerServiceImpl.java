package com.testforcentralbank.printerdispatcher.service.impl;
import com.testforcentralbank.printerdispatcher.documents_base.Document;
import com.testforcentralbank.printerdispatcher.documents_base.enums_property_document.EnumSizeDocument;
import com.testforcentralbank.printerdispatcher.documents_base.enums_property_document.EnumSortDocument;
import com.testforcentralbank.printerdispatcher.documents_base.enums_property_document.EnumTypeDocument;
import com.testforcentralbank.printerdispatcher.documents_base.compare.DocCompareDuration;
import com.testforcentralbank.printerdispatcher.documents_base.compare.DocCompareName;
import com.testforcentralbank.printerdispatcher.documents_base.compare.DocCompareSize;
import com.testforcentralbank.printerdispatcher.documents_base.compare.DocCompareType;
import com.testforcentralbank.printerdispatcher.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    // Храним принтеры в managers, в данном случае есть дефолтный принтер на который мы направляем документы
    // Можно расширить функционал и добавить другие принтеры, и в роуты добавить явное указание принтера
    // на который отправляются документы на печать
    private final HashMap<String, PrinterServiceImpl> printers = new HashMap<>();
    private static final String DEFAULT_PRINTER = "Принтер по умолчанию";
    private static final String RESPONSE_FOR_START = "Принтер запущен";
    private static final String RESPONSE_FOR_SEND_DOCUMENT_SUCCESS = "Документ отправлен на печать";
    private static final String RESPONSE_FOR_SEND_DOCUMENT_FAIL = "Принтер не запущен, необходимо запустить принтер," +
            " чтобы отправить документ на печать";
    private static final String RESPONSE_FOR_CANCEL_DOCUMENT = "Документ не будет напечатан," +
            " если он находится в очереди печати";

    {
        printers.put(DEFAULT_PRINTER, new PrinterServiceImpl());
    }

    public String sendDocument(Document document) {
        if (printers.get(DEFAULT_PRINTER).isWorking()) {
            printers.get(DEFAULT_PRINTER).processDocument(document);

            return RESPONSE_FOR_SEND_DOCUMENT_SUCCESS;
        }

        return RESPONSE_FOR_SEND_DOCUMENT_FAIL;
    }

    public void sendStop() {
        printers.get(DEFAULT_PRINTER).setWorking(false);
    }

    public String sendStart() {
        printers.get(DEFAULT_PRINTER).setWorking(true);
        log.info(RESPONSE_FOR_START);
        return RESPONSE_FOR_START;
    }

    public String cancelDocument(String name) {
        log.info("Отмена печати документа с названием: " + name);
        printers.get(DEFAULT_PRINTER).getMarkCanceledDocuments().add(name);
        return RESPONSE_FOR_CANCEL_DOCUMENT;
    }

    public List<Document> stopPrintingAndGetCanceledDocuments() {
        log.info("Завершение печати документов");
        sendStop();
        return printers.get(DEFAULT_PRINTER).getRemovedDocuments();
    }

    public List<Document> getPrintedDocuments(EnumSortDocument enumSortDocument, String order) {
        log.info("Получение напечатанных документов");
        return getSortedDocuments(printers.get(DEFAULT_PRINTER).getPrintedDocuments(), enumSortDocument, order);
    }

    public List<Document> getUnprintedDocuments(EnumSortDocument enumSortDocument, String order) {
        log.info("Получение ненапечатанных документов");
        return getSortedDocuments(printers.get(DEFAULT_PRINTER).getCanceledDocuments(), enumSortDocument, order);
    }

    public List<Document> getSortedDocuments(List<Document> documents, EnumSortDocument enumSortDocument,
                                             String order) {
        log.info("Передан тип сортировки: " + enumSortDocument.getName());

        switch (enumSortDocument) {
            case DURATION:
                documents.sort(new DocCompareDuration());
                break;
            case NAME:
                documents.sort(new DocCompareName());
                break;
            case SIZE:
                documents.sort(new DocCompareSize());
                break;
            case TYPE:
                documents.sort(new DocCompareType());
                break;
            case DEFAULT:
                break;

        }

        if (order.equals("desc")) {
            Collections.reverse(documents);
            log.info("Используется DESC");
        }

        return documents;
    }

    public long getAverageDurationPrintedDocuments() {
        return printers.get(DEFAULT_PRINTER).getAverageDuration();
    }

    public Document getDocumentByType(String type) {
        switch (type) {
            case ("smallest"):
                return new Document(1000, "smallest", EnumTypeDocument.TYPE_0, EnumSizeDocument.A0);
            case ("small"):
                return new Document(2500, "small", EnumTypeDocument.TYPE_0, EnumSizeDocument.A1);
            case ("medium"):
                return new Document(6500, "medium", EnumTypeDocument.TYPE_2, EnumSizeDocument.A3);
            case ("large"):
                return new Document(9000, "large", EnumTypeDocument.TYPE_3, EnumSizeDocument.A4);
            case ("largest"):
                return new Document(11000, "largest", EnumTypeDocument.TYPE_4, EnumSizeDocument.A5);
            default:
                return new Document(5000, "regular", EnumTypeDocument.TYPE_1, EnumSizeDocument.A2);
        }
    }

    @Override
    public void clear() {
        printers.get(DEFAULT_PRINTER).getPrintedDocuments().clear();
        printers.get(DEFAULT_PRINTER).getCanceledDocuments().clear();
        printers.get(DEFAULT_PRINTER).getMarkCanceledDocuments().clear();
    }
}

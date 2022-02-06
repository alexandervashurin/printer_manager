package com.testforcentralbank.printerdispatcher.documents_base.compare;
import com.testforcentralbank.printerdispatcher.documents_base.Document;
import javax.validation.constraints.NotNull;
import java.util.Comparator;

public class DocCompareSize implements Comparator<Document> {
    @Override
    public int compare(@NotNull Document document, @NotNull Document t1) {
        // Считаем документы прямоугольниками, поэтому можем их сравнить по сумме длины и ширины, то есть полупериметр
        int totalOfDimensionsDocument = document.getSize().getHeight() + document.getSize().getWidth();
        int totalOfDimensionsT1 = t1.getSize().getHeight() + t1.getSize().getWidth();

        return Integer.compare(totalOfDimensionsDocument, totalOfDimensionsT1);
    }
}

package com.testforcentralbank.printerdispatcher.documents_base.compare;
import com.testforcentralbank.printerdispatcher.documents_base.Document;
import javax.validation.constraints.NotNull;
import java.util.Comparator;

public class DocCompareName implements Comparator<Document> {
    @Override
    public int compare(@NotNull Document document, @NotNull Document t1) {
        return document.getName().compareTo(t1.getName());
    }
}

package com.testforcentralbank.printerdispatcher.documents_base.compare;
import com.testforcentralbank.printerdispatcher.documents_base.Document;
import javax.validation.constraints.NotNull;
import java.util.Comparator;

public class DocCompareType implements Comparator<Document> {
    @Override
    public int compare(@NotNull Document document, @NotNull Document t1) {
        return Integer.compare(document.getType().getOrdinal(), t1.getType().getOrdinal());
    }
}

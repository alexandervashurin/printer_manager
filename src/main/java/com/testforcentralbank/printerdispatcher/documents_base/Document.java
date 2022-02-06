package com.testforcentralbank.printerdispatcher.documents_base;
import com.testforcentralbank.printerdispatcher.documents_base.enums_property_document.EnumSizeDocument;
import com.testforcentralbank.printerdispatcher.documents_base.enums_property_document.EnumTypeDocument;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
public class Document {

    @NotNull
    private final long duration;
    @NotNull
    private final String name;
    @NotNull
    private final EnumTypeDocument type;
    @NotNull
    private final EnumSizeDocument size;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return duration == ((Document) o).duration &&
                Objects.equals(name, ((Document) o).name) &&
                type == ((Document) o).type &&
                size == ((Document) o).size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, name, type, size);
    }
}


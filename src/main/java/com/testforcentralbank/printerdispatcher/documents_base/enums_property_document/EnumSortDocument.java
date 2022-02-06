package com.testforcentralbank.printerdispatcher.documents_base.enums_property_document;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumSortDocument {

    DURATION("DURATION"),
    NAME("NAME"),
    SIZE("SIZE"),
    TYPE("TYPE"),
    DEFAULT("DEFAULT");

    private final String name;
}

package cl.mineduc.sidep.sostenedorapi.enums;

public enum Order {

    ASC("ASC"),
    DESC("DESC");


    private final String desc;

    Order(String desc) {
        this.desc = desc;
    }
}

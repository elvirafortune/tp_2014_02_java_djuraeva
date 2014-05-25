package utils.resources;

/**
 * Created by elvira on 19.04.14.
 */
public class InfoMessages implements Resource {
    private String WRONG_PASSWORD;
    private String EMPTY_FIELDS;
    private String DUPLICATE_LOGIN;
    private String NOT_REGISTERED;

    public String getDB_UNREACHABLE() {
        return DB_UNREACHABLE;
    }

    public String getNOT_REGISTERED() {
        return NOT_REGISTERED;
    }

    public String getDUPLICATE_LOGIN() {
        return DUPLICATE_LOGIN;
    }

    public String getEMPTY_FIELDS() {
        return EMPTY_FIELDS;
    }

    public String getWRONG_PASSWORD() {
        return WRONG_PASSWORD;
    }

    private String DB_UNREACHABLE;
}

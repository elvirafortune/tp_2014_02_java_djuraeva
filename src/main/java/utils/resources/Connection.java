package utils.resources;

/**
 * Created by elvira on 19.04.14.
 */
public class Connection implements Resource {
    int PORT;
    int TEST_PORT;
    String DB;
    String TEST_DB;

    public String getTEST_DB() {
        return TEST_DB;
    }

    public String getDB() {
        return DB;
    }

    public int getTEST_PORT() {
        return TEST_PORT;
    }

    public int getPORT() {
        return PORT;
    }


}

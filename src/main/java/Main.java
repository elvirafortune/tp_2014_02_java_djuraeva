import database.HibernateSettings.HibernateUtil;
import org.eclipse.jetty.server.Server;
import server.ServerConnection;
import utils.VFS.VFS;
import utils.resources.Connection;
import utils.resources.Resource;
import utils.resources.URL;

import java.util.Iterator;

/*
 * Created by elvira on 15.02.14.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        VFS vfs = new VFS("");
        Iterator<String> files = vfs.getIterator("data");
        while (files.hasNext()){
            String nextFile = files.next();
            if (!vfs.isDirectory(nextFile)){
                utils.resources.Resources.getInstance().addResource(
                        nextFile,
                        utils.resources.Resources.getInstance().get(nextFile)
                );
            }
        }

        HibernateUtil util = new HibernateUtil();
        Connection con = (Connection)utils.resources.Resources.getInstance().getResource("data/connection.xml");
        Server server = ServerConnection.connect(con.getPORT(), util);
        server.start();
        server.join();
    }

}

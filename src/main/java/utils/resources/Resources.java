package utils.resources;

import java.util.HashMap;
import java.util.Map;

import static utils.SaxReader.readXML;

/**
 * Created by elvira on 19.04.14.
 */
public class Resources {
    private static Resources instance;
    private Map<String, Resource> resources = new HashMap<>();

    private Resources(){
    }

    public static Resources getInstance(){
        if (instance == null){
            instance = new Resources();
        }
        return instance;
    }

    public Resource getResource(String path) {
        Resource res = instance.resources.get(path);
        if (res != null)
            return res;
        addResource(path, get(path));
        return getResource(path);
    }

    public void addResource(String path, Resource resource){
        instance.resources.put(path, resource);
    }

    public Resource get(String path){
        return (Resource)readXML(path);
    }
}

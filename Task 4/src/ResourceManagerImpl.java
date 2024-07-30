import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ResourceManagerImpl extends UnicastRemoteObject implements ResourceManager {
    private Map<String, String> resources;

    protected ResourceManagerImpl() throws RemoteException {
        super();
        resources = new HashMap<>();
        resources.put("resource1", "This is resource 1");
        resources.put("resource2", "This is resource 2");
    }

    @Override
    public String getResource(String resourceName) throws RemoteException {
        return resources.getOrDefault(resourceName, "Resource not found");
    }
}

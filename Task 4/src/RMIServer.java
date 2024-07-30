import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            ResourceManager resourceManager = new ResourceManagerImpl();
            LocateRegistry.createRegistry(1099); // Start the RMI registry on port 1099
            Naming.rebind("rmi ://localhost/ResourceManager", resourceManager);
            System.out.println("Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

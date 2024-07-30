import java.rmi.Naming;

public class RMIClient {
    public static void main(String[] args) {
        try {
            ResourceManager resourceManager = (ResourceManager) Naming.lookup("rmi://localhost/ResourceManager");
            String response = resourceManager.getResource("resource1");
            System.out.println("Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

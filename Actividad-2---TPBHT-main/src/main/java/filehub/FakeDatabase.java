package filehub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeDatabase {

    private static final List<User> users = new ArrayList<>();
    private static final List<String> branches = Arrays.asList(
            "Carpeta_A",
            "Carpeta_B",
            "Carpeta_C"
    );

    static {
        users.add(new User("admin", "admin123", "Administrador General", Role.ADMIN, null));
        users.add(new User("consult", "consult123", "Usuario Consultor", Role.CONSULTANT, null));
        users.add(new User("store1", "store123", "Encargado Carpeta A", Role.STORE_MANAGER, "Carpeta_A"));
    }

    public static User validateLogin(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public static java.util.List<String> getBranches() {
        return branches;
    }
}

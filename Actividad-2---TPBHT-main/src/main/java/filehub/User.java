package filehub;

public class User {

    private String username;
    private String password;
    private String fullName;
    private Role role;
    private String branch;

    public User(String username, String password, String fullName, Role role, String branch) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.branch = branch;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getFullName() { return fullName; }

    public Role getRole() { return role; }

    public String getBranch() { return branch; }
}

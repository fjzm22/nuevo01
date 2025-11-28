package filehub;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Label lblMessage;

    @FXML
    private void onLoginClicked() {
        String u = txtUser.getText().trim();
        String p = txtPass.getText().trim();

        if (u.isEmpty() || p.isEmpty()) {
            lblMessage.setText("Completa usuario y contraseña.");
            return;
        }

        User user = FakeDatabase.validateLogin(u, p);
        if (user == null) {
            lblMessage.setText("Credenciales inválidas.");
        } else {
            FileManager.ensureBranchStructure(FakeDatabase.getBranches());
            Main.showUploadView(user);
        }
    }
}

package filehub;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class UploadController {

    @FXML
    private ComboBox<String> comboBranch;

    @FXML
    private Label lblFile;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblStatus;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button btnChoose;

    @FXML
    private Button btnUpload;

    @FXML
    private Button btnDownload;

    private User currentUser;
    private File selectedFile;

    public void initialize() {
        progressBar.setVisible(false);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;

        lblUser.setText("Usuario: " + user.getFullName());

        comboBranch.getItems().clear();

        // ADMIN — puede ver todo
        if (user.getRole() == Role.ADMIN) {
            comboBranch.getItems().addAll(FakeDatabase.getBranches());
            btnChoose.setVisible(true);
            btnUpload.setVisible(true);
            btnDownload.setVisible(true);
        }

        // CONSULTOR — puede subir, NO descargar
        else if (user.getRole() == Role.CONSULTANT) {
            comboBranch.getItems().addAll(FakeDatabase.getBranches());
            btnChoose.setVisible(true);
            btnUpload.setVisible(true);
            btnDownload.setVisible(false);
        }

        // STORE — solo su carpeta, solo descarga
        else if (user.getRole() == Role.STORE_MANAGER) {
            comboBranch.getItems().add(user.getBranch());
            btnChoose.setVisible(false);
            btnUpload.setVisible(false);
            btnDownload.setVisible(true);
        }

        comboBranch.getSelectionModel().selectFirst();
    }

    // ------- SELECCIONAR ARCHIVO (Administradores y Consultores)
    @FXML
    private void onChooseFileClicked() {
        if (currentUser.getRole() == Role.STORE_MANAGER)
            return;

        Window window = comboBranch.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar archivo");
        File file = chooser.showOpenDialog(window);

        if (file != null) {
            selectedFile = file;
            lblFile.setText("Archivo: " + file.getName());
        }
    }

    // ------- SUBIR ARCHIVO (solo Admin y Consultor)
    @FXML
    private void onUploadClicked() {
        if (currentUser.getRole() == Role.STORE_MANAGER) {
            lblStatus.setText("No tienes permiso para subir archivos.");
            return;
        }

        if (selectedFile == null) {
            lblStatus.setText("Selecciona un archivo primero.");
            return;
        }

        String branch = comboBranch.getSelectionModel().getSelectedItem();

        try {
            progressBar.setVisible(true);
            progressBar.setProgress(0.3);

            FileManager.saveFileToBranch(selectedFile, branch);

            progressBar.setProgress(1.0);
            lblStatus.setText("Archivo subido correctamente a '" + branch + "'.");
            selectedFile = null;

        } catch (Exception ex) {
            lblStatus.setText("Error al subir archivo: " + ex.getMessage());
        }
    }

    // ------- DESCARGAR ARCHIVO (solo Admin y Store)
    @FXML
    private void onDownloadClicked() {

        if (currentUser.getRole() == Role.CONSULTANT) {
            lblStatus.setText("No tienes permisos para descargar archivos.");
            return;
        }

        String branch = comboBranch.getValue();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar archivo para descargar desde " + branch);

        File folder = new File("filehub_files/" + branch);
        if (folder.exists()) {
            chooser.setInitialDirectory(folder);
        }

        File file = chooser.showOpenDialog(comboBranch.getScene().getWindow());

        if (file != null) {
            lblStatus.setText("Archivo disponible en: " + file.getAbsolutePath());
        }
    }

    @FXML
    private void onLogoutClicked() {
        Main.showLoginView();
    }
}

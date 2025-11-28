package filehub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setTitle("FileHubFX");
        showLoginView();
        stage.show();
    }

    public static void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("login_view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showUploadView(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("upload_view.fxml"));
            Parent root = loader.load();
            UploadController controller = loader.getController();
            controller.setCurrentUser(user);
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package source;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class IconImageUtilities {
    public static void setIconImage(Stage window)
    {
        window.getIcons().add(new Image("/graphics/icon/Schema-ikon@0,25x.png"));

    }
}

package source;



import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static source.IconImageUtilities.setIconImage;

public class Main extends Application {


    final static int HEIGHT = 627;
    final static int WIDTH = 900;


    @Override
    public void start(Stage primaryStage) {


        final int button_height = 125;

        // Create Toggle Group for all icons
        ToggleGroup icons = new ToggleGroup();

        // Create Toggle Buttons and add a spreadsheet to it
        ToggleButton home_btn = new ToggleButton();
        home_btn.getStyleClass().addAll("toggle-button", "home-btn");

        // Create button for the option menu.
        ToggleButton schedule_btn = new ToggleButton();
        schedule_btn.getStyleClass().addAll("toggle-button", "schedule-btn");

        // Create preview button
        ToggleButton preview_btn = new ToggleButton();
        preview_btn.getStyleClass().addAll("toggle-button", "preview-btn");
        preview_btn.setSelected(true);


        // Add all icons to icons toggleGroup
        preview_btn.setToggleGroup(icons);
        schedule_btn.setToggleGroup(icons);
        home_btn.setToggleGroup(icons);

        // Create all VBoxes for all the menues
        VBox home_box = new VBox();
        VBox schedule_box = new VBox();
        VBox preview_box = new VBox();

        // setUserData to corresponding boxes
        home_btn.setUserData(home_box);
        schedule_btn.setUserData(schedule_box);
        preview_btn.setUserData(preview_box);

        // Set Style class
        home_box.getStyleClass().add("home-box");
        schedule_box.getStyleClass().add("schedule-box");

        // Create a root node
        HBox root = new HBox();
        // Create a node for all icons
        VBox button_pane = new VBox();
        button_pane.getStyleClass().add("pane");
        // Create a menu pane
        Pane menu_root = new Pane();
        menu_root.getStyleClass().add("menu-root");


        // Add icons to button_pane and add it to root
        button_pane.getChildren().addAll(preview_btn, schedule_btn, home_btn);
        root.getChildren().add(button_pane);


        Scene scene = new Scene(root, WIDTH, HEIGHT);

        createMenu(home_box, schedule_box, preview_box);
        root.getChildren().add(menu_root);

        // Add Stylesheet to everything
        scene.getStylesheets().add("/graphics/style/style.css");

        // Set window icon
        setIconImage(primaryStage);


        // Create listener for when toggle changes
        icons.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {

                try {
                    // Go to the buttons corresponding menu
                    menu_root.getChildren().clear();
                    menu_root.getChildren().add((Node) icons.getSelectedToggle().getUserData());
                } catch (NullPointerException e) {
                    // If no button is selected do nothing
                    toggle.setSelected(true);
                }
            }
        });

        // Set home_box as default menu when the program is launched
        menu_root.getChildren().add(preview_box);

        // Set window options
        primaryStage.setResizable(false);
        primaryStage.setTitle("Schema");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createMenu(VBox home_box, VBox schedule_box, VBox preview_box) {
        Label title = new Label("Schema");
        title.getStyleClass().add("h1");

        // Create ImageView fot the schedule
        Image schema = getSchedule();
        ImageView schema_view = new ImageView();
        schema_view.setImage(schema);
        schema_view.getStyleClass().add("schema");

        // Make a title and description
        Label description = new Label();
        description.getStyleClass().add("description");
        description.setText("Detta är en applikation som tar schemat från novasoftware och ger det direkt till dig\n" +
                "genom att spara dina inställningar så behöver du inte skriva in dem varje gång du vill få ditt schema\n" +
                "\n" +
                "-Fabian Beskow");

        home_box.getChildren().addAll(title, description);

        preview_box.getChildren().add(schema_view);

        Label opt_title = new Label("Inställnigar");
        opt_title.getStyleClass().add("h1");

        TextField class_input = new TextField();
        class_input.setPromptText("Din klass");

        // ...

        schedule_box.getChildren().addAll(opt_title ,class_input);

    }

    private Image getSchedule() {
        // Create Calendar and get current week number
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);

        int week_number;

        // If it's the weekend, show next weeks schedule
        if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            week_number = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
        }else {
            week_number = calendar.get(Calendar.WEEK_OF_YEAR);
        }

        System.out.print("WEEK: " + week_number);


        // Get the schedule from
        Image schema = new Image("http://www.novasoftware.se/ImgGen/schedulegenerator.aspx?format=png&schoolid=80220/sv-se&type=-1&id=1a&period=&week=" +
                week_number +"&mode=0&printer=0&colors=32&head=0&clock=0&foot=0&day=0&width=760&height=620&maxwidth=1885&maxheight=793",
                760, 620, false, false);
        return schema;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
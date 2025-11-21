package com.myapp.controller;
package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    private Label label;

    @FXML
    private void handleClick() {
        label.setText("Clicked ✅");
    }
}


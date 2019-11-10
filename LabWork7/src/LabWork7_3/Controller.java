package LabWork7_3;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class Controller
{
    @FXML
    private ComboBox firstPhrase, secondPhrase, thirdPhrase;
    @FXML
    private TextArea textOut;

    @FXML
    private void close(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    @FXML
    private void addPhrase(ActionEvent actionEvent)
    {
        if(firstPhrase.getValue()!=null && secondPhrase.getValue()!=null && thirdPhrase.getValue()!=null)
            textOut.appendText(firstPhrase.getValue().toString()+" "+secondPhrase.getValue().toString()+" "+thirdPhrase.getValue().toString()+"\n");
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Ошибка");
            alert.setContentText("Выберите слова для добавления");
            alert.show();
        }
    }

}

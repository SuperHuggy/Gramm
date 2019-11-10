package LabWork7_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller
{

    @FXML
    private TextField xPoint, yPoint;
    @FXML
    private Text result;


    public void check(ActionEvent actionEvent)
    {
        try
        {
            double x= Double.parseDouble(xPoint.getText()), y=Double.parseDouble(yPoint.getText());
            if( x>-3 && x<0 && y<2*x+6 && y>0 || x>0 && x<3 && x*x+y*y<36)
            {
                result.setText("Точка (" + x + "; " + y + ") лежит в этой области");
            }
            else
                result.setText("Точка ("+x+"; "+y+") не попала в эту область");
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Ошибка");
            alert.setContentText("Некоректный ввод");
            alert.show();
        }
    }
}

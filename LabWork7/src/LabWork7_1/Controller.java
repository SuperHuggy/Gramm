package LabWork7_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Controller
{
    @FXML
    private Text solution1, solution2, discriminant;
    @FXML
    private TextField a,b,c;
    @FXML
    private Button solve;

    @FXML
    private void click(ActionEvent event)
    {
        try
        {
            BigDecimal a = new BigDecimal(this.a.getText()), b = new BigDecimal(this.b.getText()), c = new BigDecimal(this.c.getText()), dis = b.multiply(b).subtract(a.multiply(c).multiply(new BigDecimal(4)));
            if(a.equals(new BigDecimal(0)))
            {
                discriminant.setText("отсутствует, уравнение линейно");
                solution1.setText(c.negate().divide(b,MathContext.DECIMAL128).toString());
                solution2.setText(c.negate().divide(b,MathContext.DECIMAL128).toString());
            }
            else
            {
                discriminant.setText(dis.setScale(4, RoundingMode.HALF_UP).toString());
                BigDecimal two = new BigDecimal(2);
                if (dis.compareTo(new BigDecimal(0)) >= 0)
                {
                    solution1.setText((b.negate().add(dis.sqrt(MathContext.DECIMAL128)).divide(a.multiply(two), MathContext.DECIMAL128)).setScale(2, RoundingMode.HALF_UP).toString());
                    solution2.setText((b.negate().subtract(dis.sqrt(MathContext.DECIMAL128)).divide(a.multiply(two), MathContext.DECIMAL128)).setScale(2, RoundingMode.HALF_UP).toString());
                } else
                {
                    System.out.println(a.multiply(two));
                    solution1.setText(b.negate().divide(a.multiply(two), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP) + " + i*" + dis.negate().sqrt(MathContext.DECIMAL128).divide(a.multiply(two), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP));
                    solution2.setText(b.negate().divide(a.multiply(two), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP) + " - i*" + dis.negate().sqrt(MathContext.DECIMAL128).divide(a.multiply(two), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP));
                }
            }
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

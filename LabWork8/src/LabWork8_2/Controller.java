package LabWork8_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller
{
    @FXML
    private Canvas line, rectangle, ellips, triangle;
    @FXML
    private TextField lineStartX, lineStartY, lineEndX, lineEndY,
            rectangleTLX, rectangleTLY, rectangleBRX, rectangleBRY,
            ellipsTLX, ellipsTLY, ellipsBRX, ellipsBRY,
            triangle1X, triangle1Y, triangle2X, triangle2Y, triangle3X, triangle3Y;

    @FXML
    private CheckBox rectangleFill, ellipsFill, triangleFill;

    @FXML
    private void printLine(ActionEvent event)
    {
        GraphicsContext graphicsContext = line.getGraphicsContext2D();
        graphicsContext.setStroke(Color.color(Math.random(),Math.random(),Math.random()));
        try
        {
            graphicsContext.strokeLine(Double.parseDouble(lineStartX.getText()), Double.parseDouble(lineStartY.getText()),
                    Double.parseDouble(lineEndX.getText()), Double.parseDouble(lineEndY.getText()));
        } catch (Exception e)
        {
            alert();
        }
    }

    @FXML
    private void printRectangle(ActionEvent event)
    {
        GraphicsContext graphicsContext = rectangle.getGraphicsContext2D();
        try
        {
            if(rectangleFill.isSelected())
            {
                graphicsContext.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                graphicsContext.fillRect(Double.parseDouble(rectangleTLX.getText()), Double.parseDouble(rectangleTLY.getText()),
                        Double.parseDouble(rectangleBRX.getText()), Double.parseDouble(rectangleBRY.getText()));
            }
            else
            {
                graphicsContext.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
                graphicsContext.strokeRect(Double.parseDouble(rectangleTLX.getText()), Double.parseDouble(rectangleTLY.getText()),
                        Double.parseDouble(rectangleBRX.getText()), Double.parseDouble(rectangleBRY.getText()));
            }
        } catch (Exception e)
        {
            alert();
        }
    }

    @FXML
    private void printEllips(ActionEvent event)
    {
        GraphicsContext graphicsContext = ellips.getGraphicsContext2D();
        graphicsContext.setStroke(Color.color(Math.random(),Math.random(),Math.random()));
        try
        {
            if(ellipsFill.isSelected())
            {
                graphicsContext.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                graphicsContext.fillOval(Double.parseDouble(ellipsTLX.getText()), Double.parseDouble(ellipsTLY.getText()),
                        Double.parseDouble(ellipsBRX.getText()), Double.parseDouble(ellipsBRY.getText()));
            }
            else
            {
                graphicsContext.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
                graphicsContext.strokeOval(Double.parseDouble(ellipsTLX.getText()), Double.parseDouble(ellipsTLY.getText()),
                        Double.parseDouble(ellipsBRX.getText()), Double.parseDouble(ellipsBRY.getText()));
            }
        } catch (Exception e)
        {
            alert();
        }
    }

    @FXML
    private void printTriangle(ActionEvent event)
    {
        GraphicsContext graphicsContext = triangle.getGraphicsContext2D();
        try
        {
            if(triangleFill.isSelected())
            {
                graphicsContext.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                graphicsContext.fillPolygon(new double[]{Double.parseDouble(triangle1X.getText()), Double.parseDouble(triangle2X.getText()), Double.parseDouble(triangle3X.getText())},
                        new double[]{Double.parseDouble(triangle1Y.getText()), Double.parseDouble(triangle2Y.getText()), Double.parseDouble(triangle3Y.getText())}, 3);
            }
            else
            {
                graphicsContext.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
                graphicsContext.strokePolygon(new double[]{Double.parseDouble(triangle1X.getText()), Double.parseDouble(triangle2X.getText()), Double.parseDouble(triangle3X.getText())},
                        new double[]{Double.parseDouble(triangle1Y.getText()), Double.parseDouble(triangle2Y.getText()), Double.parseDouble(triangle3Y.getText())}, 3);
            }
        } catch (Exception e)
        {
            alert();
        }
    }

    private void alert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Ошибка");
        alert.setContentText("Некоректный ввод");
        alert.show();
    }

}

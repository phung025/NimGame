/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nim;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author user
 */
public class NimCoinShape {
    
    private NimCoinShape(){};
    
    public NimCoinShape(Rectangle2D coinBorder, Color coinBorderColor, Rectangle2D coinShape, Color coinColor) {
        _border = coinBorder;
        _shape = coinShape;
        _coinColor = coinColor;
        _coinBorderColor = coinBorderColor;
    }
    
    ////////// COLORS
    
    public void setColor(Color color) {
        _coinColor = color;
    }
    
    public Color getColor() {
        return _coinColor;
    }
    
    public void setBorderColor(Color color) {
        _coinBorderColor = color;
    }
    
    public Color getBorderColor() {
        return _coinBorderColor;
    }
    
    ////////// SHAPE
    
    public Rectangle2D getShape() {
        return _shape;
    }
    
    public Rectangle2D getBorder() {
        return _border;
    }
    
    /**
     * Instance fields
     */
    
    // Shapes
    private Rectangle2D _border;
    private Rectangle2D _shape;
    
    // Colors
    private Color _coinColor;
    private Color _coinBorderColor;
}

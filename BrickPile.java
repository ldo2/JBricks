import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

public class BrickPile {
  private PlayField _pf;
  private Vector _bricks;
  private final int _rows = 4;
  private final int _cols = 10;

  public BrickPile(PlayField pf, Image img) {
    _pf = pf;
    _bricks = new Vector();
    int startx = 80; 
    int x = startx, y = 10;
	
    for (int r = 0; r < _rows; r++) {
      for (int c = 0; c < _cols; c++) {
        Rectangle pos =
          new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
        Brick b = new Brick(_pf, this, img, pos);
        pf.addSprite(b);
        _bricks.addElement(b);
        
        x += img.getWidth(null);
      }
      y += img.getHeight(null) + 2;
      x = startx;
    }
  }

  public int unbrokenCount() {
    int result = 0;
    for (int i = 0; i < _bricks.size(); i++) {
      if ( !((Brick) _bricks.elementAt(i)).isDead() ) 
    	result++; 	
    }	
    return result;
  }

  public int brokenCount() {
  	return _bricks.size() - unbrokenCount();	
  }
}

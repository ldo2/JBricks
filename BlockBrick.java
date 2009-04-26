import java.awt.Image;
import java.awt.Rectangle;

class BlockBrick extends Brick {
  
  public BlockBrick(PlayField pf, BrickPile bp, Image img, Rectangle p) {
    super(pf, bp, img, p);
  }
  
  public void hitBy(Puck p) {
    reflectPuck(p);
    if (_bp.unbrokenCount() == 0) {
      _pf.getMatch().win();
    }
  }
  
  public boolean isBroken() {
    return true;
  }
}
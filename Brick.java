import java.awt.Image;
import java.awt.Rectangle;

public class Brick extends StationarySprite {
  protected BrickPile _bp;
  
  public Brick(PlayField pf, BrickPile bp, Image img, Rectangle p) {
    super(pf, img, p);
    _bp = bp;
  }

  /* Если разбили все кирпичи, тогда игра заканчивается
   * победой игрока.
   */
  public void hitBy(Puck p) {
    _isDead = true;
    p.getVelocity().reverseY();
    if (_bp.unbrokenCount() == 0) {
      _pf.getMatch().win();
    }
  }

  public void update() {
    ;
  }
}

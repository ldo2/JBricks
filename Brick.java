import java.awt.Image;
import java.awt.Rectangle;

public class Brick extends StationarySprite {
  protected transient BrickPile _bp;
  
  public Brick(PlayField pf, BrickPile bp, Image img, Rectangle p) {
    super(pf, img, p);
    _bp = bp;
  }

  /* 
  * Если разбили все кирпичи, тогда игра заканчивается
  * победой игрока.
  */
  public void hitBy(Puck p) {
    _isDead = true;
    reflectPuck(p);
    if (_bp.unbrokenCount() == 0) {
      _pf.getMatch().win();
    }
  }

  protected void reflectPuck(Puck p){
    int px = p.getBounds().x + p.getBounds().width/2; 
    int py = p.getBounds().y + p.getBounds().height/2;
    // L1 - линия содержашая главную диагональ
    // L2 - линия содержащая побочную диагональ
    int y1 = _pos.height*(px-_pos.x)/_pos.width+_pos.y;
    int y2 = _pos.height*((_pos.x-px)/_pos.width+1)+_pos.y;
    int side = (py-y1)*(py-y2);
    if (side > 0){
      p.getVelocity().reverseY();
    } else if (side==0){
      p.getVelocity().reverse();
    } else {
      p.getVelocity().reverseX();
    }
  }
  
  public boolean isBroken() {
    return _isDead;
  }
  
  public void update() {
    ;
  }
  
  public void setBrickPile(BrickPile bp){
    _bp = bp;
  }
}

/**
 * @author tuzov
 **/
import java.awt.Image;
import java.awt.Rectangle;
/* ѕодвижнй спрайт */
abstract class MovableSprite extends Sprite {
  /*
   * @_v - направление и скорость 
   * @_isMoving - состо€ние: "двигаетс€", "недвигаетс€" 
   * @_prevPos - местоположение спрайта до передвижени€
   */
  protected Velocity _v;
  protected boolean _isMoving = false;
  protected Rectangle _prevPos; 

  public MovableSprite(PlayField pf, Image img, Rectangle p, int dir, int sd) {
    super(pf, img, p);
    _v = new Velocity(dir, sd);
  }

  public void setDirection(int dir) {
    _v.setDirection(dir);
  }

  public int getDirection() {
  	return _v.getDirection();
  }

  public Velocity getVelocity() {
    return _v;
  }
  
  public void setVelocity(Velocity v) {
    _v = v;
  }
  
  /* ¬ернуть спрайт с которым произошло соударение */
  public Sprite collideWith() {
    return _pf.testCollision(this);	
  }
  
  abstract public void move();

  public void update() {
    move();
  }

  public void startMoving() {
    _isMoving = true;
  }

  public void stopMoving() {
    _isMoving = false;
  }
  
  public boolean isMoving(){
    return _isMoving;
  }
}

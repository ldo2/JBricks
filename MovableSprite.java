/**
 * @author tuzov
 **/
import java.awt.Image;
import java.awt.Rectangle;
/* Подвижнй спрайт */
abstract class MovableSprite extends Sprite {
  /*
   * @_v - направление и скорость 
   * @_isMoving - состояние: "двигается", "недвигается" 
   * @_prevPos - местоположение спрайта до передвижения
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
  /* Вернуть спрайт с которым произошло соударение */
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
}

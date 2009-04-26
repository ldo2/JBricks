import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/* Игровая лопатка */
class Puddle extends MovableSprite implements KeyListener, MouseMotionListener {
  static final int LEFT = 37;
  static final int RIGHT = 39;
  static final int A = 65;
  static final int D = 68;
  static final int alpha = 10;
  static final int eps = 15;
    
  public Puddle(PlayField p, Image pic) {
    super(
      p,
      pic,
      new Rectangle(
        p.getWidth() / 2,
        p.getHeight() - 20,
        pic.getWidth(p),
        pic.getHeight(p)),
      0,
      10);
    _pf.addKeyListener(this);
    _pf.addMouseMotionListener(this);
  }

  public void move() {
    if (_isMoving) {
      Rectangle b = _pf.getBoundary();
      _pos.x += _v.getSpeedX();
      if (_pos.x < b.x)
        _pos.x = b.x;
      else if (_pos.x + _pos.width > b.x + b.width)
        _pos.x = b.x + b.width - _pos.width;
    }
  }

  public void hitBy(Puck p) {
    if (isGoUp(p)){ ;
    } else if (p.getDirection() < 100 && p.getDirection() > 80) {
    	p.setDirection(270 + (_v.getDirection()==180 ? -1 : 1)*alpha);
    } else {
      int px = p.getBounds().x + p.getBounds().width/2; 
      /*int l  = (int) (_pos.x +_pos.width*(1.0/3));
      int r  = (int) (_pos.x +_pos.width*(2.0/3));*/
      
      int c  = (int) (_pos.x +_pos.width*(1.0/2));
      p.getVelocity().reverseY();
      p.setDirection(p.getDirection()+ (int)Math.round((px-c)*0.2)+(_isMoving ? (_v.getDirection()==180 ? -1 : 1)*eps : 0));
     /* if(isApproachFromRight(p)){
        if (px > r) {
          p.getVelocity().reverse();
        } else {
          p.getVelocity().reverseY();
          p.setDirection(p.getDirection()+(int)(px < l ? -0.1 : 0.1)*eps);
        }
      } else {
        if ( px < l) {
          p.getVelocity().reverse();
        } else {
          p.getVelocity().reverseY();
          p.setDirection(p.getDirection()+(int)(px > r ? 0.1 : -0.1)*eps);
        }
      }*/
      if(p.getDirection()==270){p.setDirection(270 + (_v.getDirection()==180 ? -1 : 1)*(alpha+eps));}
      if(!isGoUp(p)){p.setDirection((isApproachFromRight(p) ? 200 : 330)+(_v.getDirection()==180 ? -1 : 1)*eps);}
    }
  }
  
  /* Проверка приближения шайбы слева */
  private boolean isApproachFromRight(Puck p){
    return p.getVelocity().getSpeedX() < 0;
  }
  /* Проверка отлетает ли шайба вверх*/
  private boolean isGoUp(Puck p){
    int dir = p.getDirection();
    return (dir > 180 && dir < 360) || (dir > -180 && dir < 0);
  }
  
  //~ public boolean isDoubleCollided(){
    //~ return true;
  //~ }
  
  /* Обработка нажатия клавиши */
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == LEFT || e.getKeyCode() == A) {
      startMoving();
      _v.setDirection(180);
    } else if (e.getKeyCode() == RIGHT || e.getKeyCode() == D) {
      startMoving();
      _v.setDirection(0);
    }
  }
  /* Обработка отжатия клавиши */
  public void keyReleased(KeyEvent e) {
    stopMoving();
  }
  public void keyTyped(KeyEvent e) {
    ;
  }
  
  public void mouseMoved(MouseEvent e) {
    Rectangle b = _pf.getBoundary();
    _pos.x = e.getX() - _pos.width/2;
    if (_pos.x < b.x)
        _pos.x = b.x;
    else if (_pos.x + _pos.width > b.x + b.width)
        _pos.x = b.x + b.width - _pos.width;
  }
  public void mouseDragged(MouseEvent e) {
    ;
  }
}

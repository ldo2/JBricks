import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

class PlayField extends Canvas implements Runnable {
  private Thread _game;
  private SpriteVector _sprites;
  private Rectangle _bounds;
  private Match _match;
  private Image _offImg;
  private Graphics _offGrfx;
  private final int _delay = 40;
 
  public PlayField(Match m) {
    _sprites = new SpriteVector();
    _match = m;
  }

  public void paint(Graphics g) {
    if (_game != null && _offImg != null) g.drawImage(_offImg, 0, 0, null);
  }

  public void update(Graphics g) {
    if (_offGrfx == null) {
      _offImg = createImage(getWidth(), getHeight());
      _offGrfx = _offImg.getGraphics();
    }

    _offGrfx.clearRect(0, 0, getWidth(), getHeight());
    _sprites.draw(_offGrfx);
    drawMessage(_match.getMessage());
    g.drawImage(_offImg, 0, 0, null);
  }


  public void run() {
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    long theStartTime = System.currentTimeMillis();
    while (Thread.currentThread() == _game) {
      _sprites.update();
      repaint();
      try {
        theStartTime += _delay;
        Thread.sleep(Math.max(0, theStartTime - System.currentTimeMillis()));
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  public void start() {
    if (_game == null) {
      _bounds  = new Rectangle(0,0,getWidth(),getHeight());
      _game = new Thread(this);
      _game.start();
    }
  }

  void stop() {
    if (_game != null) {
      _game = null;
    }
  }

  public Match getMatch() {
  	return _match;	
  }
  
  public void addSprite(Sprite s) {
    _sprites.addElement(s);
  }

  public void clean() {
  	_sprites.removeAllElements();
  }

  public Sprite testCollision(Sprite s) {
    return _sprites.testCollision(s);
  }

  private void drawMessage(String msg) {
	_offGrfx.drawString(msg,getWidth()/2-20,getHeight()/2);
  }

  public Rectangle getBoundary() {
  	return _bounds;
  }
}

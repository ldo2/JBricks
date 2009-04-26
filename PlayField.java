import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;

class PlayField extends Canvas implements Runnable {
  private Thread _game;
  private SpriteVector _sprites;
  private Rectangle _bounds;
  private Match _match;
  private Image _offImg;
  private Graphics _offGrfx;
  private final int _delay = 25;
  private Image _bg;
  private Color _textColor = new Color(0xCC6600);
  private Color _textColor2 = new Color(0xCC3366);
  private Match.MovieWriter _mw = null;
  private Match.MovieReader _mr = null;
  
  public PlayField(Match m) {
    _sprites = new SpriteVector();
    _match = m;
    _bg = null;
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
    drawBG();
    _sprites.draw(_offGrfx);
    drawMessage(_match.getMessage());
    g.drawImage(_offImg, 0, 0, null);
  }


  public void run() {
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    long theStartTime = System.currentTimeMillis();
    while (Thread.currentThread() == _game) {
      _sprites.update();
      
      if(_mw!=null){_mw.writeNextEvent();}
      if(_mr!=null){_mr.readNextEvent();}
      
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

  public void updateSprites(){
    _sprites.update();
  }
  
  public void clean() {
  	_sprites.removeAllElements();
  }

  public Sprite testCollision(Sprite s) {
    return _sprites.testCollision(s);
  }

  private void drawMessage(String msg) {
    Font f = new Font(null, Font.BOLD, 36);
    FontMetrics fm = getFontMetrics(f);
    _offGrfx.setFont(f);
    _offGrfx.setColor(_textColor);
	  _offGrfx.drawString(msg,(getWidth()-fm.stringWidth(msg))/2+2,getHeight()/2+2);
    _offGrfx.setColor(_textColor2);
	  _offGrfx.drawString(msg,(getWidth()-fm.stringWidth(msg))/2,getHeight()/2);
  }

  private void drawBG(){
    if(_bg!=null){_offGrfx.drawImage(_bg, 0, 0, null);}
  }
  
  public Rectangle getBoundary() {
  	return _bounds;
  }
  
  public void setBG(Image bg){
    _bg = bg;
  }
  
  public void add(Match.MovieWriter mw){
    if(_mw!=null){_mw.stopWrite();}
    _mw=mw;
  }
  
  public void removeWriter(){
    if(_mw!=null){_mw.stopWrite();}
    _mw=null;
  }
  
  public void add(Match.MovieReader mr){
    if(_mr!=null){_mr.stopRead();}
    _mr=mr;
  }
  
  public void removeReader(){
    if(_mr!=null){_mr.stopRead();}
    _mr=null;
  }
}

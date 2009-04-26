import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;
import java.util.Enumeration;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class BrickPile {
  private PlayField _pf;
  private Vector _bricks;
  private final int _rows = 10;
  private final int _cols = 10;

  public BrickPile(PlayField pf, Image[] imgs, String file) {
    _pf = pf;
    _bricks = new Vector();
    int startx = 80; 
    int x = startx, y = 10;
    

    Image img = imgs[2];
    try{
    FileReader fr = new FileReader(file);
    BufferedReader br =new BufferedReader(fr);
    String line;
    for (int r = 0; r < _rows; r++) {
      line = br.readLine();
      if (line==null){break;}
      for (int c = 0; c < _cols; c++) {
        Rectangle pos =
          new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
        if (line.charAt(c)=='B'){
          Brick b = new Brick(_pf, this, img, pos);
          pf.addSprite(b);
          _bricks.addElement(b);
        } else if (line.charAt(c)=='H'){
          HardBrick b = new HardBrick(_pf, this, pos, imgs[3], imgs[4]);
          pf.addSprite(b);
          _bricks.addElement(b);
        } else if (line.charAt(c)=='L'){
          BlockBrick b = new BlockBrick(_pf, this, imgs[5], pos);
          pf.addSprite(b);
          _bricks.addElement(b);
        }
        x += img.getWidth(null);
      }
      y += img.getHeight(null) + 2;
      x = startx;
    }
    
    fr.close();
    }catch(IOException e){}
  }

  public BrickPile(PlayField pf) {
    _pf = pf;
    _bricks = new Vector();

        //~ if (line.charAt(c)=='B'){
          //~ Brick b = new Brick(_pf, this, img, pos);
          //~ pf.addSprite(b);
          //~ _bricks.addElement(b);
        //~ } else if (line.charAt(c)=='H'){
          //~ HardBrick b = new HardBrick(_pf, this, pos, imgs[3], imgs[4]);
          //~ pf.addSprite(b);
          //~ _bricks.addElement(b);
        //~ } else if (line.charAt(c)=='L'){
          //~ BlockBrick b = new BlockBrick(_pf, this, imgs[5], pos);
          //~ pf.addSprite(b);
          //~ _bricks.addElement(b);
        //~ }

  }

  
  public int unbrokenCount() {
    int result = 0;
    for (int i = 0; i < _bricks.size(); i++) {
      if ( !((Brick) _bricks.elementAt(i)).isBroken() ) 
    	result++; 	
    }	
    return result;
  }

  public int brokenCount() {
  	return _bricks.size() - unbrokenCount();	
  }
  
  public int size(){
    return _bricks.size();
  }
  
  public Enumeration getBricks(){
    return _bricks.elements();
  }
  
  public void add(Brick b){
    b.setBrickPile(this);
    _pf.addSprite(b);
    _bricks.addElement(b);
  }
}
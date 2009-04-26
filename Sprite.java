import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

abstract class Sprite implements Serializable{
  /* 
   * @_img - ����������� �������
   * @_pf  - ������� ����
   * @_pos - ������� � ������� �������
   * @_isDead - ��������� - "�����", "���"
   * ������� ������ ��������� � �������� ����. 
   */

  protected transient Image _img;
  protected transient PlayField _pf;
  protected Rectangle _pos;
  protected boolean _isDead;

  public Sprite(PlayField pf, Image img, Rectangle pos) {
    _pos = pos;
    _img = img;
    _pf = pf;
  }
  
  public void draw(Graphics g) {
    g.drawImage(_img, _pos.x, _pos.y, _pf);
  }
  /* �������� �� ������� ��������. */
  public boolean testCollision(Sprite s) {
    if (s != this)
      return _pos.intersects(s.getBounds());
    return false;
  }
  /* ����� �������������� ������������� �������. */
  public Rectangle getBounds() {
    return _pos;
  }
  /* ����� ��� ���? */
  public boolean isDead() {
    return _isDead;
  }
  
  /* ��������. */
  abstract public void update();
  /* �������� ������������� � ������. */
  abstract public void hitBy(Puck p);
    
  public void setImage(Image img){
    _img = img;
  }
  
  public void setPlayField(PlayField pf){
    _pf=pf;
  }
}

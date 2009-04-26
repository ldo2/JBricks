import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

abstract class Sprite {
  /* 
   * @_img - ����������� �������
   * @_pf  - ������� ����
   * @_pos - ������� � ������� �������
   * @_isDead - ��������� - "�����", "���"
   * ������� ������ ��������� � �������� ���� 
   */

  protected Image _img;
  protected PlayField _pf;
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
  /* ����� ��� ���. */
  public boolean isDead() {
    return _isDead;
  }
  /* �������� ��������� �������. */
  abstract public void update();
  /* ��������� ���������� � ������. */
  abstract public void hitBy(Puck p);
}

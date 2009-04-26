abstract class Sprite {
  /* 
   @_img - ����������� �������
   @_pf  - ������� ����
   @_pos - ������� � ������� �������
   @_isDead - ��������� - "�����", "���"
   * ������� ������ ��������� � �������� ����.
   */

  protected Image _img;
  protected PlayField _pf;
  protected Rectangle _pos;
  protected boolean _isDead;
  ...
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
  ...
}

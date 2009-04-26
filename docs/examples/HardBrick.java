import java.awt.Image;
import java.awt.Rectangle;

class HardBrick extends Brick {
  /*
   @_hitCount - ���������� ���������� ������ ������
                ������ �����������, ��� ������ �������
                ����� ����� ����
   @_woundImg - ����������� ������ģ����� �������
                �������� �������� ����������� ��� ������
                ����� �����
   */
  private int _hitCount = 1;
  private Image _woundImg;

  public HardBrick(PlayField pf, BrickPile bp, Rectangle p,
                   Image img, Image woundImg) {
    super(pf, bp, img, p);
    _woundImg = woundImg;
  }
  /* 
   * O�������� ���������� � ������. ��� ������
   * �������� _hitCount ���������� ������ ����,
   * ������ ����� ������ � �������� ����
   */
  public void hitBy(Puck p) {
    if (_hitCount > 0) {
      _img = _woundImg;
      _hitCount--;
    } else {
      _isDead = true;
      if (_bp.unbrokenCount() == 0) {
        _pf.getMatch().win();
      }
    }
    p.getVelocity().reverseY();
  }
}

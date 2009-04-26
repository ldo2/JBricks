class Puck extends MovableSprite {
  ...
  public void move() {
    if (!_isMoving)
      return;
    Rectangle b = _pf.getBoundary();

    _prevPos = _pos;
    _pos.translate(_v.getSpeedX(), _v.getSpeedY());
    /* O�������� ����������  �� �������, ����� � ��������. */
    if (_pos.x <= b.x) {
      _pos.x = b.x;
      _v.reverseX();
    } else if (_pos.x + _pos.width >= b.width + b.x) {
      _pos.x = b.x + b.width - _pos.width;
      _v.reverseX();
    } else if (_pos.y <= b.y) {
      _pos.y = b.y;
      _v.reverseY();
    } else if (_pos.y + _pos.height > b.y + b.height) {
      /* ����� ����� �� ��� */
      _isDead = true;
       if (_ps.size() == 0) {
      	_pf.getMatch().loose();
      } else {
      	_pf.addSprite(_ps.get());
      }
    }
    /* ��������� ���������� � ������� ���������. */
    if (collideWith() != null) {
      _pos = _prevPos;
      collideInto(collideWith());
    }
  }
  /* ������� �� ������������� ��������. */ 	
  public void collideInto(Sprite s) {
    s.hitBy(this);
  }
  ...
}

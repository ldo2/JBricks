class Velocity {
  /*
   @_dx - ������������ �������� �� ��� �
   @_dy - ������������ �������� �� ��� Y
   @_speed - �������� �������
   */

  private double _dx, _dy;
  private int _speed;

  public Velocity(int D, int S) {
    _speed = S;
    setDirection(D);
  }

  public int getSpeed() {
    return _speed;
  }

  public int getSpeedX() {
    return (int) _dx;
  }

  public int getSpeedY() {
    return (int) _dy;
  }

  public void setDirection(int d) {
    _dx = Math.cos(Math.toRadians(d)) * (double) _speed;
    _dy = Math.sin(Math.toRadians(d)) * (double) _speed;
  }

  public int getDirection() {
    return ((int) Math.toDegrees(Math.atan2(_dy,_dx)))%360;
  }

  /* ������������� ����������� �������� */
  public void reverse() {
    _dx = -_dx;
    _dy = -_dy;
  }
  /* ������������� ����������� �� ��� �*/
  public void reverseX() {
    _dx = -_dx;
  }
  /* ������������� ����������� �� ��� Y*/
  public void reverseY() {
    _dy = -_dy;
  }
}

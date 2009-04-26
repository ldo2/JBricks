import java.awt.Graphics;
import java.util.Vector;
/* ��������� ���� �������� ����������� �� ������� ����.
 * ������� � ��������� ����� ��� "���������" 
 * �������� ����.
 */
class SpriteVector extends Vector {
  public void draw(Graphics g) {
    for (int i = 0; i < size(); i++)
       ((Sprite) elementAt(i)).draw(g);
  }
  /* �������� �������� */
  public Sprite testCollision(Sprite test) {
    for (int i = 0; i < size(); i++) {
      Sprite s = (Sprite) elementAt(i);
      if (test == s)
        continue;
      if (test.testCollision(s))
        return s;
    }
    return null;
  }
  /* �������� ��������� ���� �������� */ 
  public void update() {
    for (int i = 0; i < size(); i++) {
      Sprite s = (Sprite) elementAt(i);
      s.update();

      if (s.isDead()) {
        removeElementAt(i);
        continue;
      }
    }
  }

}

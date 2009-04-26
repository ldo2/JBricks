import java.awt.Graphics;
import java.util.Vector;
/* Множетсво всех спрайтов находящихся на игровом поле.
 * Выделен в отдельный класс для "разгрузки" 
 * игрового поля.
 */
class SpriteVector extends Vector {
  public void draw(Graphics g) {
    for (int i = 0; i < size(); i++)
       ((Sprite) elementAt(i)).draw(g);
  }
  /* Проверка коллизии */
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
  /* Обновить состояние всех спрайтов */ 
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

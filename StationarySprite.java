import java.awt.Image;
import java.awt.Rectangle;
/*
 * Стационарный спрайт.  
 */
abstract public class StationarySprite extends Sprite {
  public StationarySprite(PlayField pf, Image img, Rectangle p) {
    super(pf, img, p);
  }

}

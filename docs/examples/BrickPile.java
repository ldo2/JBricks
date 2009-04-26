public class BrickPile {
  /*
   @_pf     - игровое поле
   @_bricks - множество кирпичей
   @_rows   - кол-во линий кирпичей
   @_cols   - кол-во кирпичей в каждой линии
  */
  private PlayField _pf;
  private Vector _bricks;       
  private final int _rows = 4;  
  private final int _cols = 10; 
  
  public BrickPile(PlayField pf, Image img1, Image img2) {
    _pf = pf;
    int startx = 80;
    int x = startx, y = 10;

    for (int r = 0; r < _rows; r++) {
      for (int c = 0; c < _cols; c++) {
        Rectangle pos =
          new Rectangle(x,y,img1.getWidth(null),img1.getHeight(null));
        // В зависимости от номера кирпича добавим на игровое поле
        // либо простой кирпич, либо крепкий.
        if (((r+1) * (c+1)) % (_rows * _cols * 0.2) == 0 ) { 
          pf.addSprite(new HardBrick(_pf, this, pos, img1, img2)); 
        } else {
          pf.addSprite(new Brick(_pf, this, img1, pos));
        }
        x += img1.getWidth(null);
      }
      y += img1.getHeight(null) + 2;
      x = startx;
    }
  }
  ...
}

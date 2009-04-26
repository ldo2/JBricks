import java.awt.Image;
import java.io.Serializable;
/* Хранилище спрайтов */
class PuckSupply implements Serializable{
  /*
   * @_puck - массив шайб
   * @_count - кол-во оставшихся шайб
   */
  private Puck _pucks[];
  private int _count;

  public PuckSupply(int N, PlayField pf, Image pic) {
    _pucks = new Puck[N];
    for (int i = 0; i < N; i++)
      _pucks[i] = new Puck(pf, this, pic);
    _count = N;
  }
 
  public int size() {
    return _count;
  }
  /* Взять следующую шайбу из хранилища */
  public Puck get() {
    return _count > 0 ? _pucks[--_count] : null;
  }
  public Puck lastPuck(){
    return _pucks[_count];
  }
  public void setImage(Image img){
    for (int i = 0; i < _pucks.length; i++)
      _pucks[i].setImage(img);
  }
  public void setPlayField(PlayField pf){
    for (int i = 0; i < _pucks.length; i++)
      _pucks[i].setPlayField(pf);
  }
}

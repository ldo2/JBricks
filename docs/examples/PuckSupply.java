class PuckSupply {
  /*
   @_puck - массив шайб
   @_count - кол-во оставшихся у игрока шайб
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
}

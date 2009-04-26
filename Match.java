/**
 * Матч - Сеанс игры.
 **/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Match {
  private Frame _top;
  private PuckSupply _ps;
  private BrickPile _bp;
  private PlayField _pf;
  private Puddle _pud;
  private MediaTracker _tracker;
  private Image[] _lib;
  private String _message = "";
  private String _levelFile = "simple_level.jbl";
  private String _saveMovieFile = null;
  private boolean haveActiveGame;
  
  private MenuItem _start, _pause, _resume, _exit, _setLevel, _saveMovie, _loadMovie, _startRecord, _stopRecord;

  public Match() {
    _top = new Frame("Bricks");
    _pf = new PlayField(this);
    _tracker = new MediaTracker(_pf);
    _lib = new Image[7];
    _lib[0] = _pf.getToolkit().getImage("puck.gif");
    _lib[1] = _pf.getToolkit().getImage("puddle.gif");
    _lib[2] = _pf.getToolkit().getImage("brick_simple.gif");
    _lib[3] = _pf.getToolkit().getImage("brick_hard.gif");
    _lib[4] = _pf.getToolkit().getImage("brick_broken.gif");
    _lib[5] = _pf.getToolkit().getImage("brick_block.gif");
    _lib[6] = _pf.getToolkit().getImage("bg.gif");
    _tracker.addImage(_lib[0], 0);
    _tracker.addImage(_lib[1], 0);
    _tracker.addImage(_lib[2], 0);
    _tracker.addImage(_lib[3], 0);
    _tracker.addImage(_lib[4], 0);
    _tracker.addImage(_lib[5], 0);
    _tracker.addImage(_lib[6], 0);
    
    MenuBar mbar = new MenuBar();
    _top.setMenuBar(mbar);

    Menu file = new Menu("Game");
    file.add(_start = new MenuItem("Start", new MenuShortcut(KeyEvent.VK_F2)));
    file.add(_pause = new MenuItem("Pause", new MenuShortcut(KeyEvent.VK_P)));
    file.add(_resume = new MenuItem("Resume", new MenuShortcut(KeyEvent.VK_R)));
    file.addSeparator();
    file.add(_exit = new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_Q)));
    mbar.add(file);
    
    _pause.disable();
    _resume.disable();

    Menu level = new Menu("Level");
    level.add(_setLevel = new MenuItem("Set Level...", new MenuShortcut(KeyEvent.VK_L)));
    mbar.add(level);
    
    Menu movie = new Menu("Movie");
    movie.add(_loadMovie = new MenuItem("LoadMovie...", new MenuShortcut(KeyEvent.VK_O, true)));
    movie.addSeparator();
    movie.add(_saveMovie = new MenuItem("SaveMovie...", new MenuShortcut(KeyEvent.VK_S, true)));
    movie.add(_startRecord = new MenuItem("StartRecord", new MenuShortcut(KeyEvent.VK_S)));
    movie.add(_stopRecord = new MenuItem("StopRecord", new MenuShortcut(KeyEvent.VK_S)));
    mbar.add(movie);

    _startRecord.disable();
    _stopRecord.disable();

    _top.setSize(600, 400);
    _top.setMinimumSize(new Dimension(600, 400));
    _top.setResizable(false);
    
    /*_start.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        _pause.enable();
        start();
      }
    });
    
    _pause.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { 
        _message = "Pause";
        
        _resume.enable();
        _pause.disable();
        
        _pf.stop();
        _pf.repaint();
      }
    });
    
    _resume.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        _message = "";
        
        _resume.disable();
        _pause.enable();
        
        _pf.requestFocus();
        _pf.start();
      }
    });
    
    _exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    
    _setLevel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        FileDialog fd = new FileDialog(new Frame(), "Open", FileDialog.LOAD);
        fd.setFile("*.jbl");
        fd.setFilenameFilter(new FilenameFilter(){
          public boolean accept(File dir, String name){
            String[] strs = name.split("\\.");
            return strs.length > 1 && strs[1].equals("jbl");
          }
      });
        fd.setVisible(true);
        String name = fd.getFile();
        if(name!=null){_levelFile = name;}
          start();
        _pf.stop();
      }
    });
    

    _top.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(0);
				}
		});*/
    _start.addActionListener(new Start());
    _pause.addActionListener(new Pause());
    _resume.addActionListener(new Resume());
    _exit.addActionListener(new Exit());
    _setLevel.addActionListener(new LevelSetter());
    _saveMovie.addActionListener(new SaveMovie());
    _loadMovie.addActionListener(new LoadMovie());
    _startRecord.addActionListener(new StartRecord());
    _stopRecord.addActionListener(new StopRecord());
    _top.addWindowListener(new WindowCloser());
    
    haveActiveGame = false;
    
    _pf.setBG(_lib[6]);
    _top.add("Center", _pf);
    _top.show();
  }
  
  
  class Start implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      if(haveActiveGame){
        new GameEndDialog();
      }
      if(!haveActiveGame){
        _pause.enable();
        start();
      }
    }
  }
  
  class Pause implements ActionListener{
    public void actionPerformed(ActionEvent e) { 
      _resume.enable();
      _pause.disable();
      pause();
    }
  }
  
  class Resume implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      _resume.disable();
      _pause.enable();
      resume();
    }
  }
  
  class Exit implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }
  
  class LevelSetter implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      if(haveActiveGame){
        new GameEndDialog();
      }
      if(!haveActiveGame){
        FileDialog fd = new FileDialog(new Frame(), "Open", FileDialog.LOAD);
        fd.setFile("*.jbl");
        fd.setFilenameFilter(new LevelFilenameFilter());
        fd.setVisible(true);
        String name = fd.getFile();
        if(name!=null){_levelFile = name;}
      }
    }
    
    class LevelFilenameFilter implements FilenameFilter{
      public boolean accept(File dir, String name){
        String[] strs = name.split("\\.");
        return strs.length > 1 && strs[1].equals("jbl");
      }
    }
  }
  
  class WindowCloser extends WindowAdapter{
    public void windowClosing(WindowEvent ev){
				System.exit(0);
    }
  }
  
  class SaveMovie implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      pause();
      
      FileDialog fd = new FileDialog(new Frame(), "SaveMovie", FileDialog.SAVE);
      fd.setFile("*.jbm");
      fd.setFilenameFilter(new MovieFilenameFilter());
      fd.setVisible(true);
      String name = fd.getFile();
      if(name!=null){
        _saveMovieFile = name;
        _startRecord.enable();
      }
      
      resume();
    }
  }
  
  class LoadMovie implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      if(haveActiveGame){
        new GameEndDialog();
      }
      if(!haveActiveGame){
        FileDialog fd = new FileDialog(new Frame(), "LoadMovie", FileDialog.LOAD);
        fd.setFile("*.jbm");
        fd.setFilenameFilter(new MovieFilenameFilter());
        fd.setVisible(true);
        String name = fd.getFile();
        if(name!=null){
          _saveMovieFile = name;
          new MovieReader();
        }
      }
    }
  }
  
  class MovieFilenameFilter implements FilenameFilter{
    public boolean accept(File dir, String name){
      String[] strs = name.split("\\.");
      return strs.length > 1 && strs[1].equals("jbm");
    }
  }
  
  class StartRecord implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      _startRecord.disable();
      _stopRecord.enable();
      _message = "Please wait ...";
      _pf.stop();
      new MovieWriter();
      _message = "";
      _pf.start();
    }
  }
  
  class StopRecord implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      _stopRecord.disable();
      _pf.removeWriter();
    }
  }
  
  class GameEndDialog extends Dialog{
    private static final int  space = 10, starth = 40, butw = 50, buth = 30;
    public GameEndDialog(){
      super(new Frame(), "Bricks",true);
      
      pause();
      
      this.setLayout(null);
      int w = space, h = starth;
      
      Font f = new Font(null, Font.PLAIN, 12);
      this.setFont(f);
      FontMetrics fm = getFontMetrics(f);
      
      String text = "Are you sure that you want to end this game?";
      Label la = new Label(text, Label.CENTER);
      la.setBounds(w, h, fm.stringWidth(text), 2*fm.getMaxAscent());
      this.add(la);
      w+=fm.stringWidth(text)+space; h+=2*fm.getMaxAscent()+space; 
      
      Button yes = new Button("Yes"), no = new Button("No");
      int left_space = (w-2*butw)/2;
      yes.setBounds(left_space, h, butw, buth);
      no.setBounds(left_space+butw+space, h, butw, buth);
      yes.addActionListener(new YesListener());
      no.addActionListener(new NoListener());
      this.add(yes);
      this.add(no);
      h+=buth+space;
      
      
      this.addWindowListener(new DialogClose());
      this.setBounds(100, 100, w, h);
      this.setResizable(false);
      this.show();
    }
    
    class DialogClose extends WindowAdapter{
      public void windowClosing(WindowEvent ev){
				hide();
        resume();
      }
    }
    
    class YesListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
        hide();
        endGame();
      }
    }
    
    class NoListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
        hide();
        resume();
      }
    }
  }
  
  public void start() {
    try {
      _tracker.waitForID(0);
    } catch (InterruptedException e) {
      return;
    }
    _message = "";
    _pf.clean();
    
    _bp = new BrickPile(_pf, _lib, _levelFile);
    _ps = new PuckSupply(3, _pf, _lib[0]);
   
    _pf.addSprite(_pud = new Puddle(_pf,_lib[1]));
    _pf.addSprite(_ps.get());
    
    _pf.requestFocus();
    _pf.start();
    haveActiveGame = true;
  }

  public void loose() {
    _message = "You Loose";
    
    _pause.disable();
    _resume.disable();
    
    _pf.repaint();
    _pf.stop();
  }

  public void win() {
    _message = "You win";
    
    _pause.disable();
    _resume.disable();
    
    _pf.updateSprites();
    _pf.repaint();	
    _pf.stop();
  }
  
  public void pause(){
    _message = "Pause";
    _pf.stop();
    _pf.repaint();
  }
  
  public void resume(){
    _message = "";
    _pf.requestFocus();
    _pf.start();
  }
  
  public void endGame(){
    _message = "";
    _pf.stop();
    _pf.clean();
    
    _pause.disable();
    _resume.disable();
    
    haveActiveGame = false;
  }
  
  

  public static void main(String[] args) {
    Match m = new Match();
  } 
  
  public String getMessage() {
    return _message;
  }

  
  class MovieWriter{
    private Velocity puddleLastVelocity;
    private boolean pudIsMove;
    private ObjectOutputStream oos;
    private long _tic = 0l;
    public MovieWriter(){
      _pf.add(this);
      try{
        oos = new ObjectOutputStream(new FileOutputStream(_saveMovieFile));
        oos.writeObject(_pud);
        oos.writeObject(_ps);
        oos.writeInt(_bp.size());
        Enumeration en = _bp.getBricks();
        while(en.hasMoreElements()){
          oos.writeObject(en.nextElement());
        }
        oos.writeLong(_tic);
        oos.writeObject(puddleLastVelocity = new Velocity(_pud.getVelocity()));
        oos.writeBoolean(pudIsMove = _pud.isMoving());
        _tic++;
      }catch(Exception e){System.out.println(e);}
    }
    
    public void writeNextEvent(){
      Velocity vel = _pud.getVelocity();
      boolean mov = _pud.isMoving();
      if((mov!=pudIsMove) || !puddleLastVelocity.equals(vel)){
        try{
        System.out.println(vel.getDirection());
        oos.writeLong(_tic);
        oos.writeObject(new Velocity(vel));
        oos.writeBoolean(mov);
        }catch(Exception e){System.out.println(e);}
        puddleLastVelocity=new Velocity(vel);
        pudIsMove = mov;
      }
      _tic++;
    }
    
    public void stopWrite(){
      if(oos != null){
        try{
          oos.writeLong(_tic);
          oos.writeObject(_pud.getVelocity());
          oos.close();
        }catch(Exception e){System.out.println(e);}
      }
    }
    
  }
  
  class MovieReader{
    private ObjectInputStream ois;
    private long _tic = 0l;
    private long _nextTic = 0l;
    private Velocity _v;
    private boolean _mov;
    public MovieReader(){
      _pf.clean();
      _pf.add(this);
      try{
        ois = new ObjectInputStream(new FileInputStream(_saveMovieFile));
        _pud = (Puddle)ois.readObject();
        _pud.setPlayField(_pf);
        _pud.setImage(_lib[1]);
        _pf.addSprite(_pud);
        _ps = (PuckSupply)ois.readObject();
        _ps.setPlayField(_pf);
        _ps.setImage(_lib[0]);
        _pf.addSprite(_ps.lastPuck());
        int numBricks = ois.readInt();
        _bp = new BrickPile(_pf);
        while(numBricks>0){
          _bp.add(processBrick(ois.readObject()));
          numBricks--;
        }
        _tic = ois.readLong();
        _v = (Velocity) ois.readObject();
        _mov = ois.readBoolean();
        _pud.setVelocity(_v);
        if (_mov) { _pud.startMoving();}else{ _pud.stopMoving();}
        readNext();
        _tic++;
      }catch(Exception e){System.out.println(e);}
      _pf.start();
    }
    
    private Brick processBrick(Object obj){
      Brick b = null;
      if(obj instanceof Brick){
        b = (Brick) obj;
        b.setImage(_lib[2]);
        b.setPlayField(_pf);
      }
      return b;
    }
    
    private void readNext(){
      try{
        _nextTic = ois.readLong();
        _v = (Velocity) ois.readObject();
        System.out.println("  >"+_v.getDirection());
        _mov = ois.readBoolean();
      }catch(Exception e){
        _message = "End";
        _pf.stop();
        _pf.repaint();
        stopRead();
      }
    }
    
    public void readNextEvent(){
      if(_tic==_nextTic){
        try{
          System.out.println(_v.getDirection());
          _pud.setVelocity(_v);
          if (_mov) { _pud.startMoving();}else{ _pud.stopMoving();}
          readNext();
        }catch(Exception e){System.out.println(e);}
      }
      _tic++;
    }
    
    public void stopRead(){
      if(ois != null){
        try{
          ois.close();
        }catch(Exception e){System.out.println(e);}
      }
    }
    
  }
  
}

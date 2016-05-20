//2048
//By Srujay Korlakunta

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.util.Random;

public class Play2048 extends JPanel{

	//Constants such as Font, Tile Size, and Tile Margin Declared here
	private static final String FONT_NAME = "Tahoma";
	private static final int TILE_SIZE = 64;
	private static final int TILES_MARGIN = 16;

  	int[][] b = new int[4][4]; //board matrix
  	int dead = 0, win = 0;
  	int dy, dx;
  	int[][] newb; //duplicate board for temporary testing

  	public Play2048() { //Constructor Method, initiates KeyListener, takes in controls
   		setFocusable(true);
    	addKeyListener(new KeyAdapter() {
      	public void keyPressed(KeyEvent e) {
        	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
         	 restart();
        	}

        	if (e.getKeyCode() == KeyEvent.VK_E) {
          		System.exit(0);
        	}

        	if (e.getKeyCode() == KeyEvent.VK_R) {
          for(int i = 0; i <= 50; i++){
          	keyPress("LEFT");
          	keyPress("UP");
          	keyPress("DOWN");
          	keyPress("RIGHT");
          }


        }

        if (gameover()) {
        	dead = 1;
       		}

       		if (find2048()) {
        	win = 1;
       		}

        if (dead != 1) {
          switch (e.getKeyCode()) { //Switch statement, calls keyPress() method with name of key pressed
            case KeyEvent.VK_LEFT: keyPress("LEFT"); break;
            case KeyEvent.VK_RIGHT: keyPress("RIGHT"); break;
            case KeyEvent.VK_DOWN: keyPress("DOWN"); break;
            case KeyEvent.VK_UP: keyPress("UP"); break;
          	}
        }

        repaint();
      	}
    	});
    	restart();
  	}



  	public void restart(){ //Resets board if restart is requested
		b = new int[4][4];
    	win = dead = 0;
   	 	spawn();

  	}

  	public boolean isAvailable(){
  		for (int j = 0 ; j < 4; j++) for (int i = 0 ; i < 4; i++) if(b[j][i] == 0) return true;
  		return false;
  	}

  	public void spawn(){ //Randomly generates starting board
		if(isAvailable()){

    	int randRow, randCol, doneSpawning = 0;
		while(doneSpawning == 0){
			randRow = (int)((Math.random() * 3) + 0.5);
			randCol = (int)((Math.random() * 3) + 0.5);

			if(b[randRow][randCol] == 0){
				b[randRow][randCol] = 2;
				doneSpawning = 1;
			}
		}
		}else{
			dead = 1;
		}

  	}

  	public boolean find2048(){

  		for (int j = 0 ; j < 4; j++) for (int i = 0 ; i < 4; i++) if(b[j][i] == 2048) return true;
  		return false;
  	}

  	public Color textColor(int num) { //Determines text color based on value of Tile

    	int val = num;
    	if(val < 16){
     		return new Color(0,0,64);
    	}
    	return new Color(242,249,255);
  	}

  	public Color bgColor(int num) { //Determines Tile Background color based on value of Tile

    	int val = num;
    	switch (val) {
			case 2:  return new Color(242,249,255);
      		case 4:    return new Color(179,255,255);
      		case 8:    return new Color(128,255,255);
   	  	 	case 16:    return new Color(0,255,255);
 	     	case 32:   return new Color(100,205,255);
      		case 64:   return new Color(21,174,234);
      		case 128:   return new Color(40,148,255);
      		case 256:  return new Color(0,128,192);
      		case 512:  return new Color(0,85,170);
     		case 1024: return new Color(0,0,255);
     		case 2048: return new Color(0,0,64);
    	}
   		return new Color(242,249,255);
  	}

	public int textSize(int num){ //Determines Text Size of Tile based on Tile Value

		int val = num;

    	if(val < 100){
      		return 36;
    	}else if(val < 1000){
      		return 32;
    	}
    	return 24;
  	}

	private static int spaceOut(int arg) { //Spaces out tiles based on Tile Size and Margin Size
    	return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
  	}

	public void paint(Graphics g) { //Initializes JFrame and starts background color, calls drawTile method
		super.paint(g);
  		g.setColor(Color.WHITE);
   		g.fillRect(0, 0, 340, 340);
     	for (int y = 0; y < 4; y++) {
     		for (int x = 0; x < 4; x++) {
       			drawTile(g, x, y);
     		}
   		}
 	}

    private void drawTile(Graphics g2, int x, int y) { //Draws tiles and
    	Graphics2D g = ((Graphics2D) g2);
     	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

		int value = b[x][y];
		int xOffset = spaceOut(x);
		int yOffset = spaceOut(y);
		g.setColor(bgColor(value));
		g.fillRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE);
		g.setColor(textColor(value));
		final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
		final Font font = new Font(FONT_NAME, Font.BOLD, size);
		g.setFont(font);
		String s = String.valueOf(value);
		final FontMetrics fm = getFontMetrics(font);
		final int w = fm.stringWidth(s);
		final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

		//Determines the centering of the font depending on font size, which depends on the value of the tile
		if (value != 0)
			g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);

		if (dead==1) { //Prints "GAME OVER" if tiles are not able to move
			g.setColor(new Color(255, 255, 255, 30));
			g.fillRect(0, 0, 340, 390);
			g.setColor(new Color(78, 139, 202));
			g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
		 	g.drawString("Game over!", 50, 130);
		 	g.drawString("You lose!", 64, 200);
		 	g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
		 	g.setColor(new Color(128, 128, 128, 128));
		 	g.drawString("Press ESC to play again", 80, 340 - 40);
     	}

     	if (win == 1) { //Prints "GAME OVER" if tiles are not able to move
			g.setColor(new Color(255, 255, 255, 30));
			g.fillRect(0, 0, 340, 390);
			g.setColor(new Color(78, 139, 202));
			g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
		 	g.drawString("Nice Job!", 50, 130);
		 	g.drawString("You win!", 64, 200);
		 	g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
		 	g.setColor(new Color(128, 128, 128, 128));
		 	g.drawString("Press ESC to play again", 80, 340 - 40);
     	}
   	}



	public void keyPress(String keyCode){ //Determines which direction key was pressed
    	if(dead == 0){

      	String kC = keyCode;
      	int dy = kC.equals("UP")?1:(kC.equals("DOWN")?-1:0);
      	int dx = kC.equals("LEFT")?-1:(kC.equals("RIGHT")?1:0);

      	newb = go(dy,dx);

      	if(newb != null){
       		b = newb;
        	spawn();
      	}
  	}
	}

 	public boolean gameover(){ //Determines if the Game is Over
    	int[] dx = {1,-1,0,0}, dy={0,0,1,-1};
    	boolean out = true;
    	for(int i = 0; i < 4; i++) if(go(dy[i],dx[i]) != null) out = false;
    	return out;
  	}

	public int[][] go(int directY, int directX) { //Shifts board according to what arrow key was pressed by the user

  		int dy = directY;
  		int dx = directX;
		int[][] temp = new int[4][4];
		boolean moved = false;

  		for (int j = 0 ; j < 4; j++) for (int i = 0 ; i < 4; i++) temp[j][i] = b[j][i];

  		////Shifts Board Down if Down Arrow Key Pressed

  		if (dx != 0 || dy != 0) {
  			if(dy == -1){

				for(int j = 0; j <=3; j++){

					int i = 3;
  					while(i > 0){
  						if(temp[j][i] == 0){
  							i--;
  							continue;
  						}
  						int k = i - 1;
  						while( k >= 0 ){

  							if(temp[j][k] != 0){

  								if(temp[j][i] == temp[j][k] ){
  									temp[j][i] *= 2;
  									temp[j][k] = 0;
  									i=k;
  									moved = true;break;

  								}


  							}
							k--;
  						}

  						i--;
  					}
  					i = 3;
  					while( i > 0 ){
  						if(temp[j][i] == 0){
  						int k = i - 1;
  							while( k >= 0){
  								if(temp[j][k] != 0){
  									temp[j][i] = temp[j][k];
  									temp[j][k] = 0;



  									break;
  								}
  								k--;
  							}
  						}
  						if(temp[j][i] == 0){
  						break;
  						}
  						i--;

  					}
  				}
  			}

			//Shifts Board Up if Up Arrow Key Pressed

  			if(dy == 1){

				for(int j = 0; j <=3; j++){

					int i = 0;
  					while(i < 3){
  						if(temp[j][i] == 0){
  							i++;
  							continue;
  						}
  						int k = i + 1;
  						while( k <= 3 ){

  							if(temp[j][k] != 0){

  								if(temp[j][i] == temp[j][k] ){
  									temp[j][i] *= 2;
  									temp[j][k] = 0;
  									i=k;
  									moved = true;break;

  								}

  							}
							k++;
  						}

  						i++;
  					}
  					i = 0;
  					while( i < 3 ){
  						if(temp[j][i] == 0){
  						int k = i + 1;
  							while( k <= 3){
  								if(temp[j][k] != 0){
  									temp[j][i] = temp[j][k];
  									temp[j][k] = 0;



  									break;
  								}
  								k++;
  							}
  						}
  						if(temp[j][i] == 0){
  						break;
  						}
  						i++;

  					}
  				}
  			}

  			//Shifts Board Right if Right Arrown Key Pressed

  			if(dx == 1){

				for(int i = 0; i <=3; i++){

					int j = 3;
  					while(j > 0){
  						if(temp[j][i] == 0){
  							j--;
  							continue;
  						}
  						int k = j - 1;
  						while( k >= 0 ){

  							if(temp[k][i] != 0){

  								if(temp[j][i] == temp[k][i] ){
  									temp[j][i] *= 2;
  									temp[k][i] = 0;
  									j=k;
  									moved = true;break;

  								}


  							}
							k--;
  						}

  						j--;
  					}
  					j = 3;
  					while( j > 0 ){
  						if(temp[j][i] == 0){
  						int k = j - 1;
  							while( k >= 0){
  								if(temp[k][i] != 0){
  									temp[j][i] = temp[k][i];
  									temp[k][i] = 0;

  									break;
  								}
  								k--;
  							}
  						}
  						if(temp[j][i] == 0){
  						break;
  						}
  						j--;

  					}
  				}
  			}


  		}

  		//Shifts Board Left if Left Arrow Key Pressed

  		if(dx == -1){

				for(int i = 0; i <=3; i++){

					int j = 0;
  					while(j < 3){
  						if(temp[j][i] == 0){
  							j++;
  							continue;
  						}
  						int k = j + 1;
  						while( k <= 3 ){

  							if(temp[k][i] != 0){

  								if(temp[j][i] == temp[k][i] ){
  									temp[j][i] *= 2;
  									temp[k][i] = 0;
  									i=k;
  									moved = true;break;

  								}



  							}
							k++;
  						}

  						j++;
  					}
  					j = 0;
  					while( j < 3 ){
  						if(temp[j][i] == 0){
  						int k = j + 1;
  							while( k <= 3){
  								if(temp[k][i] != 0){
  									temp[j][i] = temp[k][i];
  									temp[k][i] = 0;

  									break;
  								}
  								k++;
  							}
  						}
  						if(temp[j][i] == 0){
  						break;
  						}
  						j++;

  					}
  				}
  			}

		moved = true;
  		if(moved){
  			return temp;
  		}else {
  			return null;
  		}



	}

  public static void main(String [] args) { //Main method, makes new instantiation of Play2048 Class and Displays Controls in a Pop Up

    JOptionPane.showMessageDialog ( null, "Hello! Welcome to 2048! Controls: \nESCAPE: Restart\nE: Exit\nLeft Arrow: Go Left\nUp Arrow: Go Up\nRight Arrow: Go Right\nDown Arrow: Go Down", "Welcome!", JOptionPane.PLAIN_MESSAGE);
    JFrame game = new JFrame();
    game.setTitle("2048");
    game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    game.setSize(340, 390);
    game.setResizable(false);

    game.add(new Play2048());

    game.setLocationRelativeTo(null);
    game.setVisible(true);



  }

}

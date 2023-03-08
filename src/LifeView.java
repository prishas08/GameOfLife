import java.awt.*;
import javax.swing.*;

/** The view (graphical) component */
public class LifeView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static int SIZE = 60;
	private static int i = 1;

	/** store a reference to the current state of the grid */
    private LifeCell[][] grid;
    private LifeCell[][] gridReset;
    private boolean isRandomColorTrue = false;

    public LifeView()
    {
        grid = new LifeCell[SIZE][SIZE];
        gridReset = new LifeCell[SIZE][SIZE];
    }

    /** entry point from the model, requests grid be redisplayed */
    public void updateView( LifeCell[][] mg )
    {
        grid = mg;
        if(i < 2)
        {
        		gridReset = mg;
        }
        i++;

        repaint();
    }
    
    public void reset(LifeCell[][] g)
    {
    		grid = gridReset;
    		repaint();

    }

    public void recolor()
    {
    	isRandomColorTrue = true;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int testWidth = getWidth() / (SIZE+1);
        int testHeight = getHeight() / (SIZE+1);
        // keep each life cell square
        int boxSize = Math.min(testHeight, testWidth);

        for ( int r = 0; r < SIZE; r++ )
        {
            for (int c = 0; c < SIZE; c++ )
            {
                if (grid[r][c] != null)
                {
                    if ( grid[r][c].isAliveNow() )
                    {
                    	if(isRandomColorTrue)
                    	{
                    		int red = (int) ((int) 255/(Math.random()+1));
                    		int green = (int) ((int) 255/(Math.random()+1));
                    		int blue = (int) ((int) 255/(Math.random()+1));

                    		g.setColor(new Color(red, green, blue));
                    	}
                    	else
                    	{
                    		g.setColor( Color.BLUE );
                    	}
                        
                    }
                    else
                        g.setColor( new Color(235,235,255) );

                    g.fillRect( (c+1)*boxSize, (r+1)* boxSize, boxSize-2, boxSize-2);
                }
            }
        }
    }
}

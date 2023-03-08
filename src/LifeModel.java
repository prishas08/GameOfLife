import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Timer;

public class LifeModel implements ActionListener
{

	/*
	 *  This is the Model component.
	 */

	private static int SIZE = 60;
	private LifeCell[][] grid;
    private LifeCell[][] gridReset;
	private int[][] occupied = new int[60][60];
	private LifeView view;
	private LifeModel model;


	LifeView myView;
	Timer timer;

	/** Construct a new model using a particular file */
	public LifeModel(LifeView view, String fileName) throws IOException
	{       
		int r, c;
		grid = new LifeCell[SIZE][SIZE];
		gridReset = new LifeCell[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
		{
			for ( c = 0; c < SIZE; c++ )
			{
				grid[r][c] = new LifeCell();
				gridReset[r][c] = new LifeCell();
			}
		}

		if ( fileName == null ) //use random population
		{                                           
			for ( r = 0; r < SIZE; r++ )
			{
				for ( c = 0; c < SIZE; c++ )
				{
					if ( Math.random() > 0.85) //15% chance of a cell starting alive
					{
						grid[r][c].setAliveNow(true);
						gridReset[r][c].setAliveNow(true);
					}
					
				}
			}
		}
		else
		{                 
			Scanner input = new Scanner(new File(fileName));
			int numInitialCells = input.nextInt();
			for (int count=0; count<numInitialCells; count++)
			{
				r = input.nextInt();
				c = input.nextInt();
				grid[r][c].setAliveNow(true);
				gridReset[r][c].setAliveNow(true);
				//System.out.println("R " + r);
			}
			input.close();
		}

		myView = view;
		myView.updateView(grid);

	}

	/** Constructor a randomized model */
	public LifeModel(LifeView view) throws IOException
	{
		this(view, null);
	}

	/** pause the simulation (the pause button in the GUI */
	public void pause()
	{
		timer.stop();
	}
	
	public void colorRandom() throws IOException
	{
		myView.recolor();
		resume();
	}
	
	/** resume the simulation (the pause button in the GUI */
	public void resume()
	{
		timer.restart();
	}
	
	public void reset() throws IOException
	{
		Life conway = new Life("glgun13.lif"); //parameterize to start w/ a particular file
		
		conway.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		conway.setSize(570, 640);
		//conway.show(); //deprecated, added call below
		conway.setVisible(true);
		/*view = new LifeView();
		view.setBackground(Color.white);
		view.reset(gridReset);
		//model = new LifeModel(view, "glgun13.lif");
		//myView.updateView(gridReset);*/
	}
	
	
	/** run the simulation (the pause button in the GUI */
	public void run()
	{
		timer = new Timer(50, this);
		timer.setCoalesce(true);
		timer.start();
	}

	/** called each time timer fires */
	public void actionPerformed(ActionEvent e)
	{
		oneGeneration();
		//System.out.println("Inside the grid!");
		myView.updateView(grid);
		//System.out.println("Outside!");
	}

	/** main logic method for updating the state of the grid / simulation */
	private void oneGeneration()
	{
		for(int i = 0; i < grid.length-1; i++)
		{
			for(int x = 0; x < grid[0].length-1; x++)
			{
				occupied[i][x]=0;
				//if point is at 0,0
				if(i == 0 && x ==0 )
				{
					if(grid[i][x+1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i+1][x+1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i+1][x].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
				}
				//if point is on the first row
				else if(i==0)
				{
					//if point is on last column too
					if(x==grid.length-1)
					{
						if(grid[i][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i+1][x].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i+1][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
					}
					else
					{
						if(grid[i][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i+1][x].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i+1][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i][x+1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i+1][x+1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
					}
				}
					
				//if point is on the last row
				else if(i==grid.length-1)
				{
					//if point is on last column too
					if(x==grid.length-1)
					{
						if(grid[i-1][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i-1][x].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
					}
					//if point in on last row too
					else if(i==grid.length-1)
					{
						if(grid[i-1][x].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i][x+1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i-1][x+1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
					}
					else
					{
						if(grid[i-1][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i-1][x].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i][x+1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
						if(grid[i+1][x-1].isAliveNow()==true)
						{
							occupied[i][x] = occupied[i][x] + 1;
						}
					}
				}
				//if point is on the first column
				else if(x==0)
				{
					if(grid[i-1][x].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i][x+1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i-1][x+1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i+1][x].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i+1][x+1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					
				}
				//if point is on the last column
				else if(x == grid.length)
				{
					if(grid[i][x-1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i+1][x].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i+1][x-1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i-1][x].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					if(grid[i-1][x-1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
				}
				//if point is in the middle of the grid
				else
				{
					//left
					if(grid[i][x-1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					//bottom
					if(grid[i+1][x].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					//bottom left
					if(grid[i+1][x-1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					//top
					if(grid[i-1][x].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					//top left
					if(grid[i-1][x-1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					//right
					if(grid[i][x+1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					//top right
					if(grid[i-1][x+1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
					//bottom right
					if(grid[i+1][x+1].isAliveNow()==true)
					{
						occupied[i][x] = occupied[i][x] + 1;
					}
				}
				//System.out.println("Occupied!!! " + occupied[i][x] );
			}
			
		}
		for(int i=0; i<grid.length; i++)
		{
			for(int y=0; y<grid[0].length; y++)
			{
				//System.out.println("Occupied!!! " + occupied[i][y] );
				if(grid[i][y].isAliveNow()==true)
				{
					//System.out.println("IS ALIVE NOW = TRUE");
					if(occupied[i][y]==0 || occupied[i][y]==1 || occupied[i][y]==4 || occupied[i][y]==5 || occupied[i][y]==6 || occupied[i][y]==7 || occupied[i][y]==8)
					{
						//System.out.println("THIS BOX DIED");
						grid[i][y].setAliveNow(false);
					}
					if(occupied[i][y]==3 || occupied[i][y]==2)
					{
						//System.out.println("BOX SURVIVED");
						grid[i][y].setAliveNow(true);
					}
				}
				else
				{
					//System.out.println("IS NOT ALIVE NOW = TRUE");
					if(occupied[i][y]==3)
					{
						//System.out.println("BIRTHED");
						grid[i][y].setAliveNow(true);
					}
				}
			}
		}
		//System.out.println("I'm at the end");
	}
}


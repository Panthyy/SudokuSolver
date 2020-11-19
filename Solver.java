package sodukosolver;

import java.util.ArrayList;

public class Solver {
	private int[][] Soduko;
	
	public Solver(int[][] Soduko){
		this.Soduko = Soduko;
	}
		
	public ArrayList<Integer> GetMissing(Tuple pos) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			if (Soduko[pos.Gety()][i] != 0) {
				tmp.add(Soduko[pos.Gety()][i]);
			}			
		}
		for (int i = 0; i < 9; i++) {
			if (Soduko[i][pos.GetX()] != 0) {
				tmp.add(Soduko[i][pos.GetX()]);
			}		
		}	
		return tmp;		
	}
	public ArrayList<Integer> GetMissingIn3x3(int[][] Soduko){
		ArrayList<Integer>[] tmp = new ArrayList[9];
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				count++;
				for (int j2 = 0; j2 < 3; j2++) {
					for (int k = 0; k < 3; k++) {
						
					}
				}
			}
		}
		return null;
		
	}
}
class Tuple{
	
	private int x,y;
	
	Tuple(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int GetX() {
		return x;
	}
	
	public int Gety() {
		return y;
	}
	
}

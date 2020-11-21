package com.company;
import com.sun.source.tree.Tree;

import java.util.Comparator;

import java.util.*;

public class Solver {

    Random rand = new Random();
    TreeMap[] a = new TreeMap[9];
    static ArrayList<Integer> Range  = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    public Solver(){
    }

    public void CalcValids(int[][] Sudoku){
        ArrayList<Tuple> MissingInPos;
        MissingInPos = GetsValidsIn3x3(Sudoku);
            for (Tuple te: MissingInPos) {
                te.SetMissing(GetValidsInRowAndCol(te,Sudoku));
                }
            solveSudoku(Sudoku,a);
        Print(Sudoku);
    }
    public  boolean solveSudoku(
            int[][] board,TreeMap[] tm)
    {

        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j] == 0)
                {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }
        if (isEmpty)
        {
            return true;
        }

        for (int num = 0; num < ((Tuple) tm[row].get(col)).getMissing().size(); num++)
        {
            if (IsValid(((Tuple) tm[row].get(col)),board,((Tuple) tm[row].get(col)).getMissing().get(num)))
            {
                board[row][col] = ((Tuple) tm[row].get(col)).getMissing().get(num);
                ((Tuple) tm[row].get(col)).SetValue(((Tuple) tm[row].get(col)).getMissing().get(num));
                if (solveSudoku(board, tm))
                {
                    return true;
                }
                else
                {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }
    public boolean IsValid (Tuple Pos,int[][] Sudoku,int Value){
        for (int i = 0; i < 9; i++) {
            if (Sudoku[Pos.GetCol()][i] == Value && Pos.GetRow() != i ){
                return false;
            }
            if (Sudoku[i][Pos.GetRow()] == Value && Pos.GetCol() != i) {
                return false;
            }
        }
        int c = 1+((Pos.GetCol())/3) * 3;
        int r =1+((Pos.GetRow())/3) * 3;
        for (int colNum = c - 1 ; colNum <= (c + 1) ; colNum +=1  ) {

            for (int rowNum = r - 1 ; rowNum <= (r + 1) ; rowNum +=1  ) {
                if (Sudoku[colNum][rowNum] !=0) {
                    if (Value == Sudoku[colNum][rowNum] ){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void Print(int[][] Sudoku){
        for (int[] col: Sudoku) {
            for (int value: col) {
                System.out.print(value);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }



    public ArrayList<Integer> GetValidsInRowAndCol(Tuple pos,int[][] Sudoku) {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        ArrayList<Integer> rangetmp = new ArrayList<>(pos.getMissing());
        for (int i = 0; i < 9; i++) {
            if (Sudoku[pos.GetCol()][i] != 0) {
                tmp.add(Sudoku[pos.GetCol()][i]);
            } else{
                if (a[pos.GetCol()] == null){
                    a[pos.GetCol()] = new TreeMap();
                    a[pos.GetCol()].put(pos.GetRow(),pos);
                }
                else{
                    a[pos.GetCol()].put(pos.GetRow(),pos);
                }
            }
            if (Sudoku[i][pos.GetRow()] != 0) {
                tmp.add(Sudoku[i][pos.GetRow()]);
            }
        }

        for (Integer t: tmp) {
            if (rangetmp.contains(t)){
                rangetmp.remove(t);
            }

        }
        return rangetmp;

    }
    public ArrayList<Tuple> GetsValidsIn3x3(int[][] Sudoku){
        ArrayList<Tuple> tmp1 = new ArrayList<>();
        int tempcount = 0;
        int count = 0;
        for (int i = 1; i < 9; i+=3) {
            for (int j = 1; j < 9; j+=3) {
                ArrayList<Integer> rangetmp = new ArrayList<>(Range);
                tempcount = tmp1.size();
                for (int colNum = i - 1 ; colNum <= (i + 1) ; colNum +=1  ) {

                    for (int rowNum = j - 1 ; rowNum <= (j + 1) ; rowNum +=1  ) {
                        if (Sudoku[colNum][rowNum] !=0) {
                            if (rangetmp.contains(Sudoku[colNum][rowNum])){
                                rangetmp.remove(rangetmp.indexOf(Sudoku[colNum][rowNum]));
                            }
                        } else{
                            tmp1.add(new Tuple(colNum,rowNum));
                        }
                    }
                }
                for (int h = tempcount; h <tmp1.size();h++){
                    tmp1.get(h).SetMissing(rangetmp);
                }
                count++;
            }
        }
        return tmp1;
    }
}
class TupleSorter implements Comparator<Tuple>
{
    @Override
    public int compare(Tuple o1, Tuple o2) {
        return (((Integer) o1.GetRow())).compareTo(((Integer) o2.GetRow()));
    }
}
class Tuple{

    private int col,row,Value;
    private ArrayList<Integer> missing;

    Tuple(int col, int row){
        this.col = col;
        this.row = row;
    }
    Tuple(int col, int row, int Value){
        this.col = col;
        this.Value = Value;
        this.row = col;
    }
    Tuple(int col, int row, ArrayList<Integer> missing){
        this.col = col;
        this.row = row;
        this.missing = missing;
    }

    public int GetCol() {
        return col;
    }

    public int GetRow() {
        return row;
    }
    public void SetValue( int Value){
        this.Value = Value;
    }
    public int GetValue(){
        return this.Value;
    }

    public ArrayList<Integer> getMissing() {
        return missing;
    }
    public void SetMissing(ArrayList<Integer> missing){
        this.missing = missing;

    }
}

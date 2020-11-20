package com.company;
import java.util.Comparator;

import java.util.*;

public class Solver {
    private int[][] Soduko;
    Random rand = new Random();
    static ArrayList<Integer> Range  = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    public Solver(int[][] Soduko){
        this.Soduko = Soduko;
    }
// to do next choose technique, either backtracking or shuffle

    public int[][] Solve(int[][] Soduko){
        ArrayList<Tuple>[] MissingInPos;
        MissingInPos = GetMissingIn3x3(Soduko);
        for (ArrayList<Tuple> ta : MissingInPos ) {
            ta.sort(new TupleSorter());
            for (Tuple te: ta) {
                GetMissingIn1x1(new Tuple(te.GetCol(),te.GetRow())).forEach( (e) ->{
                    if (te.getMissing().contains(e)){
                        te.getMissing().remove(e);
                    }
                });
                te.SetValue(te.getMissing().get(rand.nextInt(te.getMissing().size())));
            }
        }
        return null;
    }
    public int[][] CreateSodukoMap(){
        return null;
    }

    public ArrayList<Tuple> Checks (ArrayList<Tuple>[] MissingInPos){
        ArrayList<Tuple> List = new ArrayList<>();
        for (ArrayList<Tuple> ta : MissingInPos ) {
            for (Tuple te: ta) {
                for (int i = 0; i < 9; i++) {
                    if (Soduko[te.GetCol()][i] == te.GetValue() && te.GetRow() != i ){
                        List.add(te);
                        //backtracking
                        te.getMissing().remove(te.GetValue());
                    }
                    if (Soduko[i][te.GetRow()] == te.GetValue() && te.GetCol() != i) {
                        List.add(te);
                        //backtracking
                        te.getMissing().remove(te.GetValue());
                    }
                }
                te.SetValue(te.getMissing().get(rand.nextInt(te.getMissing().size())));
            }
        }
        return List;
    }

    public ArrayList<Integer> GetMissingIn1x1(Tuple pos) {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        ArrayList<Integer> rangetmp = Range;
        for (int i = 0; i < 9; i++) {
            if (Soduko[pos.GetCol()][i] != 0) {
                tmp.add(Soduko[pos.GetCol()][i]);
            }
            if (Soduko[i][pos.GetRow()] != 0) {
                tmp.add(Soduko[i][pos.GetRow()]);
            }
        }

        for (Integer t: tmp) {
            if (rangetmp.contains(t)){
                rangetmp.remove(t);
            }

        }
        return rangetmp;

    }
    public ArrayList<Tuple>[] GetMissingIn3x3(int[][] Soduko){
        ArrayList<Tuple>[] tmp1 = new ArrayList[9];
        ArrayList<Integer>[] tmp = new ArrayList[9];
        int count = 0;
        for (int i = 1; i < 9; i+=3) {
            for (int j = 1; j < 9; j+=3) {
                ArrayList<Integer> rangetmp = Range;
                for (int colNum = i - 1 ; colNum <= (i + 1) ; colNum +=1  ) {

                    for (int rowNum = j - 1 ; rowNum <= (j + 1) ; rowNum +=1  ) {
                        if (Soduko[colNum][rowNum] !=0) {
                            if (rangetmp.contains(Soduko[colNum][rowNum])){
                                rangetmp.remove(Soduko[colNum][rowNum]);
                            }
                        } else{
                            tmp1[count].add(new Tuple(colNum,rowNum));
                        }
                    }
                }
                for (ArrayList<Tuple> ta : tmp1 ) {
                    for (Tuple te: ta) {
                        te.SetMissing(rangetmp);
                    }
                }
                tmp[count] = rangetmp;
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
        return (((Integer) o1.getMissing().size())).compareTo(((Integer) o2.getMissing().size()));
    }
}
class Tuple{

    private int col,row,Value;
    private boolean Violation = false;
    private ArrayList<Integer> missing;

    Tuple(int col, int row){
        this.col = col;
        this.row = col;
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
    public boolean GetViolation(){
        return  Violation;
    }
    public void SetViolation(boolean Violation){
        this.Violation = Violation;
    }

    public ArrayList<Integer> getMissing() {
        return missing;
    }
    public void SetMissing(ArrayList<Integer> missing){
        this.missing = missing;

    }
}

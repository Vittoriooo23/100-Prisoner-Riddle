package Escape;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    private int numPrisoners;
    private int[] boxes;
    private ArrayList<Integer> nums = new ArrayList<>();
    Random rand;

    public Main(int numPrisoners){
        this.numPrisoners = numPrisoners;
        boxes = new int[numPrisoners];
        resetNums();
        rand = new Random();
        initBoxes();
    }

    private void resetNums(){
        nums.clear();
        for(int i = 0; i < numPrisoners; i++){
            nums.add(i);
        }
    }

    private void initBoxes(){       //sets random values inside boxes
        int randomIndex;
        for(int i = 0; i < numPrisoners; i++){
            randomIndex = rand.nextInt(nums.size());
            boxes[i] = nums.get(randomIndex);
            nums.remove(randomIndex);
        }
        resetNums();
    }

    public double randomSelection(int numTrials){
        double percent;
        boolean failed = false;
        double results = 0;
        int randomIndex;

        for(int i = 0; i < numTrials; i++){
            for(int j = 0; j < numPrisoners; j++){
                for(int k = 0; k < numPrisoners/2; k++){        //k represents number of guesses
                    randomIndex = rand.nextInt(nums.size());
                    if( j == boxes[nums.get(randomIndex)]){     //j represents prisoner number
                        break;
                    }
                    if( k == numPrisoners/2 -1){
                        failed = true;
                        break;
                    }
                    nums.remove(randomIndex);
                }
                resetNums();

                if(failed){     //if 1 prisoner failed, then break out of that trial
                    break;
                }
            }

            if(!failed){
                results = results + 1;
            }
            failed = false;
            initBoxes();
        }
        percent = results / numTrials;
        return percent;
    }

    public double strategicSelection(int numTrials){
        double percent;
        boolean failed = false;
        double results = 0;
        int lastNum;

        for(int i = 0; i < numTrials; i ++){
            for(int j = 0; j < numPrisoners; j++){
                lastNum = j;
                for(int k = 0; k < numPrisoners/2; k++){
                    if(boxes[lastNum] == j){
                        break;
                    }
                    if(k == numPrisoners/2 -1){
                        failed = true;
                        break;
                    }
                    lastNum = boxes[lastNum];
                }
                if(failed){
                    break;
                }
            }

            if(!failed){
                results = results + 1;
            }
            failed = false;
            initBoxes();
        }
        percent = results / numTrials;
        return percent;

    }






    public static void main(String[] args) {
        int numPrisoners = 100;
	    Main main = new Main(numPrisoners);
        int numTrials = 1000000;
        System.out.println("Percentages are calculated after " + numTrials + " attempts.");
        System.out.println("Percentage chance of escape using random selection: " + main.randomSelection(numTrials));
        System.out.println("Percentage chance of escape using strategic selection: " + main.strategicSelection(numTrials));
    }
}

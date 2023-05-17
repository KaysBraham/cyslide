package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generategame {
    //supposing that the cases are correctly blocked
    private int format; //3*3

    private int [][] emptycase=new int[format][format];
    private String [][] filledcase=new String[format][format];

    //option 1:randomly
    public Generategame(int numbers) {
        this.format = numbers;
        emptycase = new int[format][format];
        filledcase = new String[format][format];
    }
    //option 2:by the user
    public Generategame(int format, int[][] emptycase, String[][] filledcase) {
        this.format = format;

        this.emptycase = emptycase;
        this.filledcase = filledcase;
    }


    public String[][] generateNumbers() {
        int index1;
        int index2;

        List<Integer> outnumbers = new ArrayList<>();//List number

        Random random = new Random();


//----------------------------------------------step 1:generate empty case--------------------------------------------------------
        int occupiedcase=random.nextInt(format-3); //to have empty case<=format-3
        if (emptycase == null) {
            emptycase = new int[format][format];

            //to initialize in zero for emptycase
            for (int i = 0; i < format; i++) {
                for (int j = 0; j < format; j++) {
                    emptycase[i][j] = 0;
                }
            }
        }
        int randomx;//to random x and y by the next
        int randomy;
        for(int i=0;i<=occupiedcase;i++){

            do {

                randomx = random.nextInt(format);//random on x
                randomy = random.nextInt(format);//random on y
            } while (emptycase[randomx][randomy]!=0);
            emptycase[randomx][randomy]=1;

        }

 //----------------------------------------step 2:to place the numbers--------------------------------------------------------

        for (int i = 0; i < format*format -occupiedcase; i++) { //add the numbers until to number of case -1
            outnumbers.add(i+1);


        }

        for(int i=0;i< random.nextInt(outnumbers.size());i++){

            do {

                randomx = random.nextInt(format);//random on x
                randomy = random.nextInt(format);//random on y
            } while (emptycase[randomx][randomy]!=0 && emptycase[randomx][randomy]!=1 );
            emptycase[randomx][randomy]=2; //set 2 on this empty case in order to fill in number

        }

//----------------------------------------------step 3:generate empty--------------------------------------------------------

        for (int i = 0; i < format; i++) {
            for (int j = 0; j < format; j++) {

                if(emptycase[i][j] == 0){
                    emptycase[i][j]=3;
                }

            }
        }



//-----------------------------------------step 4:build taquin protoptype---------------------------------------------------------------------
        int index=0;
        List<Integer> listnumbers = new ArrayList<>();//List number

        for (int i = 0; i < format; i++) {
            for (int j = 0; j < format; j++) {

                switch (emptycase[i][j]) {
                    case 1:
                        filledcase[i][j]=" ";
                        break;
                    case 2:
                        filledcase[i][j]="N";
                        listnumbers.add(index);
                        index+=1;
                        break;
                    default:
                        filledcase[i][j]="_";

                }
            }
        }




        Collections.shuffle(listnumbers); //reverse the
        for (Integer number:listnumbers){
            outerloop:
            for (int i = 0; i < format; i++) {
                for (int j = 0; j < format; j++) {
                    if(filledcase[i][j]=="N"){
                        filledcase[i][j]=number.toString();
                        break outerloop;
                    }

                }
            }
        }


                        /*option 2: by the user*/

/*
        int randomNumber = random.nextInt(21);
        if (randomNumber % 2 != 0) {
            randomNumber++;
        }
        for(int i=0;i<randomNumber;i++){
            index1=random.nextInt(format-1);
            do {
                index2 = random.nextInt(format-1);
            } while (index2 == index1);


            int temp = outnumbers.get(index1);
            outnumbers.set(index1, outnumbers.get(index2));
            outnumbers.set(index2, temp);
             }
        System.out.println(randomNumber);

        return outnumbers; //return the reversed numbers
        */
        return filledcase;
    }
//if you want to test the program
    public static void main(String[] args) {
        Generategame listnumber = new Generategame(9);
        //List<Integer> numbers = listnumber.generateNumbers();
        String tab[][]=listnumber.generateNumbers();


        for (int i = 0; i < tab.length; i++) { //to see the result
            for (int j = 0; j < tab.length; j++) {
                System.out.print(tab[i][j]+" ");
            }
            System.out.println("");
        }
    }
}

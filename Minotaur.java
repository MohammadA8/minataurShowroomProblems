
import java.lang.*;
import java.util.*;


class Labyrinth
{
    boolean cupcake;
    int individualsVisited;
    int totalGuests;
    boolean stop;

    public Labyrinth(int totalGuests)
    {

        cupcake = true;
        this.totalGuests = totalGuests;
        stop = false;

    }

    synchronized void cupcakeWasEaten()
    {

        cupcake = false;

    }

    synchronized void increment()
    {

        individualsVisited++;

    }

    synchronized void requestCupcake()
    {

        cupcake = true;

    }

    public synchronized void exitManshion()
    {

        stop = true;

    }

}

class Guest
{

    boolean eaten;
    boolean visit;

    public Guest()
    {

        eaten = false;
        visit = false;

    }

    public void eat()
    {

        eaten = true;

    }

    public synchronized boolean visitingLabyrinth()
    {

        return visit;

    }

    public synchronized void visitLabyrinth()
    {

        visit = true;

    }

    public synchronized void exitLabyrinth()
    {

        visit = false;

    }



}

class CurrentGuest
{
    int i;

    public CurrentGuest(int i)
    {

        this.i = i;

    }

}

public class Minotaur
{


    public static void main(String [] args)
    {

        int guestsSize = 100;
        
        Thread [] guestsThreads = new Thread[guestsSize];
        Guest [] guests = new Guest[guestsSize];
        Labyrinth labyrinth = new Labyrinth(guestsSize);

        for (int i = 1; i < guestsSize; i++)
        {

            CurrentGuest currentGuest = new CurrentGuest(i);

            guests[i] = new Guest();
            guestsThreads[i] = new Thread(() -> {
                


                while (!labyrinth.stop)
                // while (1==1)
                {
                    synchronized(labyrinth)
                    {
                        if (guests[currentGuest.i].visitingLabyrinth())
                        {
                            System.out.println("Guest " + currentGuest.i + " is in the labyrinth ");
                            if (labyrinth.cupcake)
                            {
                                if (!guests[currentGuest.i].eaten)
                                {
                                    System.out.println("And Guest " + currentGuest.i + " is eating the cupcake");
                                    labyrinth.cupcakeWasEaten();
                                    guests[currentGuest.i].eat();
                                }
                            }
                            guests[currentGuest.i].exitLabyrinth();
                        }
                    }
                }
                
            
            }
            );
            guestsThreads[i].start();

        }

        guests[0] = new Guest();

        guestsThreads[0] = new Thread(() -> {

            while (!labyrinth.stop)
            // while (1==1)
            {

                if (guests[0].visitingLabyrinth())
                {
                    synchronized(labyrinth)
                    {
                    System.out.println("Guest " + 0 + " is in the labyrinth ");
                    if (labyrinth.cupcake)
                    {
                        if (!guests[0].eaten)
                        {
                            System.out.println("And Guest " + 0 + " is eating the cupcake");
                            guests[0].eat();
                            labyrinth.cupcakeWasEaten();
                            labyrinth.increment();
                            labyrinth.requestCupcake();
                            System.out.println("Leader is incrementing");

                        }

                    }
                    else
                    {
                        
                        labyrinth.cupcakeWasEaten();
                        labyrinth.increment();
                        labyrinth.requestCupcake();
                        System.out.println("Leader is incrementing");

                    }
                    guests[0].exitLabyrinth();
                    }
                }
            }
        
        });

        guestsThreads[0].start();   
       
        while (labyrinth.individualsVisited != labyrinth.totalGuests)
        // for (int i = 0; i < 100; i++)
        {

            System.out.println("individualsVisited is " + labyrinth.individualsVisited);

            Random r = new Random();
            int low = 0;
            int high = 100;
            int guest = r.nextInt(high-low) + low;

            System.out.println("guest " + guest + " is choosen to visit the labrynith" );
            guests[guest].visitLabyrinth();

        }

        labyrinth.exitManshion();


        for (Thread thread : guestsThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
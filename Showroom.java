import java.util.concurrent.TimeUnit;

class Room
{

    boolean signReadsAvailable;
    int guestVisited;
    int totalGuests;

    public Room(int totalGuests)
    {

        signReadsAvailable = true;
        guestVisited = 0;
        this.totalGuests = totalGuests;

    }

    public synchronized void visitRoom()
    {

        signReadsAvailable = false;

    }

    public synchronized void exitRoom()
    {

        signReadsAvailable = true;

    }

}

class Guest
{

    boolean visited;
    public Guest()
    {

        visited = false;

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

public class Showroom {


    

    public static void main(String[] args)
    {

        int guestsSize = 100;
        
        Thread [] guestsThreads = new Thread[guestsSize];
        Guest [] guests = new Guest[guestsSize];
        Room room = new Room(guestsSize);

        for (int i = 0; i < guestsSize; i++)
        {

            guests[i] = new Guest();
            CurrentGuest currentGuest = new CurrentGuest(i);
            guestsThreads[i] = new Thread(() -> {

                while (room.guestVisited != room.totalGuests)
                {
                    System.out.println(currentGuest.i + "is stil running around");
                        if (!guests[currentGuest.i].visited)
                        {
                            System.out.println("Guest " + currentGuest.i + " is checking if the room is available ");
                            if (room.signReadsAvailable)
                            {
                                synchronized (room)
                                {
                                    room.signReadsAvailable = false;
                                    System.out.println("I'm guest " + currentGuest.i + " and I'm checking vase in the room");
                                    guests[currentGuest.i].visited = true;
                                    
                                }
                                try {
                                    TimeUnit.MILLISECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                synchronized (room)
                                {
                                    room.guestVisited++;
                                    room.signReadsAvailable = true;
                                    System.out.println("I'm guest " + currentGuest.i + " and I'm exiting the room");
                                }

                            }
                        }
                }
                
            
            }
            );
            guestsThreads[i].start();

        }

        while (1==1)
        {
            
            System.out.println("guests visited the room is " + room.guestVisited);
            if (room.guestVisited == room.totalGuests)
                break;

        }

        for (Thread thread : guestsThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    
}

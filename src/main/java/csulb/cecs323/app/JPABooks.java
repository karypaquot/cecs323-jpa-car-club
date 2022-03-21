/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.


import csulb.cecs323.model.*;

import java.util.concurrent.Flow;
import java.util.logging.Level;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * A simple application to demonstrate how to persist an object in JPA.
 * <p>
 * This is for demonstration and educational purposes only.
 * </p>
 * <p>
 *     Originally provided by Dr. Alvaro Monge of CSULB, and subsequently modified by Dave Brown.
 * </p>
 */
public class JPABooks {
   /**
    * You will likely need the entityManager in a great many functions throughout your application.
    * Rather than make this a global variable, we will make it an instance variable within the CarClub
    * class, and create an instance of CarClub in the main.
    */
   private EntityManager entityManager;

   /**
    * The Logger can easily be configured to log to a file, rather than, or in addition to, the console.
    * We use it because it is easy to control how much or how little logging gets done without having to
    * go through the application and comment out/uncomment code and run the risk of introducing a bug.
    * Here also, we want to make sure that the one Logger instance is readily available throughout the
    * application, without resorting to creating a global variable.
    */
   private static final Logger LOGGER = Logger.getLogger(JPABooks.class.getName());

   /**
    * The constructor for the CarClub class.  All that it does is stash the provided EntityManager
    * for use later in the application.
    * @param manager    The EntityManager that we will use.
    */
   public JPABooks(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {
      LOGGER.setLevel(Level.OFF);
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPABooks");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of CarClub and store our new EntityManager as an instance variable.
      JPABooks JPABooks = new JPABooks(manager);


      // Any changes to the database need to be done within a transaction.
      // See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      tx.begin();

      //create publisher list and prompt user to enter information
      List <Publishers> publishers = new ArrayList<Publishers>();
      Publishers publisher = createPublishers(publishers);
      List<Writing_Group> Writing_group = new ArrayList<Writing_Group>();
      /*System.out.println("\n\nEnter the Publisher's name: ");
      String n = getString();
      System.out.println("\n\nEnter the Publisher's Phone number: ");
      String p = getString();
      System.out.println("\n\nEnter the Publisher's email: ");
      String e = getString();
      publishers.add(new Publisher(n, p, e));
      System.out.println(publishers);*/

      //add a publisher to the publishers list
      publishers.add(new Publishers("Julia", "1234567890", "navarro.jvn@gmail.com"));
      for(int i = 0; i < publishers.size(); i++){
         System.out.println((publishers.get(i)));
      }
      JPABooks.createEntity(publishers);

      //create authoring entities list
      List<Writing_Group> authors = new ArrayList<Writing_Group>();
      authors.add(new Writing_Group("audreysimp@gmail.com", "Audrey's Group", "Audrey", 1998));
      for(int i = 0; i < authors.size(); i++){
         System.out.println((authors.get(i)));
      }
      //System.out.println(authors);
      JPABooks.createEntity(authors);

      //create Ad Hoc Team
      List<Ad_Hoc_Team> team = new ArrayList<Ad_Hoc_Team>();
      team.add(new Ad_Hoc_Team("team@gmail.com", "My team"));
      for(int j = 0; j < team.size(); j++){
         System.out.println((team.get(j)));
      }

      JPABooks.createEntity(team);

      //create books list that will take in authors and publishers
      List <Books> books = new ArrayList<Books>();
      books.add(new Books("abc123", "Julia's Story", 1995, authors.get(0), publishers.get(0)));
      for(int i = 0; i < books.size(); i++){
         System.out.println((books.get(i)));
      }
      //System.out.println(books);
      JPABooks.createEntity(books);



      tx.commit();
      /*Publisher pub = manager.find(Publisher.class, publishers.get(0).get_publisherID());
      manager.getTransaction().begin();
      manager.remove(pub);
      manager.getTransaction().commit();
       */


      LOGGER.fine("End of Transaction");



   } // End of the main method

   /**
    * Create and persist a list of objects to the database.
    * @param entities   The list of entities to persist.  These can be any object that has been
    *                   properly annotated in JPA and marked as "persistable."  I specifically
    *                   used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the CarClub entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

   /**
    * Method utilizes scanner to take in user input, stores the line
    * Then returns a String when method is called
    * @return input String value from scanner
    */
   public static String getString() {
      Scanner in = new Scanner(System.in);
      String input = in.nextLine();
      return input;
   }

   public static int getInt () {
      Scanner in = new Scanner(System.in);
      int answer = 0;
      boolean valid = false;
      while (!valid) {
         if (in.hasNextInt()) {
            answer = in.nextInt();
            valid = true;
         } else {
            in.next(); //clear invalid string
            System.out.println("Invalid Input.");
         }
      }
      return answer;
   }

   public static Publishers createPublishers(List<Publishers> list){
      System.out.println("Enter the publisher's name: ");
      String publishers_name = getString();
      System.out.println("Enter the publisher's number: ");
      String publishers_number = getString();
      System.out.println("Enter the publisher's email: ");
      String publishers_email = getString();
      Publishers new_publisher = new Publishers(publishers_name,publishers_number,publishers_email);
      list.add(new_publisher);
      return new_publisher;
   }


   public static void createBook(List<Books> b, Authoring_Entities ae, Publishers p)
   {
      System.out.println("You are creating a new book. Please enter the ISBN: ");
      String isbn = getString();
      System.out.println("\nEnter the title: ");
      String title = getString();
      System.out.println("\nEnter the year published: ");
      int year = getInt();

      b.add(new Books(isbn, title, year, ae, p));
   }

   public static Writing_Group createWritingGroup(List<Writing_Group> list){
      System.out.println("Enter the Writing Group's Name: ");
      String Name = getString();
      System.out.println("Enter the Writing Group's email: ");
      String Email = getString();
      System.out.println("Enter the Writing Group's Head Writer: ");
      String HeadWriter = getString();
      System.out.println("Enter the year the group was formed: ");
      int year = getInt();
      Writing_Group writing_group = new Writing_Group(Email,Name,HeadWriter,year);
      list.add(writing_group);
      return writing_group;
   }




} // End of CarClub class

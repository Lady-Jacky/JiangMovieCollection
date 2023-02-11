import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        /* TASK 1: FINISH THE CODE BELOW */
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        // keywords, overview, runtime (int), genres,
        // user rating (double), year (int), and revenue (int)


        // create a Movie object with the row data:
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRatings = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        // add the Movie to movies:
        Movie nextMovie = new Movie(title, cast, director, tagline,keywords,overview,runtime,genres,userRatings,year,revenue);
        movies.add(nextMovie);
      }
      bufferedReader.close();
    } catch(IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();
    for(int i = 0; i < movies.size(); i++) {
      if(movies.get(i).getKeywords().contains(searchTerm)) {
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that keyword!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void searchCast() {
    System.out.print("Enter the name of a cast: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();
    ArrayList<String> cast = new ArrayList<String>();
    for (int a = 0; a < movies.size(); a++) {
      String[] actor = movies.get(a).getCast().split("\\|");
      for (int c = 0; c < actor.length; c++) {
        String name = actor[c].toLowerCase();
        if (name.contains(searchTerm) && !cast.contains(actor[c])) {
          cast.add(actor[c]);
        }
      }
    }
    if (cast.size() > 0) {

      Collections.sort(cast);

      for (int i = 0; i < cast.size(); i++) {
        String person = cast.get(i);

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + person);
      }
    }
    System.out.println("Which cast would you like the find the movies of? ");
    System.out.print("Enter the number next to the cast: ");
    int searchNum = scanner.nextInt();
    String selectedCast = cast.get(searchNum - 1);
    ArrayList<String> titles = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++) {
      if (movies.get(i).getCast().contains(selectedCast) && !titles.contains(movies.get(i).getTitle())) {
        results.add(movies.get(i));
        titles.add(movies.get(i).getTitle());
      }
    }
    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that keyword!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }
  private void listGenres() {
    ArrayList<Movie> results = new ArrayList<Movie>();
    ArrayList<String> genre = new ArrayList<String>();
    for (int a = 0; a < movies.size(); a++) {
      String[] genres = movies.get(a).getGenres().split("\\|");
      for(int g = 0; g < genres.length; g++) {
        if(!genre.contains(genres[g])) {
          genre.add(genres[g]);
        }
        }
    }
    if (genre.size() > 0) {

      Collections.sort(genre);

      for (int i = 0; i < genre.size(); i++) {
        String type = genre.get(i);

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + type);
      }
    }
    System.out.println("Which genre of movies would you like to find? ");
    System.out.print("Enter the number next to the genre: ");
    int searchNum = scanner.nextInt();
    String selectedGenre = genre.get(searchNum - 1);


    for (int i = 0; i < movies.size(); i++) {
      if (movies.get(i).getGenres().contains(selectedGenre)) {
        results.add(movies.get(i));
      }
    }
    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that keyword!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }
  
  private void listHighestRated() {
    int count = 0;
    ArrayList<Double> ratings = new ArrayList<>();
    ArrayList<Movie> titles = new ArrayList<>();
    ArrayList<Double> topRatings = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++) {
      ratings.add(movies.get(i).getUserRating());
    }
    Collections.sort(ratings);

    for (int g = movies.size()-1; g >= movies.size()-50; g--) {
      topRatings.add(ratings.get(g));
    }

    for(int a = 0; a < topRatings.size(); a++) {
      for(int b = 0; b < movies.size(); b++) {
        if(topRatings.get(a) == movies.get(b).getUserRating()) {
          if(!titles.contains(movies.get(b))) {
            titles.add(movies.get(b));
          }
        }
      }
    }

    for (int i = 0; i < 50; i++) {
      // this will print index 0 as choice 1 in the results list; better for user!
      count += 1;
      System.out.println("" + count + ". " + titles.get(i).getTitle() + ": " + topRatings.get(i));
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();
    Movie selectedMovie = titles.get(choice - 1);
    displayMovieInfo(selectedMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
    }



  
  private void listHighestRevenue() {
    int count = 0;
    ArrayList<Integer> revenue = new ArrayList<>();
    ArrayList<Integer> topRevenue = new ArrayList<>();
    ArrayList<Movie> titles = new ArrayList<>();

    for(int i = 0; i < movies.size(); i ++) {
      revenue.add(movies.get(i).getRevenue());
    }
    Collections.sort(revenue);

    for (int g = movies.size()-1; g >= movies.size()-50; g--) {
      topRevenue.add(revenue.get(g));
    }

    for(int a = 0; a < topRevenue.size(); a++) {
      for(int b = 0; b < movies.size(); b++) {
        if(topRevenue.get(a) == movies.get(b).getRevenue()) {
          if(!titles.contains(movies.get(b))) {
            titles.add(movies.get(b));
          }
        }
      }
    }

    for (int i = 0; i < 50; i++) {
      // this will print index 0 as choice 1 in the results list; better for user!
      count += 1;
      System.out.println("" + count + ". " + titles.get(i).getTitle() + ": $"+ topRevenue.get(i));
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();
    Movie selectedMovie = titles.get(choice - 1);
    displayMovieInfo(selectedMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
}
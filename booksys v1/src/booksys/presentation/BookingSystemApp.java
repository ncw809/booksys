/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation ;

import java.awt.* ;
import java.awt.event.* ;

public class BookingSystemApp extends Frame
{
  private StaffUI ui ;

  public BookingSystemApp()
  {
    setTitle("Restaurant Booking System") ;
    setResizable(false) ;
    
    setMenuBar(new MenuBar()) ;
    
    Menu fileMenu = new Menu("File") ;
    getMenuBar().add(fileMenu) ;

    MenuItem quit = new MenuItem("Quit") ;
    quit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0) ;
		}
    }) ;
    fileMenu.add(quit) ;
    
    Menu dateMenu = new Menu("Date") ;
    getMenuBar().add(dateMenu) ;

    MenuItem display = new MenuItem("Display...") ;
    display.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  ui.displayDate() ;
	}
      }) ;
    dateMenu.add(display) ;
    
    Menu bookingMenu = new Menu("Booking") ;
    getMenuBar().add(bookingMenu) ;
    
    MenuItem newReservation = new MenuItem("New Reservation...") ;
    newReservation.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  ui.addReservation() ;
	}
      }) ;
    bookingMenu.add(newReservation) ;

    MenuItem newWalkIn = new MenuItem("New Walk-in...") ;
    newWalkIn.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  ui.addWalkIn() ;
	}
      }) ;
    bookingMenu.add(newWalkIn) ;

    MenuItem cancel = new MenuItem("Cancel") ;
    cancel.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  ui.cancel() ;
	}
      });
    bookingMenu.add(cancel) ;

    MenuItem recordArrival = new MenuItem("Record Arrival") ;
    recordArrival.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  ui.recordArrival() ;
	}
      });
    bookingMenu.add(recordArrival) ;
    
    this.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {
	  System.exit(0) ;
	}
      }) ;

    ui = new StaffUI(this) ;
    this.add(ui) ;

    this.pack() ;
    this.show() ;
  }
  
  public static void main(String args[])
  {
    new BookingSystemApp() ;
  }
}

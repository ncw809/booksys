/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation ;

import booksys.application.domain.* ;

import java.awt.* ;
import java.awt.event.* ;
import java.sql.Date ;
import java.sql.Time ;
import java.util.* ;

public class StaffUI extends Canvas implements BookingObserver 
{
  final static int LEFT_MARGIN   = 50 ;
  final static int TOP_MARGIN    = 50 ;
  final static int BOTTOM_MARGIN = 50 ;
  final static int ROW_HEIGHT    = 30 ;
  final static int COL_WIDTH     = 60 ;

  final static int PPM = 2 ;	    // Pixels per minute
  final static int PPH = 60 * PPM ; // Pixels per hours
  final static int TZERO = 18 ;     // Earliest time shown
  final static int SLOTS = 12 ;     // Number of booking slots shown
  
  // Routines to convert between (x, y) and (time, table)

  private int timeToX(Time time) {
    return LEFT_MARGIN
           + PPH * (time.getHours() - TZERO)
           + PPM * time.getMinutes() ;
  }

  private Time xToTime(int x) {
    x -= LEFT_MARGIN ;
    int h = (x / PPH) + TZERO ;
    int m = (x % PPH) / PPM ;
    return new Time(h, m, 0) ;
  }

  private int tableToY(int table) {
    return TOP_MARGIN + (ROW_HEIGHT * (table - 1)) ;
  }

  private int yToTable(int y) {
    return ((y - TOP_MARGIN) / ROW_HEIGHT) + 1 ;
  }

  // Data members
  
  private Frame parentFrame ;
  private BookingSystem bs ;
  private Image offscreen ;
  private Vector tableNumbers ;
  private int firstX, firstY, currentX, currentY ;
  private boolean mouseDown ;
  
  public StaffUI(Frame f)
  {
    parentFrame = f ;

    tableNumbers = BookingSystem.getTableNumbers() ;
    setSize(LEFT_MARGIN + (SLOTS * COL_WIDTH),
	    TOP_MARGIN + tableNumbers.size()*ROW_HEIGHT + BOTTOM_MARGIN) ;
    setBackground(Color.white) ;
    bs = BookingSystem.getInstance() ;
    bs.addObserver(this) ;
    Calendar now = Calendar.getInstance() ;
    bs.display(new Date(now.getTimeInMillis())) ;

    addMouseListener(new MouseAdapter() {
	public void mousePressed(MouseEvent e) {
	  currentX = firstX = e.getX() ;
	  currentY = firstY = e.getY() ;
	  if (e.getButton() == MouseEvent.BUTTON1) {
	    mouseDown = true ;
	    bs.selectBooking(yToTable(firstY), xToTime(firstX)) ;
	  }
	}
      });
    addMouseListener(new MouseAdapter() {
	public void mouseReleased(MouseEvent e) {
	  mouseDown = false ;
	  bs.transfer(xToTime(currentX), yToTable(currentY)) ;
	}
      });
    addMouseMotionListener(new MouseMotionAdapter() {
	public void mouseDragged(MouseEvent e) {
	  currentX = e.getX() ;
	  currentY = e.getY() ;
	  update() ;
	}
      }) ;
  }

  public void update()
  {
    repaint() ;
  }

  public void paint(Graphics g)
  {
    update(g) ;
  }

  public void update(Graphics g)
  {
    Dimension canvasSize = getSize() ;
    if (offscreen == null) {
      offscreen = this.createImage(canvasSize.width, canvasSize.height) ;
    }
    Graphics og = offscreen.getGraphics() ;
    og.setColor(getBackground()) ;
    og.fillRect(0, 0, canvasSize.width, canvasSize.height) ;
    og.setColor(Color.black) ;
    
    // Draw screen outlines
    
    og.drawLine(LEFT_MARGIN, 0, LEFT_MARGIN, canvasSize.height) ;
    og.drawLine(0, TOP_MARGIN, canvasSize.width, TOP_MARGIN) ;

    // Write table numbers and horizontal rules
    
    for (int i = 0; i < tableNumbers.size(); i++) {
      int y = TOP_MARGIN + (i+1)*ROW_HEIGHT ;
      og.drawString(Integer.toString(i+1), 0, y) ;
      og.drawLine(LEFT_MARGIN, y, canvasSize.width, y) ;
    }

    // Write time labels and vertical rules
    
    for (int i = 0; i < SLOTS; i++) {
      String tmp = (TZERO+(i/2)) + (i%2 == 0? ":00" : ":30") ;
      int x = LEFT_MARGIN + i*COL_WIDTH ;
      og.drawString(tmp, x, 40) ;
      og.drawLine(x, TOP_MARGIN, x, canvasSize.height-BOTTOM_MARGIN) ;
    }

    // Display booking information

    og.drawString(((java.util.Date) bs.getCurrentDate()).toString(),
		  LEFT_MARGIN, 20) ;
    
    Enumeration enums = bs.getBookings() ;
    while (enums.hasMoreElements()) {
      Booking b = (Booking) enums.nextElement() ;
      int x = timeToX(b.getTime()) ;
      int y = tableToY(b.getTable().getNumber()) ;
      og.setColor(Color.gray) ;
      og.fillRect(x, y, 4*COL_WIDTH, ROW_HEIGHT) ;
      if (b == bs.getSelectedBooking()) {
	og.setColor(Color.red) ;
	og.drawRect(x, y, 4*COL_WIDTH, ROW_HEIGHT) ;
      }
      og.setColor(Color.white) ;
      og.drawString(b.getDetails(), x, y + ROW_HEIGHT/2) ;
    }

    // Draw an outline to represent position of dragged booking
    
    Booking b = bs.getSelectedBooking() ;
    if (mouseDown && b != null) {
      int x = timeToX(b.getTime()) + currentX - firstX ;
      int y = tableToY(b.getTable().getNumber()) + currentY - firstY ;
      og.setColor(Color.red) ;
      og.drawRect(x, y, 4*COL_WIDTH, ROW_HEIGHT) ;
    }
    
    // Write to canvas
    
    g.drawImage(offscreen, 0, 0, this) ;
  }

  public boolean message(String message, boolean confirm)
  {
    ConfirmDialog d =
      new ConfirmDialog(parentFrame, message, confirm) ;
    d.show() ;
    return d.isConfirmed() ;
  }
  
  void displayDate()
  {
    DateDialog d
      = new DateDialog(parentFrame, "Enter a date") ;
    d.show() ;
    if (d.isConfirmed()) {
      Date date = d.getDate() ;
      bs.display(date) ;
    }
  }

  void addReservation()
  {
    ReservationDialog d
      = new ReservationDialog(parentFrame,
			      "Enter reservation details") ;
    d.show() ;
    if (d.isConfirmed()) {
      bs.makeReservation(d.getCovers(),
			 bs.getCurrentDate(),
			 d.getTime(),
			 d.getTableNumber(),
			 d.getCustomerName(),
			 d.getPhoneNumber()) ;
    }
  }

  void addWalkIn()
  {
    WalkInDialog d
      = new WalkInDialog(parentFrame,
			 "Enter walk-in details") ;
    d.show() ;
    if (d.isConfirmed()) {
      bs.makeWalkIn(d.getCovers(),
		    bs.getCurrentDate(),
		    d.getTime(),
		    d.getTableNumber()) ;
    }
  }
  
  void cancel()
  {
    bs.cancel() ;
  }

  void recordArrival()
  {
    Calendar now = Calendar.getInstance() ;
    bs.recordArrival(new Time(now.getTimeInMillis())) ;
  }
}

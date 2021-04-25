/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;

public class Reservation extends BookingImp
{
  private Customer customer ;
  private Time     arrivalTime ;
  
  public Reservation(int c, Date d, Time t, Table tab, Customer cust, Time arr)
  {
    super(c, d, t, tab) ;
    customer    = cust ;
    arrivalTime = arr ;
  }

  public java.sql.Time getArrivalTime() {
    return arrivalTime ;
  }

  public Customer getCustomer() {
    return customer ;
  }

  public String getDetails()
  {
    StringBuffer details = new StringBuffer(64) ;
    details.append(customer.getName()) ;
    details.append(" ") ;
    details.append(customer.getPhoneNumber()) ;
    details.append(" (") ;
    details.append(covers) ;
    details.append(")") ;
    if (arrivalTime != null) {
      details.append(" [") ;
      details.append(arrivalTime) ;
      details.append("]") ;
    }
    return details.toString() ;
  }

  public void setArrivalTime(Time t) {
    arrivalTime = t ;
  }

  public void setCustomer(Customer c) {
    customer = c ;
  }
}

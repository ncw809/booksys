/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

public class Customer
{
  private String name ;
  private String phoneNumber ;

  public Customer(String n, String p)
  {
    name = n ;
    phoneNumber = p ;
  }

  public String getName()
  {
    return name ;
  }

  public String getPhoneNumber()
  {
    return phoneNumber ;
  }
}

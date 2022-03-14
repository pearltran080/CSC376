/*-------------------------------------------------------------------------*
 *---									---*
 *---		Caller.java						---*
 *---									---*
 *---	    This file defines a Jsoup-library using program that	---*
 *---	queries a Scienceomatic website for the HTML table-listed	---*
 *---	properties of a given subject.					---*
 *---									---*
 *---	----	----	----	----	----	----	----	----	---*
 *---									---*
 *---	Version 1a		2022 March 13		Joseph Phillips	---*
 *---									---*
 *-------------------------------------------------------------------------*/

import	java.net.*;
import	java.io.*;
import	org.json.*;
import	org.jsoup.Jsoup;
import	org.jsoup.nodes.Document;
import	org.jsoup.Connection.Method;
import	org.jsoup.Connection.Response;
import	org.jsoup.nodes.Document;
import	org.jsoup.nodes.Element;
import	org.jsoup.select.Elements;

public
class	Caller
{

  //  PURPOSE:  To hold the default URL.
  public final static
  String	DEFAULT_URL	= "http://scienceomatic.com:8080";

  //  PURPOSE:  To hold the default subject whose properties should be queried.
  public final static
  String	DEFAULT_SUBJECT	= "theBoat";


  //  PURPOSE:  To hold the URL of the website to query.
  private
  String	urlPrefix_	= DEFAULT_URL;


  //  PURPOSE:  To hold the subject whose properties should be queried.
  private
  String	subject_	= DEFAULT_SUBJECT;


  //  PURPOSE:  To hold the session cookie.
  private
  String	sessionCookie_	= "";


  //  PURPOSE:  To return the URL of the website to query.  No parameters.
  protected
  String	getUrlPrefix	()
				{ return(urlPrefix_); }


  //  PURPOSE:  To return the subject whose properties should be queried.
  protected
  String	getSubject	()
				{ return(subject_); }


  //  PURPOSE:  To return the session cookie.  No parameters.
  protected
  String	getSessionCookie()
				{ return(sessionCookie_); }


  //  PURPOSE:  To set the session cookie to 'newCookie'.  No return value.
  protected
  void		setSessionCookie(String		newCookie
				)
				{ sessionCookie_	= newCookie; }

  //  PURPOSE:  To do the initial call for knowledge bases matching the
  //  	the keyword query, parse the result for the initial kb number,
  //	and to return this kb number.  No parameters.
  protected
  int		getKbIntFromInitQuery
				()
				throws IOException, JSONException
  {
    Document	doc		= null;
    String	kbList		= "";
    JSONObject	json		= null;
    int		kbIndex		= 0;
    String	url		= getUrlPrefix() + "/kbList?keywords=base";

    System.out.print(url + " => ");
    System.out.flush();

    //  YOUR CODE HERE

    System.out.println(kbIndex);
    return(kbIndex);
  }


  //  PURPOSE:  To give the website the kb id of an existing kb ('oldKbId')
  //	in order to log into a new kb.  Both sets the cookie from this call
  //	('sessionCookie_') and returns id of new kb that we are logged into.
  protected
  int		getKbIntFromKbLogin
				(int		oldKbId
				)
				throws IOException,
				       JSONException,
				       NumberFormatException
  {
    Response	response	= null;
    String	url		= getUrlPrefix() + "/kb/" + oldKbId;
    int		newKbId;

    System.out.print(url + " => ");
    System.out.flush();

    //  YOUR CODE HERE

    System.out.println(newKbId);
    return(newKbId);
  }

  //  PURPOSE:  To get the HTML text of the properties page of 'subject' from
  //	the knowledge base with id 'kbId' and print out the attributes and
  //	their corresponding values from the table.
  protected
  void		printProperties	(int		kbId,
  				 String		subject
  				)
				throws IOException
  {
    Document	document	= null;
    Element	content		= null;
    Elements	tables		= null;
    String	url		= getUrlPrefix()	+
    				  "/kb/"		+
				  kbId			+
				  "/props/"		+
				  subject;

    System.out.println(url + ":");
    System.out.flush();

    //  YOUR CODE HERE

    System.out.print("");
  }


  //  PURPOSE:  To logout of the knowledge base with id 'kbId'.  No return
  //	value.
  protected
  void		logout		(int		kbId
  				)
				throws IOException
  {
    int		newKbId;
    Response	response	= null;
    String	url		= getUrlPrefix()	+
				  "/kb/"		+
				  kbId			+
				  "/noSaveClose?name=YOUR_NAME_HERE";
    //  PUT YOUR NAME ON THE PREVIOUS LINE!

    System.out.print(url + " => ");
    System.out.flush();

    //  YOUR CODE HERE

    System.out.println(response.statusMessage().toString());
  }


  //  PURPOSE:  To initialize 'this' to query website 'newUrlPrefix'.
  //	No return value.
  public
  Caller			(String		newUrlPrefix,
  				 String		newSubject
  				)
  {
    urlPrefix_	= newUrlPrefix;
    subject_	= newSubject;
  }


  //  PURPOSE:  To run the program.   Returns 0 on success or 1 otherwise.
  public
  int		run		()
  {
    int			statusForSys	= 0;

    try
    {
      //  (1) Get the base kb id:
      int	oldKbId	= getKbIntFromInitQuery();

      //  (2) Get the new kb id:
      int	newKbId	= getKbIntFromKbLogin(oldKbId);

      //  (3) Get the properties
      printProperties(newKbId,getSubject());

      //  (4) Logout:
      logout(newKbId);
    }
    catch  (JSONException error)
    {
      System.err.println("Error: " + error);
      statusForSys	= 1;
    }
    catch  (IOException error)
    {
      System.err.println("Error: " + error);
      statusForSys	= 1;
    }
    catch  (NumberFormatException error)
    {
      System.err.println("Error: " + error);
      statusForSys	= 1;
    }

    return(statusForSys);
  }


  //  PURPOSE:  To query the URL either given in 'args[0]' (if it exists) or
  //	'DEFAULT_URL' for the properties of 'theBoat'.  Returns 0 to OS on
  //	success or 1 otherwise.
  public static
  void		main		(String[]		args
				)
  {
    String	urlPrefix	= (args.length < 1) ? DEFAULT_URL : args[0];
    String	subject		= (args.length < 2) ? DEFAULT_SUBJECT : args[1];
    Caller	caller		= new Caller(urlPrefix,subject);
    int		statusForOs	= caller.run();

    System.exit(statusForOs);
  }

}

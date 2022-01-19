/*-------------------------------------------------------------------------*
 *---									---*
 *---		lsRevsortSelect.c					---*
 *---									---*
 *---	    This file defines a program that chains together child	---*
 *---	processes running 'ls', 'sort -r' and 'select int' such that	---*
 *---	the output of one becomes the input for the next using pipes.	---*
 *---	The output of the 'select' process comes back to this process	---*
 *---	using another pipe.  Then this process prints that output.	---*
 *---									---*
 *---	----	----	----	----	----	----	----	----	---*
 *---									---*
 *---	Version 1a		2022 January 9		Joseph Phillips	---*
 *---									---*
 *-------------------------------------------------------------------------*/

#include	<stdlib.h>
#include	<stdio.h>
#include	<string.h>
#include	<errno.h>
#include	<sys/types.h>
#include	<sys/stat.h>
#include	<fcntl.h>
#include	<dirent.h>
#include	<unistd.h>

const int	LINE_LEN	= 4096;

int		main		(int		argc,
				 char*		argv[]
				)
{
  char*	cPtr;
  int	passThruLen;

  //  YOUR CODE HERE

  //  ls
  //
  int	lsToSort[2];
  pid_t	lsChild;

  //  YOUR CODE HERE


  //  sort
  //
  int	sortToSelect[2];
  pid_t	sortChild;

  //  YOUR CODE HERE


  //  select
  int	selectToParent[2];
  pid_t	selectChild;

  //  YOUR CODE HERE


  //  Receive and print output:
  char		line[LINE_LEN];
  int		numBytes;

  while  ( (numBytes=read(selectToParent[STDIN_FILENO],line,LINE_LEN-1)) > 0 )
  {
    line[numBytes]	= '\0';
    printf("%s",line);
  }

  close(selectToParent[STDIN_FILENO]);

  //  YOUR CODE HERE
  return(EXIT_SUCCESS);
}

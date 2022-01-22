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

	if (argc > 1) {
		passThruLen = strtol(argv[1], &cPtr, 10);

		if (passThruLen < 0) {
			fprintf(stderr,"Number format error\n");
			exit(EXIT_FAILURE);
		}
	} else {
		fprintf(stderr,
        "Usage:\tlsRevsortSelect num\n"
        "Where 'num' is a non-negative int telling"
        " the column widths to allow through.\n"
       );
		exit(EXIT_FAILURE);
	}

  //  ls
  //
  int	lsToSort[2];
  pid_t	lsChild;

  //  YOUR CODE HERE
	pipe(lsToSort);
	lsChild = fork();

	if (lsChild == 0) {
		dup2(lsToSort[1], STDOUT_FILENO);
		close(lsToSort[0]);
		close(lsToSort[1]);
		printf("lschild");
		execl("ls", "ls", NULL);

	} else if (lsChild > 0) {
		close(lsToSort[1]);
		printf("lsparent");
	} else {
		exit(EXIT_FAILURE);

	}
  //  sort
  //
  int	sortToSelect[2];
  pid_t	sortChild;

  //  YOUR CODE HERE
	pipe(sortToSelect);
	sortChild = fork();

	if (sortChild == 0) {
		dup2(sortToSelect[1], STDOUT_FILENO);
		close(sortToSelect[0]);
		close(sortToSelect[1]);

		dup2(lsToSort[0], STDIN_FILENO);
		close(lsToSort[0]);
		printf("sortchild");
		execl("sort", "sort", "-r", NULL);

	} else if (sortChild > 0){
		close(lsToSort[0]);
		close(sortToSelect[1]);
		printf("sortparent");
	} else {
		exit(EXIT_FAILURE);

	}

  //  select
  int	selectToParent[2];
  pid_t	selectChild;

  //  YOUR CODE HERE
	pipe(selectToParent);
	selectChild = fork();

	if (selectChild == 0) {
		close(selectToParent[0]);
		close(selectToParent[1]);
		printf("selectchild");
		execl("select", "select", argv[1], NULL);

	} else if (selectChild > 0) {
	  //  Receive and print output:
	  char		line[LINE_LEN];
	  int		numBytes;
		printf("selectparent");
	  while  ( (numBytes=read(selectToParent[STDIN_FILENO],line,LINE_LEN-1)) > 0 )
	  {
	    line[numBytes]	= '\0';
	    printf("%s",line);
	  }

	  close(selectToParent[STDIN_FILENO]);

	  //  YOUR CODE HERE
		wait(NULL);

	} else {
		exit(EXIT_FAILURE);

	}
  return(EXIT_SUCCESS);
}

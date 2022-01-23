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

	if (lsChild < 0) exit(EXIT_FAILURE);

	if (lsChild == 0)
	{
		close(lsToSort[0]);
		printf("ls child  hey\n");
		dup2(lsToSort[1], STDOUT_FILENO);
		close(lsToSort[1]);
		printf("first dup...\n");

		printf("ls child bye\n");
		execl("/usr/bin/ls", "ls", NULL);
		exit(EXIT_FAILURE);

	}

	close(lsToSort[1]);
	printf("ls parent hey\n");
  //  sort
  //
  int	sortToSelect[2];
  pid_t	sortChild;

  //  YOUR CODE HERE
	pipe(sortToSelect);
	sortChild = fork();

	if (sortChild < 0) exit(EXIT_FAILURE);

	if (sortChild == 0)
	{
		close(sortToSelect[0]);
		printf("sort child hey\n");
		dup2(sortToSelect[1], STDOUT_FILENO);
		close(sortToSelect[1]);
		printf("second dup...\n");

		dup2(lsToSort[0], STDIN_FILENO);
		close(lsToSort[0]);
		printf("third dup...\n");

		printf("sort child bye\n");
		execl("sort", "sort", "-r", NULL);
		exit(EXIT_FAILURE);
	}

	printf("sort parent hey\n");
	close(lsToSort[0]);
	close(sortToSelect[0]); // maybe
	close(sortToSelect[1]);

  //  select
  int	selectToParent[2];
  pid_t	selectChild;

  //  YOUR CODE HERE
	pipe(selectToParent);
	selectChild = fork();

	if (selectChild < 0) exit(EXIT_FAILURE);

	if (selectChild == 0)
	{
		printf("select child hey\n");
		close(selectToParent[0]);
		close(selectToParent[1]);

		printf("select child bye\n");
		execl("select", "select", argv[1], NULL);
		exit(EXIT_FAILURE);
	}

	close(selectToParent[1]);
	printf("select parent hey\n");
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
	printf("waiting...\n");
	wait(NULL);
	printf("waiting...\n");
	wait(NULL);
	printf("waiting...\n");
	wait(NULL);
	printf("3 parents bye...\n");
	return(EXIT_SUCCESS);

}

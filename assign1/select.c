/*-------------------------------------------------------------------------*
 *---									---*
 *---		select.c						---*
 *---									---*
 *---	    This file defines a program that takes a non-negative	---*
 *---	integer on the command line, reads lines from 'stdin', and	---*
 *---	prints only those lines to 'stdout' with the specified width.	---*
 *---									---*
 *---	----	----	----	----	----	----	----	----	---*
 *---									---*
 *---	Version 1a		2022 January 9		Joseph Phillips	---*
 *---									---*
 *-------------------------------------------------------------------------*/

#include	<stdlib.h>
#include	<stdio.h>

const int	LINE_LEN	= 4096;

int		main		(int	argc,
				 char*	argv[]
				)
{
  char*	cPtr;
  char	line[LINE_LEN];
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
        "Usage:\tselect num\n"
        "Where 'num' is a non-negative int telling"
        " the column widths to allow through.\n"
       );
		exit(EXIT_FAILURE);
	}

	while(fgets(line, LINE_LEN, stdin) != NULL) {
		if (line[passThruLen] == '\n') {
			printf("%s", line);
			line[passThruLen] = '\0';
		}
	}

  return(EXIT_SUCCESS);
}

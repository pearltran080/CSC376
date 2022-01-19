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

	// fgets(line, LINE_LEN, stdin);

	int i = 0;
	int start = 0;
	int columns = 0;
	int iColumn;

	while (scanf("%c", &line[i]) != EOF) {
		if (line[i] == '\n')  {
			columns = i-start;
			if (columns == passThruLen) {
				for (iColumn = start; iColumn < i; iColumn++) {
					printf("%c", line[iColumn]);
				}
				printf("\n");
			}
			start = i+1;	// set start to index after discovered "new line"
		}
		i++;
	}

  return(EXIT_SUCCESS);
}

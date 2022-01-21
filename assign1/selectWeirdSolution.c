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

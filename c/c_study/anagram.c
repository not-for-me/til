#include <stdio.h>
#include <string.h>
/*
Problem Ref.: https://algospot.com/judge/problem/read/ANAGRAM
*/
int main() {
	int cases = 0;
	int results[1000];

	scanf("%d", &cases);
	for(int i = 0; i < cases; i++){
		char fChars[100];
		char sChars[100];
		int histogram[75] = {0, };
		scanf("%s %s", fChars, sChars);

		if(strcmp(fChars, sChars) == 0 || strlen(fChars) != strlen(sChars)) {
			results[i] = 0;
			continue;
		}

		for(int j = 0; j < strlen(fChars); j++) {
			histogram[(int) fChars[j] - 48]++;
			histogram[(int) sChars[j] - 48]--;
		}

		results[i] = 1;
		for (int k = 0; k < 75; k++) {
			if(histogram[k] != 0) {
				results[i] = 0;
				break;
			}
		}
	}

	for(int i = 0; i < cases; i++) {
		if (results[i] == 1) {
			printf("Yes\n");
		} else {
			printf("No.\n");
		}
	}
}

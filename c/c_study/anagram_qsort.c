#include <stdio.h>
#include <string.h>

int main() {
	int cases = 0;
	int results[1000] = {0, };

	scanf("%d", &cases);
	for(int i = 0; i < cases; i++){
		char fChars[100], sChars[100];
		int histogram[75] = {0, };
		scanf("%s %s", fChars, sChars);

		if(strcmp(fChars, sChars) == 0 || strlen(fChars) != strlen(sChars)) continue;
		
		for(int j = 0; j < strlen(fChars); j++) {
			if( fChars[j] != sChars[j]) {
				histogram[(int) fChars[j] - 48]++;
				histogram[(int) sChars[j] - 48]--;
			}
		}

		results[i] = 1;
		for (int k = 0; k < 75; k++) {
			if(histogram[k] != 0) {
				results[i] = 0;
				break;
			}
		}
	}

	for(int i = 0; i < cases; i++)
		results[i] == 1 ?  printf("Yes\n") : printf("No.\n");
}

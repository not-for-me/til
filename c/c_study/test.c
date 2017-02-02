#include <stdio.h> 

char* husband = "남편"; 
char* wife = "아내";

int buyEgg() {
    printf("%s 왈: 계란 있으면 여섯개 사와\n", wife);
    return 1;
}

int main() { 
  char yesOrNo = 'n';

  printf("%s 왈: 여보 나 일찍 퇴근하고\n들어가는 길인데 마트들러서 뭐 사갈까?: ", husband);
  scanf("%c", &yesOrNo);
  fflush(stdin);

  if ( yesOrNo == 'y' ) {
    printf("%s 왈: 응 사와 사와 우유 사와\n", wife);
    printf("%s 왈: 응 그럼 또 뭐 살꺼 있어?: \n", husband);
    scanf("%c", &yesOrNo);
    fflush(stdin);

    if ( yesOrNo == 'y' ) {
      int hasEggsInMart = buyEgg();
      if ( hasEggsInMart ) {
        printf("계란 여섯개 사와");
      } 
    }
    else {
      printf("%s 왈: 계란 없어도 된다. 우유만 사와\n", wife);
    }
  } 
  else { 
    printf("%s 왈: 아니 필요 없어 그냥 빨리 들어와\n", wife);
  }
}

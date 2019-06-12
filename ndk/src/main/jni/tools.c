#include <sys/types.h>
#include <sys/stat.h>
#include <sys/file.h>
#include <ctype.h>
#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include "libApp.h"
#include "jni_utils.h"
#include "str_utils.h"
#include <time.h>

//16进制转字符串
void HexToStr(char *pbDest, char *pbSrc, int nLen){
    char	ddl,ddh;
    int i;

    for (i=0; i<nLen; i++){
        ddh = 48 + pbSrc[i] / 16;
        ddl = 48 + pbSrc[i] % 16;
        if (ddh > 57) ddh = ddh + 7;
        if (ddl > 57) ddl = ddl + 7;
        pbDest[i*2] = ddh;
        pbDest[i*2+1] = ddl;
    }

    pbDest[nLen*2] = '\0';
}

//将dex文件转为txt文件
void transDexToTxt(){
    FILE * outfile, *infile;
    outfile = fopen("./JRWrap.txt", "wb" );
    infile = fopen("./JRWrap.dex", "rb");

    int MAXLEN = 1024;
    char buf[MAXLEN];

    if( outfile == NULL || infile == NULL ){
        printf("no file");
        return;
    }

    int rc;
    int length = 0;
    int writeLength = 0;
    while( (rc = fread(buf,sizeof(unsigned char), MAXLEN,infile)) != 0 ){
        char *pDes = malloc(MAXLEN*2);
        HexToStr(pDes, buf, MAXLEN);
        fwrite( pDes, sizeof( unsigned char ), rc * 2, outfile );
        printf("rc:%d, outlength:%d\n", rc, rc * 2);
        length += rc;
        writeLength += strlen(pDes);
        free(pDes);
        pDes = NULL;
    }
    printf("dex length:%d", length);
    fclose(infile);
    fclose(outfile);
}

int main(){
    transDexToTxt();
	return 0;
}

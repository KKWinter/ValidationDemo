#include <string.h>
#include <stdio.h>
#include <jni.h>
#include <stdbool.h>
#include <stdarg.h>
#include "str_utils.h"

#define IS_BLANK(c) ((c) == ' ' || (c) == '\t')
#define IS_DIGIT(c) ((c) >= '0' && (c) <= '9')
#define IS_ALPHA(c) ( ((c) >= 'a' && (c) <= 'z') || ((c) >= 'A' && (c) <= 'Z') )
#define IS_HEX_DIGIT(c) (((c) >= 'A' && (c) <= 'F') || ((c) >= 'a' && (c) <= 'f'))

/*返回str2第一次出现在str1中的位置(下表索引),不存在返回-1*/
int indexOf(char *str1, char *str2) {
    if(str1 == NULL || str2 == NULL)
        return -1;

	char *p = str1;
	int i = 0;
	p = strstr(str1, str2);
	if (p == NULL)
		return -1;
	else {
		while (str1 != p) {
			str1++;
			i++;
		}
	}
	return i;
}

int str_is_empty(const char* str) {
	if (str == NULL) return 1;
	if (str[0] == '\0') return 1;

	while(*str == ' ' || *str == '\t') str++;
	if (str[0] == '\0') return 1;

	return 0;
}

void int_to_str(int i, char *string)
{
        int power=0,j=0;

        j=i;
        for( power=1;j>10;j/=10)
                power*=10;

        for(;power>0;power/=10)
        {
                *string++='0'+i/power;
                i%=power;
        }
        *string='\0';
}

static size_t strcat2(char **dst_out, ...)
{
    size_t len = 0, len_sub;
    va_list argp;
    char *src;
    char *dst = NULL, *dst_p;

    *dst_out = NULL;

    va_start(argp, dst_out);
    for (;;)
    {
        if ((src = va_arg(argp, char *)) == NULL) break;
        len += strlen(src);
    }
    va_end(argp);

    if (len == 0) return 0;

    dst = (char *)malloc(sizeof(char) * (len + 1));
    if (dst == NULL) return -1;
    dst_p = dst;

    va_start(argp, dst_out);
    for (;;)
    {
        if ((src = va_arg(argp, char *)) == NULL) break;
        len_sub = strlen(src);
        memcpy(dst_p, src, len_sub);
        dst_p += len_sub;
    }
    va_end(argp);
    *dst_p = '\0';

    *dst_out = dst;

    return len;
}

void ins_str(char source_str[], char des_str[], int n) {
	int i, j, k;
	j = 0;
	while (des_str[j])
		j++;
	k = 0;
	while (source_str[k])
		k++;
	/*把source串第index及以后的字符（k-n个）后移j位*/
	/*要注意把source_str末尾的\0也要算在内*/
	for (i = k - n; i >= 0; i--) {
		source_str[i + j + n] = source_str[i + n];
	}
	/*把des_str插入到串source_str中第n个字符之前*/
	for (i = 0; i < j; i++)
		source_str[i + n] = des_str[i];
}

/* Whether string s is a number.
   Returns 0 for non-number, 1 for integer, 2 for hex-integer, 3 for float */
int is_number(const char * s)
{
    int base = 10;
    const char *ptr;
    int type = 0;

    if (s==NULL) return 0;

    ptr = s;

    /* skip blank */
    while (IS_BLANK(*ptr)) {
        ptr++;
    }

    /* skip sign */
    if (*ptr == '-' || *ptr == '+') {
        ptr++;
    }

    /* first char should be digit or dot*/
    if (IS_DIGIT(*ptr) || ptr[0]=='.') {

        if (ptr[0]!='.') {
            /* handle hex numbers */
            if (ptr[0] == '0' && ptr[1] && (ptr[1] == 'x' || ptr[1] == 'X')) {
                type = 2;
                base = 16;
                ptr += 2;
            }

            /* Skip any leading 0s */
            while (*ptr == '0') {
                ptr++;
            }

            /* Skip digit */
            while (IS_DIGIT(*ptr) || (base == 16 && IS_HEX_DIGIT(*ptr))) {
                    ptr++;
            }
        }

        /* Handle dot */
        if (base == 10 && *ptr && ptr[0]=='.') {
            type = 3;
            ptr++;
        }

        /* Skip digit */
        while (type==3 && base == 10 && IS_DIGIT(*ptr)) {
            ptr++;
        }

        /* if end with 0, it is number */
        if (*ptr==0)
            return (type>0) ? type : 1;
        else
            type = 0;
    }
    return type;
}

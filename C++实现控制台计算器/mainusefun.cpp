#include<iostream>
#include<string>
using namespace std;
int othernotion(string exp,char notion[],int lenc)//检查是否存在其他不合法字符,若存在则返回1 
{
	int j;
	int flag = 0;
	int lens = exp.length();
	for(int i = 0;i < lens;i++)
	{
		for(j = 0;j < lenc;j++)
		{if(notion[j] == exp[i])
		break;}
		
		if(j == lenc)
		{	
			flag = 1;
			break;
		}
		else
		;
	
	}
	return flag;
}


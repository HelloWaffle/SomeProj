#include<iostream>
#include<string>
using namespace std;
int othernotion(string exp,char notion[],int lenc)//����Ƿ�����������Ϸ��ַ�,�������򷵻�1 
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


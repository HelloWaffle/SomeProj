#include<iostream>
#include<cmath>
#include<string>
using namespace std;
void opr_brackets(int *s,string exp)//���������Ժ���
{
	int len = exp.length();

	char bracketstack[len] = {'\0'};
	int btop = -1;

	int i = 0;
	while(i < len)
	{
		if( exp[i] == '(' )//��ջ
		bracketstack[++btop] = exp[i++];
		
		else if (exp[i] == ')' )
		{
			if(btop != -1)//���ջ�ǿ�
			{btop--;i++;}
		
			else
			{*s=0;return;break;}
		} 
	} 	
}

int judge(char ch)//�ж��Ƿ�������done
{
	if( (ch >= '0') && (ch <= '9') )
	return 1;
	else
	return 0;
}

int opr_exist(string str,char inspect)//�ú������inspect�����Ƿ�����ڸñ��ʽdone
{
	int flag = 0;
	for(int i = 0;i < str.length();i++)
	if(str[i] == inspect)
	{flag = 1;break;}
	
	return flag;
}

void opr_base_rules(int *s,string exp,char inspect)//�ú������inspect�Ƿ�Ϸ�done
{
	int len = exp.length();
	if(inspect == '+' || inspect == '-' || inspect == '(')
	return;
	
	else if(exp[0] == inspect)//��ͷ������inspect 
	{*s = false;
	return;}
	
	for(int i = 1;i < len;i++)//inspect���߲��ܶ��Ƿ����� 
	{
		if(exp[i] == inspect)
		{
		if( !judge(exp[i-1]) && !judge(exp[i+1]) )
		*s = false;
		return;
		}	
	}
	
}

int level(char con)//�ú�����������������ȼ� 
{
	switch(con){
	//case '=':return 0;break;
	case '+':return 3;break;
	case '-':return 3;break;
	case '*':return 4;break;
	case '/':return 4;break;
	case '^':return 5;break;
	case '%':return 4;break;
	default:return 0;break;
	}
}
double disposal(double o1,double o2,char ch)//�ú���ʵ��2���������ļ���,o2ǰ��o1
{
	
	switch(ch){
		case '+':return o1+o2;break;
		case '-':return o2-o1;break;
		case '*':return o1*o2;break;
		case '/':return o2/o1;break;
		case '%':return (int)o2 % (int)o1;break;
		case '^':return pow(o2,o1);break;
		//default:return;break;
	}
}

double value_of(string exp)//�ú���ʵ�ֽ��ַ���ת��Ϊ����
{ 
	int len = exp.length();
	int k = len;
	
	char numset[10] = {'0','1','2','3','4','5','6','7','8','9'};
	int integer = 0;
	double db = 0.0;
	int i;
	
	for( i = 0; i < len;i++)
	{
		if(exp[i] != '.')
		{
			for(int j = 0; j < 10;j++)
			if(numset[j] == exp[i])
			{
				integer += j * pow(10,k-1);
				k--;
				break;
			}
		}
		else
		break;
	}
	
	integer /= pow(10,len - i);
	
	
	if(exp[i] == '.')
	{
		k = -1;
		for(i += 1 ; i < len; i++)
		for(int j = 0;j < 10; j++)
		if(numset[j] == exp[i])
		{db += j * pow(10,k);
		k--;break;				}
	}
	
	return integer+db;
	
}

void opr_mod(int *s,string exp)//���%ǰ���Ƿ�ΪС�� 
{
	int i = 0;
	
	while(i < exp.length())
	{
		while(exp[++i] != '%' && i < exp.length())//���ҵ��÷�������λ��
		;
	
		if(i == exp.length())
		break;
		
		//���ǰ���Ƿ�ΪС�� 
		int s_index = i;//��Ƿ���λ
		int temp = i-1;
		
		while( (exp[temp] >= '0' && exp[temp] <= '9') || (exp[temp] == '.') )
		temp--;
		
		double value = value_of(exp.substr(temp+1,s_index-temp-1));
		int integer = (int)value;
		
		if(value - integer != 0)
		{*s = 0;
		return;break;}
		else
		;
		
		
		//�������Ƿ�ΪС��
		temp = i+1;
		
		while( (exp[temp] >= '0' && exp[temp] <= '9') || (exp[temp] == '.') )
		temp++;
		
		value = value_of(exp.substr(s_index+1,temp-i-1));
		integer = (int)value;
		
		if(value - integer != 0)
		{*s = 0;
		return;break;}
		else
		;
	} 
}

void opr_no_zero(int *s,string exp,char inspect)//�ú�������inspect��������治��Ϊ0����� done
{
	int i = 0;
	
	while(i < exp.length())
	{
		while(exp[++i] != inspect && i < exp.length())//���ҵ��÷�������λ��
		;
	
		if(i == exp.length())
		break;
		
		//�������Ƿ�Ϊ0
		int s_index = i;//��Ƿ���λ
		int temp = i+1;
		
		while( (exp[temp] >= '0' && exp[temp] <= '9') || (exp[temp] == '.') )
		temp++;
		
		double value = value_of(exp.substr(s_index+1,temp-i-1));
		//int integer = (int)value;
		
		if(value == 0)
		{*s = 0;
		return;break;}
		else
		;
	}
	
}

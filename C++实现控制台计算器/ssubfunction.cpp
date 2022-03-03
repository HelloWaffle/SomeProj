#include<iostream>
#include<cmath>
#include<string>
using namespace std;
void opr_brackets(int *s,string exp)//检查括号配对函数
{
	int len = exp.length();

	char bracketstack[len] = {'\0'};
	int btop = -1;

	int i = 0;
	while(i < len)
	{
		if( exp[i] == '(' )//入栈
		bracketstack[++btop] = exp[i++];
		
		else if (exp[i] == ')' )
		{
			if(btop != -1)//如果栈非空
			{btop--;i++;}
		
			else
			{*s=0;return;break;}
		} 
	} 	
}

int judge(char ch)//判断是否是数字done
{
	if( (ch >= '0') && (ch <= '9') )
	return 1;
	else
	return 0;
}

int opr_exist(string str,char inspect)//该函数检查inspect符号是否存在于该表达式done
{
	int flag = 0;
	for(int i = 0;i < str.length();i++)
	if(str[i] == inspect)
	{flag = 1;break;}
	
	return flag;
}

void opr_base_rules(int *s,string exp,char inspect)//该函数检查inspect是否合法done
{
	int len = exp.length();
	if(inspect == '+' || inspect == '-' || inspect == '(')
	return;
	
	else if(exp[0] == inspect)//开头不能是inspect 
	{*s = false;
	return;}
	
	for(int i = 1;i < len;i++)//inspect两边不能都是非数字 
	{
		if(exp[i] == inspect)
		{
		if( !judge(exp[i-1]) && !judge(exp[i+1]) )
		*s = false;
		return;
		}	
	}
	
}

int level(char con)//该函数返回运算符的优先级 
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
double disposal(double o1,double o2,char ch)//该函数实现2个操作数的计算,o2前于o1
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

double value_of(string exp)//该函数实现将字符串转化为数字
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

void opr_mod(int *s,string exp)//检查%前后是否为小数 
{
	int i = 0;
	
	while(i < exp.length())
	{
		while(exp[++i] != '%' && i < exp.length())//先找到该符号所在位置
		;
	
		if(i == exp.length())
		break;
		
		//检查前面是否为小数 
		int s_index = i;//标记符号位
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
		
		
		//检查后面是否为小数
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

void opr_no_zero(int *s,string exp,char inspect)//该函数处理inspect运算符后面不能为0的情况 done
{
	int i = 0;
	
	while(i < exp.length())
	{
		while(exp[++i] != inspect && i < exp.length())//先找到该符号所在位置
		;
	
		if(i == exp.length())
		break;
		
		//检查后面是否为0
		int s_index = i;//标记符号位
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

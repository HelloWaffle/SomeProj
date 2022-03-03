#include<iostream>
#include<cmath>
#include<string>
#include"ssubfunction.cpp"
using namespace std;

int makeSense(string exp)//判断表达式是否合理
{
	
	int sense = true;
	
	//判断+-*/^()%=的合理性 
	char ope[9] = {'+','-','(',')','*','/','%','^','='};
	int exist[9] = {0};
	
	for(int i = 3;i < 8 && sense != false;i++)
	if(opr_exist(exp,ope[4]))//判断是否存在该符号 
	{opr_base_rules(&sense,exp,ope[i]);
	exist[i] = 1;}
	
	if(sense == 0)
	return 0;
	
	
	if(exist[5])
	{	
		opr_no_zero(&sense,exp,'/');
		if(sense == 0)
		return 0;
	}
	
	if(exist[6])//检查%两边是否为小数
	opr_mod(&sense,exp); 
	
	
	if(exist[2])
	opr_brackets(&sense,exp);//检查括号是否合理
	
	if(exp[0] == '=')
	sense = 0;
	 
	
	return sense;
	
}

void calculate(double *num,int *numtop,char *ope,int *opetop)//从栈顶开始计算，优先级相同的从左到右计算 
{
	if(*opetop == 0)
	{
		;//return;
	}
	
	
	else if(*opetop == 1)
	{
		num[0] = disposal(num[1],num[0],ope[1]);
		ope[1] = '#';
		*opetop-=1;
		*numtop-=1;
		;//return;
	}
	
	
	else//当栈顶指针大于1 
	{	//else块1 
		int opebase = *opetop - 1;
		int numbase = *numtop - 1;
		int count = 0;
		while(level(ope[opebase]) == level(ope[*opetop]))
			{opebase--;numbase--;count++;}//寻找是否有优先级相同的 
		 			
	
		if(count == 0)//不存在优先级相同的时候 
		{
			num[*numtop-1] = disposal(num[*numtop],num[*numtop-1],ope[*opetop]);
			*numtop -= 1;
			*opetop -= 1;
			
			ope[*opetop+1] = '#';
			
			calculate(num,numtop,ope,opetop);
		}
		
		else//存在优先级相同的时候 
		{
			opebase++;
			
			int fabase = numbase + 1;
			while(opebase <= *opetop)
			{
		 				
				num[numbase] = disposal(num[fabase],num[numbase],ope[opebase]);
				
				fabase+=1;
				ope[opebase] = '#';
				opebase++;		
			 }
			 *numtop = numbase;
			 *opetop = *numtop;
			 
			 calculate(num,numtop,ope,opetop);
			 
		}
		
			
	}//else块1 	 			
}

double calculate(string exp)
{
	
		
	int len = exp.length();
	
	
	double num[len] = {0.0};
	int numtop = -1;
	
	char ope[len] = {'#'};
	for(int i = 0;i < len;i++)
	ope[i] = '#';
	int opetop = 0;
	
	//初始化运算符栈与操作数栈
	
	int i;
	for(i = 0;i < len; i+=0)
	{
	 	if( (exp[i] >= '0' && exp[i] <= '9') )//数字入栈
		{
		 string strtemp;
		 	while((exp[i] >= '0' && exp[i] <= '9') || (exp[i] == '.') )
			{strtemp += exp[i];
		 	i++;}
		 	num[++numtop]  = value_of(strtemp);
		 	
		 	continue;
	 	}
		 	
		else//若当前字符为运算符 
		
		{//else块1 
		
		 	if( exp[i] == '(' )//递归处理括号
		 	{
		 		int j = i;
		 		while( exp[++i] !=')')
		 		;
		 		num[++numtop] = calculate(exp.substr(j+1,i-j-1) + '=');
		 		i++;
			 }
		 
	 		else if( level(ope[opetop]) <= level(exp[i]) )//当运算符优先级大于或者等于栈中运算符，入栈 
	 		{ope[++opetop] = exp[i++];
	 		}
		 	else		
		 	{		
		 		if(exp[i] == '=')//当输入等号，代表结束，此时等式中的运算符优先级：栈顶 >= 栈底 
		 		{
		 			calculate(num,&numtop,ope,&opetop);
		 			break;
	 			}
				 
				 else if( level(ope[opetop]) > level(exp[i]) )//当运算符优先级小于栈顶运算符，取2个操作数进行运算，运算符栈栈顶出栈 
	 			{
					double o1 = num[numtop--];
	 				double o2 = num[numtop--];
	 				
	 				num[++numtop] = disposal(o1,o2,ope[opetop]);// 将操作完毕的结果返还给操作数栈
	 				ope[opetop] = '#';
	 				opetop -= 1;
				 }
			 	
		 	}
		 	
		  }//else块1		
	}//for块  	
	return num[0];
}

void untobi(string *exp)//查找并将字符串中的单目运算符转为双目运算符，并返回处理过后的字符串 
{
	if( (*exp)[0] == '-' || (*exp)[0] == '+')
	*exp = '0' + *exp;
	
	int len = exp->length();
	int i = 1;
	while(i < len)
	{
		if( (*exp)[i] == '-' || (*exp)[i] == '+')
		if(!judge( (*exp)[i-1]) && (*exp)[i-1] != ')')
		{string front = exp->substr(0,i);string late = exp -> substr(i,len-1);*exp = front + '0' + late;len++;
		}
		
		
		i++;
	}
	if((*exp)[len-1] != '=')
	*exp = *exp + '=';
	
}

#include<iostream>
#include<cmath>
#include<string>
#include"ssubfunction.cpp"
using namespace std;

int makeSense(string exp)//�жϱ��ʽ�Ƿ����
{
	
	int sense = true;
	
	//�ж�+-*/^()%=�ĺ����� 
	char ope[9] = {'+','-','(',')','*','/','%','^','='};
	int exist[9] = {0};
	
	for(int i = 3;i < 8 && sense != false;i++)
	if(opr_exist(exp,ope[4]))//�ж��Ƿ���ڸ÷��� 
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
	
	if(exist[6])//���%�����Ƿ�ΪС��
	opr_mod(&sense,exp); 
	
	
	if(exist[2])
	opr_brackets(&sense,exp);//��������Ƿ����
	
	if(exp[0] == '=')
	sense = 0;
	 
	
	return sense;
	
}

void calculate(double *num,int *numtop,char *ope,int *opetop)//��ջ����ʼ���㣬���ȼ���ͬ�Ĵ����Ҽ��� 
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
	
	
	else//��ջ��ָ�����1 
	{	//else��1 
		int opebase = *opetop - 1;
		int numbase = *numtop - 1;
		int count = 0;
		while(level(ope[opebase]) == level(ope[*opetop]))
			{opebase--;numbase--;count++;}//Ѱ���Ƿ������ȼ���ͬ�� 
		 			
	
		if(count == 0)//���������ȼ���ͬ��ʱ�� 
		{
			num[*numtop-1] = disposal(num[*numtop],num[*numtop-1],ope[*opetop]);
			*numtop -= 1;
			*opetop -= 1;
			
			ope[*opetop+1] = '#';
			
			calculate(num,numtop,ope,opetop);
		}
		
		else//�������ȼ���ͬ��ʱ�� 
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
		
			
	}//else��1 	 			
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
	
	//��ʼ�������ջ�������ջ
	
	int i;
	for(i = 0;i < len; i+=0)
	{
	 	if( (exp[i] >= '0' && exp[i] <= '9') )//������ջ
		{
		 string strtemp;
		 	while((exp[i] >= '0' && exp[i] <= '9') || (exp[i] == '.') )
			{strtemp += exp[i];
		 	i++;}
		 	num[++numtop]  = value_of(strtemp);
		 	
		 	continue;
	 	}
		 	
		else//����ǰ�ַ�Ϊ����� 
		
		{//else��1 
		
		 	if( exp[i] == '(' )//�ݹ鴦������
		 	{
		 		int j = i;
		 		while( exp[++i] !=')')
		 		;
		 		num[++numtop] = calculate(exp.substr(j+1,i-j-1) + '=');
		 		i++;
			 }
		 
	 		else if( level(ope[opetop]) <= level(exp[i]) )//����������ȼ����ڻ��ߵ���ջ�����������ջ 
	 		{ope[++opetop] = exp[i++];
	 		}
		 	else		
		 	{		
		 		if(exp[i] == '=')//������Ⱥţ������������ʱ��ʽ�е���������ȼ���ջ�� >= ջ�� 
		 		{
		 			calculate(num,&numtop,ope,&opetop);
		 			break;
	 			}
				 
				 else if( level(ope[opetop]) > level(exp[i]) )//����������ȼ�С��ջ���������ȡ2���������������㣬�����ջջ����ջ 
	 			{
					double o1 = num[numtop--];
	 				double o2 = num[numtop--];
	 				
	 				num[++numtop] = disposal(o1,o2,ope[opetop]);// ��������ϵĽ��������������ջ
	 				ope[opetop] = '#';
	 				opetop -= 1;
				 }
			 	
		 	}
		 	
		  }//else��1		
	}//for��  	
	return num[0];
}

void untobi(string *exp)//���Ҳ����ַ����еĵ�Ŀ�����תΪ˫Ŀ������������ش��������ַ��� 
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
